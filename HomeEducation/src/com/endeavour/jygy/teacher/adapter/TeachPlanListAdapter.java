package com.endeavour.jygy.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.teacher.bean.GetLessonPlansResp;

import java.util.ArrayList;
import java.util.List;

public class TeachPlanListAdapter extends BaseAdapter {
    private Context context;

    public List<GetLessonPlansResp> list = new ArrayList<GetLessonPlansResp>();


    public TeachPlanListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public GetLessonPlansResp getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView tv_teach_title;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.teachplan_list_item, null);
            viewHolder.tv_teach_title = (TextView) convertView.findViewById(R.id.tv_teach_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_teach_title.setText(list.get(position).getTitle());
        return convertView;
    }

    public void setDataChanged(List<GetLessonPlansResp> list) {
        if (list == null) {
            this.list.clear();
        } else {
            this.list = list;
        }
        this.notifyDataSetChanged();
    }

}
