package com.endeavour.jygy.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.teacher.bean.GrowUpBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/14.
 */
public class GrowUpAdapter extends BaseAdapter {

    private Context context;

    private List<GrowUpBean> growUpBeans = new ArrayList<>();

    public GrowUpAdapter(Context context) {
        this.context = context;
    }

    public void setDataChanged(List<GrowUpBean> growUpBeans) {
        if (growUpBeans == null) {
            this.growUpBeans.clear();
        } else {
            this.growUpBeans = growUpBeans;
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return growUpBeans == null ? 0 : growUpBeans.size();
    }

    @Override
    public GrowUpBean getItem(int position) {
        return growUpBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grow_up, null);
            holder = new Holder();
            holder.ivUserIcon = (ImageView) convertView.findViewById(R.id.ivGrowUp);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(growUpBeans.get(position).getE_icon(), holder.ivUserIcon, MainApp.getOptions());
        return convertView;
    }

    class Holder {
        ImageView ivUserIcon;
    }
}
