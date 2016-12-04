package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Calculater;
import com.endeavour.jygy.parent.bean.GetMyPayRecordsResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/6/26.
 */
public class PayHistoryAdapter extends BaseAdapter {

    private List<GetMyPayRecordsResp> getMyPayRecordsRespList = new ArrayList<>();

    private Context context;

    public PayHistoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return getMyPayRecordsRespList.size();
    }

    @Override
    public GetMyPayRecordsResp getItem(int position) {
        return getMyPayRecordsRespList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pay_history, null);
            viewHolder = new ViewHolder(convertView);
            viewHolder.initView(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GetMyPayRecordsResp getMyPayRecordsResp = getMyPayRecordsRespList.get(position);
        viewHolder.tvPayAmount.setText(Calculater.formotFen(getMyPayRecordsResp.getTranAmount()));
        viewHolder.tvPayName.setText(getMyPayRecordsResp.getTranDescn());
        viewHolder.tvPayTime.setText(getMyPayRecordsResp.getTranTime());
        if ("0".equals(getMyPayRecordsResp.getTranType())) {
            viewHolder.tvStatus.setText("现金");
        } else if ("1".equals(getMyPayRecordsResp.getTranType())) {
            viewHolder.tvStatus.setText("微信");
        } else if ("2".equals(getMyPayRecordsResp.getTranType())) {
            viewHolder.tvStatus.setText("支付宝");
        } else {
            viewHolder.tvStatus.setText("未支付");
        }
        return convertView;
    }

    static class ViewHolder {
        protected TextView tvPayName;
        protected TextView tvPayAmount;
        protected TextView tvPayTime;
        protected TextView tvStatus;

        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            tvPayName = (TextView) rootView.findViewById(R.id.tvPayName);
            tvPayAmount = (TextView) rootView.findViewById(R.id.tvPayAmount);
            tvPayTime = (TextView) rootView.findViewById(R.id.tvPayTime);
            tvStatus = (TextView) rootView.findViewById(R.id.tvStatus);
        }
    }

    public void setDataChanged(List<GetMyPayRecordsResp> getMyPayRecordsRespList) {
        if (getMyPayRecordsRespList == null) {
            this.getMyPayRecordsRespList.clear();
        } else {
            this.getMyPayRecordsRespList = getMyPayRecordsRespList;
        }
        this.notifyDataSetChanged();
    }
}
