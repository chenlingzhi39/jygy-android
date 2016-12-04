package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.Dynamic;

import java.util.ArrayList;
import java.util.List;

public class DynamicDetialRcvAdapter extends RecyclerView.Adapter<DynamicDetialRcvViewHolder> {

    private Context context;

    private DynamicDiscussListener listener;

    private List<Dynamic> dynamics = new ArrayList<Dynamic>();

    public DynamicDetialRcvAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DynamicDetialRcvViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View dynamicView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_dynamic, viewGroup, false);
        return new DynamicDetialRcvViewHolder(context, dynamicView, listener);
    }

    @Override
    public void onBindViewHolder(DynamicDetialRcvViewHolder dynamicDetialRcvViewHolder, int i) {
        dynamicDetialRcvViewHolder.rander(dynamics.get(i));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (dynamics == null) {
            return 0;
        } else {
            return dynamics.size();
        }
    }


    public void setDataChanged(List<Dynamic> dynamics) {
        try {
            this.dynamics.clear();
            this.dynamics.addAll(dynamics);
            this.notifyDataSetChanged();
        } catch (Exception e) {
        }

    }

    public void addDataChanged(List<Dynamic> dynamics) {
        try {
            this.dynamics.addAll(dynamics);
            this.notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

    public void setDynamicDiscussListener(DynamicDiscussListener dynamicDiscussListener) {
        this.listener = dynamicDiscussListener;
    }
}
