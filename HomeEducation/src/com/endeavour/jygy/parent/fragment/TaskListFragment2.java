package com.endeavour.jygy.parent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.parent.activity.EditTaskFeedBackActivity;
import com.endeavour.jygy.parent.activity.ParentClassListActivity;
import com.endeavour.jygy.parent.activity.ParentTaskReplyContentActivity;
import com.endeavour.jygy.parent.activity.TaskContentActivity;
import com.endeavour.jygy.parent.adapter.TaskExpandableAdapter;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.endeavour.jygy.parent.bean.TaskReply;

import net.hy.android.media.ChildrenAnimation;
import net.hy.android.media.MediaMainSActivity;

import java.util.List;

/**
 * Created by wu on 15/11/14.
 */
public class TaskListFragment2 extends BaseViewFragment {
    private ExpandableListView expandableList;
    private TaskExpandableAdapter adapter;
    private List<GetStudenTasksResp> resps;

    public TaskListFragment2() {
    }

    public void setDataChanged(List<GetStudenTasksResp> resps) {
        this.resps = resps;
        if (adapter != null) {
            if (resps == null || resps.isEmpty()) {
                progresser.showError("暂无数据", false);
            } else {
                adapter.setDataChanged(resps);
                progresser.showContent();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void initView() {
        setMainView(R.layout.fragment_task_list);
        expandableList = (ExpandableListView) mainView.findViewById(R.id.lvTasks);
        adapter = new TaskExpandableAdapter(getActivity());
        expandableList.setAdapter(adapter);
        if (resps == null || resps.isEmpty()) {
            progresser.showError("暂无数据", false);
        } else {
            adapter.setDataChanged(resps);
            progresser.showContent();
        }
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (childPosition == 0) {
                    GetStudenTasksResp task = adapter.getGroup(groupPosition);
                    Intent intent = new Intent(getActivity(), TaskContentActivity.class);
                    intent.putExtra("task", task);
                    startActivity(intent);
                } else if (childPosition == 4) {
//                    Tools.toActivity(getActivity(), ParentClassListActivity.class);
                    startActivity(ParentClassListActivity.getStartIntent(getActivity(), adapter.getGroup(groupPosition).getLessonPlanId()));
                } else if (childPosition == 5) {
                    GetStudenTasksResp task = adapter.getGroup(groupPosition);
                    ChildrenAnimation childrenAnimation = new ChildrenAnimation();
                    childrenAnimation.setDescn(task.getChildAnimationDescn());
                    childrenAnimation.setTitle(task.getChildAnimationTitle());
                    childrenAnimation.setLinkUrl(task.getChildAnimationUrl());
                    startActivity(MediaMainSActivity.getStartIntent(getActivity(), childrenAnimation));
                } else {
                    if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
                        Toast.makeText(getActivity(), "您的宝宝已毕业, 无法操作", Toast.LENGTH_SHORT).show();
                        return false;
                    } else {
                        TaskReply taskReply = adapter.getChild(groupPosition, childPosition);
                        if (taskReply != null) {
                            if ("1".equals(taskReply.getStatus())) {
                                if (!TextUtils.isEmpty(taskReply.getContent())) {
                                    Intent intent = new Intent(getActivity(), ParentTaskReplyContentActivity.class);
                                    intent.putExtra("content", taskReply.getContent());
                                    startActivity(intent);
                                }
                                return false;
                            } else {
                                String flag = taskReply.getExpiredFlag();
                                if (TextUtils.isEmpty(flag)) {
                                    return false;
                                } else {
                                    int flagInt = 0;
                                    try {
                                        flagInt = Integer.parseInt(flag);
                                    } catch (Exception ignored) {
                                    }
                                    if (flagInt < 0) {
                                        return false;
                                    } else {
                                        GetStudenTasksResp task = adapter.getGroup(groupPosition);
                                        Intent intent = new Intent(getActivity(), EditTaskFeedBackActivity.class);
                                        intent.putExtra("task", task);
                                        intent.putExtra("taskReply", taskReply);
                                        intent.putExtra("replyWeek", (childPosition + 1) + "");
                                        startActivity(intent);
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
        });
        int groupCount = expandableList.getCount();
        for (int i = 0; i < groupCount; i++) {
            expandableList.expandGroup(i);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
