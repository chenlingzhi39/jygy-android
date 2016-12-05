package com.endeavour.jygy.parent.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.TimeUtils;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseHomeViewFragment;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.endeavour.jygy.common.view.CircleImageView;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.login.activity.bean.UserInfo;
import com.endeavour.jygy.parent.activity.AskForLeaveActivity;
import com.endeavour.jygy.parent.activity.BabyMoralDetailActvity;
import com.endeavour.jygy.parent.activity.ChooseBabyActivity;
import com.endeavour.jygy.parent.activity.DynamicActivity;
import com.endeavour.jygy.parent.activity.FoodActivity;
import com.endeavour.jygy.parent.activity.HelpActivity;
import com.endeavour.jygy.parent.activity.MessageOpter;
import com.endeavour.jygy.parent.activity.ModifyTeacherInfoActivity;
import com.endeavour.jygy.parent.activity.ParentTabActivity;
import com.endeavour.jygy.parent.activity.PayListActivity;
import com.endeavour.jygy.parent.activity.TeacherTabActivity;
import com.endeavour.jygy.parent.adapter.DemoPagerAdapter;
import com.endeavour.jygy.parent.adapter.HomeMenuAdapter;
import com.endeavour.jygy.parent.bean.AttendReq;
import com.endeavour.jygy.parent.bean.FindUserExpiryEndDateReq;
import com.endeavour.jygy.parent.bean.GetAttendsReq;
import com.endeavour.jygy.parent.bean.GetAttendsResp;
import com.endeavour.jygy.parent.bean.GetStudentMoralReq;
import com.endeavour.jygy.parent.bean.GetStudentMoralResp;
import com.endeavour.jygy.parent.bean.HomeMenuItem;
import com.endeavour.jygy.parent.bean.Student;
import com.endeavour.jygy.parent.caldroid.CaldroidFragment;
import com.endeavour.jygy.parent.caldroid.CaldroidListener;
import com.endeavour.jygy.parent.shake.ShakeListener;
import com.endeavour.jygy.parent.shake.ShakeListener.OnShakeListener;
import com.endeavour.jygy.schoolboss.activity.SchoolBossGrowupAcitivity;
import com.endeavour.jygy.schoolboss.activity.SchoolBossTaskActivity;
import com.endeavour.jygy.schoolboss.activity.SchoolGradesActivity;
import com.endeavour.jygy.teacher.activity.MyClassGridActivity;
import com.endeavour.jygy.teacher.activity.TeacherPlanActivity;
import com.flyco.dialog.listener.OnBtnLeftClickL;
import com.flyco.dialog.listener.OnBtnRightClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.hy.android.media.MediaMainSActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParentHomeFragment2 extends BaseHomeViewFragment implements
        HomeMenuAdapter.OnItemSelectedListener {
    public static final String type = "type";
    private AlertDialog.Builder dialogBuilder;
    private final int VIBRATOR_MIN = 19;
    private final int VIBRATOR_TIME = 300;
    private GridView glMain;
    private HomeMenuAdapter adapter;
    private CaldroidFragment dialogCaldroidFragment; // 日历类
    private Bundle savedInstanceState;
    private int main_type = 1; // 1 家长端 2老师端 3园长端
    private List<String> timelist; // 签到
    private List<String[]> leavelist_one; // 请假
    private List<String[]> leavelist; // 请假
    final String dialogTag = "日历";
    private CircleImageView ivIcon;
    private TextView tvName;
    private TextView tvStudenMoral;
    public static final String UpdateBabyInfoBroadCastAction = "com.endeavour.jygy.parent.fragment.UpdateBabyInfoBroadCast";
    private UpdateBabyInfoBroadCast updateBabyInfoBroadCast;
    private ShakeListener mShakeListener = null;
    private Vibrator mVibrator;
    private TextView tvClassInfo;
    private ViewPager viewPager;
    private DemoPagerAdapter pagerAdapter;

    public class UpdateBabyInfoBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ivIcon != null) {
                if (main_type == 1) {
                    if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.babySex))) {
                        ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivIcon, MainApp.getBobyOptions());
                    } else {
                        ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivIcon, MainApp.getGrilsOptions());
                    }
                } else if (main_type == 2) {
                    ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivIcon, MainApp.getTeacherOptions());
                } else if (main_type == 3) {
                    ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivIcon, MainApp.getSchoolBoosOptions());
                }
                if (MainApp.isParent()) {
                    tvName.setText(AppConfigHelper.getConfig(AppConfigDef.babyName));
                } else {
                    tvName.setText(AppConfigHelper.getConfig(AppConfigDef.parentName));
                }
            }

        }
    }

    public ParentHomeFragment2() {
        // Required empty public constructor
    }

    private SensorManager mSensorManager; // 定义sensor管理器, 注册监听器用
    private boolean shakable = true; // 是否正在处理摇动

    @Override
    public void initView() {
        String roleKey = AppConfigHelper.getConfig(AppConfigDef.roleKey);
        if (roleKey.contains(UserInfo.ROLE_LEADER_ADMIN)) { // 园长
            main_type = 3;
        } else if (roleKey.contains(UserInfo.ROLE_TEACHER)) { // 老师
            main_type = 2;
        } else if (roleKey.contains(UserInfo.ROLE_USER)) { // 家长
            main_type = 1;
        }
        DefaultAppConfigHelper.setConfig(
                AppConfigDef.isbabyUi, "1");//是否进入主界面 1是进入
        AppConfigHelper.setConfig(AppConfigDef.loginType, main_type + "");// 记录登录状态
        setMainView(R.layout.fragment_parent_home2);
        viewPager = (ViewPager) mainView.findViewById(R.id.viewpager_default);
        pagerAdapter = new DemoPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        glMain = (GridView) mainView.findViewById(R.id.glMain);
        tvClassInfo = (TextView) mainView.findViewById(R.id.tvClassInfo);
        adapter = new HomeMenuAdapter(getActivity());
        adapter.setOnItemSelectedListener(this);
        glMain.setAdapter(adapter);
        if (main_type == 1) {
            tvStudenMoral = (TextView) mainView.findViewById(R.id.tvStudenMoral);
            String moral = AppConfigHelper.getConfig(AppConfigDef.moral);
            tvStudenMoral.setText("德育分: " + (TextUtils.isEmpty(moral) ? "--" : moral));
            mainView.findViewById(R.id.rlStudenMoral).setVisibility(View.VISIBLE);
            mainView.findViewById(R.id.rlStudenMoral).setOnClickListener(this);
            if (main_type == 1) {
                mShakeListener = new ShakeListener(getActivity());
                mShakeListener.setOnShakeListener(new OnShakeListener() {
                    public void onShake() {
                        mShakeListener.stop();
                        startVibrato(); // 开始 震动
                        regist();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mVibrator != null) {
                                    mVibrator.cancel();
                                }
                                mShakeListener.start();
                            }
                        }, 2000);
                    }
                });
            }
        } else {
            mainView.findViewById(R.id.rlStudenMoral).setVisibility(View.INVISIBLE);
            mainView.findViewById(R.id.ivShake).setVisibility(View.INVISIBLE);
        }
        mainView.findViewById(R.id.ivShake).setOnClickListener(this);
        ivIcon = (CircleImageView) mainView.findViewById(R.id.ivIcon);
        tvName = (TextView) mainView.findViewById(R.id.tvName);
        mainView.findViewById(R.id.ivShake).setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        List<HomeMenuItem> homeMenuItems = new ArrayList<HomeMenuItem>();
        homeMenuItems.add(new HomeMenuItem("最新动态",
                R.drawable.menu_dongtai_2_selecter));
        if (main_type == 1) {
            homeMenuItems.add(new HomeMenuItem("家园共育",
                    R.drawable.menu_renwu_2_selecter));
            homeMenuItems.add(new HomeMenuItem("我要请假",
                    R.drawable.menu_qingjia_selecter));
            homeMenuItems.add(new HomeMenuItem("健康食谱",
                    R.drawable.menu_shipu_selecter));
            homeMenuItems.add(new HomeMenuItem("精彩瞬间",
                    R.drawable.menu_wunderful_selecter));
            homeMenuItems.add(new HomeMenuItem("帮助",
                    R.drawable.menu_ketang_selecter));

            if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.babySex))) {
                ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivIcon, MainApp.getBobyOptions());
            } else {
                ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivIcon, MainApp.getGrilsOptions());
            }

            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    mVibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(getActivity(), "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                }

            };
            new TedPermission(getActivity())
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("请开启权限")
                    .setPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.VIBRATE)
                    .check();

            if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
                tvClassInfo.setText("幼儿园已毕业");
            } else {
                tvClassInfo.setText(AppConfigHelper.getConfig(AppConfigDef.gradeNick) + "-" + AppConfigHelper.getConfig(AppConfigDef.classNick));
            }
        } else if (main_type == 2) {
            ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivIcon, MainApp.getTeacherOptions());
            homeMenuItems.add(new HomeMenuItem("小任务",
                    R.drawable.menu_renwu_2_selecter));
            homeMenuItems.add(new HomeMenuItem("幼儿动画",
                    R.drawable.menu_video_selecter));
            homeMenuItems.add(new HomeMenuItem("老师教案",
                    R.drawable.menu_lsdangan_selecter));
            homeMenuItems.add(new HomeMenuItem("精彩瞬间",
                    R.drawable.menu_wunderful_selecter));
            homeMenuItems.add(new HomeMenuItem("我的班级",
                    R.drawable.menu_banji_selecter));

            tvClassInfo.setText(AppConfigHelper.getConfig(AppConfigDef.gradeNick) + "-" + AppConfigHelper.getConfig(AppConfigDef.className));
        } else if (main_type == 3) {
            homeMenuItems.add(new HomeMenuItem("小任务",
                    R.drawable.menu_renwu_2_selecter));
            homeMenuItems.add(new HomeMenuItem("成长档案",
                    R.drawable.menu_dangan_2_selecter));
            ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivIcon, MainApp.getSchoolBoosOptions());
            homeMenuItems.add(new HomeMenuItem("我的园所",
                    R.drawable.menu_lsdangan_selecter));
            homeMenuItems.add(new HomeMenuItem("精彩瞬间",
                    R.drawable.menu_wunderful_selecter));
        }
        adapter.setDataChanged(homeMenuItems);
        if (main_type == 1) {
            tvName.setText(AppConfigHelper.getConfig(AppConfigDef.babyName));
        } else {
            tvName.setText(AppConfigHelper.getConfig(AppConfigDef.parentName));
        }
        // 日历弹出框相关
        // Setup listener
        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                // Toast.makeText(getActivity(), formatter.format(date),
                // Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                // Toast.makeText(getActivity(), text,
                // Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                // Toast.makeText(getActivity(),
                // "Long click " + formatter.format(date),
                // Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {

            }

        };
        dialogCaldroidFragment = new CaldroidFragment();
        dialogCaldroidFragment.setCaldroidListener(listener);
        final Bundle state = savedInstanceState;
        if (state != null) {
            dialogCaldroidFragment.restoreDialogStatesFromKey(getActivity()
                            .getSupportFragmentManager(), state,
                    "DIALOG_CALDROID_SAVED_STATE", dialogTag);
            Bundle args = dialogCaldroidFragment.getArguments();
            if (args == null) {
                args = new Bundle();
                dialogCaldroidFragment.setArguments(args);
            }
        } else {
            Bundle bundle = new Bundle();
            dialogCaldroidFragment.setArguments(bundle);
        }
    }

    public void startVibrato() {
        if (mVibrator == null) {
            return;
        }
        try {
            MediaPlayer player;
            player = MediaPlayer.create(getActivity(), R.raw.shake_sound_male);
            player.setLooping(false);
            player.start();
            // 定义震动
            mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1); // 第一个｛｝里面是节奏数组，
            // 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDateDialog() {
        // 日历调试用
        // Calendar cal = Calendar.getInstance();
        // cal = Calendar.getInstance();
        // cal.add(Calendar.DATE, 2);
        // Date fromDate = cal.getTime();
        //
        // cal = Calendar.getInstance();
        // cal.add(Calendar.DATE, 6);
        // Date toDate = cal.getTime();
        try {
//            for (int i = 0; i < leavelist.size(); i++) {
//                if (leavelist.get(i)[0].equals(leavelist.get(i)[1])) {
//                	String[] arr = new String[3];
//                	arr[0] = leavelist.get(i)[0];
//                	arr[1] = leavelist.get(i)[1];
//                	arr[2] = leavelist.get(i)[2];
//                	arr[3] = leavelist.get(i)[3];
//                    leavelist_one.add(arr);
//                }
//            }
            //摇一摇签到的日期，先打勾，如果当天有请假，再覆盖一个圈圈
            dialogCaldroidFragment.setBackgroundResourceForDate(
                    R.drawable.gou,
                    TimeUtils.stringToDate(TimeUtils.getCurrentDate(), "yyyy-MM-dd"));
//            for (String[] time : leavelist) {
//                dialogCaldroidFragment.setSelectedDates(
//                        TimeUtils.stringToDate(time[0], "yyyy-MM-dd"),
//                        TimeUtils.stringToDate(time[1], "yyyy-MM-dd"));
//            }
            // 设置单个日期为红勾
            for (String time : timelist) {
                dialogCaldroidFragment.setBackgroundResourceForDate(
                        R.drawable.gou,
                        TimeUtils.stringToDate(time, "yyyy-MM-dd"));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dBegin;
            // 设置单个日期为圆圈 请假的圈优先覆盖签到
            for (String[] time : leavelist) {
                try {
                    dBegin = sdf.parse(time[0]);
                    Date dEnd = sdf.parse(time[1]);
                    List<Date> list = TimeUtils.getDatesBetweenTwoDate(dBegin, dEnd);
                    for (int i = 0; i < list.size(); i++) {
                        if (i == 0) {
                            if (time[2].equals("0"))
                                dialogCaldroidFragment.setBackgroundResourceForDate(
                                        R.drawable.quan, list.get(i));
                            else if (time[2].equals("2"))
                                dialogCaldroidFragment.setBackgroundResourceForDate(
                                        R.drawable.banxia, list.get(i));
                            else if (time[2].equals("1"))
                                dialogCaldroidFragment.setBackgroundResourceForDate(
                                        R.drawable.banshang, list.get(i));
                        } else if (i == list.size() - 1) {
                            if (time[3].equals("0"))
                                dialogCaldroidFragment.setBackgroundResourceForDate(
                                        R.drawable.quan, list.get(i));
                            else if (time[3].equals("2"))
                                dialogCaldroidFragment.setBackgroundResourceForDate(
                                        R.drawable.banxia, list.get(i));
                            else if (time[3].equals("1"))
                                dialogCaldroidFragment.setBackgroundResourceForDate(
                                        R.drawable.banshang, list.get(i));
                        } else
                            dialogCaldroidFragment.setBackgroundResourceForDate(
                                    R.drawable.quan, list.get(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
        }
        // dialogCaldroidFragment.setTextColorForDate(R.color.white,
        // selectDate);
        if (dialogCaldroidFragment.isAdded() == false)
            dialogCaldroidFragment.show(getActivity()
                    .getSupportFragmentManager(), dialogTag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Override
    public void onResume() {
        if (main_type == 1) {
            mShakeListener.regist();
        }
        handler.postDelayed(switchTask, 3000);
        if (MainApp.getInstance().isParent()) {
            updateMoral();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (main_type == 1) {
            mShakeListener.stop();
        }
        handler.removeCallbacks(switchTask);
        super.onPause();
    }

    private void updateMoral() {
        GetStudentMoralReq req = new GetStudentMoralReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setGradeLevel(AppConfigHelper.getConfig(AppConfigDef.gradeLevel));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        req.setSemester(AppConfigHelper.getConfig(AppConfigDef.semester));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                try {
                    GetStudentMoralResp resp = JSONObject.parseObject(String.valueOf(response.getResult()), GetStudentMoralResp.class);
                    String moral = resp.getMoral() + "";
                    tvStudenMoral.setText("德育分: " + moral);
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailed(Response response) {

            }
        });
    }

    /**
     * 签到
     */
    private void regist() {
        progresser.showProgress();
        AttendReq req = new AttendReq();
        req.setAttendType("1");
        req.setParentId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(getActivity(), "签到成功");
                        getRegistList();
                    }

                    @Override
                    public void onFailed(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(getActivity(), response.getMsg());
                        getRegistList();
                    }
                });
    }

    /**
     * 获取签到列表
     */
    private void getRegistList() {
        progresser.showProgress();
        timelist = new ArrayList<String>();
        leavelist = new ArrayList<String[]>();
        leavelist_one = new ArrayList<String[]>();
        GetAttendsReq req = new GetAttendsReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        List<GetAttendsResp> resps = JSONObject.parseArray(
                                String.valueOf(response.getResult()),
                                GetAttendsResp.class);
                        if (resps == null && resps.isEmpty()) {
                            return;
                        }
                        for (GetAttendsResp getAttendsResp : resps) {
                            if (getAttendsResp.getType().equals("1")) {
                                timelist.add(getAttendsResp.getAttendate());
                            } else if (getAttendsResp.getType().equals("2")) {
                                String[] strdate = new String[4];
                                strdate[0] = getAttendsResp.getLeaveStartDate();
                                strdate[1] = getAttendsResp.getLeaveEndDate();
                                strdate[2] = getAttendsResp.getStartTimeType();  //1上半天 2下半天 0全天
                                strdate[3] = getAttendsResp.getEndTimeType();
                                leavelist.add(strdate);
                            }
                        }
                        showDateDialog();
                    }

                    @Override
                    public void onFailed(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(getActivity(), response.getMsg());
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        updateBabyInfoBroadCast = new UpdateBabyInfoBroadCast();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                updateBabyInfoBroadCast,
                new IntentFilter(UpdateBabyInfoBroadCastAction));
    }

    /**
     * 获取签到记录
     */
    private void getAttends() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.ivShake) {
            startVibrato(); // 开始 震动
            regist();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mVibrator != null) {
                        mVibrator.cancel();
                    }
                    mShakeListener.start();
                }
            }, 2000);
        } else if (v.getId() == R.id.rlStudenMoral) {
            Student studen = new Student();
            studen.setClassId(AppConfigHelper.getConfig(AppConfigDef.studentID));
            studen.setMoral(AppConfigHelper.getConfig(AppConfigDef.moral));
            studen.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
            studen.setName(AppConfigHelper.getConfig(AppConfigDef.babyName));
            studen.setGradeId(AppConfigHelper.getConfig(AppConfigDef.gradeID));
            studen.setGradeLevel(AppConfigHelper.getConfig(AppConfigDef.gradeLevel));
            studen.setSemester(AppConfigHelper.getConfig(AppConfigDef.semester));
            studen.setMoral(AppConfigHelper.getConfig(AppConfigDef.moral));
            studen.setHeadPortrait(AppConfigHelper.getConfig(AppConfigDef.headPortrait));
            studen.setSex(AppConfigHelper.getConfig(AppConfigDef.babySex));
            Intent intent = new Intent(getActivity(), BabyMoralDetailActvity.class);
            intent.putExtra("student", studen);
            startActivity(intent);
        } else if (v.getId() == R.id.ivIcon) {
            if (main_type == 1) {
                switchUser();
            } else {
                modifyTeacherIcon();
            }
        }
    }

    private void switchUser() {
        Intent intent = new Intent(getActivity(), ChooseBabyActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void modifyTeacherIcon() {
        Intent intent = new Intent(getActivity(), ModifyTeacherInfoActivity.class);
        intent.putExtra("type", 1);
        startActivityForResult(intent, 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onItemClicked(int position, View v) {
        switch (position) {
            case 0:// 最新动态
                Tools.toActivity(getActivity(), DynamicActivity.class);
                MessageOpter messageOpter = new MessageOpter();
                messageOpter.updataMsgs();
                adapter.notifyDataSetChanged();
                break;
            case 1:// 小任务
                if (main_type == 1) {
                    checkPermission();
                } else if (main_type == 2) {
                    Intent intent = new Intent(getActivity(), TeacherTabActivity.class);
                    intent.putExtra("show", "task");
                    startActivity(intent);
                } else if (main_type == 3) {
                    Intent intent = new Intent(getActivity(), SchoolBossTaskActivity.class);
                    startActivity(intent);
                }

                break;
            case 2:// 成长档案
                if (main_type == 1) {
                    Tools.toActivity(getActivity(), AskForLeaveActivity.class);
                } else if (main_type == 2) {
                    startActivity(MediaMainSActivity.getStartIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), SchoolBossGrowupAcitivity.class);
                    startActivity(intent);
                }
                break;
            case 3:
                if (main_type == 1) {// 家长端
                    Tools.toActivity(getActivity(), FoodActivity.class);
                } else if (main_type == 2)// 老师端
                    Tools.toActivity(getActivity(), TeacherPlanActivity.class);
                else if (main_type == 3) {//园长端 我的园所
                    Tools.toActivity(getActivity(), SchoolGradesActivity.class);
                }
                break;
            case 4:
                if (main_type == 1) {// 家长端
                    getActivity().startActivity(DynamicActivity.getStartIntent(getActivity(), true));
                } else if (main_type == 2) {// 老师端
                    getActivity().startActivity(DynamicActivity.getStartIntent(getActivity(), true));
                } else if (main_type == 3) {//园长端 我的园所
                    getActivity().startActivity(DynamicActivity.getStartIntent(getActivity(), true));
                }
                break;
            case 5:// 我要请假
                if (main_type == 1) {
                    startActivity(HelpActivity.getStartIntent(getActivity()));
                } else {
                    Tools.toActivity(getActivity(), MyClassGridActivity.class);
                }
                break;
        }
    }

    private void checkPermission() {
        progresser.showProgress();
        FindUserExpiryEndDateReq req = new FindUserExpiryEndDateReq();
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                progresser.showContent();
                if ("true".equals(String.valueOf(response.getResult()))) {
                    toParentTab();
                } else {
                    if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
                        toParentTab();
                    } else {
                        showTipsDialog();
                    }
                }
            }

            @Override
            public void onFailed(Response response) {
                progresser.showContent();
                if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
                    toParentTab();
                } else {
                    showTipsDialog();
                }
            }
        });
    }

    private void showTipsDialog() {
        final MaterialDialog dialog = new MaterialDialog(getActivity());
        dialog.title("友情提醒");
        dialog.titleTextColor(getActivity().getResources().getColor(R.color.red_FB4354));
        dialog.content("家园共育栏目需要缴费开通!");
        dialog.contentTextColor(R.color.black);
        dialog.btnText("取消", "前往缴费");
        dialog.btnTextColor(getActivity().getResources().getColor(R.color.gray), getActivity().getResources().getColor(R.color.red_FB4354));
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
                startActivity(PayListActivity.getStartIntent(getActivity()));
            }
        });
        dialog.show();
    }

    private void toParentTab() {
        if (main_type == 1) {
            Intent intent = new Intent(getActivity(), ParentTabActivity.class);
            intent.putExtra("show", "task");
            startActivity(intent);
//                    Tools.toActivity(getActivity(), ParentTabActivity.class);
        } else if (main_type == 2) {
            Intent intent = new Intent(getActivity(), TeacherTabActivity.class);
            intent.putExtra("show", "task");
            startActivity(intent);
//                    Tools.toActivity(getActivity(), TeacherTabActivity.class);
        } else if (main_type == 3) {
            Intent intent = new Intent(getActivity(), SchoolBossTaskActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (updateBabyInfoBroadCast != null) {
            try {
                getActivity().unregisterReceiver(updateBabyInfoBroadCast);
            } catch (Exception e) {

            }
        }
    }

    private Handler handler = new Handler();
    private boolean isAuto = true;
    private Runnable switchTask = new Runnable() {
        public void run() {
            try {
                if (isAuto) {
                    int currentItem = viewPager.getCurrentItem();
                    if (currentItem < pagerAdapter.getCount() - 1) {
                        currentItem++;
                    } else {
                        currentItem = 0;
                    }
                    try {
                        viewPager.setCurrentItem(currentItem);
                    } catch (OutOfMemoryError error) {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.postDelayed(switchTask, 3000);
        }
    };

}
