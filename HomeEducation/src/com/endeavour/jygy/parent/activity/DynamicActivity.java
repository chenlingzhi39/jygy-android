package com.endeavour.jygy.parent.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.Unicoder;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.ui.swipyrefresh.SwipyRefreshLayout;
import com.endeavour.jygy.common.ui.swipyrefresh.SwipyRefreshLayoutDirection;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.DividerItemDecoration;
import com.endeavour.jygy.parent.adapter.DynamicDetialRcvAdapter;
import com.endeavour.jygy.parent.adapter.DynamicDiscussListener;
import com.endeavour.jygy.parent.bean.Dynamic;
import com.endeavour.jygy.parent.bean.DynamicDelReq;
import com.endeavour.jygy.parent.bean.DynamicDiscuss;
import com.endeavour.jygy.parent.bean.DynamicDiscussDelReq;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.endeavour.jygy.parent.bean.RecommendContent;
import com.endeavour.jygy.parent.bean.SendDynamicDiscussReq;
import com.endeavour.jygy.schoolboss.activity.SchoolGradesActivity;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.util.List;

/**
 * 动态
 * Created by wu on 15/11/26.
 */
public class DynamicActivity extends BaseViewActivity implements SwipyRefreshLayout.OnRefreshListener, DynamicDiscussListener {
    private DynamicDetialRcvAdapter adapter;
    private DynamicOpter dynamicOpter;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    private DynamicDiscuss currentDiscuss;
    private Dynamic currentDynamic;
    private View rlDiscuss;
    private EditText etDiscuss;
    private Dynamic toDelDynamic;
    private DynamicDiscuss toDelDiscuss;

    public static final int REQUEST_CODE = 10001;

    public static final String UpdateDynamicBroadCastAction = "com.endeavour.jygy.parent.activity.UpdateDynamicBroadCast";

    private UpdateDynamicBroadCast updateBabyInfoBroadCast;

    public class UpdateDynamicBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            refreshDynamics();
        }
    }


    public static Intent getStartIntent(Context context, boolean isWunderfulTime) {
        Intent intent = new Intent(context, DynamicActivity.class);
        intent.putExtra("isWunderfulTime", isWunderfulTime);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.fragment_dynamic);
        showTitleBack();
        if (!isWunderfulTime()) {
            setTitleText(R.string.dynamic);
            if (MainApp.isShoolboss()) {
                setTitleRight("切换班级");
            } else {
                if (MainApp.isTeacher()) {
                    MainApp.getInstance().resetBabyInfo();
                    setTitleRightImage(R.drawable.title_btn_add);
                } else {
                    if (!"1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
                        setTitleRightImage(R.drawable.title_btn_add);
                    }
                }
            }
        } else {
            setTitleText("精彩瞬间");
            if (MainApp.isShoolboss()) {
                setTitleRight("切换班级");
            }
        }
        isLogin();
        isBabyChoose();
        mSwipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        RecyclerView lvDynamic = (RecyclerView) findViewById(R.id.lvDynamic);
        etDiscuss = (EditText) findViewById(R.id.etDiscuss);
        findViewById(R.id.btnSend).setOnClickListener(this);
        rlDiscuss = findViewById(R.id.rlDiscuss);
        View btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        adapter = new DynamicDetialRcvAdapter(this);
        adapter.setDynamicDiscussListener(this);
        lvDynamic.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL, 1);
        lvDynamic.setLayoutManager(new LinearLayoutManager(this));
        lvDynamic.addItemDecoration(itemDecoration);
        dynamicOpter = new DynamicOpter();
        if (!TextUtils.isEmpty(AppConfigHelper.getConfig(AppConfigDef.classID))) {
            startRefresh();
            progresser.showProgress();
            refreshDynamics();
        } else {
            progresser.showError("请先选择班级", false);
        }

        updateBabyInfoBroadCast = new UpdateDynamicBroadCast();
        LocalBroadcastManager.getInstance(this).registerReceiver(updateBabyInfoBroadCast, new IntentFilter(UpdateDynamicBroadCastAction));
    }

    private boolean isWunderfulTime() {
        return getIntent().getBooleanExtra("isWunderfulTime", false);
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        if (MainApp.isShoolboss()) {
            Intent intent = new Intent(DynamicActivity.this, SchoolGradesActivity.class);
            intent.putExtra("title", "切换班级");
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Tools.toActivity(this, EditDynamicActivity.class);
        }
    }

    /**
     * 开始加载
     */
    private void startRefresh() {
        Log.d("recordfragment", "开始加载");
        mSwipyRefreshLayout.setRefreshing(true);
    }

    /**
     * 停止加载
     */
    private void stopRefresh() {
        mSwipyRefreshLayout.setRefreshing(false);
//        progresser.showContent();
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (SwipyRefreshLayoutDirection.TOP == direction) {
            refreshDynamics();
        } else if (SwipyRefreshLayoutDirection.BOTTOM == direction) {
            loadMoreDynamics();
        }
    }

    private void refreshDynamics() {
        if (TextUtils.isEmpty(AppConfigHelper.getConfig(AppConfigDef.classID))) {
            return;
        }
        dynamicOpter.refreshDynamics(isWunderfulTime(), new DynamicOpter.LoadDynamicListener() {
            @Override
            public void onLoad(List<Dynamic> dynamics) {
                if (dynamics == null || dynamics.isEmpty()) {
                    if(isWunderfulTime()){
                        findViewById(R.id.swipyrefreshlayout).setVisibility(View.GONE);
                        findViewById(R.id.ivWunderful).setVisibility(View.VISIBLE);
                        progresser.showContent();
                    }else{
                        progresser.showError("暂无数据", false);
                    }
                } else {
                    findViewById(R.id.swipyrefreshlayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.ivWunderful).setVisibility(View.GONE);
                    adapter.setDataChanged(dynamics);
                    progresser.showContent();
                }
                stopRefresh();
            }
        });
    }

    private void loadMoreDynamics() {
        if (TextUtils.isEmpty(AppConfigHelper.getConfig(AppConfigDef.classID))) {
            return;
        }
        dynamicOpter.loadMoreDynamics(isWunderfulTime(), new DynamicOpter.LoadDynamicListener() {
            @Override
            public void onLoad(List<Dynamic> dynamics) {
                if (dynamics == null || dynamics.isEmpty()) {
                    Tools.toastMsg(DynamicActivity.this, "没有更多数据了");
                } else {
                    adapter.addDataChanged(dynamics);
                }
                stopRefresh();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btnSend) {//评论
            String discussContent = etDiscuss.getText().toString();
            if (TextUtils.isEmpty(discussContent)) {
                return;
            }
            if (currentDynamic == null) {
                return;
            }
            sendDynamicDiscuss(discussContent);
        }
    }

    private void sendDynamicDiscuss(String content) {
        Tools.hideKeyBorad(this, etDiscuss);
        progresser.showProgress();
        SendDynamicDiscussReq req = new SendDynamicDiscussReq();
        req.setUserId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setContent(Unicoder.emojiToUnicode(content));
        req.setMsgId(currentDynamic.getId());
        req.setType("2");
        req.setReStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        req.setBeUserId(currentDynamic.getUserId());
        if (currentDiscuss != null) {
            req.setBeStudentId(currentDiscuss.getReStudentId());
            req.setBeUserId(currentDiscuss.getReUserId());
            req.setCommentId(currentDiscuss.getId());
        } else {
            req.setBeStudentId(currentDynamic.getStudentId());
        }
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                clearAndHideBtmDisscussView();
                refreshDynamics();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(DynamicActivity.this, response.getMsg());
            }
        });
    }

    @Override
    public void onDiscussClicked(Dynamic dynamic, DynamicDiscuss dynamicDiscuss) {
        currentDiscuss = dynamicDiscuss;
        currentDynamic = dynamic;
        showDiscussView();
    }

    @Override
    public void onDelClicked(Dynamic dynamic) {
        currentDiscuss = null;
        currentDynamic = null;
        clearAndHideBtmDisscussView();
        toDelDynamic = dynamic;
        showDeleteDynamicDialog();
    }

    private void delDynamic(Dynamic dynamic) {
        if (dynamic == null) {
            return;
        }
        progresser.showProgress();
        DynamicDelReq req = new DynamicDelReq();
        req.setMsgId(dynamic.getId());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                refreshDynamics();
                progresser.showContent();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(DynamicActivity.this, response.getMsg());
            }
        });
    }

    @Override
    public void onDelDiscuss(DynamicDiscuss discuss) {
        currentDiscuss = null;
        currentDynamic = null;
        clearAndHideBtmDisscussView();
        toDelDiscuss = discuss;
        showDeleteDiscussDialog();
    }

    private void delDynamicDicuss(DynamicDiscuss discuss) {
        if (discuss == null) {
            return;
        }
        progresser.showProgress();
        DynamicDiscussDelReq req = new DynamicDiscussDelReq();
        req.setCommentId(discuss.getId());
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                refreshDynamics();
                progresser.showContent();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(DynamicActivity.this, response.getMsg());
            }
        });
    }

    @Override
    public void onLikeCliked(Dynamic dynamic) {
        clearAndHideBtmDisscussView();
        //增加逻辑: 重复赞提示"已赞" :先查询本地数据库,如果数据库有已经赞的记录,直接提示已赞,否则网络请求赞
        boolean isLiked = dynamicOpter.isSelfLiked(dynamic);
        if (isLiked) {
            Tools.toastMsg(DynamicActivity.this, "已赞");
            return;
        }
        progresser.showProgress();
        dynamicOpter.like(dynamic, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                //增加逻辑: 重复赞提示"已赞"
                refreshDynamics();
                progresser.showContent();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                Tools.toastMsg(DynamicActivity.this, response.getMsg());
            }
        });
    }

    @Override
    public void onTaskDetialClicked(Dynamic dynamic) {
        RecommendContent recommendContent = dynamic.toRecommendContent();
        List<String> completeAttachement = recommendContent.getCompleteAttachement();

        GetStudenTasksResp getStudenTasksResp = new GetStudenTasksResp();
        getStudenTasksResp.setTaskTitle(recommendContent.getLessonPlanTitle());
        getStudenTasksResp.setTaskContent(recommendContent.getTaskContent());
        getStudenTasksResp.setUserTaskStatus("1");
        getStudenTasksResp.setCompleteAttach(JSONArray.toJSONString(completeAttachement));
        getStudenTasksResp.setCompleteTime(dynamic.getCreateTime());

        getStudenTasksResp.setCompleteContent(dynamic.getMsgComments().get(0).getCommentContent());
        Intent intent = new Intent(this, TaskContentActivity.class);
        intent.putExtra("task", getStudenTasksResp);
        startActivity(intent);
    }


    public void showDiscussView() {
        rlDiscuss.setVisibility(View.VISIBLE);
        Tools.showKeyBorad(this, etDiscuss);
    }

    public void clearAndHideBtmDisscussView() {
        Tools.hideKeyBorad(this, etDiscuss);
        etDiscuss.setText("");
        rlDiscuss.setVisibility(View.GONE);
    }

    private void showDeleteDynamicDialog() {
        final String[] stringItems = {"确认删除"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this,
                stringItems, findViewById(R.id.rlMain));
        dialog.isTitleShow(false).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                dialog.dismiss();
                if (position == 0) {
                    delDynamic(toDelDynamic);
                }
            }
        });
    }

    private void showDeleteDiscussDialog() {
        final String[] stringItems = {"确认删除"};
        final ActionSheetDialog dialog = new ActionSheetDialog(this,
                stringItems, findViewById(R.id.rlMain));
        dialog.isTitleShow(false).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                dialog.dismiss();
                if (position == 0) {
                    delDynamicDicuss(toDelDiscuss);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (updateBabyInfoBroadCast != null) {
            try {
                this.unregisterReceiver(updateBabyInfoBroadCast);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (rlDiscuss.getVisibility() == View.VISIBLE) {
            clearAndHideBtmDisscussView();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == REQUEST_CODE && arg1 == RESULT_OK) {
            progresser.showProgress();
            refreshDynamics();
        }
    }
}
