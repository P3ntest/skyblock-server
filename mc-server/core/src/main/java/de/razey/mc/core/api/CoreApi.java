package de.razey.mc.core.api;

import de.razey.mc.core.Main;
import de.razey.mc.core.sql.CoreSql;

public class CoreApi {

    private static CoreApi _instance;

    private CoreSql sql;

    public CoreApi(Main main) {
        _instance = this;
        if (sql == null) {
            sql = new CoreSql("", "", "", "", 0);
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
