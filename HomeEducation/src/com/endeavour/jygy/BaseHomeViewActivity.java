package com.endeavour.jygy;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.endeavour.jygy.common.base.BaseDrawerLayoutActivity;

/**
 * 家长/教师/院长 端首页基类 .处理相同逻辑
 * Created by wu on 15/11/14.
 */
public abstract class BaseHomeViewActivity extends BaseDrawerLayoutActivity {

    protected String[] leftMeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected View initMenu() {
        View menuView = getLayoutInflater().inflate(R.layout.container_left_menus, null);
        ListView lvLeftMenu = (ListView) menuView.findViewById(R.id.lv_left_menu);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, leftMeans);
        lvLeftMenu.setAdapter(arrayAdapter);
        return menuView;
    }

    @Override
    protected View initContainer() {
        View drawerLayout = getLayoutInflater().inflate(R.layout.container_drawerlayout, null);
        return drawerLayout;
    }
}
