package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.GetAdjustClassApplysResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/7/6.
 */
public class TeacherChangeClassAdapter extends BaseAdapter {


    private List<GetAdjustClassApplysResp> resps = new ArrayList<>();

    private LayoutInflater layoutInflater;

    private Context context;

    public TeacherChangeClassAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resps.size();
    }

    @Override
    public GetAdjustClassApplysResp getItem(int position) {
        return resps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_change_history, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(GetAdjustClassApplysResp resp, ViewHolder holder) {
        holder.tvBabyName.setText(resp.getApplyUserName());
        holder.tvClassName.setText(resp.getKindergartenClassName());
        holder.tvNewClassName.setText(resp.getNewkindergartenClassName());
        holder.tvData.setText(resp.getCreateTime());
        //0 :未审核 1：已通过 2 ：拒绝 3 取消申请
        if ("0".equals(resp.getStatus())) {
            holder.tvStatus.setText("未审核");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.tv_status_blue));
        } else if ("1".equals(resp.getStatus())) {
            holder.tvStatus.setText("已通过");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.tv_status_green));
        } else if ("2".equals(resp.getStatus())) {
            holder.tvStatus.setText("拒绝");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.orangered));
        } else {
            holder.tvStatus.setText("取消申请");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.gray));
        }
    }

    protected class ViewHolder {
        private TextView tvBabyName;
        private TextView tvClassName;
        private TextView tvNewClassName;
        private TextView tvStatus;
        private TextView tvData;

        public ViewHolder(View view) {
            tvBabyName = (TextView) view.findViewById(R.id.tvBabyName);
            tvClassName = (TextView) view.findViewById(R.id.tvClassName);
            tvNewClassName = (TextView) view.findViewById(R.id.tvNewClassName);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            tvData = (TextView) view.findViewById(R.id.tvData);
        }
    }

    public void setDataChanged(List<GetAdjustClassApplysResp> resps) {
        if (resps == null || resps.isEmpty()) {
            this.resps.clear();
        } else {
            this.resps = resps;
        }
        this.notifyDataSetChanged();
    }
}
