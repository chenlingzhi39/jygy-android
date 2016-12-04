package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.endeavour.jygy.parent.bean.TaskReply;

import java.util.ArrayList;
import java.util.List;

public class TaskExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<GetStudenTasksResp> resps = new ArrayList<>();

    public TaskExpandableAdapter(Context context) {
        this.context = context;
    }

    public int getGroupCount() {
        return resps == null ? 0 : resps.size();
    }

    public int getChildrenCount(int groupPosition) {
//        List<TaskReply> tasksReply = resps.get(groupPosition).getTasksReply();
//        return tasksReply == null ? 0 : tasksReply.size();
        return 6;
    }

    public GetStudenTasksResp getGroup(int groupPosition) {
        if (groupPosition >= resps.size()) {
            return null;
        } else {
            return resps.get(groupPosition);
        }
    }

    public TaskReply getChild(int groupPosition, int childPosition) {
        List<TaskReply> tasksReply = resps.get(groupPosition).getTasksReply();
        if (childPosition - 1 >= tasksReply.size()) {
            return null;
        } else {
            return tasksReply.get(childPosition - 1);
        }
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public boolean hasStableIds() {
        return false;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_group, null);
        TextView tv = (TextView) view.findViewById(R.id.groupLabel);
        View ivFinished = view.findViewById(R.id.ivFinished);
        View tvStartTask = view.findViewById(R.id.tvStartTask);
        tv.setText(resps.get(groupPosition).getTaskTitle());
        tv.setTextColor(context.getResources().getColor(R.color.black));
        if ("1".equals(resps.get(groupPosition).getUserTaskStatus())) {
            ivFinished.setVisibility(View.VISIBLE);
            tvStartTask.setVisibility(View.INVISIBLE);
        } else {
            ivFinished.setVisibility(View.INVISIBLE);
            tvStartTask.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_child, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.childLabel_title);
        TextView tvStatus = (TextView) view.findViewById(R.id.tvRight);
        TextView ivIcon = (TextView) view.findViewById(R.id.ivIcon);
        String title;
        if (childPosition == 0) {
            title = "小任务";
            ivIcon.setBackgroundResource(R.drawable.ic_task);
        } else if (childPosition == 1) {
            title = "好习惯第一周反馈";
            ivIcon.setBackgroundResource(R.drawable.ic_task);
        } else if (childPosition == 2) {
            title = "好习惯第二周反馈";
            ivIcon.setBackgroundResource(R.drawable.ic_task);
        } else if (childPosition == 3) {
            title = "好习惯第三周反馈";
            ivIcon.setBackgroundResource(R.drawable.ic_task);
        } else if (childPosition == 4) {
            title = "共育课堂";
            ivIcon.setBackgroundResource(R.drawable.ic_book);
        } else {
            title = "幼儿动画";
            ivIcon.setBackgroundResource(R.drawable.ic_video);
        }
        tvTitle.setText(title);
        if (childPosition == 0) {
            if (!"1".equals(resps.get(groupPosition).getUserTaskStatus())) {
                tvStatus.setText("开始任务");
                tvStatus.setTextColor(context.getResources().getColor(R.color.text_red));
            } else {
                tvStatus.setVisibility(View.INVISIBLE);
            }
        } else if (childPosition == 4 || childPosition == 5) {
            tvStatus.setVisibility(View.INVISIBLE);
        } else {
            tvStatus.setVisibility(View.VISIBLE);
            TaskReply taskReply = getChild(groupPosition, childPosition);
            if (taskReply != null) {
                if ("1".equals(taskReply.getStatus())) {
                    tvStatus.setText("已完成");
                    tvStatus.setTextColor(context.getResources().getColor(R.color.gray));
                } else {
                    Integer exporedFlag = -1;
                    try {
                        exporedFlag = Integer.parseInt(taskReply.getExpiredFlag());
                    } catch (Exception e) {
                    }
                    if (exporedFlag < 0) {
                        tvStatus.setText("已过期");
                        tvStatus.setTextColor(context.getResources().getColor(R.color.path_orange));
                    } else {
                        tvStatus.setText("剩余" + exporedFlag + "天");
                        tvStatus.setTextColor(context.getResources().getColor(R.color.text_red));
                    }
                }
            } else {
                tvStatus.setTextColor(context.getResources().getColor(R.color.text_wks));
                tvStatus.setText("未开始");
            }
        }
        return view;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setDataChanged(List<GetStudenTasksResp> resps) {
        if (resps == null) {
            this.resps.clear();
        } else {
            this.resps = resps;
        }
        this.notifyDataSetChanged();
    }

}
