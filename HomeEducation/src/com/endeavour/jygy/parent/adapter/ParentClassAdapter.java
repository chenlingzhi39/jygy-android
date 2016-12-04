package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.GetParentLessonPracticeDet;
import com.endeavour.jygy.parent.bean.GetParentLessonResp;

import java.util.ArrayList;
import java.util.List;

public class ParentClassAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<GetParentLessonResp> resps = new ArrayList<>();

    public ParentClassAdapter(Context context) {
        this.context = context;
    }

    public int getGroupCount() {
        return resps == null ? 0 : resps.size();
    }

    public int getChildrenCount(int groupPosition) {
        List<GetParentLessonPracticeDet> practiceDets = resps.get(groupPosition).getPracticeDet();
        return practiceDets == null ? 0 : practiceDets.size();
    }

    public GetParentLessonResp getGroup(int groupPosition) {
        if (groupPosition >= resps.size()) {
            return null;
        } else {
            return resps.get(groupPosition);
        }
    }

    public GetParentLessonPracticeDet getChild(int groupPosition, int childPosition) {
        List<GetParentLessonPracticeDet> tasksReply = resps.get(groupPosition).getPracticeDet();
        if (childPosition >= tasksReply.size()) {
            return null;
        } else {
            return tasksReply.get(childPosition);
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
        tv.setText(resps.get(groupPosition).getPlanName());
        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task_child, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.childLabel_title);
        TextView tvStatus = (TextView) view.findViewById(R.id.tvRight);
        tvStatus.setVisibility(View.INVISIBLE);
        tvTitle.setText(getChild(groupPosition, childPosition).getPracticeTitle());
        return view;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setDataChanged(List<GetParentLessonResp> resps) {
        if (resps == null) {
            this.resps.clear();
        } else {
            this.resps = resps;
        }
        this.notifyDataSetChanged();
    }

}
