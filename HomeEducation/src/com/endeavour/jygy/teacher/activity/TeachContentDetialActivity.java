package com.endeavour.jygy.teacher.activity;

import android.os.Bundle;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.view.HtmlView;

/**
 * Created by wu on 15/11/26.
 */
public class TeachContentDetialActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_teacher_content_detial);
        setTitleText("详情");
        showTitleBack();
        HtmlView htmlView = (HtmlView) findViewById(R.id.htmlView);
        htmlView.render(getIntent().getStringExtra("html"));
    }
}
