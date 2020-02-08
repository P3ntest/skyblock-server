package de.razey.mc.core.api;

import de.razey.mc.core.Main;
import de.razey.mc.core.sql.CoreSql;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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

    public String getUsername(String uuid) {
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT username FROM users WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
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
            userIdResult.next();
            return userIdResult.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> getPlayerPermissions(String uuid) {
        List<String> permissions = new ArrayList<>();

        int playerId;

        try {
            PreparedStatement userIdStatement = sql.getConnection().prepareStatement("SELECT id FROM users WHERE uuid=?");
            userIdStatement.setString(1, uuid);
            ResultSet userIdResult = userIdStatement.executeQuery();
            if (!userIdResult.next()) {
                return null;
            }
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
                if (!permissions.contains(playerPermissionsResult.getString(1))) {
                    permissions.add(playerPermissionsResult.getString(1));
                }
            }

            PreparedStatement userRanksIdStatement = sql.getConnection().prepareStatement("SELECT rank FROM player_ranks WHERE player=?");
            userRanksIdStatement.setInt(1, playerId);
            ResultSet userRanksIdResult = userRanksIdStatement.executeQuery();

            while (userRanksIdResult.next()) {
                PreparedStatement rankPermissionsStatement = sql.getConnection().prepareStatement("SELECT permission FROM rank_permissions WHERE rank=?");
                rankPermissionsStatement.setInt(1, userRanksIdResult.getInt(1));
                ResultSet rankPermissionsResult = rankPermissionsStatement.executeQuery();
                while (rankPermissionsResult.next()) {
                    if (!permissions.contains(rankPermissionsResult.getString(1))) {
                        permissions.add(rankPermissionsResult.getString(1));
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permissions;
    }

    private String getPluginPrefix(String plugin) throws SQLException {
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
    }

    public void displayMessage(Player p, String message, @Nullable String plugin, String... args) {
        try {
            String prefix = "";

            if (plugin != null) {
                prefix = getPluginPrefix(plugin);
            }

            String preferredLanguage = "de";

            PreparedStatement userLanguageStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                    "SELECT language FROM users WHERE uuid=?"
            );
            userLanguageStatement.setString(1, p.getUniqueId().toString());

            ResultSet userLanguageQuery = userLanguageStatement.executeQuery();

            if (userLanguageQuery.next()) {
                preferredLanguage = userLanguageQuery.getString(1);
            }

            String printMessage = message;

            PreparedStatement messageStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                    "SELECT message FROM messages WHERE id=? AND language=?"
            );
            messageStatement.setString(1, message);
            messageStatement.setString(2, "de");
            ResultSet messageResult = messageStatement.executeQuery();

            if (messageResult.next()) {
                printMessage = messageResult.getString(1);
            }

            PreparedStatement preferredMessageStatement = CoreApi.getInstance().getSql().getConnection().prepareStatement(
                    "SELECT message FROM messages WHERE id=? AND language=?"
            );
            preferredMessageStatement.setString(1, message);
            preferredMessageStatement.setString(2, preferredLanguage);
            ResultSet preferredMessageResult = preferredMessageStatement.executeQuery();

            if (preferredMessageResult.next()) {
                printMessage = preferredMessageResult.getString(1);
            }

            for (int i = 0; i < args.length; i++) {
                printMessage.replaceAll("%" + (i + 1) + "%", args[i]);
            }

            p.sendMessage(prefix + printMessage);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
