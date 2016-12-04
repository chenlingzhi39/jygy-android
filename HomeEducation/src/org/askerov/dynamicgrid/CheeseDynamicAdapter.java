package org.askerov.dynamicgrid;

/**
 * Author: alex askerov
 * Date: 9/9/13
 * Time: 10:52 PM
 */

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.teacher.bean.GetStudenList2Resp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CheeseDynamicAdapter extends BaseDynamicGridAdapter {
    public CheeseDynamicAdapter(Context context, List<?> items, int columnCount) {
        super(context, items, columnCount);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheeseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid, null);
            holder = new CheeseViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheeseViewHolder) convertView.getTag();
        }
        GetStudenList2Resp resp = (GetStudenList2Resp) getItem(position);
        holder.build(resp);
        return convertView;
    }

    private class CheeseViewHolder {
        private TextView titleText;
        private TextView scoreText;
        private ImageView image;

        private CheeseViewHolder(View view) {
            titleText = (TextView) view.findViewById(R.id.item_title);
            scoreText = (TextView) view.findViewById(R.id.item_score);
            image = (ImageView) view.findViewById(R.id.item_img);
        }

        void build(GetStudenList2Resp resp) {
            titleText.setText(resp.getUserName());
            String moralScore = resp.getMoral();
            scoreText.setText((TextUtils.isEmpty(moralScore) ? "--" : moralScore));
            ImageLoader.getInstance().displayImage(resp.getHeadPortrait(), image, ("1".equals(resp.getSex()) ? MainApp.getBobyOptions() : MainApp.getGrilsOptions()));
        }
    }
}