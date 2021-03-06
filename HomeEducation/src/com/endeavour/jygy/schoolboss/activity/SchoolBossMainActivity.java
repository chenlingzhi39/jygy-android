package com.endeavour.jygy.schoolboss.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.LoginActivity;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.TimeUtils;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.Unicoder;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DbHelper;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.common.xg.receiver.MessageReceiver;
import com.endeavour.jygy.login.activity.ChangePasswdActivity;
import com.endeavour.jygy.parent.activity.MessageOpter;
import com.endeavour.jygy.parent.activity.NoticeListActivity;
import com.endeavour.jygy.parent.activity.SchoolBossFoodHistoryActivity;
import com.endeavour.jygy.parent.bean.GetNoticeAnnounceReq;
import com.endeavour.jygy.parent.bean.GetNoticeAnnounceResp;
import com.endeavour.jygy.parent.bean.Message;
import com.endeavour.jygy.parent.bean.TrendCollectReq;
import com.endeavour.jygy.parent.bean.TrendCollectResp;
import com.endeavour.jygy.parent.fragment.ParentHomeFragment2;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import java.util.List;

public class SchoolBossMainActivity extends BaseViewActivity {
    private ResideMenu resideMenu;
    private ResideMenuItem itemFood;
    private ResideMenuItem itemSetting;
    private ResideMenuItem itemExit;
    MessageOpter messopter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        YtTemplate.init(this);
        setTitleText(R.string.app_name);
        setTitleRightImage(R.drawable.title_btn_notice);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.title_btn_menu);
        showTitleBack();
        messopter = new MessageOpter();
        IntentFilter filter = new IntentFilter(MessageReceiver.mess_action);
        registerReceiver(broadcastReceiver, filter);
        setUpMenu();
        setMainView(R.layout.activity_parent_home);
        ParentHomeFragment2 fragment2 = new ParentHomeFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt(ParentHomeFragment2.type, 1);
        fragment2.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rlReplace, fragment2).commit();
        doGetNoticeAciton();
        getRegistList();

        resideMenu.hideHeaderView();
    }

    /**
     * 获取评论点赞
     */
    private void getRegistList() {
        progresser.showProgress();
        TrendCollectReq req = new TrendCollectReq();
        req.setParentId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        if (messopter.getNewestMessageTime("3") != null
                && !messopter.getNewestMessageTime("3").equals(""))
            req.setUpdateTime(messopter.getNewestMessageTime("3"));
        else
            req.setUpdateTime("0");
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        List<TrendCollectResp> resps = JSONObject.parseArray(String.valueOf(response.getResult()), TrendCollectResp.class);
                        if (resps == null && resps.isEmpty()) {
                            return;
                        }
                        for (TrendCollectResp getAttendsResp : resps) {
                            // {"createTime":1452181437000,"fMsgCommentId":5,"friendMsgId":1,"type":"2","userName":"张三"}
                            // String[] strdate = new String[2];
                            // strdate[0] = getAttendsResp.getLeaveStartDate();
                            // strdate[1] = getAttendsResp.getLeaveEndDate();
                            try {
                                Message mess = new Message();
                                mess.setTitle("评论点赞");
                                mess.setType("3");
                                mess.setMessid(getAttendsResp
                                        .getfMsgCommentId());
                                mess.setContent(getAttendsResp.getContent() == null ? ""
                                        : Unicoder.unicode2Emoji(getAttendsResp.getContent()));
                                // mess.setMesstime(TimeUtils.longToString_ymd(getAttendsResp.getStartDate())
                                // +"至"+TimeUtils.longToString_ymd(getAttendsResp.getEndDate()));
                                mess.setCreateTime(TimeUtils.getFormatDate(getAttendsResp.getCreateTime()));
                                mess.setUserName(getAttendsResp.getUserName());
                                // mess.setAttendDays(getAttendsResp.getAttendDays());
                                mess.setUpdateTime(Long.valueOf(getAttendsResp
                                        .getCreateTime()));
                                mess.setIsread(0);
                                if (messopter.getMessageFromDB(mess.getMessid()) <= 0)
                                    DbHelper.getInstance().getDbController()
                                            .save(mess);
                                else
                                    DbHelper.getInstance()
                                            .getDbController()
                                            .update(mess, "messid", "type",
                                                    "content", "title",
                                                    "createTime", "updateTime",
                                                    "userName");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        showMessNum();
                    }

                    @Override
                    public void onFailed(Response response) {
                        progresser.showContent();
                    }
                });
    }

    /**
     * 获取通知列表
     */
    private void doGetNoticeAciton() {
        progresser.showProgress();
        GetNoticeAnnounceReq req = new GetNoticeAnnounceReq();
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        if (messopter.getNewestMessageTime("1") != null
                && !messopter.getNewestMessageTime("1").equals(""))
            req.setUpdateTime(messopter.getNewestMessageTime("1"));
        else
            req.setUpdateTime("0");
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        List<GetNoticeAnnounceResp> resps = JSONArray
                                .parseArray(
                                        String.valueOf(response.getResult()),
                                        GetNoticeAnnounceResp.class);
                        if (resps == null && resps.isEmpty()) {
                            return;
                        }
                        for (GetNoticeAnnounceResp getAttendsResp : resps) {
                            try {
                                Message mess = new Message();
                                mess.setTitle("通知");
                                mess.setType("1");
                                mess.setMessid(getAttendsResp.getNoticeId());
                                mess.setContentHtml(getAttendsResp.getContent());
                                mess.setContent(Unicoder.unicode2Emoji(getAttendsResp.getTitle()));
                                // mess.setMesstime(TimeUtils.longToString_ymd(getAttendsResp.getStartDate())
                                // +"至"+TimeUtils.longToString_ymd(getAttendsResp.getEndDate()));
                                mess.setCreateTime(getAttendsResp.getLastTime());
                                mess.setUserName("");
                                // mess.setAttendDays(getAttendsResp.getAttendDays());
                                mess.setUpdateTime(Long.valueOf(getAttendsResp
                                        .getLastTime()));
                                mess.setIsread(0);
                                if (messopter.getMessageFromDB(mess.getMessid()) <= 0)
                                    DbHelper.getInstance().getDbController()
                                            .save(mess);
                                else
                                    DbHelper.getInstance()
                                            .getDbController()
                                            .update(mess, "messid", "type",
                                                    "content", "title",
                                                    "createTime", "updateTime",
                                                    "userName");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        showMessNum();
                    }

                    @Override
                    public void onFailed(Response response) {
                        progresser.showContent();

                    }
                });
    }

    private void showMessNum() {
        int num = messopter.getMessagesCount();
        if (num > 0)
            showRedDot(num); // 刷新消息数量
        else
            showRedDot(0);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            showMessNum(); // 刷新消息数量
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (resideMenu.isOpened()) {
                resideMenu.closeMenu();
            } else {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        }
        return true;
    }

    private void setUpMenu() {

        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(false);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        resideMenu.setScaleValue(0.6f);

        itemFood = new ResideMenuItem(this, R.drawable.menu_shipu_selecter,
                "健康食谱");
        itemSetting = new ResideMenuItem(this, R.drawable.menu_shezhi_selecter,
                "修改密码");
        itemExit = new ResideMenuItem(this, R.drawable.menu_exit_selecter,
                "退出系统");

        itemFood.setOnClickListener(this);
        itemSetting.setOnClickListener(this);
        itemExit.setOnClickListener(this);

        resideMenu.addMenuItem(itemFood, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSetting, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemExit, ResideMenu.DIRECTION_LEFT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        MainApp.getInstance().resetBabyInfo();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == itemFood) {// 健康食谱
            Tools.toActivity(this, SchoolBossFoodHistoryActivity.class);
        } else if (v == itemSetting) {// 系统设置
            Intent intent = new Intent(SchoolBossMainActivity.this,
                    ChangePasswdActivity.class);
            startActivity(intent);
        } else if (v == itemExit) { // 退出系统
            DefaultAppConfigHelper.setConfig(AppConfigDef.isLogin,
                    Constants.FALSE);
            DefaultAppConfigHelper.setConfig(AppConfigDef.isbabyUi, "0");// 是否进入主界面
            // 1是进入
            MainApp.getInstance().resetApp();
            Tools.toActivity(this, LoginActivity.class);
            this.finish();
        }
        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {

        }

        @Override
        public void closeMenu() {

        }
    };

    @Override
    public void onBackPressed() {
        backToHome();
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        Tools.toActivity(this, NoticeListActivity.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        YtTemplate.release(this);
    }
}
