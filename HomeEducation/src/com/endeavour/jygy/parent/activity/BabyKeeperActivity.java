package com.endeavour.jygy.parent.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.AddBabyReq;
import com.endeavour.jygy.parent.bean.Parent;
import com.endeavour.jygy.parent.bean.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/11/26.
 */
public class BabyKeeperActivity extends BaseViewActivity implements RadioGroup.OnCheckedChangeListener {


    public static final String SCHOOLID = "SCHOOLID";
    public static final String PARENT_NAME = "PARENT_NAME";

    private TextView tvParentName;
    private RadioGroup rgBabySex;
    private RadioButton rbMan;

    private EditText et_parentName, et_keeper, et_PhoneNum, et_PhoneNum_two;

    private OptionsPickerView pwIdentity;
    private ArrayList<String> optsRoles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("添加监护人");
        setTitleRight("提交");
        showTitleBack();
        setMainView(R.layout.activity_keeper_baby);
        et_parentName = (EditText) findViewById(R.id.et_parentName);
        et_keeper = (EditText) findViewById(R.id.et_keeper);
        et_PhoneNum = (EditText) findViewById(R.id.et_PhoneNum);
        et_PhoneNum_two = (EditText) findViewById(R.id.et_PhoneNum_two);
        et_keeper.setOnClickListener(this);

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
                et_keeper.setText(tx);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.et_keeper:
                hideKeyboard();
                pwIdentity.show();
                break;
        }
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        submit();
    }

    private void submit() {
        String parentName = et_parentName.getText().toString();
        String role = et_keeper.getText().toString();
        String phoneNum = et_PhoneNum.getText().toString();
        String phoneNum_two = et_PhoneNum_two.getText().toString();
        if (TextUtils.isEmpty(parentName) || TextUtils.isEmpty(role) || TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(phoneNum_two)) {
            Tools.toastMsg(BabyKeeperActivity.this, "请将信息补充完整");
            return;
        }
        parentName = parentName.trim();
        if (TextUtils.isEmpty(parentName) || TextUtils.isEmpty(role) || TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(phoneNum_two)) {
            Tools.toastMsg(BabyKeeperActivity.this, "请将信息补充完整");
            return;
        }
        if (!phoneNum.startsWith("1") || phoneNum.length() != 11) {
            Tools.toastMsg(BabyKeeperActivity.this, "请输入正确的手机号码");
            return;
        }
        if (!phoneNum.equals(phoneNum_two)) {
            Tools.toastMsg(BabyKeeperActivity.this, "手机号输入不一致");
            return;
        }

        final Student studen = new Student();
        studen.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        Parent parent = new Parent();
        parent.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        parent.setMobileNo(phoneNum_two);
        parent.setParentName(parentName);
        parent.setProtectRole(getRolesId(role));
        List<Parent> parents = new ArrayList<Parent>();
        parents.add(parent);
        final AddBabyReq req = new AddBabyReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setParents(parents);
        req.setUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setStudentReq(studen);
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                Tools.toastMsg(BabyKeeperActivity.this, "添加成功");
                BabyKeeperActivity.this.finish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(BabyKeeperActivity.this, response.getMsg());
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
