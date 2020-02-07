package de.razey.mc.core.sql;

import java.sql.*;

public class CoreSql {

    private String host, username, password, database;
    private int port;

    private Connection connection;

    public CoreSql(String host, String username, String password, String database, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;
    }

    public Connection getConnection() {
        return connection;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        } catch (SQLException e) {
            System.out.print("[ServerCore] Could not connect to database.");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet resultStatement(String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        return ps.executeQuery();
    }

    public void updateStatement(String query) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        ps.executeUpdate();
    }

}
