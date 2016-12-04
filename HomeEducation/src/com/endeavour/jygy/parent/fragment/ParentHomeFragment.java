package com.endeavour.jygy.parent.fragment;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseDrawerLayoutFragment;
import com.endeavour.jygy.common.view.CirclePageIndicator;
import com.endeavour.jygy.parent.activity.NoticeListActivity;
import com.endeavour.jygy.parent.adapter.DemoPagerAdapter;

public class ParentHomeFragment extends BaseDrawerLayoutFragment {
	private boolean isAuto = true;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;

	protected String[] leftMeans;

	public ParentHomeFragment() {
		// Required empty public constructor
	}

	@Override
	protected View initMenu() {
		leftMeans = new String[] { "共育课堂", "健康食谱", "我要请假", "系统设置", "退出登录" };
		View menuView = LayoutInflater.from(getActivity()).inflate(
				R.layout.container_left_menus, null);
		ListView lvLeftMenu = (ListView) menuView
				.findViewById(R.id.lv_left_menu);
		ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_1, leftMeans);
		lvLeftMenu.setAdapter(arrayAdapter);
		return menuView;
	}

	@Override
	protected View initContainer() {
		View container = LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_parent_home, null);
		viewPager = (ViewPager) container.findViewById(R.id.viewpager_default);
		CirclePageIndicator indicator = (CirclePageIndicator) container
				.findViewById(R.id.indicator);
		pagerAdapter = new DemoPagerAdapter(getChildFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		indicator.setViewPager(viewPager);
		return container;
	}

	@Override
	public void initView() {
		super.initView();
		setTitleText(getString(R.string.app_name));
		setTitleRightImage(R.drawable.ic_launcher);
	}

	private Handler handler = new Handler();
	private Runnable switchTask = new Runnable() {
		public void run() {
			try {
				if (isAuto) {
					int currentItem = viewPager.getCurrentItem();
					if (currentItem < pagerAdapter.getCount() - 1) {
						currentItem++;
					} else {
						currentItem = 0;
					}
					viewPager.setCurrentItem(currentItem);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.postDelayed(switchTask, 3000);
		}
	};

	@Override
	public void onPause() {
		super.onPause();
		handler.removeCallbacks(switchTask);
	}

	@Override
	public void onResume() {
		super.onResume();
		handler.postDelayed(switchTask, 3000);
	}

	@Override
	protected void onTitleRightClicked() {
		super.onTitleRightClicked();
		Tools.toActivity(getActivity(), NoticeListActivity.class);
	}

}
