package com.wizarpos.log.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by wu on 16/9/12.
 */
public class LogEx {
    private static final String LOG_TAG = "LogEx";

    public static void d(String tag, String msg) {
        if(TextUtils.isEmpty(msg)){
            return;
        }
        Log.d(tag, msg);
    }
}
