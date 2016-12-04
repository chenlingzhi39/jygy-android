package com.endeavour.jygy.parent.activity;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.bean.GetGradeClassReq;
import com.endeavour.jygy.parent.bean.GetGradeClassResp;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by wu on 16/1/7.
 */
public class GradeClassOpter {

    public List<GetGradeClassResp> getClasses(String gradeID) {
        try {
            return DbHelper.getInstance().getDbController().findAll(Selector.from(GetGradeClassResp.class).where("parentID", "=", gradeID));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GetGradeClassResp> getGrades() {
        try {
            final String schoolID = AppConfigHelper.getConfig(AppConfigDef.schoolID);
            return DbHelper.getInstance().getDbController().findAll(Selector.from(GetGradeClassResp.class).where("parentId", "=", schoolID));//年级列表
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doGetGradeClassAction(final BaseRequest.ResponseListener listener) {
        GetGradeClassReq req = new GetGradeClassReq();
        final String schoolID = AppConfigHelper.getConfig(AppConfigDef.schoolID);
        req.setKindergartenId(schoolID);//学校 ID
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                List<GetGradeClassResp> resp = JSONObject.parseArray(String.valueOf(response.getResult()), GetGradeClassResp.class);
                if (resp == null || resp.isEmpty()) {
                    listener.onFaild(new Response(1, "没有找到班级对应关系"));
                } else {
                    try {
                        DbUtils dbController = DbHelper.getInstance().getDbController();
                        dbController.dropTable(GetGradeClassResp.class);
                        dbController.saveAll(resp);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    List<GetGradeClassResp> grades = getGrades();
                    if (grades == null || grades.isEmpty()) {
                        listener.onFaild(new Response(1, "没有找到班级对应关系"));
                    } else {
                        listener.onSuccess(new Response(0, "success", grades));
                    }
                }

            }

            @Override
            public void onFaild(Response response) {
                listener.onFaild(response);
            }
        });
    }


}
