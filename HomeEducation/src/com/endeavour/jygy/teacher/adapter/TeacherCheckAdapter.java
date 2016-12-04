package com.endeavour.jygy.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.teacher.bean.StudentVerityInfosResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/1/13.
 */
public class TeacherCheckAdapter extends BaseAdapter {

    private Context context;
    private List<StudentVerityInfosResp> resps = new ArrayList<>();

    public interface  TeacherCheckAdapterClickListener{
        void onCheckClick(StudentVerityInfosResp resp);
    }

    private TeacherCheckAdapterClickListener listener;

    public void setListener(TeacherCheckAdapterClickListener listener) {
        this.listener = listener;
    }

    public TeacherCheckAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return resps.size();
    }

    @Override
    public StudentVerityInfosResp getItem(int position) {
        return resps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_teacher_check, null);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvParentName = (TextView) convertView.findViewById(R.id.tvParentName);
            holder.tvState = (TextView) convertView.findViewById(R.id.tvState);
            holder.tvCheck = (TextView) convertView.findViewById(R.id.tvCheck);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final StudentVerityInfosResp studentVerityInfosResp = resps.get(position);
        holder.tvParentName.setText("[" + studentVerityInfosResp.getParentName() + "]");
        holder.tvTitle.setText(studentVerityInfosResp.getStudentName());
        if ("0".equals(studentVerityInfosResp.getValidFlag())) {
            holder.tvState.setText("已审核");
            holder.tvState.setTextColor(context.getResources().getColor(R.color.text_green));
            holder.tvCheck.setVisibility(View.INVISIBLE);
        } else {
            holder.tvState.setText("请求审核");
            holder.tvState.setTextColor(context.getResources().getColor(R.color.text_red));
            holder.tvCheck.setVisibility(View.VISIBLE);
        }
        holder.tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onCheckClick(studentVerityInfosResp);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvParentName;
        TextView tvState;
        TextView tvCheck;
    }


    public void setDatachanged(List<StudentVerityInfosResp> resps) {
        if (resps == null) {
            this.resps.clear();
        } else {
            this.resps = resps;
        }
        this.notifyDataSetChanged();
    }
}
