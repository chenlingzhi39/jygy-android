package com.endeavour.jygy.parent.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.view.NineSquaresView;

import java.util.List;

/**
 * Created by wu on 15/12/23.
 */
public class DynamicViewHolder extends RecyclerView.ViewHolder {

    private NineSquaresView nineSquaresView;

    public DynamicViewHolder(View itemView) {
        super(itemView);
        nineSquaresView = (NineSquaresView) itemView.findViewById(R.id.nine);
    }

    public void rander(List<String> urls) {
        nineSquaresView.rander(urls);
    }
}
