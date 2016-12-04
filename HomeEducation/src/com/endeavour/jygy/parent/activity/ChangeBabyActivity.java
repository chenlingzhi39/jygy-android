package com.endeavour.jygy.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bigkoo.pickerview.TimePickerView;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.file.FileUploadProxy;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.Student;
import com.endeavour.jygy.parent.bean.UpdateUserReq;
import com.endeavour.jygy.parent.fragment.ParentHomeFragment2;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wizarpos.log.util.LogEx;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wu on 15/11/26.
 */
public class ChangeBabyActivity extends BaseViewActivity {

    private RadioGroup rgBabySex;
    private RadioButton rbMan;
    private RadioButton rbWoman;
    private ImageView ivBabyIcon;

    private EditText etBabyName;
    private EditText etBridthDay;
    private EditText etGrowupID;

    private TimePickerView TimePickerView;

    private String path;
    private String attach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("修改信息");
        setTitleRight("提交");
        showTitleBack();

        setMainView(R.layout.activity_change_baby);

        rgBabySex = (RadioGroup) findViewById(R.id.rgBabySex);
        rbMan = (RadioButton) findViewById(R.id.rbMan);
        rbWoman = (RadioButton) findViewById(R.id.rbWoman);

        etBabyName = (EditText) findViewById(R.id.etBabyName);
        etBridthDay = (EditText) findViewById(R.id.etBridthDay);
        ivBabyIcon = (ImageView) findViewById(R.id.ivBabyIcon);
        etGrowupID = (EditText) findViewById(R.id.etGrowupID);

        etBridthDay.setOnClickListener(this);
        ivBabyIcon.setOnClickListener(this);

        TimePickerView = new TimePickerView(this, com.bigkoo.pickerview.TimePickerView.Type.YEAR_MONTH_DAY);
        TimePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = format.format(date);
                etBridthDay.setText(dateStr);
            }
        });
        ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivBabyIcon, MainApp.getBabyImgOptions());
        etBridthDay.setText(AppConfigHelper.getConfig(AppConfigDef.birthday));
        etBabyName.setText(AppConfigHelper.getConfig(AppConfigDef.babyName));
        if("1".equals(AppConfigHelper.getConfig(AppConfigDef.babySex))){
            rbMan.performClick();
        }else{
            rbWoman.performClick();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ivBabyIcon:
                Intent intent = new Intent(this, BabyImgActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.etBridthDay:
                TimePickerView.show();
                break;
        }
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        submit();
    }

    private void submit() {
        String babyName = etBabyName.getText().toString();
        String birthDay = etBridthDay.getText().toString();
        if (TextUtils.isEmpty(babyName) || TextUtils.isEmpty(birthDay)) {
            Tools.toastMsg(ChangeBabyActivity.this, "请将宝宝信息补充完整");
            return;
        }
        if (isBirthDayBigerToday(birthDay)) {
            Tools.toastMsg(this, "输入日期错误");
            return;
        }
        hideKeyboard();
        progresser.showProgress();
        if (!TextUtils.isEmpty(path)) {
            uploadSignImg(new BaseRequest.ResponseListener() {
                @Override
                public void onSuccess(Response response) {
                    attach = String.valueOf(response.getResult());
                    doUploadAction();
                }

                @Override
                public void onFaild(Response response) {
                    progresser.showContent();
                    Tools.toastMsg(ChangeBabyActivity.this, response.getMsg());
                }
            });
        } else {
            doUploadAction();
        }

    }

    public void uploadSignImg(BaseRequest.ResponseListener listener) {
        FileUploadProxy fileUploader = new FileUploadProxy();
        fileUploader.uploadUserIconFile(path, listener);
    }

    private void doUploadAction() {
        String babyName = etBabyName.getText().toString();
        String birthDay = etBridthDay.getText().toString();

        final Student studen = new Student();
        studen.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        studen.setBirthday(birthDay);
        studen.setGradeLevel(AppConfigHelper.getConfig(AppConfigDef.gradeLevel));
        studen.setSemester(AppConfigHelper.getConfig(AppConfigDef.semester));
        studen.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        studen.setGradeId(AppConfigHelper.getConfig(AppConfigDef.gradeID));
        studen.setName(babyName);
        studen.setSex(rbMan.isChecked() ? "1" : "2");
        String headPortrait = TextUtils.isEmpty(attach) ? AppConfigHelper.getConfig(AppConfigDef.headPortrait) : attach;
        studen.setHeadPortrait(headPortrait);
        studen.setOtherId(etGrowupID.getText().toString());
        final UpdateUserReq req = new UpdateUserReq();
        req.setStudentReq(studen);
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                studen.setUserId(studen.getStudentId());
                MainApp.getInstance().updateBabyInfo(studen);
                Tools.toastMsg(ChangeBabyActivity.this, "修改成功");
                AppConfigHelper.setConfig(AppConfigDef.headPortrait, studen.getHeadPortrait());
                LogEx.d("update headPortrait", studen.getHeadPortrait());
                Intent intent = new Intent(ParentHomeFragment2.UpdateBabyInfoBroadCastAction);
                LocalBroadcastManager.getInstance(ChangeBabyActivity.this).sendBroadcast(intent);
                ChangeBabyActivity.this.finish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(ChangeBabyActivity.this, response.getMsg());
            }
        });
    }

    /**
     * 判断生日是不是比今天大
     * @param birthDay
     * @return
     */
    private boolean isBirthDayBigerToday(String birthDay) {
        Date nowdate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(nowdate);
        int result = 0;
        try {
            result = format.parse(birthDay).compareTo(format.parse(today));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return (result > 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            path = data.getStringExtra("path");
            if (TextUtils.isEmpty(path)) {
                return;
            }
            ImageLoader.getInstance().displayImage("file://" + path, ivBabyIcon, MainApp.getBabyImgOptions());
        }
    }
}
