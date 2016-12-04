package com.endeavour.jygy.parent.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.view.TabPageIndicator;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.endeavour.jygy.parent.bean.GetStudentTasksReq;

import java.util.List;

/**
 * Created by wu on 15/12/25.
 */
public class ParentTaskFragment extends BaseViewFragment {

    private ViewPager pager;
    private TaskListFragment2 taskListFragment;
    private TaskFeedBackFragment taskFeedBackFragment;
    private TaskFragmentAdapter taskFragmentAdapter;
    private TaskBroadCastReceiver receiver;

    public class TaskBroadCastReceiver extends BroadcastReceiver {
        public static final String TASK_BROAD_CAST_RECEIVER_FILTER = "com.endeavour.jygy.parent.fragment.TASK_BROAD_CAST_RECEIVER_FILTER";

        @Override
        public void onReceive(Context context, Intent intent) {
            getTaskList();
        }
    }

    @Override
    public void initView() {
        setMainView(R.layout.activity_task);
        setTitleText("家园共育");
        showTitleBack();
        setTitleRight("刷新");
        taskFragmentAdapter = new TaskFragmentAdapter(getChildFragmentManager());
        taskListFragment = new TaskListFragment2();
        taskFeedBackFragment = new TaskFeedBackFragment();
        pager = (ViewPager) mainView.findViewById(R.id.viewpager);
        pager.setAdapter(taskFragmentAdapter);
        TabPageIndicator indicator = (TabPageIndicator) mainView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        getTaskList();
        receiver = new TaskBroadCastReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(TaskBroadCastReceiver.TASK_BROAD_CAST_RECEIVER_FILTER));
    }


    public void getTaskList() {
        progresser.showProgress();
        GetStudentTasksReq req = new GetStudentTasksReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        req.setPageNo("1");
        req.setPerNo("999");
        req.setSemester(AppConfigHelper.getConfig(AppConfigDef.semester));
        req.setGradeLevel(AppConfigHelper.getConfig(AppConfigDef.gradeLevel));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetStudenTasksResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetStudenTasksResp.class);
                taskListFragment.setDataChanged(resps);
                taskFeedBackFragment.setDataChanged(resps);
            }

            @Override
            public void onFaild(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        getTaskList();
    }

    class TaskFragmentAdapter extends FragmentPagerAdapter {
        public TaskFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? taskListFragment : taskFeedBackFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "课本" : "打分";
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            try {
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
