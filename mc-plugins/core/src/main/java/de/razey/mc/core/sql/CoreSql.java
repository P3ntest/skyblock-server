package de.razey.mc.core.sql;

import de.razey.mc.core.Main;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoreSql {

    private String host, username, password, database;
    private int port;

    private List<Connection> connections;

    public CoreSql(String host, String username, String password, String database, int port) {
        connections = new ArrayList<>();
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
    }

    public Connection getConnection() {
        for (Connection connection : connections) {
            if (connection != null) {
                return connection;
            }
        }
        addConnection();
        return getConnection();
    }

    public void startupConnectionPool() {
        for (int i = 0; i < 5; i++) {
            addConnection();
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main._instance, new Runnable() {
            @Override
            public void run() {
                System.out.println("Checking database pool...");
                connections.removeIf(connection -> connection == null);
                if (connections.size() < 5) {
                    System.out.println(5 - connections.size() + " connection(s) failed, starting up....");
                    for (int i = 0; i < 5 - connections.size(); i++) {
                        addConnection();
                    }
                }
                System.out.println("Done.");
            }
        }, 6000, 6000);
    }

    public void addConnection() {
        Connection con;
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            connections.add(con);
        } catch (SQLException e) {
            System.out.print("[ServerCore] Could not connect to database.");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        for (Connection connection : connections) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ResultSet resultStatement(String query) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(query);
        return ps.executeQuery();
    }

    public void updateStatement(String query) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(query);
        ps.executeUpdate();
    }

}
