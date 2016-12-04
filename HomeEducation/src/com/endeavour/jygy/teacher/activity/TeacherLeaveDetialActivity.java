package com.endeavour.jygy.teacher.activity;

import android.content.Intent;
import android.os.Bundle;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.view.HtmlView;
import com.endeavour.jygy.common.xg.receiver.MessageReceiver;
import com.endeavour.jygy.parent.activity.MessageOpter;
import com.endeavour.jygy.parent.bean.Message;

public class TeacherLeaveDetialActivity extends BaseViewActivity {
    private Message resp;
    private HtmlView htmlView;
    MessageOpter messopter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resp = (Message) getIntent().getSerializableExtra("message");
        if (resp == null) {
            return;
        }
        messopter = new MessageOpter();
        if(resp.getType().equals("2"))
        	setTitleText("请假详情");
        else if(resp.getType().equals("1"))
        	setTitleText("公告详情");
        showTitleBack();
        setMainView(R.layout.activity_teacher_feedback_detail_list);
        	htmlView = (HtmlView) findViewById(R.id.htmlView);
        initData();
    }

    private void initData() {
        progresser.showProgress();
        if(resp.getType().equals("2"))
        	htmlView.render_str(resp.getContent()+"\n"+"请假时间:"+resp.getMesstime());
        if(resp.getType().equals("1")){
        	htmlView.render(resp.getContentHtml());
        }
        progresser.showContent();
        messopter.updateMessagesFromDB(resp);
        Intent intent = new Intent(MessageReceiver.mess_action);
        intent.putExtra("hasmess", "1");//通知主界面显示消息数量
        sendBroadcast(intent);
    }
}
