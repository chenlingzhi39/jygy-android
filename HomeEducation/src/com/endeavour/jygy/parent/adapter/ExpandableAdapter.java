package com.endeavour.jygy.parent.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.activity.NoticeListActivity;

import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private NoticeListActivity expandable;

    public ExpandableAdapter(NoticeListActivity expandable) {
        this.expandable = expandable;
    }

    public int getGroupCount() {
        if (expandable == null) {
            return 0;
        }
        List<String> groupArray = expandable.getGroupArray();
        if(groupArray == null){
            return 0;
        }
        return groupArray.size();
    }

    public int getChildrenCount(int groupPosition) {
        return expandable.getChildArray().get(groupPosition).size();
    }

    public Object getGroup(int groupPosition) {
        return expandable.getGroupArray().get(groupPosition);
    }

    public Object getChild(int groupPosition, int childPosition) {
        return expandable.getChildArray().get(groupPosition).get(childPosition);
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

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // String string = expandable.getGroupArray().get(groupPosition);
        // return getGenericView(string);
        View view = null;
        // if(convertView == null) {
        view = expandable.getLayoutInflater().inflate(R.layout.group, null);
        TextView tv = (TextView) view.findViewById(R.id.groupLabel);
        String ss = expandable.getGroupArray().get(groupPosition);
        tv.setText(ss);
        // } else {
        // view = convertView;
        // }
        return view;
    }

    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // String string =
        // expandable.getChildArray().get(groupPosition).get(childPosition);
        // return getGenericView(string);
        View view = null;
        // if(convertView == null) {
        view = expandable.getLayoutInflater().inflate(R.layout.group_child,
                null);
        //LinearLayout childLabel_lay = (LinearLayout) view.findViewById(R.id.childLabel_lay);
        try {
            String[] txt = expandable.getChildArray().get(groupPosition)
                    .get(childPosition).split("\\|");
            TextView tv_title = (TextView) view.findViewById(R.id.childLabel_title);
            TextView tv_content = (TextView) view
                    .findViewById(R.id.childLabel_content);
            TextView tv_title_m = (TextView) view
                    .findViewById(R.id.childLabel_title_m);
            TextView tv_content_m = (TextView) view
                    .findViewById(R.id.childLabel_content_m);
            if (txt[4] != null && txt[4].equals("1")) {
                //childLabel_lay.setBackgroundResource(R.color.caldroid_lighter_gray);
                tv_title_m.setTextColor(Color.parseColor("#C4C4C4"));
                tv_content.setTextColor(Color.parseColor("#C4C4C4"));
                tv_content_m.setTextColor(Color.parseColor("#C4C4C4"));
            } else {
                //childLabel_lay.setBackgroundResource(R.color.caldroid_gray_w);
                tv_title_m.setTextColor(Color.parseColor("#343434"));
                tv_content.setTextColor(Color.parseColor("#343434"));
                tv_content_m.setTextColor(Color.parseColor("#343434"));
            }
            if (!txt[0].equals("") && txt[0] != null && !txt[0].equals("null"))
                tv_title.setText(txt[0]);
            if (!txt[1].equals("") && txt[1] != null && !txt[1].equals("null"))
                tv_title_m.setText(txt[1]);
            if (!txt[2].equals("") && txt[2] != null && !txt[2].equals("null"))
                tv_content.setText(txt[2]);
            if (!txt[3].equals("") && txt[3] != null && !txt[3].equals("null"))
                tv_content_m.setText(txt[3]);
        } catch (Exception ex) {
        }
        // } else {
        // view = convertView;
        // }
        return view;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public TextView getGenericView(String string) {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, 64);
        TextView text = new TextView(expandable);
        text.setLayoutParams(layoutParams);
        // Center the text vertically
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        text.setPadding(36, 0, 0, 0);
        text.setText(string);
        return text;

    }

}
