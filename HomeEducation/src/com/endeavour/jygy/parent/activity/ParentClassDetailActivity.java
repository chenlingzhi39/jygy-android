package com.endeavour.jygy.parent.activity;

import android.os.Bundle;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.view.HtmlView;
import com.endeavour.jygy.parent.bean.GetParentLessonPracticeDet;

/**
 * Created by wu on 15/12/5.
 */
public class ParentClassDetailActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("共育课堂");
        showTitleBack();
        setMainView(R.layout.activity_parent_class_detail);
        HtmlView htmlView = (HtmlView) findViewById(R.id.htmlView);
        GetParentLessonPracticeDet det = (GetParentLessonPracticeDet) getIntent().getSerializableExtra("det");
        htmlView.render(det.getPracticeContent());
    }

}
