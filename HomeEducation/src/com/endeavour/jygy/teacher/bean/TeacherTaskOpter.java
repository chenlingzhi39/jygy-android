package com.endeavour.jygy.teacher.bean;

import com.endeavour.jygy.common.db.DbHelper;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

/**
 * Created by wu on 15/12/24.
 */
public class TeacherTaskOpter {

    public interface LoadTaskListener {

        void onLoad(List<GetTeacherTaskResp> tasks);

    }

    public void refreshTask(LoadTaskListener listener) {
        getDataFromNet();
    }

    private void getDataFromNet() {

    }

    public void getDataFromDb(LoadTaskListener listener) {

    }

    public void loadMoreTask(LoadTaskListener listener) {

    }

    /**
     * 获取最新的一条动态的时间戳
     *
     * @return
     */
    private String getNewestDynamicTime() {
        try {
            SqlInfo info = new SqlInfo("select updateTime from GetTeacherTaskResp2 order by updateTime DESC limit 1");
            DbModel last = DbHelper.getInstance().getDbController().findDbModelFirst(info);
            return last.getString("updateTime");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return "";
    }

}
