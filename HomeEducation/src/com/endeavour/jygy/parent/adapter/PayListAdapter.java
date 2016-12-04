package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.GetPayInfoResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/6/26.
 */
public class PayListAdapter extends BaseAdapter {

    private List<GetPayInfoResp> payInfoResps = new ArrayList<GetPayInfoResp>();

    private LayoutInflater layoutInflater;

    private PayListener payListener;

    public void setPayListener(PayListener payListener) {
        this.payListener = payListener;
    }

    public PayListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return payInfoResps.size();
    }

    @Override
    public GetPayInfoResp getItem(int position) {
        return payInfoResps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_pay_info, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(final GetPayInfoResp resp, ViewHolder holder) {
        holder.tvPayName.setText(resp.getFeeName());
        holder.tvPayTime.setText(resp.getExpiryStartDate() + " - " + resp.getExpiryEndDate());
        holder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (payListener != null) {
                    payListener.onPay(resp);
                }
            }
        });
    }

    protected class ViewHolder {
        private TextView tvPayName;
        private TextView tvPayTime;
        private Button btnPay;

        public ViewHolder(View view) {
            tvPayName = (TextView) view.findViewById(R.id.tvPayName);
            tvPayTime = (TextView) view.findViewById(R.id.tvPayTime);
            btnPay = (Button) view.findViewById(R.id.btnPay);
        }
    }

    public void setDataChanged(List<GetPayInfoResp> getPayInfoResps) {
        if (getPayInfoResps == null) {
            this.payInfoResps.clear();
        } else {
            this.payInfoResps = getPayInfoResps;
        }
        this.notifyDataSetChanged();
    }

    public interface PayListener {

        void onPay(GetPayInfoResp getPayInfoResp);
    }
}
