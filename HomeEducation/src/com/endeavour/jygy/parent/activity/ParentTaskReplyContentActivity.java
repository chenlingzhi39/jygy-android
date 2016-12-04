package com.endeavour.jygy.parent.activity;

import android.os.Bundle;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.view.HtmlView;

/**
 * Created by wu on 15/12/5.
 */
public class ParentTaskReplyContentActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("反馈详情");
        showTitleBack();
        setMainView(R.layout.activity_parent_class_detail);
        HtmlView htmlView = (HtmlView) findViewById(R.id.htmlView);
        htmlView.setText(getIntent().getStringExtra("content"));
    }

}
