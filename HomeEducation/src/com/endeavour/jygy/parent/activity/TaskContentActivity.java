package com.endeavour.jygy.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.view.HtmlView;
import com.endeavour.jygy.parent.adapter.TaskGridAdapter;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.flyco.roundview.RoundTextView;

import java.util.ArrayList;

/**
 * Created by wu on 15/11/26.
 */
public class TaskContentActivity extends BaseViewActivity implements TaskGridAdapter.TaskGridAdapterCallBack {
    private RoundTextView tv_task_feedback;
    private Animation Anim_Alpha;
    private GetStudenTasksResp task;
    private HtmlView hvTask;
    private TextView hvFinish;
    private GridView gvImgs;
    private TaskGridAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText(R.string.taskcontent);
        showTitleBack();
        task = (GetStudenTasksResp) getIntent().getSerializableExtra("task");
        if (task == null) {
            return;
        }
        setMainView(R.layout.task_content);
        TextView tvTask = (TextView) findViewById(R.id.tvTask);
        tvTask.setText(task.getTaskTitle());
        hvTask = (HtmlView) findViewById(R.id.htmlView);
        hvFinish = (TextView) findViewById(R.id.hvFinish);
        tv_task_feedback = (RoundTextView) findViewById(R.id.tv_task_feedback);
        tv_task_feedback.setOnClickListener(this);
        hvTask.render(task.getTaskContent());

        //小任务附件
        taskAdapter = new TaskGridAdapter(this);
        gvImgs = (GridView) findViewById(R.id.gvImgs);
        taskAdapter.setTaskGridAdapterCallBack(this);
        gvImgs.setAdapter(taskAdapter);

        if ("1".equals(task.getUserTaskStatus())) {//用户任务状态（当此值为1时，客户端应不可在修改回复）
            findViewById(R.id.ll_task_bottom).setVisibility(View.GONE);
            randerAttach();
        } else if (!TextUtils.isEmpty(task.getCompleteTime())) {
            tv_task_feedback.setText("重新提交");
            randerAttach();
        }else{
            findViewById(R.id.llUploadHistroy).setVisibility(View.GONE);
        }
    }

    private void randerAttach() {
        hvFinish.setText(task.getCompleteContent());
        JSONArray attach = JSONArray.parseArray(task.getCompleteAttach());
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
                            pathlist.remove(j);
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


    @Override
    public void onClick(View v) {
        Anim_Alpha = AnimationUtils.loadAnimation(TaskContentActivity.this, R.anim.alpha_action);
        v.startAnimation(Anim_Alpha);
        if (v.getId() == R.id.tv_task_feedback) {
            if("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))){
                Toast.makeText(this, "您的宝宝已毕业, 无法操作", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, EditTaskActivity.class);
            intent.putExtra("GetStudenTasksResp", task);
            startActivityForResult(intent, 10001);
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && resultCode == RESULT_OK) {
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
