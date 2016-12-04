package com.endeavour.jygy.common.db;

import android.content.Context;

import com.endeavour.jygy.app.MainApp;
import com.lidroid.xutils.DbUtils;

import java.util.HashMap;

/**
 * Created by wu on 15/11/3.
 */
public class DbHelper {
    private static DbHelper dbHelper = new DbHelper();

    public static DbHelper getInstance() {
        return dbHelper;
    }

    private DbHelper() {
    }

    private final static int CACHE_MAX_SIZE = 30;
    private DbUtils dbController;

    public DbUtils getDbController() {
        if (dbController == null) {
            init(MainApp.getInstance(), DefaultAppConfigHelper.getConfig(AppConfigDef.dbName_saveLoginID));
        }
        return dbController;
    }

    public void init(Context context, String name) {
        dbController = DbUtils.create(context, name);
        dbController.configDebug(true);

        HashMap<String, String> appConfig = new HashMap<String, String>(
                CACHE_MAX_SIZE);
        AppConfigHelper.setCacheMap(appConfig);
    }

}
