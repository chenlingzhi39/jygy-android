package com.endeavour.jygy.debug;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.debug.adapter.TestAdapter;
import com.endeavour.jygy.parent.bean.Dynamic;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Debug Created by wu on 15/11/8.
 */
public class DebugActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_debug);
        setTitleText("Debug");
        showTitleBack();
        Button btnChooseImg = (Button) findViewById(R.id.btnChooseImg);
        Button btnDialog1 = (Button) findViewById(R.id.btnDialog1);
        Button btnDialog2 = (Button) findViewById(R.id.btnDialog2);
        Button btnDialog3 = (Button) findViewById(R.id.btnDialog3);
        Button btnDeleteDynamic = (Button) findViewById(R.id.btnDeleteDynamic);
        btnChooseImg.setOnClickListener(this);
        btnDialog1.setOnClickListener(this);
        btnDialog2.setOnClickListener(this);
        btnDialog3.setOnClickListener(this);
        btnDeleteDynamic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnChooseImg:
                intent = new Intent(this, DebugChooseImage.class);
                startActivity(intent);
                break;
            case R.id.btnDialog1:
                showCustomDialog1();
                break;
            case R.id.btnDialog2:
                showCustomDialog2();
                break;
            case R.id.btnDialog3:
                showCustomDialog3();
                break;
            case R.id.btnDeleteDynamic:
                deleteDynamic();
                break;
        }
    }

    private void deleteDynamic() {
        try {
            List<Dynamic> dynamicList = DbHelper.getInstance().getDbController().findAll(Dynamic.class);
            StringBuilder sb = new StringBuilder();
            if (dynamicList != null && (dynamicList.isEmpty() == false)) {
                int deleteCount = dynamicList.size() / 2;
                for (int i = 0; i < deleteCount; i++) {
                    DbHelper.getInstance().getDbController().delete(dynamicList.get(i));
                }
                sb.append("删除了" + deleteCount + "条");
                dynamicList = DbHelper.getInstance().getDbController().findAll(Dynamic.class);
                if (dynamicList != null && (dynamicList.isEmpty() == false)) {
                    sb.append("剩余" + dynamicList.size() + "条");
                } else {
                    sb.append("剩余" + 0 + "条");
                }
            } else {
                sb.append("没有数据!");
            }
            Tools.toastMsg(this, sb.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void showCustomDialog3() {
        ArrayList<DialogMenuItem> testItems = new ArrayList<>();
        testItems.add(new DialogMenuItem("收藏", R.drawable.ic_launcher));
        testItems.add(new DialogMenuItem("下载", R.drawable.ic_launcher));
        testItems.add(new DialogMenuItem("分享", R.drawable.ic_launcher));
        testItems.add(new DialogMenuItem("删除", R.drawable.ic_launcher));
        testItems.add(new DialogMenuItem("歌手", R.drawable.ic_launcher));
        testItems.add(new DialogMenuItem("专辑", R.drawable.ic_launcher));
        final NormalListDialog dialog = new NormalListDialog(this,
                new TestAdapter(this, testItems));
        dialog.title("请选择").titleBgColor(Color.parseColor("#008080")).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                dialog.dismiss();
            }
        });
    }

    private void showCustomDialog2() {
        final String[] stringItems = {"接收消息并提醒", "接收消息但不提醒", "收进群助手且不提醒",
                "屏蔽群消息"};
        final NormalListDialog dialog = new NormalListDialog(this, stringItems);
        dialog.title("请选择")//
                .layoutAnimation(null).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                dialog.dismiss();
            }
        });
    }

    private void showCustomDialog1() {
        final String[] stringItems = {"版本更新", "帮助与反馈", "退出QQ"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this,
                stringItems, findViewById(R.id.rlMain));
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                dialog.dismiss();
            }
        });
    }
}
