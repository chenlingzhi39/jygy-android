package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.activity.MessageOpter;
import com.endeavour.jygy.parent.bean.HomeMenuItem;
import com.flyco.roundview.RoundTextView;
import com.flyco.tablayout.utils.UnreadMsgUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/11/26.
 */
public class HomeMenuAdapter extends BaseAdapter {
    private List<HomeMenuItem> homeMenuItems = new ArrayList<HomeMenuItem>();
    private Context context;

    private OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public HomeMenuAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return homeMenuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return homeMenuItems.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_menu, null);
            holder.tvIcon = (TextView) convertView.findViewById(R.id.tvIcon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.rtvRedDot = (RoundTextView) convertView.findViewById(R.id.rtvRedDot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvIcon.setBackgroundResource(homeMenuItems.get(position).getResID());
        holder.tvName.setText(homeMenuItems.get(position).getName());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemClicked(position, v);
                }
            }
        };
        holder.tvIcon.setOnClickListener(listener);
        holder.tvName.setOnClickListener(listener);
        if (position == 0) {
            MessageOpter messageOpter = new MessageOpter();
            int dynamicMessageCount = messageOpter.getDynamicMessageCount();
            if(dynamicMessageCount == 0){
                holder.rtvRedDot.setVisibility(View.INVISIBLE);
            }else{
                UnreadMsgUtils.show(holder.rtvRedDot, dynamicMessageCount);
                holder.rtvRedDot.setVisibility(View.VISIBLE);
            }
        } else {
            holder.rtvRedDot.setVisibility(View.INVISIBLE);
        }
//        UnreadMsgUtils.show(holder.rtvRedDot, 10);
        return convertView;
    }

    class ViewHolder {
        TextView tvIcon;
        TextView tvName;
        RoundTextView rtvRedDot;
    }

    public void setDataChanged(List<HomeMenuItem> homeMenuItems) {
        this.homeMenuItems = homeMenuItems;
        this.notifyDataSetChanged();
    }

    public interface OnItemSelectedListener {
        void onItemClicked(int position, View v);
    }
}
