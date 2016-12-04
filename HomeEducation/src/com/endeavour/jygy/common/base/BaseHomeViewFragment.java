package com.endeavour.jygy.common.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.ui.ErrorView;
import com.endeavour.jygy.common.ui.ProgressLayout;

public abstract class BaseHomeViewFragment extends Fragment implements
		ErrorView.OnRetryListener, OnClickListener {

	protected Toolbar toolbar;
	private TextView tvTitle;
	private TextView tvRight;
	private RelativeLayout rlMain;
	protected ProgressLayout progresser;
	protected View rlTopRight;
	protected Spinner spToolbarTitle;

	protected View mainView;

	public BaseHomeViewFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_base_home_view, container,
				false);
		initToolbar(rootView);
		rlMain = (RelativeLayout) rootView.findViewById(R.id.rlMain);
		progresser = (ProgressLayout) rootView.findViewById(R.id.progress);
		spToolbarTitle = (Spinner) rootView.findViewById(R.id.spToolbarTitle);
		progresser.setRetryListener(this);
		initView();
		return rootView;
	}

	public abstract void initView();

	protected void setMainView(int layoutId) {
		mainView = LayoutInflater.from(getActivity()).inflate(layoutId,
				null);
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
	protected void initToolbar(View view) {
		toolbar = (Toolbar) view.findViewById(R.id.toolbar);
		tvTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);
		tvRight = (TextView) toolbar.findViewById(R.id.tvToolbarRight);
		rlTopRight = toolbar.findViewById(R.id.rlTopRight);
		((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
	}

	/**
	 * 显示标题栏左侧返回按钮
	 */
	protected void showTitleBack() {
		((AppCompatActivity) getActivity()).getSupportActionBar()
				.setHomeButtonEnabled(true);
		((AppCompatActivity) getActivity()).getSupportActionBar()
				.setDisplayHomeAsUpEnabled(true);
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

	/**
	 * 设置标题栏文字
	 * 
	 * @param title
	 */
	protected void setTitleText(CharSequence title) {
		tvTitle.setText(title);
		showToolbar();
	}

	public void showToolbar() {
		if (toolbar.getVisibility() != View.VISIBLE) {
			toolbar.setVisibility(View.VISIBLE);
		}
		((AppCompatActivity) getActivity()).getSupportActionBar()
				.setDisplayShowTitleEnabled(false);
	}

	/**
	 * 设置标题栏右侧文字
	 * 
	 * @param title
	 */
	protected void setTitleRight(int title) {
		rlTopRight.setVisibility(View.VISIBLE);
		tvRight.setText(title);
		tvRight.setOnClickListener(this);
	}

	/**
	 * 设置标题栏右侧文字并添加监听
	 * 
	 * @param title
	 */
	protected void setTitleRight(CharSequence title) {
		rlTopRight.setVisibility(View.VISIBLE);
		tvRight.setText(title);
		tvRight.setOnClickListener(this);
	}

	/**
	 * 设置标题栏右侧文字并添加监听
	 * 
	 * @param title
	 */
	protected void setTitleRightImage(int title) {
		rlTopRight.setVisibility(View.VISIBLE);
		tvRight.setBackgroundResource(title);
		tvRight.setOnClickListener(this);
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
	}

	/**
	 * 标题栏右侧文字被点击
	 */
	protected void onTitleRightClicked() {
	}

	@Override
	public void onRetry() {

	}

	public void refresh() {

	}
}
