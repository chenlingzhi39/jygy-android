package com.endeavour.jygy;

import android.os.Bundle;

import com.endeavour.jygy.common.base.BaseViewActivity;

public class MainActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_main);
        setTitleText("儒灵童好习惯");
    }
}
