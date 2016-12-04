package com.endeavour.jygy.teacher.fragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.view.TabPageIndicator;
import com.endeavour.jygy.schoolboss.activity.SchoolGradesActivity;

/**
 * Created by wu on 15/12/25.
 */
public class TeacherTaskFragment extends BaseViewFragment {

    private static final int REQUEST_CODE = 1001;
    private ViewPager pager;
    private TeacherTaskListFragment teacherListFragment;
    private TeacherTaskFeedBackFragment taskFeedBackFragment;

    @Override
    public void initView() {
        setMainView(R.layout.activity_task);
        setTitleText("小任务");
        showTitleBack();
        TaskFragmentAdapter taskFragmentAdapter = new TaskFragmentAdapter(getChildFragmentManager());
        teacherListFragment = new TeacherTaskListFragment();
        taskFeedBackFragment = new TeacherTaskFeedBackFragment();
        pager = (ViewPager) mainView.findViewById(R.id.viewpager);
        pager.setAdapter(taskFragmentAdapter);
        TabPageIndicator indicator = (TabPageIndicator) mainView.findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        if (MainApp.isShoolboss()) {
            setTitleRight("切换班级");
            if(TextUtils.isEmpty(AppConfigHelper.getConfig(AppConfigDef.classID))){
                progresser.showError("请先选择班级", false);
            }
        }else{
            setTitleRight("刷新");
        }
    }


    class TaskFragmentAdapter extends FragmentPagerAdapter {
        public TaskFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? teacherListFragment : taskFeedBackFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "小任务" : "反馈";
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        if (MainApp.isShoolboss()) {
            Intent intent = new Intent(getActivity(), SchoolGradesActivity.class);
            intent.putExtra("title", "切换班级");
            this.startActivityForResult(intent, REQUEST_CODE);
        }else{
            refreshData();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            refreshData();
        }
    }

    private void refreshData() {
        progresser.showContent();
        if(teacherListFragment != null && teacherListFragment.isVisible()){
            teacherListFragment.getTaskList();
        }
        if(taskFeedBackFragment != null&& taskFeedBackFragment.isVisible()){
            taskFeedBackFragment.getTaskFeedback();
        }
    }
}
