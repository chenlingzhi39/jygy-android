package com.endeavour.jygy.common.crash;

import android.content.Context;

import com.endeavour.jygy.BuildConfig;

/**
 * Created by wu on 15/11/3.
 */
public class CrashUtil {

	private final static String BUGLY_ID = "900011872";

	public static void init(Context context) {
		CustomActivityOnCrash.install(context);
		CustomActivityOnCrash.setShowErrorDetails(BuildConfig.DEBUG);// 显示错误详情,
																		// 便于调试
	}
}
