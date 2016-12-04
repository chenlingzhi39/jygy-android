package com.endeavour.jygy.parent.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.AdjustClassApplyReq;
import com.endeavour.jygy.parent.bean.GetGradeClassReq;
import com.endeavour.jygy.parent.bean.GetGradeClassResp;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.roundview.RoundTextView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/11/26.
 */
public class ChangeClassActivity extends ChangeClassTransFlow {

    private static final String LOG_TAG = ChangeClassActivity.class.getSimpleName();

    protected TextView tvBabyName;
    protected TextView tvPhoneNum;
    protected TextView tvLocation;
    protected TextView tvSchool;
    protected TextView tvClass;
    protected TextView tvChooseClass;
    protected EditText etClass;
    protected RoundTextView btnApply;

    private OptionsPickerView pwClass;
    private List<GetGradeClassResp> classes;

    private GetGradeClassResp classResp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("申请调班");
        setTitleRight("换班记录");
        showTitleBack();
        setMainView(R.layout.activity_change_class);
        initView();

        if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
            progresser.showError("您的宝宝已毕业, 无法操作", false);
        } else {
            doGetGradeClassAction();
        }
    }

    private void initClasses() {
        classes = getClassesFromDb(AppConfigHelper.getConfig(AppConfigDef.gradeID));
        if (classes != null && !classes.isEmpty()) {
            final ArrayList<String> optsClass = new ArrayList<>();
            for (GetGradeClassResp resp : classes) {
                if (!resp.getName().equals(AppConfigHelper.getConfig(AppConfigDef.className))) {
                    optsClass.add(resp.getName());
                    classResp = resp;
                    etClass.setText(classResp.getName());
                }

            }
            pwClass = new OptionsPickerView(this);
            pwClass.setPicker(optsClass);
            pwClass.setLabels("");
            pwClass.setCyclic(false);
            pwClass.setSelectOptions(0);
            //监听确定选择按钮
            pwClass.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    etClass.setText(optsClass.get(options1));
                    classResp = classes.get(options1);
                }
            });
            etClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tools.hideKeyBorad(ChangeClassActivity.this, getCurrentFocus());
                    pwClass.show();
                }
            });
        }
    }


    private void initView() {
        tvBabyName = (TextView) findViewById(R.id.tv_babyName);
        tvPhoneNum = (TextView) findViewById(R.id.tv_ParentName);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvSchool = (TextView) findViewById(R.id.tvSchool);
        tvClass = (TextView) findViewById(R.id.tvClass);
        tvChooseClass = (TextView) findViewById(R.id.tvChooseClass);
        etClass = (EditText) findViewById(R.id.etClass);
        btnApply = (RoundTextView) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        tvBabyName.setText(AppConfigHelper.getConfig(AppConfigDef.babyName));
        tvPhoneNum.setText(AppConfigHelper.getConfig(AppConfigDef.phoneNum));
        tvSchool.setText(AppConfigHelper.getConfig(AppConfigDef.schoolName));
        tvLocation.setText(AppConfigHelper.getConfig(AppConfigDef.location));
        tvClass.setText(AppConfigHelper.getConfig(AppConfigDef.className));
    }

    private void showDialog() {
        final MaterialDialog dialog = new MaterialDialog(this);
        dialog.title("是否确定更换班级");
        dialog.setOnBtnLeftClickL(new OnBtnLeftClickL() {
            @Override
            public void onBtnLeftClick() {
                dialog.dismiss();
            }
        });
        dialog.setOnBtnRightClickL(new OnBtnRightClickL() {
            @Override
            public void onBtnRightClick() {
                dialog.dismiss();
                apply();
            }
        });
        dialog.show();
    }

    private void apply() {
        if (classResp == null) {
            return;
        }
        progresser.showProgress();
        AdjustClassApplyReq adjustClassApplyReq = new AdjustClassApplyReq();
        adjustClassApplyReq.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        adjustClassApplyReq.setNewClassId(classResp.getId());
        adjustClassApplyReq.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        adjustClassApplyReq.setStudengId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        NetRequest.getInstance().addRequest(adjustClassApplyReq, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                Toast.makeText(ChangeClassActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
                ChangeClassActivity.this.finish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    private void doGetGradeClassAction() {
        progresser.showProgress();
        GetGradeClassReq req = new GetGradeClassReq();
        final String schoolID = AppConfigHelper.getConfig(AppConfigDef.schoolID);
        req.setKindergartenId(schoolID);//学校 ID
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                List<GetGradeClassResp> resp = JSONObject.parseArray(String.valueOf(response.getResult()), GetGradeClassResp.class);
                if (resp == null) {
                    Tools.toastMsg(ChangeClassActivity.this, "没有找到班级对应关系");
                    return;
                }
                saveClasses(resp, schoolID);
                initClasses();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(ChangeClassActivity.this, response.getMsg());
            }
        });
    }

    private void saveClasses(List<GetGradeClassResp> resp, String schoolID) {
        try {
            DbUtils dbController = DbHelper.getInstance().getDbController();
            dbController.dropTable(GetGradeClassResp.class);
            dbController.saveAll(resp);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private List<GetGradeClassResp> getClassesFromDb(String gradeID) {
        try {
            return DbHelper.getInstance().getDbController().findAll(Selector.from(GetGradeClassResp.class).where("parentID", "=", gradeID));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        startActivity(ChangeClassHistoryActivity.getStartIntent(this));
    }
}
