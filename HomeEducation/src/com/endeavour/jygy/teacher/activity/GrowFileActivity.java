package com.endeavour.jygy.teacher.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.teacher.dialog.ConfirmDialog;

public class GrowFileActivity extends BaseViewActivity {
	Button bt_grow_do;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainView(R.layout.grow_content);
		setTitleText(R.string.growcontent);
		bt_grow_do = (Button) findViewById(R.id.bt_grow_do);
		bt_grow_do.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final ConfirmDialog confirmDialog = new ConfirmDialog(GrowFileActivity.this,
						"确定要退出吗?", "退出", "取消");
				confirmDialog.show();
				confirmDialog
						.setClicklistener(new ConfirmDialog.ClickListenerInterface() {
							@Override
							public void doConfirm() {

								confirmDialog.dismiss();
								// toUserHome(context);
							}

							@Override
							public void doCancel() {

								confirmDialog.dismiss();
							}
						});
			}
		});
		showTitleBack();
		// setTitleRightImage(R.drawable.title_btn_add); Grow
	}

	// @Override
	// protected void onTitleRightClicked() {
	// super.onTitleRightClicked();
	// Tools.toActivity(this, EditDynamicActivity.class);
	// }
}
