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
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.file.FileUploadProxy;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.fragment.ParentHomeFragment2;
import com.endeavour.jygy.teacher.bean.TeacherReq;

/**
 * Created by wu on 15/11/26.
 */
public class ModifyTeacherInfoActivity extends BaseViewActivity {


    public static final String SCHOOLID = "SCHOOLID";
    public static final String PARENT_NAME = "PARENT_NAME";

    private TextView tvParentName;
    private RadioGroup rgBabySex;
    private RadioButton rbMan;

    private EditText et_parentName, et_PhoneNum, et_PhoneNum_two;
    private ImageView ivBabyIcon;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progresser.showProgress();
        setTitleText("修改头像");
//        setTitleRight("提交");
        showTitleBack();
//        setMainView(R.layout.activity_teacher_modify);
//        et_parentName = (EditText) findViewById(R.id.et_parentName);
//        et_PhoneNum = (EditText) findViewById(R.id.et_PhoneNum);
//        et_PhoneNum_two = (EditText) findViewById(R.id.et_PhoneNum_two);
//        ivBabyIcon = (ImageView) findViewById(R.id.ivBabyIcon);
//        ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivBabyIcon, MainApp.getTeacherOptions());
//        ivBabyIcon.setOnClickListener(this);
//        et_parentName.setText(AppConfigHelper.getConfig(AppConfigDef.parentName));
        Intent intent = new Intent(this, BabyImgActivity.class);
        startActivityForResult(intent, 1001);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ivBabyIcon:
                Intent intent = new Intent(this, BabyImgActivity.class);
                startActivityForResult(intent, 1001);
                break;
        }
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        submit();
    }

    private void submit() {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        progresser.showProgress();
        uploadSignImg(new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                final String headUrl = String.valueOf(response.getResult());
                TeacherReq req = new TeacherReq();
                req.setTeacherId(AppConfigHelper.getConfig(AppConfigDef.parentId));
                req.setHeadUrl(String.valueOf(response.getResult()));
                req.setName(AppConfigHelper.getConfig(AppConfigDef.parentName));
                progresser.showProgress();
                NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        AppConfigHelper.setConfig(AppConfigDef.headPortrait, headUrl);
                        Intent intent = new Intent(ParentHomeFragment2.UpdateBabyInfoBroadCastAction);
                        LocalBroadcastManager.getInstance(ModifyTeacherInfoActivity.this).sendBroadcast(intent);
                        ModifyTeacherInfoActivity.this.finish();
                    }

                    @Override
                    public void onFailed(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(ModifyTeacherInfoActivity.this, response.getMsg());
                    }
                });
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Tools.toastMsg(ModifyTeacherInfoActivity.this, response.getMsg());
            }
        });

    }

    public void uploadSignImg(BaseRequest.ResponseListener listener) {
        FileUploadProxy fileUploader = new FileUploadProxy();
        fileUploader.uploadUserIconFile(path, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            path = data.getStringExtra("path");
            if (TextUtils.isEmpty(path)) {
                this.finish();
                return;
            }
//            ImageLoader.getInstance().displayImage("file://" + path, ivBabyIcon);
            submit();
        }else{
            this.finish();
        }
    }
}
