package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.GetFoodResp;

import java.util.ArrayList;
import java.util.List;

public class FoodAdpater extends BaseAdapter {

    private List<GetFoodResp> resps = new ArrayList<GetFoodResp>();

    private Context context;
    private LayoutInflater layoutInflater;

    public FoodAdpater(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resps.size();
    }

    @Override
    public GetFoodResp getItem(int position) {
        return resps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_food_detail, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

            convertView.setTag(viewHolder);
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(GetFoodResp resp, ViewHolder holder) {
        holder.tvTitle.setText(resp.getTitle());
    }

    protected class ViewHolder {
        //        private ImageView ivFoolImg;
        private TextView tvTitle;
//        private FrameLayout igDynamicViewCon;
    }

    public void setDataChanged(List<GetFoodResp> resps) {
        this.resps = resps;
        this.notifyDataSetChanged();
    }

    public void addDataChanged(List<GetFoodResp> resps) {
        this.resps.addAll(resps);
        this.notifyDataSetChanged();
    }
}

