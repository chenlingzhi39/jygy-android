package com.endeavour.jygy.teacher.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.teacher.bean.TeacherContentDetialBean;

import java.util.ArrayList;
import java.util.List;

public class TeacherPlanDetialAdapter extends BaseExpandableListAdapter {

    private Context context;

    private List<TeacherContentDetialBean> resps = new ArrayList<>();

    public TeacherPlanDetialAdapter(Context context) {
        this.context = context;
    }

    public int getGroupCount() {
        return resps == null ? 0 : resps.size();
    }

    public int getChildrenCount(int groupPosition) {
        List<TeacherContentDetialBean> list = resps.get(groupPosition).getList();
        return (list == null || list.isEmpty()) ? 1 : list.size();
    }

    public TeacherContentDetialBean getGroup(int groupPosition) {
        if (groupPosition >= resps.size()) {
            return null;
        } else {
            return resps.get(groupPosition);
        }
    }

    public TeacherContentDetialBean getChild(int groupPosition, int childPosition) {
        return resps.get(groupPosition).getList().get(childPosition);
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher_plan_detial_group, null);
        TextView tv = (TextView) view.findViewById(R.id.groupLabel);
        tv.setText(resps.get(groupPosition).getTitle());
        if (resps.get(groupPosition).getLevel() == 3) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.text_big));
            tv.setTextColor(context.getResources().getColor(R.color.text_red));
        } else {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.text_moderate3));
            tv.setTextColor(context.getResources().getColor(R.color.black));
        }
        return view;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher_plan_detial, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        List<TeacherContentDetialBean> list = resps.get(groupPosition).getList();
        tvTitle.setText(list.get(childPosition).getTitle());
        return view;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setDataChanged(List<TeacherContentDetialBean> resps) {
        if (resps == null) {
            this.resps.clear();
        } else {
            this.resps = resps;
        }
        this.notifyDataSetChanged();
    }

}
