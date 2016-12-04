package com.endeavour.jygy.parent.activity;

import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.parent.bean.Message;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.SqlInfo;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

public class MessageOpter {
    public interface LoadMessageListener {
        void onLoad(List<Message> Messages);
    }

    public boolean isNoMorePage() {
        return false;
    }


    /**
     * 获取最旧的一条的时间戳
     *
     * @return
     */
    public String getOldestMessageTime(String type) {
        try {
            SqlInfo info = new SqlInfo(
                    "select updateTime from message6 where type='" + type + "' order by updateTime ASC limit 1");
            DbModel last = DbHelper.getInstance().getDbController()
                    .findDbModelFirst(info);
            return last.getString("createTime");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取最新的一条消息的时间戳
     *
     * @return
     */
    public String getNewestMessageTime(String type) {
        try {
            SqlInfo info = new SqlInfo(
                    "select updateTime from message6 where type='" + type + "' order by updateTime DESC limit 1");
            DbModel last = DbHelper.getInstance().getDbController()
                    .findDbModelFirst(info);
            return last.getString("updateTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取最新的一条消息的时间戳
     *
     * @return
     */
    public String getNewestMessageTime(String[] type) {
        try {
            String build = "";
            for (int i = 0; i < type.length; i++) {
                if (i != type.length - 1)
                    build += type[i] + ",";
                else
                    build += type[i];
            }
            SqlInfo info = new SqlInfo(
                    "select updateTime from message6 where type in('" + build + "') order by updateTime DESC limit 1");
            DbModel last = DbHelper.getInstance().getDbController()
                    .findDbModelFirst(info);
            return last.getString("updateTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void updateMessagesFromDB(Message mes) {
        try {
            DbHelper.getInstance()
                    .getDbController().update(mes, WhereBuilder.b("messid", "=", mes.getMessid()).and("type", "=", mes.getType()), "isread");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessagesFromDB(Message mes) {
        try {
            DbHelper.getInstance()
                    .getDbController().delete(Message.class, WhereBuilder.b("messid", "=", mes.getMessid()).and("type", "=", mes.getType()));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessagesFromDB() {
        try {
            List<Message> Messages = new ArrayList<>();
            Messages = DbHelper
                    .getInstance()
                    .getDbController()
                    .findAll(Selector.from(Message.class).orderBy("updateTime", true)); //.where(WhereBuilder.b("isread", "=","0"))
            return Messages;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Message> getMessagesFromDB_dynamic() {
        try {
            return DbHelper
                    .getInstance()
                    .getDbController()
                    .findAll(Selector.from(Message.class).where(WhereBuilder.b("type", "=", "3").and("isread", "=", "0")).orderBy("updateTime", true));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getMessageFromDB(String id) {
        try {
            Message mess = new Message();
            SqlInfo info = new SqlInfo("select count(*) from message6 where messid=" + id);
            DbModel count = DbHelper
                    .getInstance()
                    .getDbController().findDbModelFirst(info);
            return count.getInt("count(*)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getMessagesCount() {
        try {
            SqlInfo info = new SqlInfo("select count(*) from message6 where isread=0 and type != 3");
            DbModel count = DbHelper.getInstance().getDbController()
                    .findDbModelFirst(info);
            return count.getInt("count(*)");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getDynamicMessageCount() {
        List<Message> messagesFromDB_dynamic = getMessagesFromDB_dynamic();
        if (messagesFromDB_dynamic == null || messagesFromDB_dynamic.isEmpty()) {
            return 0;
        } else {
            return messagesFromDB_dynamic.size();
        }
    }

    public void updataMsgs() {
        List<Message> messagesFromDB_dynamic = getMessagesFromDB_dynamic();
        if (messagesFromDB_dynamic == null) {
            return;
        }
        for (Message message : messagesFromDB_dynamic) {
            message.setIsread(1);
            updateMessagesFromDB(message);
        }
    }
}
