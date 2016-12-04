package com.endeavour.jygy.leader.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.ui.titlepop.ActionItem;
import com.endeavour.jygy.common.ui.titlepop.TitlePopup;
import com.endeavour.jygy.login.activity.SignStep2Activity;
import com.flyco.roundview.RoundTextView;

public class LeadMainActivity extends BaseViewActivity {

	private ImageView lead_main_img, title_img;
	private TextView text_title;
	private com.flyco.roundview.RoundTextView lead_main_zxdt, lead_main_xrw,
			lead_main_czda, lead_main_wdys, lead_main_jkst;
	// 定义标题栏弹窗按钮
	private TitlePopup titlePopup;
	public static final String SIGN_FINISH = "com.endeavour.jygy.leader.activity.LeadMainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainView(R.layout.activity_sign);
		setTitleText("儒灵童好习惯");
		initView();
		initevent();
		initData();
	}

	private void initView() {
		this.lead_main_zxdt = (RoundTextView) findViewById(R.id.lead_main_zxdt);
		this.lead_main_xrw = (RoundTextView) findViewById(R.id.lead_main_xrw);
		this.lead_main_czda = (RoundTextView) findViewById(R.id.lead_main_czda);
		this.lead_main_wdys = (RoundTextView) findViewById(R.id.lead_main_wdys);
		this.lead_main_jkst = (RoundTextView) findViewById(R.id.lead_main_jkst);
		this.text_title = (TextView) findViewById(R.id.text_title);
		this.title_img = (ImageView) findViewById(R.id.title_img);
		this.lead_main_img = (ImageView) findViewById(R.id.lead_main_img);
		// 实例化标题栏弹窗
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}

	private void initevent() {
		lead_main_zxdt.setOnClickListener(this);
		lead_main_xrw.setOnClickListener(this);
		lead_main_czda.setOnClickListener(this);
		lead_main_wdys.setOnClickListener(this);
		lead_main_jkst.setOnClickListener(this);
		title_img.setOnClickListener(this);
		LocalBroadcastManager.getInstance(this).registerReceiver(
				new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						LeadMainActivity.this.finish();
					}
				}, new IntentFilter(SIGN_FINISH));
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 给标题栏弹窗添加子类
		titlePopup.addAction(new ActionItem(this, "账户信息",
				R.drawable.mm_title_btn_compose_normal));
		titlePopup.addAction(new ActionItem(this, "关于app",
				R.drawable.mm_title_btn_receiver_normal));
		titlePopup.addAction(new ActionItem(this, "退出登录",
				R.drawable.mm_title_btn_keyboard_normal));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.title_img:
			titlePopup.show(v);
			break;
		case R.id.lead_main_zxdt:
			toNext();
			break;
		case R.id.lead_main_xrw:
			toNext();
			break;
		case R.id.lead_main_czda:
			toNext();
			break;
		case R.id.lead_main_wdys:
			toNext();
			break;
		case R.id.lead_main_jkst:
			toNext();
			break;
		}
	}

	private void toNext() {
		Tools.toActivity(LeadMainActivity.this, SignStep2Activity.class);
	}
}
