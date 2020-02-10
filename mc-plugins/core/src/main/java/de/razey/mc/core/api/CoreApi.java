package de.razey.mc.core.api;

import com.google.gson.internal.bind.SqlDateTypeAdapter;
import de.razey.mc.core.Main;
import de.razey.mc.core.sql.CoreSql;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CoreApi {

    private static CoreApi _instance;

    private CoreSql sql;

    private Main main;

    public CoreApi(Main main) {
        this.main = main;

        _instance = this;
        FileConfiguration config = main.getConfig();
        if (sql == null) {
            sql = new CoreSql(
                    config.getString("database.host"),
                    config.getString("database.username"),
                    config.getString("database.password"),
                    config.getString("database.database"),
                    config.getInt("database.port"));
            sql.connect();
        }
    }

    public CoreSql getSql() {
        return sql;
    }

    public void end() {
        sql.disconnect();
    }

    public static CoreApi getInstance() {
        return _instance;
    }

    public void updateUsername(String uuid, String username) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("UPDATE users SET username=? WHERE uuid='" + uuid + "'");
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateLastOnlineTime(String uuid) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("UPDATE users SET last_online=? WHERE uuid='" + uuid + "'");
            ps.setLong(1, System.currentTimeMillis());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void playerFirstJoin(Player player) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO `users`(`uuid`, `username`, `first_online`, `last_online`) VALUES (?, ?, ?, ?)");

            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, player.getName());
            ps.setLong(3, System.currentTimeMillis());
            ps.setLong(4, System.currentTimeMillis());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public long getLastOnlineFromPlayerId(int id) {
        try {
            ResultSet result = sql.resultStatement("SELECT last_online FROM users WHERE id=" + id);
            if (!result.next())
                return System.currentTimeMillis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    public long getLastOnlineFromPlayerUuid(String uuid) {
        try {
            ResultSet result = sql.resultStatement("SELECT last_online FROM users WHERE uuid=" + uuid);
            if (!result.next())
                return System.currentTimeMillis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    public long getLastOnlineFromPlayerName(String name) {
        try {
            ResultSet result = sql.resultStatement("SELECT last_online FROM users WHERE LOWER(username)=LOWER(" + name + ")");
            if (!result.next())
                return System.currentTimeMillis();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    public String getPlayerNameFromId(int id) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT username FROM users WHERE id=?");

            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();
            if (!result.next())
                return null;
            return result.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPlayerNameFromUuid(String uuid) {
        return getUsername(uuid);
    }

    public String getUsername(String uuid) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT username FROM users WHERE uuid=?");

            ps.setString(1, uuid);

            ResultSet result = ps.executeQuery();
            if (!result.next())
                return null;
            return result.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getPlayerId(String uuid) {
        try {
            PreparedStatement userIdStatement = sql.getConnection().prepareStatement("SELECT id FROM users WHERE uuid=?");

            userIdStatement.setString(1, uuid);

            ResultSet userIdResult = userIdStatement.executeQuery();
            if (!userIdResult.next())
                return -1;
            return userIdResult.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPlayerIdFromName(String name) {
        try {
            PreparedStatement userIdStatement = sql.getConnection().prepareStatement("SELECT id FROM users WHERE LOWER(username)=LOWER(?)");

            userIdStatement.setString(1, name);

            ResultSet userIdResult = userIdStatement.executeQuery();
            if (!userIdResult.next())
                return -1;
            return userIdResult.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getPlayerIdFromUuid(String uuid) {
        return getPlayerId(uuid);
    }

    public int getIdOfRankByName(String rankName) {
        try {
            ResultSet rankIdQuery = CoreApi.getInstance().getSql().resultStatement("SELECT id FROM ranks WHERE name=" + rankName);
            if (!rankIdQuery.next())
                return -1;
            return rankIdQuery.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public float getDefaultSkyblockBalance() {
        return 2000.0f;
    }

    private void enterPlayerToSkyblockStats(int playerId) {
        try {
            ResultSet result = CoreApi.getInstance().getSql().resultStatement("SELECT balance FROM skyblock_stats WHERE player=" + playerId);
            if (!result.next())
                CoreApi.getInstance().getSql().updateStatement("INSERT INTO `skyblock_stats`(`player`, `balance`, `level`, `xp`) VALUES (" + playerId + ", " + getDefaultSkyblockBalance() + ", 1, 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getSkyblockBalanceFromPlayerId(int playerId) {
        enterPlayerToSkyblockStats(playerId);
        try {
            ResultSet result = CoreApi.getInstance().getSql().resultStatement("SELECT balance FROM skyblock_stats WHERE player=" + playerId);
            if (!result.next())
                return -1;
            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void modifySkyblockBalanceFromPlayerId(int playerId, float addition) {
        enterPlayerToSkyblockStats(playerId);
        try {
            CoreApi.getInstance().getSql().updateStatement(
                    "UPDATE UPDATE `skyblock_stats` SET `balance`=`balance`+" + addition + " WHERE player="+ playerId
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSkyblockBalanceFromPlayerId(int playerId, float value) {
        enterPlayerToSkyblockStats(playerId);
        try {
            CoreApi.getInstance().getSql().updateStatement("UPDATE UPDATE `skyblock_stats` SET `balance`=" + value + " WHERE player="+ playerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCorrectPlayerName(String wrongName) {
        return getPlayerNameFromId(getPlayerIdFromName(wrongName));
    }

    public int getPowerOfRank(int rankId) {
        try {
            ResultSet rankPowerQuery = CoreApi.getInstance().getSql().resultStatement("SELECT power FROM ranks WHERE id=" + rankId);
            if (!rankPowerQuery.next())
                return -1;
            return rankPowerQuery.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Integer> getAllRankIdsOfPlayer(int playerId) {
        List<Integer> allRanks = new ArrayList<>();
        try {
            ResultSet allRanksQuery = CoreApi.getInstance().getSql().resultStatement("SELECT id FROM player_ranks WHERE player=" + playerId);
            while (allRanksQuery.next())
                allRanks.add(allRanksQuery.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRanks;
    }

    public int getDefaultRankId() {
        try {
            ResultSet defaultRankQuery = CoreApi.getInstance().getSql().resultStatement("SELECT id FROM ranks WHERE power=0");

            defaultRankQuery.next();

            return defaultRankQuery.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getHighestRankIdOfPlayer(int playerId) {
        List<Integer> allRanks = getAllRankIdsOfPlayer(playerId);

        if (allRanks.size() == 0)
            return getDefaultRankId();

        int highest = allRanks.get(0);
        int highest_power = getPowerOfRank(highest);

        for (int rank : allRanks) {
            int power = getPowerOfRank(rank);
                if (power > highest_power) {
                    highest_power = power;
                    highest = rank;
                }
        }

        return highest;
    }

    public String getUuidFromPlayerId(int id) {
        return getUuidOfPlayerId(id);
    }

    public String getUuidOfPlayerId(int id) {
        try {
            PreparedStatement userIdStatement = sql.getConnection().prepareStatement("SELECT uuid FROM users WHERE id=?");

            userIdStatement.setInt(1, id);

            ResultSet userIdResult = userIdStatement.executeQuery();
            if (!userIdResult.next())
                return null;
            return userIdResult.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getPlayerPermissions(String uuid) {
        List<String> permissions = new ArrayList<>();

        int playerId;

        try {
            PreparedStatement userIdStatement = sql.getConnection().prepareStatement("SELECT id FROM users WHERE uuid=?");

            userIdStatement.setString(1, uuid);

            ResultSet userIdResult = userIdStatement.executeQuery();
            if (!userIdResult.next())
                return null;
            playerId = userIdResult.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            PreparedStatement playerPermissionsStatement = sql.getConnection().prepareStatement("SELECT permission FROM player_permissions WHERE player=?");

            playerPermissionsStatement.setInt(1, playerId);

            ResultSet playerPermissionsResult = playerPermissionsStatement.executeQuery();

            while (playerPermissionsResult.next()) {
                if (!permissions.contains(playerPermissionsResult.getString(1)))
                    permissions.add(playerPermissionsResult.getString(1));
            }

            PreparedStatement userRanksIdStatement = sql.getConnection().prepareStatement("SELECT rank FROM player_ranks WHERE player=?");

            userRanksIdStatement.setInt(1, playerId);

            ResultSet userRanksIdResult = userRanksIdStatement.executeQuery();

            while (userRanksIdResult.next()) {
                PreparedStatement rankPermissionsStatement = sql.getConnection().prepareStatement("SELECT permission FROM rank_permissions WHERE rank=?");
                rankPermissionsStatement.setInt(1, userRanksIdResult.getInt(1));
                ResultSet rankPermissionsResult = rankPermissionsStatement.executeQuery();
                while (rankPermissionsResult.next()) {
                    if (!permissions.contains(rankPermissionsResult.getString(1)))
                        permissions.add(rankPermissionsResult.getString(1));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permissions;
    }

    private String getPluginPrefix(String plugin) {
        try {
            if (plugin == null)
                return "";
            PreparedStatement pluginPrefixStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                    "SELECT prefix FROM plugin_prefix WHERE plugin=?"
            );

            pluginPrefixStatement.setString(1, plugin);

            ResultSet pluginPrefixQuery = pluginPrefixStatement.executeQuery();

            if (pluginPrefixQuery.next()) {
                return pluginPrefixQuery.getString(1);
            } else {
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getMessage(String message, String language) {
        try {
            PreparedStatement messageStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                    "SELECT message FROM messages WHERE id=? AND language=?"
            );

            messageStatement.setString(1, message);
            messageStatement.setString(2, language);

            ResultSet messageResult = messageStatement.executeQuery();

            if (!messageResult.next())
                return null;

            return messageResult.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDefaultLanguage() {
        return "de";
    }

    public String getPreferredUserLanguageFromUuid(String uuid) {
        try {
            PreparedStatement userLanguageStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                    "SELECT language FROM users WHERE uuid=?"
            );
            userLanguageStatement.setString(1, uuid);

            ResultSet userLanguageQuery = userLanguageStatement.executeQuery();

            if (userLanguageQuery.next())
                return userLanguageQuery.getString(1);

            return getDefaultLanguage();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getDefaultLanguage();
    }

    public int getSkyblockLevelFromPlayerId(int playerId) {
        enterPlayerToSkyblockStats(playerId);
        try {
            ResultSet result = sql.resultStatement("SELECT level FROM skyblock_stats WHERE player=" + playerId);

            if (!result.next())
                return 1;

            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int getSkyblockXpFromPlayerId(int playerId) {
        enterPlayerToSkyblockStats(playerId);
        try {
            ResultSet result = sql.resultStatement("SELECT xp FROM skyblock_stats WHERE player=" + playerId);

            if (!result.next())
                return 0;

            return result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSkyblockXpNeededForLevelUp(int currentLevel) {
        return 100 * 2 ^ currentLevel;
    }

    public void setSkyblockXpForPlayerId(int playerId, int xp) {
        enterPlayerToSkyblockStats(playerId);
        try {
            CoreApi.getInstance().getSql().updateStatement("UPDATE skyblock_stats SET xp=" + xp + " WHERE player=" + playerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSkyblockLevelForPlayerId(int playerId, int level) {
        enterPlayerToSkyblockStats(playerId);
        try {
            CoreApi.getInstance().getSql().updateStatement("UPDATE skyblock_stats SET level=" + level + " WHERE player=" + playerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifySkyblockLevelForPlayerId(int playerId, int levelAmount) {
        enterPlayerToSkyblockStats(playerId);
        try {
            CoreApi.getInstance().getSql().updateStatement("UPDATE skyblock_stats SET level=level+" + levelAmount + " WHERE player=" + playerId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gives a Player xp
     *
     * @param amount The amount of XP to add.
     * @param checkLevel Wenn this is true, the level will be returned, and a level-Up is checked and executed if needed.
     * @return Returns the level of the player, when he got a Level-Up, otherwise always -1
     */
    public int giveSkyblockXpToPlayerId(int amount, int playerId, boolean checkLevel) {
        enterPlayerToSkyblockStats(playerId);
        try {
            CoreApi.getInstance().getSql().updateStatement("UPDATE skyblock_stats SET xp=xp+" + amount + " WHERE player=" + playerId);
            if (checkLevel) {
                int currentLevel = getSkyblockLevelFromPlayerId(playerId);
                int neededForNextLevel = getSkyblockXpNeededForLevelUp(currentLevel);
                int currentXp = getSkyblockXpFromPlayerId(playerId);
                if (neededForNextLevel <= currentXp) {
                    setSkyblockXpForPlayerId(playerId, currentXp - neededForNextLevel);
                    modifySkyblockLevelForPlayerId(playerId, 1);
                    return currentLevel + 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void displayMessage(Player p, String message, @Nullable String plugin, String... args) {
        String prefix = getPluginPrefix(plugin);

        String preferredLanguage = getPreferredUserLanguageFromUuid(p.getUniqueId().toString());

        String printMessage = message;

        String preferredLanguageMessage = getMessage(message, preferredLanguage);
        if (preferredLanguageMessage != null) {
            printMessage = preferredLanguageMessage;
        } else {
            String defaultLanguageMessage = getMessage(message, getDefaultLanguage());
            if (defaultLanguageMessage != null)
                printMessage = defaultLanguageMessage;
        }

        for (int i = 0; i < args.length; i++)
            printMessage = printMessage.replaceAll("%" + (i + 1) + "%", args[i]);

        p.sendMessage(prefix + printMessage);
    }

    public void broadcastMessage(String message, @Nullable String plugin, String... args) {
        for (Player p : Bukkit.getOnlinePlayers())
            displayMessage(p, message, plugin, args);
    }

    public void broadcastMessageToPermissionGroup(String message, @Nullable String plugin, String permission, String... args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(permission))
                displayMessage(p, message, plugin, args);
        }
    }

    public String getUuidFromPlayer(Player p) {
        return p.getUniqueId().toString();
    }

    public UUID getUuidObjectFromUuid(String uuid) {
        return UUID.fromString(uuid);
    }
}