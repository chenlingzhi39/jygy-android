package com.endeavour.jygy.common.xg.service;

import android.app.IntentService;
import android.content.Intent;

import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.parent.activity.DynamicActivity;

public class MessService extends IntentService {
	public static final String SERVICE_NAME = "startactivity";
	public MessService() {
		super(SERVICE_NAME);
	}

	public interface PrintListener {
		void onFinish();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			String activityname = intent.getStringExtra("activityname");
			Tools.toActivity(getApplicationContext(), DynamicActivity.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
