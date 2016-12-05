package com.endeavour.jygy.parent.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.AttendReq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 请假
 * Created by wu on 15/12/2.
 */
public class AskForLeaveActivity extends BaseViewActivity {

    private EditText etContent, etStartTime, etEndTime, etEndTimeType, etStartType;

    private TimePickerView TimePickerViewStart, TimePickerViewEnd;
    private Date startDate, endDtae;

    private ArrayList<String> optTimeTypes = new ArrayList<>();
    private OptionsPickerView pwStartPicketView;
    private OptionsPickerView pwEndPicketView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_ask_for_leave);
        etContent = (EditText) findViewById(R.id.etContent);
        etStartTime = (EditText) findViewById(R.id.etStartTime);
        etEndTime = (EditText) findViewById(R.id.etEndTime);
        etEndTimeType = (EditText) findViewById(R.id.etEndTimeType);
        etStartType = (EditText) findViewById(R.id.etStartType);
        findViewById(R.id.etStartTime).setOnClickListener(this);
        findViewById(R.id.etEndTime).setOnClickListener(this);
        etEndTimeType.setOnClickListener(this);
        etStartType.setOnClickListener(this);
        setTitleText("我要请假");
        setTitleRight("发送");
        showTitleBack();

        optTimeTypes.add("全天");
        optTimeTypes.add("上午");
        optTimeTypes.add("下午");
        pwStartPicketView = new OptionsPickerView(this);
        pwStartPicketView.setPicker(optTimeTypes);
        pwStartPicketView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                etStartType.setText(optTimeTypes.get(options1));
            }
        });
        pwEndPicketView = new OptionsPickerView(this);
        pwEndPicketView.setPicker(optTimeTypes);
        pwEndPicketView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                etEndTimeType.setText(optTimeTypes.get(options1));
            }
        });
        etStartType.setText("全天");
        etEndTimeType.setText("全天");

        TimePickerViewStart = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        TimePickerViewStart.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                startDate = date;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = format.format(date);
                etStartTime.setText(dateStr);
            }
        });
        TimePickerViewEnd = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        TimePickerViewEnd.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                endDtae = date;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = format.format(date);
                etEndTime.setText(dateStr);
            }
        });

        if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
            progresser.showError("您的宝宝已毕业, 无法操作", false);
        }

    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        submit();
    }

    private void submit() {
        String content = etContent.getText().toString();
        String startTime = etStartTime.getText().toString();
        String endTime = etEndTime.getText().toString();
        String startTimeType = etStartType.getText().toString();
        String endTimeType = etEndTimeType.getText().toString();

        if (TextUtils.isEmpty(content)) {
            Tools.toastMsg(this, "请输入请假事由");
            return;
        }
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            Tools.toastMsg(this, "请输入请假时间");
            return;
        }
        java.util.Date nowdate = new java.util.Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(nowdate);
        int result = 0;
        try {
            result = format.parse(startTime).compareTo(format.parse(today));
        } catch (Exception e) {
            e.printStackTrace();

        }
        if (endDtae.equals(startDate)) {
            if (!startTimeType.equals(endTimeType)) {
                Tools.toastMsg(this, "一天请假，上下午，全天选择要一致!");
                return;
            }
        }

        if (result < 0 || endDtae.before(startDate)) {
            Tools.toastMsg(this, "输入日期错误");
            return;
        }
        String startTimeTypeReq = "0";
        String endTimeTypeReq = "0";
        if (startTimeType.equals("上午")) {
            startTimeTypeReq = "1";
        } else if (startTimeType.equals("下午")) {
            startTimeTypeReq = "2";
        }
        if (endTimeType.equals("上午")) {
            endTimeTypeReq = "1";
        } else if (endTimeType.equals("下午")) {
            endTimeTypeReq = "2";
        }

        progresser.showProgress();
        AttendReq req = new AttendReq();
        req.setStartTimeType(startTimeTypeReq);
        req.setEndTimeType(endTimeTypeReq);
        req.setAttendType("2");
        req.setParentId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        req.setLeaveStartDate(startTime + " 00:00:00");
        req.setLeaveEndDate(endTime + " 00:00:00");
        req.setLeaveReason(content);
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                Tools.toastMsg(AskForLeaveActivity.this, "请假成功!");
                AskForLeaveActivity.this.finish();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Tools.toastMsg(AskForLeaveActivity.this, response.getMsg());
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.etStartTime:
                TimePickerViewStart.show();
                break;
            case R.id.etEndTimeType:
                pwEndPicketView.show();
                break;
            case R.id.etStartType:
                pwStartPicketView.show();
                break;
            case R.id.etEndTime:
                TimePickerViewEnd.show();
                break;
        }
    }

}
