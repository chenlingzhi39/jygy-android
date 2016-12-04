package com.endeavour.jygy.schoolboss.activity;

import android.os.Bundle;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.teacher.fragment.TeacherTaskFragment;

/**
 * Created by wu on 16/1/7.
 */
public class SchoolBossTaskActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        replaceFragment();

    }

    private void replaceFragment() {
        TeacherTaskFragment teacherTaskFragment = new TeacherTaskFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, teacherTaskFragment).commit();
    }
}
