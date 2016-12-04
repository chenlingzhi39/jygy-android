package com.endeavour.jygy.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.EditText;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.endeavour.jygy.parent.bean.ParentTaskFeedbackReq;
import com.endeavour.jygy.parent.bean.TaskReply;
import com.endeavour.jygy.parent.fragment.ParentTaskFragment;

public class EditTaskFeedBackActivity extends BaseViewActivity {
    private EditText etContent;
    private GetStudenTasksResp task;
    private TaskReply taskReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("反馈");
        showTitleBack();
        task = (GetStudenTasksResp) getIntent().getSerializableExtra("task");
        taskReply = (TaskReply) getIntent().getSerializableExtra("taskReply");
        setTitleRight(R.string.send);
        setMainView(R.layout.activity_edit_task_feedback);
        etContent = (EditText) findViewById(R.id.etContent);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        Tools.hideKeyBorad(this, etContent);
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        ParentTaskFeedbackReq req = new ParentTaskFeedbackReq();
        req.setTaskId(task.getTaskId());
        req.setUserTaskId(task.getUserTasksId());
        req.setContent(content);
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        req.setParentId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setReplyId(taskReply.getId());
        req.setReplyWeek(getIntent().getStringExtra("replyWeek"));
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                LocalBroadcastManager.getInstance(EditTaskFeedBackActivity.this).sendBroadcast(new Intent(ParentTaskFragment.TaskBroadCastReceiver.TASK_BROAD_CAST_RECEIVER_FILTER));
                EditTaskFeedBackActivity.this.finish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(EditTaskFeedBackActivity.this, "暂时不支持个别特殊表情");
            }
        });
    }
}
