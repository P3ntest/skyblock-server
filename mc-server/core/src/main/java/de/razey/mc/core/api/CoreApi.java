package de.razey.mc.core.api;

import de.razey.mc.core.Main;
import de.razey.mc.core.sql.CoreSql;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO users ('uuid', 'username', 'first_online') VALUES (?, ?, ?)");
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, player.getName());
            ps.setLong(3, System.currentTimeMillis());
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

    public List<String> getPlayerPermissions(String uuid) {
        List<String> permissions = new ArrayList<>();

        int playerId;

        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT id FROM users WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            playerId = result.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT permission FROM player_permissions WHERE player=?");
            ps.setInt(1, playerId);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                if (!permissions.contains(result.getString(1))) {
                    permissions.add(result.getString(1));
                }
            }

            ps = sql.getConnection().prepareStatement("SELECT rank FROM player_ranks WHERE player=?");
            ps.setInt(1, playerId);
            result = ps.executeQuery();
            while (result.next()) {
                PreparedStatement permps = sql.getConnection().prepareStatement("SELECT permission FROM rank_permissions WHERE rank=?");
                permps.setInt(1, result.getInt(1));
                ResultSet permResult = ps.executeQuery();
                while (permResult.next()) {
                    if (!permissions.contains(permResult.getString(1))) {
                        permissions.add(permResult.getString(1));
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permissions;
    }
}
