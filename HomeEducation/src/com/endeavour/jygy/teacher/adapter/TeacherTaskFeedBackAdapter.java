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
import com.endeavour.jygy.teacher.bean.GetTeacherTaskFeedbackListResp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/16.
 */
public class TeacherTaskFeedBackAdapter extends BaseAdapter {
    private List<GetTeacherTaskFeedbackListResp> resps = new ArrayList<GetTeacherTaskFeedbackListResp>();

    private Context context;
    private LayoutInflater layoutInflater;

    public TeacherTaskFeedBackAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resps.size();
    }

    @Override
    public GetTeacherTaskFeedbackListResp getItem(int position) {
        return resps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_teacher_task_feedback, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.ivUserIcon = (ImageView) convertView.findViewById(R.id.ivUserIcon);
            viewHolder.tvTeacherTaskTitle = (TextView) convertView.findViewById(R.id.tvTeacherTaskTitle);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            viewHolder.tvWeek = (TextView) convertView.findViewById(R.id.tvWeek);
            viewHolder.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(GetTeacherTaskFeedbackListResp resp, ViewHolder holder) {
        holder.rl.setBackgroundColor(context.getResources().getColor(resp.isReaded() ? R.color.teacher_task_list_readed : R.color.teacher_task_list_noread));
        holder.tvTeacherTaskTitle.setText(resp.getUserName());
        holder.tvContent.setText(resp.getTaskTitle());
        holder.tvWeek.setText(resp.getReplyWeek());
        ImageLoader.getInstance().displayImage(resp.getHeadPortrait(), holder.ivUserIcon, MainApp.getOptions());
    }

    protected class ViewHolder {
        private ImageView ivUserIcon;
        private TextView tvTeacherTaskTitle;
        private TextView tvContent;
        private TextView tvWeek;
        private RelativeLayout rl;
    }

    public void setDataChanged(List<GetTeacherTaskFeedbackListResp> resps) {
        if (resps == null) {
            this.resps.clear();
        } else {
            this.resps = resps;
        }
        this.notifyDataSetChanged();
    }
}
