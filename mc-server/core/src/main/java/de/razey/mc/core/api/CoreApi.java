package de.razey.mc.core.api;

import de.razey.mc.core.Main;
import de.razey.mc.core.sql.CoreSql;
import org.bukkit.configuration.file.FileConfiguration;

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

    public void end() {
        sql.disconnect();
    }

    public static CoreApi getInstance() {
        return _instance;
    }

}
