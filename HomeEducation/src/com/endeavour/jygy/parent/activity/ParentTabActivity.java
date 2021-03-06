package com.endeavour.jygy.parent.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.parent.bean.TabEntity;
import com.endeavour.jygy.parent.fragment.DynamicTabFragment;
import com.endeavour.jygy.parent.fragment.FoodFragment;
import com.endeavour.jygy.parent.fragment.HomeTabFragment;
import com.endeavour.jygy.parent.fragment.ParentTaskFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;


public class ParentTabActivity extends BaseViewActivity implements OnTabSelectListener {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ParentTaskFragment parentTaskFragment;
    private DynamicTabFragment dynamicFragment;
    private HomeTabFragment homeTabFragment;
    private FoodFragment foodFragment;
//    private TeacherGrowupFragment growUpDocFragment;

    private ArrayList<CustomTabEntity> tabs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_base_home);
        isLogin();
        isBabyChoose();
        initTabs();
    }

    private void initTabs() {
        homeTabFragment = new HomeTabFragment();
        parentTaskFragment = new ParentTaskFragment();
        foodFragment = new FoodFragment();
        dynamicFragment = new DynamicTabFragment();
        TabEntity parentTab = new TabEntity("", R.drawable.menu_home_active, R.drawable.menu_home_normal);
        TabEntity taskTab = new TabEntity("", R.drawable.menu_renwu_active, R.drawable.menu_renwu_normal);
        TabEntity dynamicTab = new TabEntity("", R.drawable.menu_dongtai_active, R.drawable.menu_dongtai_normal);
        TabEntity growupTab = new TabEntity("", R.drawable.menu_shipu_active, R.drawable.menu_shipu_normal);
        fragments.add(homeTabFragment);
        tabs.add(parentTab);
        fragments.add(parentTaskFragment);
        tabs.add(taskTab);
        fragments.add(dynamicFragment);
        tabs.add(dynamicTab);
        fragments.add(foodFragment);
        tabs.add(growupTab);
        CommonTabLayout tablayout = (CommonTabLayout) findViewById(R.id.tlBtm);
        tablayout.setTabData(tabs, getSupportFragmentManager(), R.id.llReplace, fragments);
        tablayout.setOnTabSelectListener(this);
        String show = getIntent().getStringExtra("show");
        if ("task".equals(show)) {
            tablayout.setCurrentTab(1);
        } else if("growup".equals(show)){
            tablayout.setCurrentTab(3);
        } else
        	tablayout.setCurrentTab(1);
    }


    @Override
    public void onTabSelect(int position) {
//        Tools.toastMsg(ParentTabActivity.this, "onTabSelect -- position -->" + position);
        handleTarget(position);
    }

    @Override
    public void onTabReselect(int position) {
//        Tools.toastMsg(ParentTabActivity.this, "onTabSelect -- position -->" + position);
        handleTarget(position);
    }

    private void handleTarget(int position) {
        if (position == 0) {
            this.finish();
        } else if (position == 2) {
            Tools.toActivity(ParentTabActivity.this, DynamicActivity.class);
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
