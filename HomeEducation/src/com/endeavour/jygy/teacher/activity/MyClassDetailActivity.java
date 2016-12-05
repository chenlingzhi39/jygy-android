package com.endeavour.jygy.teacher.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.TaskListAdapter;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.endeavour.jygy.parent.bean.GetStudentTasksReq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyClassDetailActivity extends BaseViewActivity {

	private static final String TAG = MyClassDetailActivity.class.getName();

	private ListView task_list;
	private TaskListAdapter taskAdapter;
	private List<GetStudenTasksResp> getStudenTasksResps;

	// private ArrayList<HashMap<String, String>> classlist;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMainView(R.layout.class_detail);
		setTitleText("班级详情");
		showTitleBack();
		initview();
		initdata();
	}

	public void setDataChanged(List<GetStudenTasksResp> resps) {
		if (resps == null) {
			return;
		}
		getStudenTasksResps = new ArrayList<>();
		Iterator<GetStudenTasksResp> iterator = resps.iterator();
		while (iterator.hasNext()) {
			GetStudenTasksResp resp = iterator.next();
			if (TextUtils.isEmpty(resp.getReviewContent())
					|| TextUtils.isEmpty(resp.getReviewTeacherName())
					|| TextUtils.isEmpty(resp.getReviewTime())) {
				continue;
			} else {
				getStudenTasksResps.add(resp);
			}
		}
		if (taskAdapter != null) {
			taskAdapter.setDataChanged(getStudenTasksResps);
		}
	}

	private void initdata() {
		progresser.showProgress();
		GetStudentTasksReq req = new GetStudentTasksReq();
		req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
		req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
		req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
		req.setPageNo("1");
		req.setPerNo("999");
		NetRequest.getInstance().addRequest(req,
				new BaseRequest.ResponseListener() {
					@Override
					public void onSuccess(Response response) {
						progresser.showContent();
						List<GetStudenTasksResp> resps = JSONArray.parseArray(String.valueOf(response.getResult()), GetStudenTasksResp.class);
						getStudenTasksResps = resps;
						refreshdata();
					}

					@Override
					public void onFailed(Response response) {
						progresser.showContent();
						refreshdata();
					}
				});
	}

	private void refreshdata(){
		if (getStudenTasksResps != null && !getStudenTasksResps.isEmpty()) {
			taskAdapter.setDataChanged(getStudenTasksResps);
		} else {
			progresser.showError("暂无数据", false);
		}
	}

	private void initview() {
		task_list = (ListView) findViewById(R.id.task_list);
		taskAdapter = new TaskListAdapter(this);
		task_list.setAdapter(taskAdapter);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
