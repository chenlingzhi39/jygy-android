package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;

/**
 * Created by wu on 16/7/24.
 */
public class HelpActivity extends BaseViewActivity {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, HelpActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("儒灵童好习惯APP使用");
        showTitleBack();
        setMainView(R.layout.activity_help_1);
//        progresser.showError("该功能还在开发中", false);
    }
}
