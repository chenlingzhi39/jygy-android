package com.endeavour.jygy.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.view.HtmlView;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.TaskGridAdapter;
import com.endeavour.jygy.parent.adapter.TaskGridAdapter.TaskGridAdapterCallBack;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskDetailReq;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskDetialResp;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskResp;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;

public class TeacherTaskDetialActivity extends BaseViewActivity implements TaskGridAdapterCallBack {
    private GetTeacherTaskResp resp;
    private TextView tvTaskTitle;
    private HtmlView hvTask;
    private TextView tvFinishTitle;
    private TextView hvFinish;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private String picpath = Environment.getExternalStorageDirectory()
            + "/jygy/pic/";
    String sdpath = Environment.getExternalStorageDirectory().getPath();
    private String picname = ""; // 每次拍照的图片名称
    private String videopath = "";
    private String soundpath = "";
    private GridView gvImgs;
    private TaskGridAdapter taskAdapter;
    private RoundTextView tvDiscuss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resp = (GetTeacherTaskResp) getIntent().getSerializableExtra("GetTeacherTaskResp");
        if (resp == null) {
            return;
        }
        setTitleText("小任务");
        showTitleBack();
        setMainView(R.layout.activity_teacher_task_detial);
        tvTaskTitle = (TextView) findViewById(R.id.tvTaskTitle);
        hvTask = (HtmlView) findViewById(R.id.hvTask);
        tvFinishTitle = (TextView) findViewById(R.id.tvFinishTitle);
        hvFinish = (TextView) findViewById(R.id.hvFinish);
        tvFinishTitle.setText("完成情况");
        //小任务附件
        taskAdapter = new TaskGridAdapter(this);
        gvImgs = (GridView) findViewById(R.id.gvImgs);
        taskAdapter.setTaskGridAdapterCallBack(this);
        gvImgs.setAdapter(taskAdapter);
        tvDiscuss = (RoundTextView) findViewById(R.id.tvDiscuss);
        tvDiscuss.setOnClickListener(this);
        if(MainApp.isTeacher()){
            tvDiscuss.setVisibility(View.VISIBLE);
        }else{
            tvDiscuss.setVisibility(View.GONE);
        }
        initData();
    }

    private void initData() {
        progresser.showProgress();
        final GetTeacherTaskDetailReq req = new GetTeacherTaskDetailReq();
        req.setId(resp.getId());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                GetTeacherTaskDetialResp resp = JSONObject.parseObject(String.valueOf(response.getResult()), GetTeacherTaskDetialResp.class);
                if (resp == null) {
                    progresser.showError("没有相关记录", false);
                } else {
                    if ("1".equals(resp.getStatus())) {
                        tvDiscuss.setVisibility(View.GONE);
                        findViewById(R.id.tvDiscussDisable).setVisibility(View.VISIBLE);
                    }
                    tvTaskTitle.setText(TeacherTaskDetialActivity.this.resp.getTitle());
                    hvTask.render(resp.getContent());
                    hvFinish.setText(resp.getCompleteContent());
                    JSONArray attach = JSONArray.parseArray(resp.getCompleteAttachement());
                    if (attach != null && !attach.isEmpty()) {
                        gvImgs.setVisibility(View.VISIBLE);
                        ArrayList<String> pathlist = new ArrayList<String>();
                        for (int i = 0; i < attach.size(); i++) {
                            pathlist.add(attach.getString(i));
                        }
                        int flag = 0;
                        for (int i = 0; i < pathlist.size() - 1; i++) {
                            for (int j = i + 1; j < pathlist.size(); j++) {
                                if (pathlist.get(i).split("\\.")[2].equals(pathlist.get(j).split("\\.")[2])) { //文件名比对
                                    if (pathlist.get(i).contains(".jpeg")
                                            || pathlist.get(i).contains(".jpg")) {
                                        pathlist.remove(i);
                                        flag = 1;
                                        break;
                                    } else if (pathlist.get(j).contains(".jpeg")
                                            || pathlist.get(j).contains(".jpg")) {
                                        pathlist.remove(i);
                                        flag = 1;
                                        break;
                                    }
                                }
                            }
                            if (flag == 1)
                                break;
                        }
                        taskAdapter.addDataChanged(pathlist);
                    } else {
                        gvImgs.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Tools.toastMsg(TeacherTaskDetialActivity.this, response.getMsg());
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.tvDiscuss) {
//            private GetTeacherTaskResp resp;
            Intent intent = new Intent(this, TeacherTaskFeedbackActivity.class);
            intent.putExtra("GetTeacherTaskResp", resp);
            startActivityForResult(intent, 10001);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10001 && resultCode == RESULT_OK){
            setResult(RESULT_OK);
            this.finish();
        }
    }

    @Override
    public void takePic() {

    }

    @Override
    public void choosePic() {

    }

    @Override
    public void takeVideo() {

    }

    @Override
    public void showArcLayout() {

    }
}
