package com.endeavour.jygy.common.db;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.util.Map;

public class DefaultAppConfigHelper {

    public static Map<String, String> cacheMap;

    public static String getConfig(String configName) {
        String _value = getFormCache(configName);
        if (!TextUtils.isEmpty(_value)) {
            return _value;
        } // 先从缓存中获取数据,如果缓存中有相应数据 ,则直接返回
        try { // 缓存中没有相应数据,则从数据库中获取
            AppConfig config = DefaultDbHelper.getInstance().getDbController().findFirst(
                    Selector.from(AppConfig.class).where("config_name", "=", configName));
            if (config == null) {
                return "";
            }// 数据库也没有相应数据,返回"";
            _value = config.getConfig_value();
            if (TextUtils.isEmpty(_value) == false) {
                updataCache(configName, _value);// 数据库中有相应数据,更新缓存中的数据并返回
                return _value;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getConfig(String configName, String defaultValue) {
        String value = getConfig(configName);
        return TextUtils.isEmpty(value) ? defaultValue : value;
    }

    public static void setConfig(String configName, String value) {
        try {
            /* 更新数据中的数据 */
            AppConfig appConfig = new AppConfig(configName, value);
            AppConfig _config = DefaultDbHelper.getInstance().getDbController().findFirst(
                Selector.from(AppConfig.class).where("config_name", "=", configName));
            if (_config == null) {
                DefaultDbHelper.getInstance().getDbController().save(appConfig);
            } else {
                DefaultDbHelper.getInstance().getDbController().update(appConfig,
                        WhereBuilder.b("config_name", "=", configName), "config_value");
            }
            /* 更新缓存中的数据 */
            updataCache(configName, value);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void setCacheMap(Map<String, String> cacheMap) {
        DefaultAppConfigHelper.cacheMap = cacheMap;
    }

    private static String getFormCache(String configName) {
        if (cacheMap == null) {
            return null;
        }
        return cacheMap.get(configName);
    }

    private static void updataCache(String configName, String value) {
        if (cacheMap == null) {
            return;
        }
        cacheMap.put(configName, value);
    }

    public static <T> T convert2Bean(String beanStr, Class<T> calss) {
        return JSONObject.parseObject(beanStr, calss);
    }
}
