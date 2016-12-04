package com.endeavour.jygy.parent.fragment;

import android.text.TextUtils;
import android.widget.ListView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.parent.adapter.TaskListAdapter;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wu on 15/11/26.
 */
public class TaskFeedBackFragment extends BaseViewFragment {
    private ListView listView;
    private TaskListAdapter taskAdapter;
    private List<GetStudenTasksResp> getStudenTasksResps;

    public void setDataChanged(List<GetStudenTasksResp> resps) {
        if (resps == null) {
            return;
        }
        getStudenTasksResps = new ArrayList<>();
        Iterator<GetStudenTasksResp> iterator = resps.iterator();
        while (iterator.hasNext()) {
            GetStudenTasksResp resp = iterator.next();
            if (TextUtils.isEmpty(resp.getReviewContent()) || TextUtils.isEmpty(resp.getReviewTeacherName()) || TextUtils.isEmpty(resp.getReviewTime())) {
                continue;
            } else {
                getStudenTasksResps.add(resp);
            }
        }
        if (taskAdapter != null) {
            taskAdapter.setDataChanged(getStudenTasksResps);
        }
    }

    public TaskFeedBackFragment() {
    }

    @Override
    public void initView() {
        setMainView(R.layout.task_list_main);
        listView = (ListView) mainView.findViewById(R.id.data_list);
        initdata();
    }

    private void initdata() {
        taskAdapter = new TaskListAdapter(getActivity());
        listView.setAdapter(taskAdapter);
        if (getStudenTasksResps != null && !getStudenTasksResps.isEmpty()) {
            taskAdapter.setDataChanged(getStudenTasksResps);
        } else {
            progresser.showError("暂无数据", false);
        }
    }
}
