package com.endeavour.jygy.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.ui.swipyrefresh.SwipyRefreshLayout;
import com.endeavour.jygy.common.ui.swipyrefresh.SwipyRefreshLayoutDirection;
import com.endeavour.jygy.parent.adapter.FoodAdpater;
import com.endeavour.jygy.parent.bean.GetFoodResp;

import java.util.List;

/**
 * 健康食堂
 * Created by wu on 15/12/2.
 */
public class FoodHistoryActivity extends BaseViewActivity implements SwipyRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private ListView lvFoods;
    private FoodAdpater adpater;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private FoodOpter foodOpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("健康食谱-更多");
        showTitleBack();
        setMainView(R.layout.activity_food_history);
        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        lvFoods = (ListView) findViewById(R.id.lvFoods);
        adpater = new FoodAdpater(this);
        lvFoods.setAdapter(adpater);
        lvFoods.setOnItemClickListener(this);
        foodOpter = new FoodOpter();
        refresh();
    }

    private void refresh() {
        mSwipyRefreshLayout.setRefreshing(true);
        foodOpter.refresh(new FoodOpter.GetFoodRespListener() {
            @Override
            public void onLoad(List<GetFoodResp> resps) {
                mSwipyRefreshLayout.setRefreshing(false);
                progresser.showContent();
                adpater.setDataChanged(resps);
            }

            @Override
            public void onLoad(GetFoodResp resps) {
                progresser.showContent();
            }

            @Override
            public void onFaild(String msg) {
//                progresser.showContent();
                progresser.showError(msg, false);
                mSwipyRefreshLayout.setRefreshing(false);
//                Tools.toastMsg(FoodHistoryActivity.this, msg);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, FoodActivity.class);
        intent.putExtra(FoodActivity.GetFoodResp, adpater.getItem(position));
        intent.putExtra("showRight", false);
        startActivity(intent);
    }

    private class Text {
        int type;
        String content;
    }


    /**
     * 不允许加载更多
     */
    private void cannotLoadMore() {
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.TOP);
    }

    private void canLoadMore() {
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
    }

    /**
     * 开始加载
     */
    private void startRefresh() {
        Log.d("recordfragment", "开始加载");
        mSwipyRefreshLayout.setRefreshing(true);
    }

    /**
     * 停止加载
     */
    private void stopRefresh() {
        mSwipyRefreshLayout.setRefreshing(false);
        progresser.showContent();
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (SwipyRefreshLayoutDirection.TOP == direction) {
            refresh();
        } else if (SwipyRefreshLayoutDirection.BOTTOM == direction) {
            loadMore();
        }
    }

    private void loadMore() {
        mSwipyRefreshLayout.setRefreshing(true);
        foodOpter.loadMore(new FoodOpter.GetFoodRespListener() {
            @Override
            public void onLoad(List<GetFoodResp> resps) {
                mSwipyRefreshLayout.setRefreshing(false);
                adpater.addDataChanged(resps);
            }

            @Override
            public void onLoad(GetFoodResp resps) {
                progresser.showContent();

            }

            @Override
            public void onFaild(String msg) {
                mSwipyRefreshLayout.setRefreshing(false);
                Tools.toastMsg(FoodHistoryActivity.this, msg);
            }
        });
    }

}
