package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.endeavour.jygy.R;
import com.flyco.roundview.RoundTextView;

/**
 * 消息通知适配器
 * Created by wu on 15/11/14.
 */
public class NoticeAdapter extends BaseExpandableListAdapter {

    private Context context;

    public NoticeAdapter(Context context) {
        this.context = context;
    }

    String[] groups = {"评论/@点赞", "学校/班级通知", "小任务提醒", "共育课堂提醒"};

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View groupView = LayoutInflater.from(context).inflate(R.layout.itemview_notice_group, null);
        RoundTextView textView = (RoundTextView) groupView.findViewById(R.id.tvGroupTitle);
        textView.setText(groups[groupPosition]);
        return groupView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
