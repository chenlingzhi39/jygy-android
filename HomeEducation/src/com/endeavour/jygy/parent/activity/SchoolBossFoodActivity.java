package com.endeavour.jygy.parent.activity;

import android.content.Intent;
import android.os.Bundle;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.view.HtmlView;
import com.endeavour.jygy.parent.bean.GetFoodResp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/5.
 */
public class SchoolBossFoodActivity extends BaseViewActivity {

    private HtmlView htmlView;
    private FoodOpter opter;

    public static final String GetFoodResp = "GetFoodResp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("健康食谱");
        showTitleBack();
        if (getIntent().getBooleanExtra("showRight", true)) {
            setTitleRight("更多");
        }
        setMainView(R.layout.activity_food);
        htmlView = (HtmlView) findViewById(R.id.htmlView);
        htmlView.setOnImageClickListener(new HtmlView.OnImageClickListener() {
            @Override
            public void onImageClick(String img) {
                Intent intent = new Intent(SchoolBossFoodActivity.this, ImageBrowserActivity.class);
                int position = 0;
                List<String> list = new ArrayList<String>();
                list.add(img);
                intent.putExtra("paths", (Serializable) list);
                intent.putExtra("position", position);
                SchoolBossFoodActivity.this.startActivity(intent);
            }
        });
        this.opter = new FoodOpter();
        GetFoodResp resp = (com.endeavour.jygy.parent.bean.GetFoodResp) getIntent().getSerializableExtra("GetFoodResp");
        if (resp != null) {
            setTitleText(resp.getTitle());
            htmlView.renderFood(resp.getContent());
        } else {
            doGetCurrentFoodAction();
        }

    }

    private void doGetCurrentFoodAction() {
        progresser.showProgress();
        opter.doGetCurrentFoodAction(new FoodOpter.GetFoodRespListener() {
            @Override
            public void onLoad(List<GetFoodResp> resps) {

            }

            @Override
            public void onLoad(GetFoodResp resps) {
                progresser.showContent();
                setTitleText(resps.getTitle());
                htmlView.renderFood(resps.getContent());
            }

            @Override
            public void onFailed(String msg) {
                progresser.showError(msg, false);
            }
        });
    }


    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        Tools.toActivity(this, FoodHistoryActivity.class);
    }
}
