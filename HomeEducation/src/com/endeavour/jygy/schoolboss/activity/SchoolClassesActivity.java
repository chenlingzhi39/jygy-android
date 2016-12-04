package com.endeavour.jygy.schoolboss.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.parent.activity.GradeClassOpter;
import com.endeavour.jygy.parent.adapter.HomeMenuAdapter.OnItemSelectedListener;
import com.endeavour.jygy.schoolboss.adapter.SchoolClassesAdapter;

/**
 * Created by wu on 15/12/24.
 */
public class SchoolClassesActivity extends BaseViewActivity implements OnItemSelectedListener {

    private SchoolClassesAdapter adapter;
    private String classTitle;

    private String gradeId;
    private String gradeNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classTitle = getIntent().getStringExtra("title");
        setTitleText(TextUtils.isEmpty(classTitle) ? "班级信息" : classTitle);
        showTitleBack();
        setMainView(R.layout.activity_my_classes);
        adapter = new SchoolClassesAdapter(this);
        adapter.setOnItemSelectedListener(this);
        GridView gvClasses = (GridView) findViewById(R.id.gvClasses);
        gvClasses.setAdapter(adapter);

        GradeClassOpter opter = new GradeClassOpter();

//        getClassesList();
        gradeId = getIntent().getStringExtra("gradeId");
        gradeNick = getIntent().getStringExtra("gradeNick");

        adapter.setDataChanged(opter.getClasses(gradeId));
    }


    @Override
    public void onItemClicked(int position, View v) {
        AppConfigHelper.setConfig(AppConfigDef.classID, adapter.getItem(position).getId());
        AppConfigHelper.setConfig(AppConfigDef.gradeID, gradeId);
        AppConfigHelper.setConfig(AppConfigDef.gradeNick, gradeNick);
        AppConfigHelper.setConfig(AppConfigDef.classNick, adapter.getItem(position).getNickName());
//        if (TextUtils.isEmpty(classTitle)) {
//            Intent intent = new Intent(this, MyClassGridActivity.class);
//            startActivity(intent);
//        } else {
            setResult(RESULT_OK);
            this.finish();
//        }
    }

}
