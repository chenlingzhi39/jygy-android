package com.endeavour.jygy.teacher.fragment;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewFragment;

/**
 * Created by wu on 15/12/25.
 */
public class TeacherGrowupFragment extends BaseViewFragment {

    @Override
    public void initView() {
        setMainView(R.layout.activity_growup);
        setTitleText("成长档案");
        showTitleBack();
        progresser.showError("功能升级中,敬请期待!", false);
    }

}
