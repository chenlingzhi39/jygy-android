package com.endeavour.jygy.common.xg.receiver;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.endeavour.jygy.LoginActivity;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.endeavour.jygy.common.xg.common.NotificationService;
import com.endeavour.jygy.common.xg.po.XGNotification;
import com.endeavour.jygy.parent.activity.MessageOpter;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import java.util.List;

public class MessageReceiver extends XGPushBaseReceiver {
    private Intent intent = new Intent(
            "com.endeavour.jygy.activity.UPDATE_LISTVIEW");
    public static final String LogTag = "TPushReceiver";
    public static final String mess_action = "message.broadcast.action"; //发送广播到主页，显示消息数字
    private static final String TAG = "XGPush";
    private SharedPreferences prefs;
    private MessageOpter messopter = new MessageOpter();

    private void show(Context context, String text) {
        // Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    private String getRunningActivityName(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).baseActivity
                .getClassName();
        return runningActivity;
    }

    // 通知展示
    @Override
    public void onNotifactionShowedResult(Context context,
                                          XGPushShowedResult notifiShowedRlt) {
        if (context == null || notifiShowedRlt == null) {
            return;
        }
        Uri notification = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(
                context.getApplicationContext(), notification);
        r.play();
        // XGPushShowedResult [msgId=18, title=点个赞, content=
        // , customContent={"type":"1","content":"{\"data\":\"26\"}"}
        // , activity=com.endeavour.jygy.LoginActivity, notificationActionType1]
        XGNotification notific = new XGNotification();
        notific.setMsg_id(notifiShowedRlt.getMsgId());
        notific.setTitle(notifiShowedRlt.getTitle());
        notific.setContent(notifiShowedRlt.getContent());
        // notificationActionType==1为Activity，2为url，3为intent
        notific.setNotificationActionType(notifiShowedRlt
                .getNotificationActionType());
        // // Activity,url,intent都可以通过getActivity()获得

//        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject
//                .parseObject(notifiShowedRlt.getCustomContent());
//        Message mess = new Message();
//        mess.setTitle(notifiShowedRlt.getTitle());
//        mess.setType(json.getString("type"));
//        com.alibaba.fastjson.JSONObject json_ct = com.alibaba.fastjson.JSONObject
//                .parseObject(json.getString("content"));
//        mess.setMessid(json_ct.getString("data"));//主键
//        mess.setCreateTime(TimeUtils.getCurrentDate());
//        mess.setIsread(0);
////		Message mess = new Message();
////		mess.setTitle("请假9");
////		mess.setType("9");
////		mess.setContent("哇哈哈");
//        mess.setCreateTime(TimeUtils.getCurrentDateTime());
//
        try {
//            try {
//                if (messopter.getMessageFromDB(mess.getMessid()) <= 0)
//                    DbHelper.getInstance().getDbController().save(mess);
//                else
//                    DbHelper.getInstance().getDbController().update(mess, "messid", "type", "content", "title", "createTime", "updateTime");
//            } catch (DbException e) {
//                e.printStackTrace();
//            }
//            Intent intent = new Intent(mess_action);
//            intent.putExtra("hasmess", "1");//通知主界面显示消息数量
//            context.sendBroadcast(intent);
            // Intent intent =
            // context.getPackageManager().getLaunchIntentForPackage(
            // "com.endeavour.jygy");
//				if(json.getString("type").equals("1")){ //学校班级通知
//					notific.setActivity("com.endeavour.jygy.parent.activity.DynamicActivity");					
//				}else if(json.getString("type").equals("4")
//						||json.getString("type").equals("5")
//						||json.getString("type").equals("6")
//						||json.getString("type").equals("7")
//						||json.getString("type").equals("8")
//						||json.getString("type").equals("9")){ 
//					String type = AppConfigHelper.getConfig("AppConfigDef.loginType");
//					if(type.equals("1")){
//						notific.setActivity("com.endeavour.jygy.parent.activity.TeacherTabActivity");
////						intent = new Intent(context,ParentTabActivity.class);
//					}else if(type.equals("2")){
//						notific.setActivity("com.endeavour.jygy.parent.activity.ParentTabActivity");
////						intent = new Intent(context,ParentTabActivity.class);
//					}
//				}else if(json.getString("type").equals("2")){ //请假申请，跳回通知列表
//					notific.setActivity("com.endeavour.jygy.parent.activity.NoticeListActivity");
////					intent = new Intent(context,NoticeListActivity.class);
//				}
            NotificationService.getInstance(context).save(notific);
            //context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUnregisterResult(Context context, int errorCode) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        //Log.d(LogTag, text);
        show(context, text);

    }

    @Override
    public void onSetTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        //Log.d(LogTag, text);
        show(context, text);

    }

    @Override
    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        if (context == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
//		Log.d(LogTag, text);
        show(context, text);

    }

    // 判断类是否在运行
    private boolean isClsRunning(String MY_PKG_NAME, Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(20);
        for (RunningTaskInfo info : list) {
            // Log.d(TAG, "[MyReceiver]" + info.topActivity.getPackageName() +
            // "|" + info.baseActivity.getPackageName());
            if (info.topActivity.getClassName().equals(MY_PKG_NAME)) {
                return true;
            }
        }
        return false;
    }

    // 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
    @Override
    public void onNotifactionClickedResult(Context context,
                                           XGPushClickedResult msg) {
        if (context == null || msg == null) {
            return;
        }
//        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject
//                .parseObject(msg.getCustomContent());
//        Message mess = new Message();
//        mess.setTitle(msg.getTitle());
//        mess.setType(json.getString("type"));
//        com.alibaba.fastjson.JSONObject json_ct = com.alibaba.fastjson.JSONObject
//                .parseObject(json.getString("content"));
//        mess.setMessid(json_ct.getString("data"));//主键
//        mess.setCreateTime(TimeUtils.getCurrentDate());
//        mess.setIsread(1);
////		Message mess = new Message();
////		mess.setTitle("请假9");
////		mess.setType("9");
////		mess.setContent("哇哈哈");
//        mess.setCreateTime(TimeUtils.getCurrentDateTime());
//        try {
//            if (messopter.getMessageFromDB(mess.getMessid()) <= 0)
//                DbHelper.getInstance().getDbController().save(mess);
//            else
//                DbHelper.getInstance().getDbController().update(mess, "messid", "type", "content", "title", "updateTime");
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(mess_action);
//        intent.putExtra("hasmess", "1");//通知主界面显示消息数量
//        context.sendBroadcast(intent);
        // 获取自定义key-value
        // String customContent = mess.getCustomContent();
        // if (customContent != null && customContent.length() != 0) {
        // try {
        // JSONObject obj = new JSONObject(customContent);
        // // key1为前台配置的key
        // if (!obj.isNull("key")) {
        // String value = obj.getString("key");
        // Log.d(LogTag, "get custom value:" + value);
        // }
        // // ...
        // } catch (JSONException e) {
        // e.printStackTrace();
        // }
        // }
        // APP自主处理的过程。。。
        // Log.d(LogTag, text);
        // show(context, text);
    }

    private void resetapp(Context context) {
        if (!Constants.TRUE.equals(DefaultAppConfigHelper.getConfig(AppConfigDef.isLogin))) {
            MainApp.getInstance().resetApp();
            Tools.toActivity(context, LoginActivity.class);
        }
    }

    @Override
    public void onRegisterResult(Context context, int errorCode,
                                 XGPushRegisterResult message) {
        if (context == null || message == null) {
            return;
        }
        String text = "";
        if (errorCode == XGPushBaseReceiver.SUCCESS) {
            text = message + "注册成功";
            // 在这里拿token
            String token = message.getToken();
        } else {
            text = message + "注册失败，错误码：" + errorCode;
        }
        // Log.d(LogTag, text);
        show(context, text);
    }

    // 消息透传
    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        if (context == null || message == null) {
            return;
        }
        // XGPushShowedResult [title=我是推送,
        // content=你好，我是推送,customContent={"type":"7"}]
//        String title = message.getTitle();
//        String customcontent = message.getCustomContent();
//        String content = message.getContent();
//        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject
//                .parseObject(customcontent);
//        Message mess = new Message();
//        mess.setTitle(title);
//        mess.setType(json.getString("type"));
//        com.alibaba.fastjson.JSONObject json_ct = com.alibaba.fastjson.JSONObject
//                .parseObject(json.getString("content"));
//        mess.setMessid(json_ct.getString("data"));
//        mess.setCreateTime(TimeUtils.getCurrentDate());
//        mess.setIsread(0);
//        try {
//            if (messopter.getMessageFromDB(mess.getMessid()) <= 0)
//                DbHelper.getInstance().getDbController().save(mess);
//            else
//                DbHelper.getInstance().getDbController().update(mess, "messid", "type", "content", "title", "createTime", "updateTime");
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
        // 保存消息对象
        // String customContent = message.getCustomContent();
        // if (customContent != null && customContent.length() != 0) {
        // try {
        // JSONObject obj = new JSONObject(customContent);
        // // key1为前台配置的key
        // if (!obj.isNull("key")) {
        // String value = obj.getString("key");
        // // Log.d(LogTag, "get custom value:" + value);
        // }
        // // ...
        // } catch (JSONException e) {
        // e.printStackTrace();
        // }
        // }
        // APP自主处理消息的过程...
        // Log.d(LogTag, text);
        // show(context, text);
    }
}
