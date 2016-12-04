package com.endeavour.jygy.schoolboss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.ImageLoadApp;
import com.endeavour.jygy.common.view.CircleImageView;
import com.endeavour.jygy.parent.adapter.HomeMenuAdapter.OnItemSelectedListener;
import com.endeavour.jygy.parent.bean.GetGradeClassResp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/24.
 */
public class SchoolClassesAdapter extends BaseAdapter {
    private List<GetGradeClassResp> resp = new ArrayList<>();
    private Context context;
    
    private OnItemSelectedListener onItemSelectedListener;

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }


    public SchoolClassesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return resp == null ? 0 : resp.size();
    }

    @Override
    public GetGradeClassResp getItem(int position) {
        return resp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.itme_classes, parent, false);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.civ = (CircleImageView) convertView.findViewById(R.id.civClassIcon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(resp.get(position).getName());
        ImageLoader.getInstance().displayImage(resp.get(position).getHeadPortrait(), holder.civ, ImageLoadApp.getClassOptions());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemClicked(position, v);
                }
            }
        };
        holder.tvName.setOnClickListener(listener);
        holder.civ.setOnClickListener(listener);
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        CircleImageView civ;
    }

    public void setDataChanged(List<GetGradeClassResp> resp) {
        if (resp == null) {
            this.resp.clear();
        } else {
            this.resp = resp;
        }
        notifyDataSetChanged();
    }

    public void addDataChanged(List<GetGradeClassResp> resp) {
        if (resp == null) {
            this.resp.clear();
        } else {
            this.resp.addAll(resp);
        }
        notifyDataSetChanged();
    }
}
