package com.endeavour.jygy.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.TimeUtils;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.file.FileUploadProxy;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.login.activity.bean.LoginResp;
import com.endeavour.jygy.parent.bean.AddBabyReq;
import com.endeavour.jygy.parent.bean.GetGradeClassResp;
import com.endeavour.jygy.parent.bean.Student;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wu on 15/11/26.
 */
public class EditBabyActivity extends BaseViewActivity implements RadioGroup.OnCheckedChangeListener {


    public static final String SCHOOLID = "SCHOOLID";
    public static final String PARENT_NAME = "PARENT_NAME";

    private TextView tvParentName;
    private RadioGroup rgBabySex;
    private RadioButton rbMan;

    private EditText etRole;
    private EditText etClass;
    private EditText etGrade;
    private EditText etBabyName;
    private EditText etBridthDay;
    private EditText etParentName;
    private EditText etPhoneNum;
    private ImageView ivBabyIcon;

    private OptionsPickerView pwIdentity, pwClass, pwGrade;
    private TimePickerView timePopupWindow;
    private ArrayList<String> optsRoles = new ArrayList<>();
    private ArrayList<String> optsGrade = new ArrayList<>();
    private ArrayList<String> optsClass = new ArrayList<>();

    private List<GetGradeClassResp> grades;
    private List<GetGradeClassResp> classes;

    private int selectedGrade = 0;
    private int selectedClass = 0;

    private String path;
    private String attach;

    private GradeClassOpter opter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("宝宝信息");
        setTitleRight("提交");
        showTitleBack();

        opter = new GradeClassOpter();

        setMainView(R.layout.activity_edit_baby);

        tvParentName = (TextView) findViewById(R.id.tvParentName);
        rgBabySex = (RadioGroup) findViewById(R.id.rgBabySex);
        rbMan = (RadioButton) findViewById(R.id.rbMan);
        rgBabySex.setOnCheckedChangeListener(this);
        rbMan.performClick();

        etRole = (EditText) findViewById(R.id.etRole);
        etClass = (EditText) findViewById(R.id.etClass);
        etGrade = (EditText) findViewById(R.id.etGrade);
        etBabyName = (EditText) findViewById(R.id.etBabyName);
        etBridthDay = (EditText) findViewById(R.id.etBridthDay);
        etParentName = (EditText) findViewById(R.id.etParentName);
        etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
        ivBabyIcon = (ImageView) findViewById(R.id.ivBabyIcon);

        etRole.setOnClickListener(this);
        etClass.setOnClickListener(this);
        etGrade.setOnClickListener(this);
        etBridthDay.setOnClickListener(this);
        ivBabyIcon.setOnClickListener(this);
        //etParentName.setOnClickListener(this);

        //1 父亲 2母亲 3 长辈 4 保姆 5其他
        optsRoles.add("父亲");
        optsRoles.add("母亲");
        optsRoles.add("长辈");
        optsRoles.add("保姆");
        optsRoles.add("其他");

        pwIdentity = new OptionsPickerView(this);
        pwIdentity.setPicker(optsRoles);
        pwIdentity.setLabels("角色");
        pwIdentity.setCyclic(false);
        pwIdentity.setSelectOptions(0);
        //监听确定选择按钮
        pwIdentity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = optsRoles.get(options1);
                etRole.setText(tx);
            }
        });
        pwGrade = new OptionsPickerView(this);
        pwGrade.setPicker(optsGrade);
        pwGrade.setLabels("");
        pwGrade.setCyclic(false);
        pwGrade.setSelectOptions(0);
        //监听确定选择按钮
        pwGrade.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = optsGrade.get(options1);
                etGrade.setText(tx);
                selectedGrade = options1;
                etClass.setText("");
                selectedClass = 0;
            }
        });
        pwClass = new OptionsPickerView(this);
        pwClass.setPicker(optsClass);
        pwClass.setCyclic(false);
        pwClass.setLabels("");
        pwClass.setSelectOptions(0);
        //监听确定选择按钮
        pwClass.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                selectedClass = options1;
                String tx = optsClass.get(options1);
                etClass.setText(tx);
            }
        });

        timePopupWindow = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        timePopupWindow.setTime(new Date());
        timePopupWindow.setCancelable(true);
        timePopupWindow.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = format.format(date);
                try {
					Date now = format.parse(dateStr);
					Date before = format.parse(TimeUtils.getCurrentDate());
					boolean flag = now.before(before);
					if(flag)
						etBridthDay.setText(dateStr);
					else{
						if(!TimeUtils.getCurrentDate().equals(dateStr))
							Tools.toastMsg(EditBabyActivity.this, "不能大于当前日期!");
					}
					etBridthDay.setText(dateStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
            }
        });

        tvParentName.setText(AppConfigHelper.getConfig(AppConfigDef.parentName));

        doGetGradeClassAction();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.etGrade:
                pwGrade.show();
                break;
            case R.id.ivBabyIcon:
                Intent intent = new Intent(this, BabyImgActivity.class);
                startActivityForResult(intent, 1001);
                break;
            case R.id.etClass:
                String grade = etGrade.getText().toString();
                if (TextUtils.isEmpty(grade)) {
                    Tools.toastMsg(this, "请先选择年级");
                    return;
                }
                classes = opter.getClasses(grades.get(selectedGrade).getId());
                if (classes == null || classes.isEmpty()) {
                    Tools.toastMsg(this, "没有对应的班级");
                    return;
                }
                optsClass.clear();
                for (GetGradeClassResp resp : classes) {
                    String name = resp.getName();
                    if (TextUtils.isEmpty(name)) {
                        continue;
                    }
                    optsClass.add(name);
                }
                if (optsClass == null || optsClass.isEmpty()) {
                    Tools.toastMsg(this, "没有对应的班级");
                    return;
                }
                pwClass.setPicker(optsClass);
                pwClass.show();
                break;
            case R.id.etBridthDay:
                timePopupWindow.show();
                break;
            case R.id.etRole:
                pwIdentity.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            path = data.getStringExtra("path");
            if (TextUtils.isEmpty(path)) {
                return;
            }
            ImageLoader.getInstance().displayImage("file://" + path, ivBabyIcon);
        }
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        submit();
    }

    private void submit() {
        String strGrade = etGrade.getText().toString();
        String strClass = etClass.getText().toString();
        String babyName = etBabyName.getText().toString();
        String birthDay = etBridthDay.getText().toString();
        if (TextUtils.isEmpty(strGrade) || TextUtils.isEmpty(strClass) || TextUtils.isEmpty(babyName) || TextUtils.isEmpty(birthDay)) {
            Tools.toastMsg(EditBabyActivity.this, "请将宝宝信息补充完整");
            return;
        }

        progresser.showProgress();
        if (!TextUtils.isEmpty(path)) {
            uploadSignImg(new BaseRequest.ResponseListener() {
                @Override
                public void onSuccess(Response response) {
                    attach = String.valueOf(response.getResult());
                    doEditBabyInfoAciton();
                }

                @Override
                public void onFailed(Response response) {
                    progresser.showContent();
                    Tools.toastMsg(EditBabyActivity.this, response.getMsg());
                }
            });
        } else {
            doEditBabyInfoAciton();
        }
    }

    private void doEditBabyInfoAciton() {
        String babyName = etBabyName.getText().toString();
        String birthDay = etBridthDay.getText().toString();

        babyName = babyName.trim();
        if (TextUtils.isEmpty(babyName) || TextUtils.isEmpty(birthDay)) {
            Tools.toastMsg(EditBabyActivity.this, "请将宝宝信息补充完整");
            return;
        }

        final Student studen = new Student();
        studen.setBirthday(birthDay);
        studen.setClassId(classes.get(selectedClass).getId());
        studen.setGradeId(grades.get(selectedGrade).getId());
        studen.setName(babyName);
        studen.setSex(rbMan.isChecked() ? "1" : "2");
        studen.setHeadPortrait(attach);
//        Parent parent = new Parent();
//        parent.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
//        parent.setMobileNo(newPhone);
//        parent.setParentName(parentName);
//        parent.setProtectRole(getRolesId(role));
//        List<Parent> parents = new ArrayList<Parent>();
//        parents.add(parent);

        final AddBabyReq req = new AddBabyReq();
        req.setStudentReq(studen);
        req.setClassId(classes.get(selectedClass).getId());
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
//        req.setParents(parents);
        req.setUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        //添加人员
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                String studentId = String.valueOf(response.getResult());
                studen.setUserId(studentId);
                LoginResp resp = JSONObject.parseObject(AppConfigHelper.getConfig(AppConfigDef.loginResp), LoginResp.class);
                List<Student> children = resp.getChildInfo();
                if (children == null) {
                    children = new ArrayList<Student>();
                }
                children.add(studen);
                resp.setChildInfo(children);
                AppConfigHelper.setConfig(AppConfigDef.loginResp, JSONObject.toJSONString(resp));
//                Tools.toActivity(EditBabyActivity.this, ChooseBabyActivity.class);
                setResult(RESULT_OK);
                EditBabyActivity.this.finish();
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Tools.toastMsg(EditBabyActivity.this, response.getMsg());
            }
        });
    }

    public void uploadSignImg(BaseRequest.ResponseListener listener) {
        FileUploadProxy fileUploader = new FileUploadProxy();
        fileUploader.uploadUserIconFile(path, listener);
    }

    private void doGetGradeClassAction() {
        progresser.showProgress();
        opter.doGetGradeClassAction(new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                grades = (List<GetGradeClassResp>) response.getResult();
                optsGrade.clear();
                for (GetGradeClassResp temp : grades) {
                    optsGrade.add(temp.getName());
                }
                pwGrade.setPicker(optsGrade);
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                Tools.toastMsg(EditBabyActivity.this, "没有找到班级对应关系");
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbMan) {

        } else if (checkedId == R.id.rbWoman) {

        }
    }

    public String getRolesId(String role) {
        for (int i = 0; i < optsRoles.size(); i++) {
            if (optsRoles.get(i).equals(role)) {
                return (i + 1) + "";
            }
        }
        return "0";
    }
}
