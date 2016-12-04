package com.endeavour.jygy.common.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.endeavour.jygy.LoginActivity;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.endeavour.jygy.common.ui.ErrorView.OnRetryListener;
import com.endeavour.jygy.common.ui.ProgressLayout;
import com.flyco.roundview.RoundTextView;
import com.flyco.tablayout.utils.UnreadMsgUtils;

import java.util.List;

public class BaseViewActivity extends BaseActivity implements OnClickListener,
		OnRetryListener, BaseViewStateListener {

	protected Toolbar toolbar;
	private TextView tvTitle;
	protected TextView tvRight;
	protected RoundTextView tvRedDot;
	private RelativeLayout rlMain;
	private View rlTopRight;
	protected ProgressLayout progresser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		initToolbar();
		rlMain = (RelativeLayout) findViewById(R.id.rlMain);
		progresser = (ProgressLayout) findViewById(R.id.progress);
		progresser.setRetryListener(this);
		progresser.setBaseViewStateListener(this);
	}

	protected void setMainView(int layoutId) {
		View mainView = getLayoutInflater().inflate(layoutId, null);
		setMainView(mainView);
	}

	protected void setMainView(View view) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		rlMain.addView(view, params);
		progresser.showContent();
	}

	/**
	 * 初始化状态栏
	 */
	protected void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		tvTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);
		tvRight = (TextView) toolbar.findViewById(R.id.tvToolbarRight);
		tvRedDot = (RoundTextView) toolbar.findViewById(R.id.rtvRedDot);
		rlTopRight = toolbar.findViewById(R.id.rlTopRight);
		setSupportActionBar(toolbar);
	}

	/**
	 * 显示标题栏左侧返回按钮
	 */
	protected void showTitleBack() {
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		showToolbar();
	}

	protected void showRedDot(int count) {
		UnreadMsgUtils.show(tvRedDot, count);
	}

	/**
	 * 设置标题栏文字
	 * 
	 * @param title
	 */
	protected void setTitleText(int title) {
		tvTitle.setText(title);
		showToolbar();
	}

	protected void setTitleRightImage(int title) {
		rlTopRight.setVisibility(View.VISIBLE);
		tvRight.setBackgroundResource(title);
		tvRight.setOnClickListener(this);
	}

	/**
	 * 设置标题栏文字
	 * 
	 * @param title
	 */
	protected void setTitleText(String title) {
		tvTitle.setText(title);
		rlTopRight.setVisibility(View.VISIBLE);
		showToolbar();
	}

	private void showToolbar() {
		if (toolbar.getVisibility() != View.VISIBLE) {
			toolbar.setVisibility(View.VISIBLE);
		}
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}

	public void showTitleRightText() {
		if (tvRight.getVisibility() != View.VISIBLE) {
			tvRight.setVisibility(View.VISIBLE);
		}
		if (rlTopRight.getVisibility() != View.VISIBLE) {
			rlTopRight.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void hideTitleRightText() {

	}

//	public void hideTitleRightText() {
//		rlTopRight.setVisibility(View.GONE);
//		if (tvRight.getVisibility() == View.VISIBLE) {
//			tvRight.setVisibility(View.INVISIBLE);
//		}
//	}

	/**
	 * 设置标题栏右侧文字
	 * 
	 * @param title
	 */
	protected void setTitleRight(int title) {
		tvRight.setText(title);
		tvRight.setOnClickListener(this);
		rlTopRight.setVisibility(View.VISIBLE);
		showToolbar();
	}

	protected void setTitleRightVisible(boolean flag) {
		if (flag) {
			rlTopRight.setVisibility(View.VISIBLE);
			tvRight.setVisibility(View.VISIBLE);
		} else {
			rlTopRight.setVisibility(View.GONE);
			tvRight.setVisibility(View.GONE);
		}
	}

	/**
	 * 设置标题栏右侧文字并添加监听
	 * 
	 * @param title
	 */
	protected void setTitleRight(CharSequence title) {
		rlTopRight.setVisibility(View.VISIBLE);
		if (!TextUtils.isEmpty(title)) {
			tvRight.setText(title);
			tvRight.setOnClickListener(this);
		}
		showToolbar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onTitleBackClikced();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvToolbarRight:
			onTitleRightClicked();
			break;
		default:
			break;
		}
	}

	/**
	 * 点击返回按钮
	 */
	protected void onTitleBackClikced() {
		this.finish();
	}

	/**
	 * 标题栏右侧文字被点击
	 */
	protected void onTitleRightClicked() {
	}

	@Override
	public void onRetry() {

	}

	public void backToHome() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	public void setOnClickListenerById(int viewId, View.OnClickListener listener) {
		View view = findViewById(viewId);
		try {
			if (view != null) {
				view.setOnClickListener(listener);
			}
		} catch (Exception e) {
		}
	}

	public void setOnClickListenerByIds(int[] viewIds,
			View.OnClickListener listener) {
		for (int viewId : viewIds) {
			setOnClickListenerById(viewId, listener);
		}
	}

	public void startNewActivity(Class<?> cls) {
		startActivity(new Intent(this, cls));
		finish();
	}

	protected void hideKeyboard() {
		Tools.hideKeyBorad(this, rlMain);
	}

	// 判断应用是否在运行
	private boolean isClsRunningContains(String MY_PKG_NAME, Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(20);
		for (RunningTaskInfo info : list) {
			// Log.d(TAG, "[MyReceiver]" + info.topActivity.getPackageName() +
			// "|" + info.baseActivity.getPackageName());
			if (info.baseActivity.getClassName().contains(MY_PKG_NAME)) {
				return true;
			}
		}
		return false;
	}

	// 判断应用是否在运行
	private boolean isClsRunningContains_top(String MY_PKG_NAME, Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(20);
		for (RunningTaskInfo info : list) {
			// Log.d(TAG, "[MyReceiver]" + info.topActivity.getPackageName() +
			// "|" + info.baseActivity.getPackageName());
			if (info.topActivity.getClassName().contains(MY_PKG_NAME)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 点消息的时候是否已经选择宝宝
	 */
	public void isBabyChoose() {
		if (Constants.TRUE.equals(DefaultAppConfigHelper
				.getConfig(AppConfigDef.isLogin))) {
			if (DefaultAppConfigHelper.getConfig(AppConfigDef.isbabyUi).equals(
					"0")) {
				Tools.toastMsg(this, "请先选择宝宝并进入！");
				this.finish();
			}
		}
	}

	/**
	 * 判断是否登录
	 */
	public void isLogin() {
		if (!Constants.TRUE.equals(DefaultAppConfigHelper
				.getConfig(AppConfigDef.isLogin))) {
			if (!isClsRunningContains("com.endeavour.jygy.LoginActivity", this)) {
				Tools.toActivity(this, LoginActivity.class);
				this.finish();
			} else {
				Tools.toastMsg(this, "请先登录!");
				this.finish();
			}
		}
		// else
		// return true;
	}
}
