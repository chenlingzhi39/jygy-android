package com.endeavour.jygy.teacher.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.base.BaseViewFragment;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.schoolboss.activity.SchoolGradesActivity;
import com.endeavour.jygy.teacher.activity.MyClassGridActivity;
import com.endeavour.jygy.teacher.adapter.GrowUpAdapter;
import com.endeavour.jygy.teacher.bean.GetGrowUpDataReq;
import com.endeavour.jygy.teacher.bean.GetGrowUpDataResp;
import com.endeavour.jygy.teacher.bean.GrowUpBean;
import com.endeavour.jygy.teacher.dialog.ConfirmDialog;

/**
 * Created by wu on 15/12/25.
 */
public class TeacherGrowupFragment2 extends BaseViewFragment implements AdapterView.OnItemClickListener {

    private GridView gvGrowUp;
    private Button btnGrowDo;
    private GrowUpAdapter adapter;

    @Override
    public void initView() {
        setMainView(R.layout.activity_growup);
        setTitleText("成长档案");
        showTitleBack();
        gvGrowUp = (GridView) mainView.findViewById(R.id.gvGrowUp);
        btnGrowDo = (Button) mainView.findViewById(R.id.btnGrowDo);
        adapter = new GrowUpAdapter(getActivity());
        gvGrowUp.setAdapter(adapter);
        gvGrowUp.setOnItemClickListener(this);
        btnGrowDo.setOnClickListener(this);
//        if (MainApp.isTeacher()) {
//            setTitleRight("切换学生");
//        } else
        if (MainApp.isShoolboss()) {
            setTitleRight("切换班级");
            AppConfigHelper.setConfig(AppConfigDef.classID, "");
            AppConfigHelper.setConfig(AppConfigDef.gradeID, "");
            AppConfigHelper.setConfig(AppConfigDef.gradeNick, "");
            AppConfigHelper.setConfig(AppConfigDef.classNick, "");
            progresser.showError("请选择班级", false);
        }else{
            initData();
        }
    }

    public void initData() {
        progresser.showProgress();
        final GetGrowUpDataReq req = new GetGrowUpDataReq();
        req.setPageNum("1");
        req.setPageSize("9999");
        if (MainApp.isTeacher()) {
            req.setType("3");
            req.setKindergartenId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
            req.setKindergartenClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        } else if (MainApp.isShoolboss()) {
            req.setType("2");
            req.setKindergartenId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        } else {
            req.setType("1");
            req.setUserid(AppConfigHelper.getConfig(AppConfigDef.studentID));
        }
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                GetGrowUpDataResp resp = JSONObject.parseObject(String.valueOf(response.getResult()), GetGrowUpDataResp.class);
                if (resp != null && resp.getData() != null && !resp.getData().isEmpty()) {
                    adapter.setDataChanged(resp.getData());
                } else {
                    progresser.showError("暂无数据", false);
                }
            }

            @Override
            public void onFaild(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnGrowDo) {
            showDialog();
        }
    }

    private void showDialog() {
        final ConfirmDialog confirmDialog = new ConfirmDialog(getActivity(), "确定要退出吗?", "退出", "取消");
        confirmDialog.show();
        confirmDialog.setClicklistener(new ConfirmDialog.ClickListenerInterface() {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GrowUpBean growUpBean = adapter.getItem(position);
        String url = "http://czda.rulingtong.cn//czda/web/tempTool/editWork.action?workId=" + growUpBean.getE_code();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        if (MainApp.isTeacher()) {
            Intent intent = new Intent(getActivity(), MyClassGridActivity.class);
            intent.putExtra("title", "切换学生");
            this.startActivityForResult(intent, 10002);
        } else if (MainApp.isShoolboss()) {
            Intent intent = new Intent(getActivity(), SchoolGradesActivity.class);
            intent.putExtra("title", "切换班级");
            this.startActivityForResult(intent, 10001);
        }
    }

    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 10001 && arg1 == Activity.RESULT_OK) {
            if (MainApp.isShoolboss()) {
                initData();
            } else {
                Intent intent = new Intent(getActivity(), MyClassGridActivity.class);
                intent.putExtra("title", "切换学生");
                this.startActivityForResult(intent, 10002);
            }
        } else if (arg0 == 10002 && arg1 == Activity.RESULT_OK) {
            initData();
        }
    }
}
