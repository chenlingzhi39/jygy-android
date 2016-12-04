package com.endeavour.jygy.parent.fragment;

import android.view.View;
import android.widget.Button;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.teacher.dialog.ConfirmDialog;

/**
 * Created by wu on 15/11/14.
 */
public class GrowUpDocFragment extends BaseViewFragment {

    private Button bt_grow_do;

    @Override
    public void initView() {
        setMainView(R.layout.grow_content);
        setTitleText(R.string.growcontent);
        showTitleBack();
        bt_grow_do = (Button) mainView.findViewById(R.id.bt_grow_do);
        bt_grow_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ConfirmDialog confirmDialog = new ConfirmDialog(getActivity(), "确定要退出吗?", "退出", "取消");
                confirmDialog.show();
                confirmDialog
                        .setClicklistener(new ConfirmDialog.ClickListenerInterface() {
                            @Override
                            public void doConfirm() {
                                confirmDialog.dismiss();
                            }

                            @Override
                            public void doCancel() {
                                confirmDialog.dismiss();
                            }
                        });
            }
        });

    }
}
