package com.endeavour.jygy.app;

import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.LogUtil;
import com.endeavour.jygy.common.crash.CrashUtil;
import com.endeavour.jygy.common.db.AppConfig;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.endeavour.jygy.common.db.DefaultDbHelper;
import com.endeavour.jygy.login.activity.bean.UserInfo;
import com.endeavour.jygy.parent.bean.Dynamic;
import com.endeavour.jygy.parent.bean.GetGradeClassResp;
import com.endeavour.jygy.parent.bean.Student;
import com.endeavour.jygy.teacher.bean.GetLessonPlansResp;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskFeedbackListResp;
import com.endeavour.jygy.teacher.bean.GetTeacherTaskResp;
import com.lidroid.xutils.exception.DbException;
import com.wizarpos.log.util.LogEx;

import song.image.crop.ImageChooseApp;

/**
 * Created by wu on 15/11/2.
 */
public class MainApp extends ImageChooseApp {
    private static MainApp app;

    public static MainApp getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        init();
    }

    private void init() {
        CrashUtil.init(this);
        LogUtil.init(this);
        DefaultDbHelper.getInstance().init(this);
        DefaultAppConfigHelper.setConfig(AppConfigDef.ip, Constants.DETAULT_IP);
        DefaultAppConfigHelper.setConfig(AppConfigDef.port, Constants.DETAULT_PORT);
    }

    public void updateParentInfo(UserInfo resp) {
        AppConfigHelper.setConfig(AppConfigDef.parentId, resp.getUserId());
        LogEx.d("updateParentInfo parentId", resp.getUserId());
        AppConfigHelper.setConfig(AppConfigDef.roleKey, resp.getRoleKey());
        LogEx.d("updateParentInfo roleKey", resp.getRoleKey());
        AppConfigHelper.setConfig(AppConfigDef.schoolID, resp.getKinderId());
        LogEx.d("updateParentInfo schoolID", resp.getKinderId());
        AppConfigHelper.setConfig(AppConfigDef.roleValue, resp.getRoleValue());
        LogEx.d("updateParentInfo roleValue", resp.getRoleValue());
        AppConfigHelper.setConfig(AppConfigDef.parentName, resp.getUserName());
        LogEx.d("updateParentInfo parentName", resp.getUserName());
        AppConfigHelper.setConfig(AppConfigDef.classID, resp.getClassId());
        LogEx.d("updateParentInfo classID", resp.getClassId());
        AppConfigHelper.setConfig(AppConfigDef.gradeLevel, resp.getGradeLevel());
        LogEx.d("updateParentInfo gradeLevel", resp.getClassId());
        AppConfigHelper.setConfig(AppConfigDef.gradeNick, resp.getGradeNick());
        LogEx.d("updateBabyInfo gradeNick", resp.getGradeNick());
        AppConfigHelper.setConfig(AppConfigDef.classNick, resp.getClassNick());
        LogEx.d("updateBabyInfo classNick", resp.getClassNick());
        AppConfigHelper.setConfig(AppConfigDef.semester, resp.getSemester());
        LogEx.d("updateParentInfo semester", resp.getClassId());
        AppConfigHelper.setConfig(AppConfigDef.headPortrait, resp.getHeadPortrait());
        LogEx.d("updateBabyInfo headPortrait", resp.getHeadPortrait());
        AppConfigHelper.setConfig(AppConfigDef.phoneNum, resp.getPhoneNum());
        LogEx.d("updateBabyInfo phoneNum", resp.getPhoneNum());
    }

    public void updateBabyInfo(Student resp) {
        AppConfigHelper.setConfig(AppConfigDef.studentID, resp.getUserId());
        LogEx.d("updateBabyInfo studentID", resp.getUserId());
        AppConfigHelper.setConfig(AppConfigDef.schoolName, resp.getKindergartenName());
        LogEx.d("updateBabyInfo schoolName", resp.getKindergartenName());
        AppConfigHelper.setConfig(AppConfigDef.location, resp.getArea());
        LogEx.d("updateBabyInfo location", resp.getArea());
        AppConfigHelper.setConfig(AppConfigDef.babyName, resp.getName());
        LogEx.d("updateBabyInfo babyName", resp.getName());
        AppConfigHelper.setConfig(AppConfigDef.birthday, resp.getBirthday());
        LogEx.d("updateBabyInfo birthday", resp.getBirthday());
        AppConfigHelper.setConfig(AppConfigDef.babySex, resp.getSex());
        LogEx.d("updateBabyInfo babySex", resp.getSex());
        AppConfigHelper.setConfig(AppConfigDef.gradeID, resp.getGradeId());
        LogEx.d("updateBabyInfo gradeID", resp.getGradeId());
        AppConfigHelper.setConfig(AppConfigDef.classID, resp.getClassId());
        LogEx.d("updateBabyInfo classID", resp.getClassId());
        AppConfigHelper.setConfig(AppConfigDef.classNick, resp.getClassNick());
        LogEx.d("updateBabyInfo classNick", resp.getClassNick());
        AppConfigHelper.setConfig(AppConfigDef.className, resp.getClassName());
        LogEx.d("updateBabyInfo className", resp.getClassName());
        AppConfigHelper.setConfig(AppConfigDef.gradeNick, resp.getGradeNick());
        LogEx.d("updateBabyInfo gradeNick", resp.getGradeNick());
        AppConfigHelper.setConfig(AppConfigDef.gradeLevel, resp.getGradeLevel());
        LogEx.d("updateBabyInfo gradeLevel", resp.getHeadPortrait());
        AppConfigHelper.setConfig(AppConfigDef.semester, resp.getSemester());
        LogEx.d("updateBabyInfo semester", resp.getClassNick());
        AppConfigHelper.setConfig(AppConfigDef.moral, resp.getMoral());
        LogEx.d("updateBabyInfo moral", resp.getMoral());
        AppConfigHelper.setConfig(AppConfigDef.growup_userid, resp.getOtherId());
        LogEx.d("updateBabyInfo growup_userid", resp.getOtherId());
        AppConfigHelper.setConfig(AppConfigDef.graduationFlag, resp.getGraduationFlag());
        LogEx.d("updateBabyInfo graduationFlag", resp.getGraduationFlag());
    }

    public void resetBabyInfo() {
        AppConfigHelper.setConfig(AppConfigDef.studentID, "");
        AppConfigHelper.setConfig(AppConfigDef.babyName, "");
        AppConfigHelper.setConfig(AppConfigDef.birthday, "");
        AppConfigHelper.setConfig(AppConfigDef.babySex, "");
        AppConfigHelper.setConfig(AppConfigDef.growup_userid, "");
    }

    public static boolean isShoolboss() {
        return AppConfigHelper.getConfig(AppConfigDef.roleKey).contains(UserInfo.ROLE_LEADER_ADMIN);
    }

    public static boolean isParent() {
        return AppConfigHelper.getConfig(AppConfigDef.roleKey).contains(UserInfo.ROLE_USER);
    }

    public static boolean isTeacher() {
        return AppConfigHelper.getConfig(AppConfigDef.roleKey).contains(UserInfo.ROLE_TEACHER);
    }

    public void resetApp() {
        try {
            DbHelper.getInstance().getDbController().deleteAll(AppConfig.class);
            DbHelper.getInstance().getDbController().deleteAll(GetGradeClassResp.class);
            DbHelper.getInstance().getDbController().deleteAll(Dynamic.class);
            DbHelper.getInstance().getDbController().deleteAll(GetLessonPlansResp.class);
            DbHelper.getInstance().getDbController().deleteAll(GetTeacherTaskFeedbackListResp.class);
            DbHelper.getInstance().getDbController().deleteAll(GetTeacherTaskResp.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
