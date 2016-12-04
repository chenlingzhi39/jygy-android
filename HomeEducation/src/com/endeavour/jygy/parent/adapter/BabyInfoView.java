package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.parent.bean.Student;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by wu on 15/12/29.
 */
public class BabyInfoView extends LinearLayout {

    private ImageView ivBabyIcon;
    private TextView tvName;

    public BabyInfoView(Context context) {
        super(context);
        init();
    }

    public BabyInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BabyInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_choose_baby, null);
        this.addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ivBabyIcon = (ImageView) view.findViewById(R.id.ivBabyIcon);
        tvName = (TextView) view.findViewById(R.id.tvName);
    }

    public void rander(final Student studen, final ChooseBabyAdapter.OnBabySelectedListener listener) {
        if("1".equals(studen.getSex())){
            ImageLoader.getInstance().displayImage(studen.getHeadPortrait(), ivBabyIcon, MainApp.getBobyOptions());
        }else{
            ImageLoader.getInstance().displayImage(studen.getHeadPortrait(), ivBabyIcon, MainApp.getGrilsOptions());
        }
        tvName.setText(studen.getName());
        ivBabyIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBabySelected(studen);
            }
        });
    }

}
