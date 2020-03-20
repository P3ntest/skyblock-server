package de.razey.mc.core.command.coresubcommands;

import de.razey.mc.core.api.CoreApi;
import org.bukkit.command.CommandSender;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SqlCommand implements CoreSubcommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("/core sql <result/update> <query>");
            return;
        }

        switch (args[1].toLowerCase()) {
            case "result":
            case "q":
            case "query":
                result(sender, args);
                break;
            case "update":
            case "change":
                update(sender, args);
                break;
            default:
                sender.sendMessage("/core sql <result/update> <query>");
                break;
        }
        return;
    }

    private String sqlBuilder(String args[]) {
        String sql = "";
        for (int x = 2; x < args.length; x++) {
            sql += args[x] + " ";
        }
        return sql.trim();
    }

    private void update(CommandSender sender, String[] args) {
        try {
            String query = sqlBuilder(args);
            CoreApi.getInstance().getSql().updateStatement(query);
            sender.sendMessage("Query: " + query);
        } catch (SQLException e) {
            sender.sendMessage(e.getMessage());
        }
    }

    private void result(CommandSender sender, String[] args) {
        try {
            String query = sqlBuilder(args);
            ResultSet set = CoreApi.getInstance().getSql().resultStatement(query);
            ResultSetMetaData rsmd = set.getMetaData();
            sender.sendMessage("Result from: " + query);
            int columnsNumber = rsmd.getColumnCount();
            while (set.next()) {
                String msg = "";
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) msg += (",  ");
                    String columnValue = set.getString(i);
                    msg += (columnValue + " " + rsmd.getColumnName(i));
                }
                sender.sendMessage(msg);
            }
        } catch (SQLException e) {
            sender.sendMessage(e.getMessage());
        }
    }

}

