package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends BaseAdapter {

    private Context context;
    private List<GetStudenTasksResp> resps = new ArrayList<>();

    public TaskListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return resps.size();
    }

    @Override
    public GetStudenTasksResp getItem(int position) {
        return resps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.class_list_data_item, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvTaskScore = (TextView) convertView.findViewById(R.id.tvTaskScore);
            viewHolder.tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
            viewHolder.tvDiscuss = (TextView) convertView.findViewById(R.id.tvDiscuss);
            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(GetStudenTasksResp resp, ViewHolder viewHolder) {
        try {
            viewHolder.tvTaskScore.setText(resp.getStar() + "分");
            viewHolder.tvTaskName.setText(resp.getTaskTitle());
            viewHolder.tvDiscuss.setText(resp.getReviewContent());
        } catch (Exception ex) {
        }
    }

    protected class ViewHolder {
        private LinearLayout llTaskTitle;
        private TextView tvTaskScore;
        private TextView tvTaskName;
        private TextView tvDiscuss;
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
