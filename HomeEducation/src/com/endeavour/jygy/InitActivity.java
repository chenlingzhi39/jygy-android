package com.endeavour.jygy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.debug.DebugActivity;
import com.endeavour.jygy.login.activity.StartupActivity;
import com.wizarpos.log.util.LogEx;

public class InitActivity extends BaseViewActivity {

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogEx.d(TAG, "onCreate");
		setMainView(R.layout.activity_init);
		setTitleText("首页");
		findViewById(R.id.btnDebug).setOnClickListener(this);

		// 友推分享平台初始化
//		YtTemplate.init(this);

		handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Tools.toActivity(InitActivity.this, StartupActivity.class);
				InitActivity.this.finish();
			}
		}, 2 * 1000);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btnDebug:
			Intent intent = new Intent(this, DebugActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
