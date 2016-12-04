package com.endeavour.jygy.parent.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.parent.bean.Student;

/**
 * Created by wu on 15/12/29.
 */
public class ChooseBabyFragment extends BaseViewFragment {


    private TextView tvLeft;
    private ImageView ivBabyIcon;
    private TextView tvName;
    private TextView tvRight;

    private Student studen;

    @Override
    public void initView() {
        setMainView(R.layout.layout_choose_baby);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        studen = (Student) getArguments().getSerializable("studen");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvLeft = (TextView) view.findViewById(R.id.tvLeft);
        ivBabyIcon = (ImageView) view.findViewById(R.id.ivBabyIcon);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvRight = (TextView) view.findViewById(R.id.tvRight);

    }
}
