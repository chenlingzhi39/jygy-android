package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;

import java.util.ArrayList;
import java.util.List;

public class StudenMoralTaskListAdapter extends BaseAdapter {

    private Context context;
    private List<GetStudenTasksResp> resps = new ArrayList<>();

    public StudenMoralTaskListAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_studen_moral_task, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvTaskScore = (TextView) convertView.findViewById(R.id.tvTaskScore);
            viewHolder.tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(GetStudenTasksResp resp, ViewHolder viewHolder) {
        try {
            viewHolder.tvTaskScore.setText(resp.getStar() + "分");
            viewHolder.tvTaskName.setText(resp.getTaskTitle());
        } catch (Exception ex) {
        }
    }

    protected class ViewHolder {
        private TextView tvTaskScore;
        private TextView tvTaskName;
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
