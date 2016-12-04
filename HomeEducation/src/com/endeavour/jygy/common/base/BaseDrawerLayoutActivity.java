package com.endeavour.jygy.common.base;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.FrameLayout;

import com.endeavour.jygy.R;

/**
 * 侧滑菜单实现
 * Created by wu on 15/11/14.
 */
public abstract class BaseDrawerLayoutActivity extends BaseViewActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private FrameLayout flDrawerLayoutMenus;
    private FrameLayout flDrawerLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTitleText(getApplication().getApplicationInfo().name);
        setMainView(R.layout.activity_drawerlayout);
        flDrawerLayoutMenus = (FrameLayout) findViewById(R.id.flDrawerLayoutMenus);
        flDrawerLayoutMain = (FrameLayout) findViewById(R.id.flDrawerLayoutMain);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        flDrawerLayoutMenus.addView(initMenu());         //左侧菜单
        flDrawerLayoutMain.addView(initContainer());     //主页面
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    /**
     * 初始化菜单
     */
    protected abstract View initMenu();

    /**
     * 初始化主页面
     */
    protected abstract View initContainer();

    /**
     * 打开
     *
     * @param drawerView
     */
    protected void onDrawerOpened(View drawerView) {

    }

    /**
     * 关闭
     *
     * @param drawerView
     */
    protected void onDrawerClosed(View drawerView) {

    }
}
