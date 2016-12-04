package com.endeavour.jygy.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.teacher.adapter.TeacherPlanDetialAdapter;
import com.endeavour.jygy.teacher.bean.GetLessonPlanReq;
import com.endeavour.jygy.teacher.bean.GetLessonPlanResp;
import com.endeavour.jygy.teacher.bean.GetLessonPlansResp;
import com.endeavour.jygy.teacher.bean.SendTaskReq;
import com.endeavour.jygy.teacher.bean.TeacherContentDetialBean;
import com.endeavour.jygy.teacher.bean.TeacherPlanDetailUserPractices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/11/26.
 */
public class TeachContentActivity extends BaseViewActivity {

    private GetLessonPlansResp resp;
    private GetLessonPlanResp detialResp;
    private ExpandableListView lvTeacherPlanDetail;
    private TeacherPlanDetialAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.teach_content2);
        setTitleText(R.string.teachcontent);
        showTitleBack();
        lvTeacherPlanDetail = (ExpandableListView) findViewById(R.id.lvTeacherPlanDetail);
        adapter = new TeacherPlanDetialAdapter(this);
        lvTeacherPlanDetail.setAdapter(adapter);
        lvTeacherPlanDetail.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                TeacherContentDetialBean detialBean = adapter.getGroup(groupPosition);
                if (detialBean.getLevel() == 3 || detialBean.getLevel() == 2) {
                    Intent intent = new Intent(TeachContentActivity.this, TeachContentDetialActivity.class);
                    intent.putExtra("html", detialBean.getContetn());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        lvTeacherPlanDetail.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                TeacherContentDetialBean detialBean = adapter.getChild(groupPosition, childPosition);
                Intent intent = new Intent(TeachContentActivity.this, TeachContentDetialActivity.class);
                intent.putExtra("html", detialBean.getContetn());
                startActivity(intent);
                return false;
            }
        });
        findViewById(R.id.btnSendTask).setOnClickListener(this);
        resp = (GetLessonPlansResp) getIntent().getSerializableExtra("GetLessonPlansResp");
        if (resp == null) {
            return;
        }
        initData();

    }

    private void initData() {
        progresser.showProgress();
        GetLessonPlanReq req = new GetLessonPlanReq();
        req.setUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setId(resp.getId());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                detialResp = JSONObject.parseObject(String.valueOf(response.getResult()), GetLessonPlanResp.class);
                List<TeacherContentDetialBean> teacherContentDetialBeans = new ArrayList<TeacherContentDetialBean>();
                TeacherContentDetialBean content = new TeacherContentDetialBean();
                content.setContetn(detialResp.getContent());
                content.setTitle(detialResp.getTitle());
                content.setLevel(3);
                teacherContentDetialBeans.add(content);
                if (detialResp.getTasks() != null && detialResp.getTasks().isEmpty() == false) {
                    TeacherContentDetialBean task = new TeacherContentDetialBean();
                    task.setTitle(detialResp.getTasks().get(0).getTitle());
                    task.setContetn(detialResp.getTasks().get(0).getContent());
                    task.setLevel(2);
                    teacherContentDetialBeans.add(task);
                }
                List<TeacherPlanDetailUserPractices> userPractices = detialResp.getUserPractices();
                if (userPractices != null && userPractices.isEmpty() == false) {
                    TeacherContentDetialBean title = new TeacherContentDetialBean();
                    title.setTitle("共育课堂");
                    for (TeacherPlanDetailUserPractices userPractice : userPractices) {
                        TeacherContentDetialBean practice = new TeacherContentDetialBean();
                        practice.setContetn(userPractice.getPracticeContent());
                        practice.setTitle(userPractice.getPracticeTitle());
                        practice.setLevel(1);
                        title.getList().add(practice);
                    }
                    teacherContentDetialBeans.add(title);
                }
                if (teacherContentDetialBeans.isEmpty()) {
                    progresser.showError("暂无数据", false);
                } else {
                    adapter.setDataChanged(teacherContentDetialBeans);
                }
                if (!"1".equals(detialResp.getDupSend()) && !noTask()) {
                    findViewById(R.id.btnSendTask).setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(TeachContentActivity.this, response.getMsg());
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnSendTask) {
            sendTask();
        }
    }

    private void sendTask() {
        if(noTask()){
            return;
        }

        int taskId = detialResp.getTasks().get(0).getTaskId();
        progresser.showProgress();
        SendTaskReq req = new SendTaskReq();
        req.setTaskId(taskId + "");
        req.setUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                Tools.toastMsg(TeachContentActivity.this, "发送成功");
                TeachContentActivity.this.finish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(TeachContentActivity.this, response.getMsg());
            }
        });
    }

    private boolean noTask() {
        return detialResp == null || detialResp.getTasks() == null || detialResp.getTasks().isEmpty() ||detialResp.getTasks().get(0) == null;
    }
}
