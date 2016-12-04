package com.endeavour.jygy.common.db;

import android.content.Context;

import com.endeavour.jygy.app.MainApp;
import com.lidroid.xutils.DbUtils;

import java.util.HashMap;

/**
 * Created by wu on 15/11/3.
 */
public class DefaultDbHelper {
    private static DefaultDbHelper dbHelper = new DefaultDbHelper();

    public static DefaultDbHelper getInstance() {
        return dbHelper;
    }

    private DefaultDbHelper() {
    }

    private final static int CACHE_MAX_SIZE = 30;
    private DbUtils dbController;

    public DbUtils getDbController() {
        if(dbController == null){
            init(MainApp.getInstance());
        }
        return dbController;
    }

    public DbUtils getDbController_Mess(Context context, String name) {
        if (dbController == null) {
            dbController = DbUtils.create(context, name);
            dbController.configDebug(true);
        }
        return dbController;
    }

    public void init(Context context) {
        this.init(context, "common.db");
    }

    public void init(Context context, String name) {
        dbController = DbUtils.create(context, name);
        dbController.configDebug(true);

        HashMap<String, String> appConfig = new HashMap<String, String>(
                CACHE_MAX_SIZE);
        AppConfigHelper.setCacheMap(appConfig);
    }

}
