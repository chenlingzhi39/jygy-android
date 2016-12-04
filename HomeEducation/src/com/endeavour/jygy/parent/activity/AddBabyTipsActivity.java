package com.endeavour.jygy.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;

/**
 * Created by wu on 15/11/26.
 */
public class AddBabyTipsActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_add_baby);
        findViewById(R.id.btnAddBaby).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnAddBaby:
                Intent intent = getIntent();
                intent.setClass(AddBabyTipsActivity.this, EditBabyActivity.class);
                startActivityForResult(intent, 1001);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            this.finish();
        }
    }
}
