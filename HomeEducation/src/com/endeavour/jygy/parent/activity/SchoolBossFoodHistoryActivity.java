package com.endeavour.jygy.parent.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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
public class SchoolBossFoodHistoryActivity extends BaseViewActivity implements SwipyRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    public static final String REFRESH = "com.endeavour.jygy.parent.activity.SchoolBossFoodHistoryActivity";
    private ListView lvFoods;
    private FoodAdpater adpater;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private FoodOpter foodOpter;

    private BroadcastReceiver foodRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("健康食谱");
        showTitleBack();
        setTitleRight("新增");
        setMainView(R.layout.activity_food_history);
        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        lvFoods = (ListView) findViewById(R.id.lvFoods);
        adpater = new FoodAdpater(this);
        lvFoods.setAdapter(adpater);
        lvFoods.setOnItemClickListener(this);
        foodOpter = new FoodOpter();
        refresh();

        LocalBroadcastManager.getInstance(this).registerReceiver(foodRefreshReceiver, new IntentFilter(REFRESH));
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
                Tools.toastMsg(SchoolBossFoodHistoryActivity.this, msg);
            }
        });
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        startActivity(EditFoodActivity.getStartIntent(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (foodRefreshReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(foodRefreshReceiver);
        }
    }
}

