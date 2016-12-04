package com.endeavour.jygy.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskResp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TeachTaskListAdapter extends BaseAdapter {
    private Context context;

    public List<GetTeacherTaskResp> list = new ArrayList<GetTeacherTaskResp>();


    public TeachTaskListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public GetTeacherTaskResp getItem(int position) {
        if (list == null) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    protected class ViewHolder {
        private ImageView ivUserIcon;
        private TextView tvTeacherTaskTitle;
        private ImageView tvTeacherTaskStatus;
        private TextView tvContent;
        private RelativeLayout rl;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_teacher_task, null);
            viewHolder.ivUserIcon = (ImageView) convertView.findViewById(R.id.ivUserIcon);
            viewHolder.tvTeacherTaskTitle = (TextView) convertView.findViewById(R.id.tvTeacherTaskTitle);
            viewHolder.tvTeacherTaskStatus = (ImageView) convertView.findViewById(R.id.tvTeacherTaskStatus);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            viewHolder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GetTeacherTaskResp resp = list.get(position);
        viewHolder.rl.setBackgroundColor(context.getResources().getColor(resp.isReaded() ? R.color.teacher_task_list_readed : R.color.teacher_task_list_noread));
        viewHolder.tvTeacherTaskTitle.setText(resp.getUserName());
        viewHolder.tvContent.setText(resp.getTitle());
        ImageLoader.getInstance().displayImage(resp.getHeadPortrait(), viewHolder.ivUserIcon, "1".equals(resp.getSex()) ? MainApp.getBobyOptions() : MainApp.getGrilsOptions());
        if ("1".equals(resp.getStatus())) {
            viewHolder.tvTeacherTaskStatus.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvTeacherTaskStatus.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public void setDataChanged(List<GetTeacherTaskResp> list) {
        if (list == null) {
            this.list.clear();
        } else {
            this.list = list;
        }
        this.notifyDataSetChanged();
    }

}
