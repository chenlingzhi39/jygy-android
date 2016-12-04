package com.endeavour.jygy.parent.fragment;

import android.content.Context;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.common.view.HtmlView;
import com.endeavour.jygy.parent.activity.FoodHistoryActivity;
import com.endeavour.jygy.parent.activity.FoodOpter;
import com.endeavour.jygy.parent.bean.GetFoodResp;

import java.util.List;

/**
 * Created by wu on 15/12/5.
 */
public class FoodFragment extends BaseViewFragment {

    private HtmlView htmlView;
    private FoodOpter opter;

    public static final String GetFoodResp = "GetFoodResp";

    public FoodFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
            public void onFaild(String msg) {
                progresser.showError(msg, false);
            }
        });
    }


    @Override
    public void initView() {
        setTitleText("健康食谱");
        showTitleBack();
        setTitleRight("更多");
        setMainView(R.layout.activity_food);
        htmlView = (HtmlView) mainView.findViewById(R.id.htmlView);
        this.opter = new FoodOpter();
        doGetCurrentFoodAction();
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        Tools.toActivity(getActivity(), FoodHistoryActivity.class);
    }
}
