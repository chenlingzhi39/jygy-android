package com.endeavour.jygy.leader.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.login.activity.SignStep2Activity;

public class MyCourtyardActivity extends BaseViewActivity {

	private GridView class_grid;
	private TextView text_title;
	private com.flyco.roundview.RoundTextView lead_main_zxdt;
	public static final String SIGN_FINISH = "com.endeavour.jygy.leader.activity.ClassMainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainView(R.layout.activity_myclass);
		setTitleText("我的院所");
		initView();
		initevent();
		initData();
	}

	private void initView() {
		this.class_grid = (GridView) findViewById(R.id.class_grid);
	}

	private void initevent() {
		class_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				selectedIndex = position;
//				String[ ] selPor = listItems.get(position);
//				if (selPor[0].equals("3")) {
//					addProduct(dbAccess.getProductById(selPor[5]));
//					adapter.notifyDataSetChanged();
//					//newOrderItem(dbAccess.getProductById(selPor[5]));
//				}
			}
		});
		LocalBroadcastManager.getInstance(this).registerReceiver(
				new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						MyCourtyardActivity.this.finish();
					}
				}, new IntentFilter(SIGN_FINISH));
	}

	private void initData() {
		
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.lead_main_zxdt:
			toNext();
			break;
		}
	}

	private void toNext() {
		Tools.toActivity(MyCourtyardActivity.this, SignStep2Activity.class);
	}
}
