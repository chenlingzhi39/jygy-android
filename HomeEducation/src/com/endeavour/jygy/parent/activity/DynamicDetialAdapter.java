package com.endeavour.jygy.parent.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.parent.bean.GetDyanmicDetailResp;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wu on 15/12/5.
 */
public class DynamicDetialAdapter extends BaseAdapter {

    private Context context;

    private List<GetDyanmicDetailResp> resps = new ArrayList<>();

    public DynamicDetialAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return resps.size();
    }

    @Override
    public Object getItem(int position) {
        return resps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_discuss_detial_dapter, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvDiscuss = (TextView) convertView.findViewById(R.id.tvDiscuss);

            convertView.setTag(viewHolder);
        }
        initializeViews((GetDyanmicDetailResp) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(GetDyanmicDetailResp resp, ViewHolder holder) {
        holder.tvDiscuss.setText(resp.getCommentContent());
    }

    protected class ViewHolder {
        private TextView tvDiscuss;
    }
}
