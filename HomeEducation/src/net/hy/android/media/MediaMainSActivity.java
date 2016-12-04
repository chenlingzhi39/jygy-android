package net.hy.android.media;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivityHome;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import tv.danmaku.ijk.media.player.IMediaPlayer;

//import io.vov.vitamio.Vitamio;

public class MediaMainSActivity extends BaseViewActivityHome {

    private RecyclerView videoList;
    private LinearLayoutManager mLayoutManager;
    private VideoAdapter adapter;
    private FrameLayout videoLayout;

    private int postion = -1;
    private int lastPostion = -1;
    private Context context;
    private VideoPlayView videoItemView;

    private FrameLayout fullScreen;
    // private VideoListData listData;
    private ArrayList<ChildrenAnimation> childDataList;
    private RelativeLayout smallLayout;
    private ImageView close;

    private ChildrenAnimation studenChildrenAnimation;
    public static final String EXTRA_CHILD_ANIM = "studenChildrenAnimation";

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MediaMainSActivity.class);
        return intent;
    }

    public static Intent getStartIntent(Context context, ChildrenAnimation studenChildrenAnimation) {
        Intent intent = new Intent(context, MediaMainSActivity.class);
        intent.putExtra(EXTRA_CHILD_ANIM, studenChildrenAnimation);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        YtTemplate.init(this);
        setTitleText(R.string.childerAnimation);
        showTitleBack();
        setMainView(R.layout.activity_media_main);
        context = this;
        // setContentView(R.layout.activity_media_main);
        mLayoutManager = new LinearLayoutManager(this);
        videoList = (RecyclerView) findViewById(R.id.video_list);
        videoList.setLayoutManager(mLayoutManager);
        adapter = new VideoAdapter(this);
        videoList.setAdapter(adapter);
        fullScreen = (FrameLayout) findViewById(R.id.full_screen);
        videoLayout = (FrameLayout) findViewById(R.id.layout_video);

        videoItemView = new VideoPlayView(context);
      /*  String data = readTextFileFromRawResourceId(this, R.raw.video_list);
        listData = new Gson().fromJson(data, VideoListData.class);
        adapter.refresh(listData.getList());*/
        smallLayout = (RelativeLayout) findViewById(R.id.small_layout);

        close = (ImageView) findViewById(R.id.close);
        initActions();

        childDataList = new ArrayList<>();
        studenChildrenAnimation = (ChildrenAnimation) getIntent().getSerializableExtra(EXTRA_CHILD_ANIM);
        if(studenChildrenAnimation != null){
            childDataList.add(studenChildrenAnimation);
            adapter.refresh(childDataList);
        }else{
            LoadAnimation();
        }
    }

    private void LoadAnimation() {
        childDataList.clear();
        GetChildAnimationsReq req = new GetChildAnimationsReq();
        req.setGradeLevel(AppConfigHelper.getConfig(AppConfigDef.gradeLevel));
        req.setSemester(AppConfigHelper.getConfig(AppConfigDef.semester));
        String lastTime = AppConfigHelper.getConfig(AppConfigDef.lastTime);
//        if (TextUtils.isEmpty(lastTime)) {
        req.setLastTime("");
//        } else {
//            req.setLastTime(lastTime);
//        }
        progresser.showProgress();
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                try {
                    ArrayList<ChildrenAnimation> childAnimaList = (ArrayList<ChildrenAnimation>) JSONObject.parseArray(String.valueOf(response.getResult()), ChildrenAnimation.class);
                    if (childAnimaList != null && !childAnimaList.isEmpty()) {
                        childDataList.addAll(childAnimaList);
                        adapter.refresh(childAnimaList);
                        progresser.showContent();
                    } else {
                        progresser.showError("暂无数据", false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progresser.showError("数据解析异常", false);
                }
            }

            @Override
            public void onFaild(Response response) {
                progresser.showError(response.getMsg(), false);
            }
        });
    }

    private void initActions() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoItemView.isPlay()) {
                    videoItemView.pause();
                    smallLayout.setVisibility(View.GONE);
                }
            }
        });

        smallLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallLayout.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });
        videoItemView.setCompletionListener(new VideoPlayView.CompletionListener() {
            @Override
            public void completion(IMediaPlayer mp) {

                //播放完还原播放界面
                if (smallLayout.getVisibility() == View.VISIBLE) {
                    videoLayout.removeAllViews();
                    smallLayout.setVisibility(View.GONE);
                    videoItemView.setShowContoller(true);
                }

                FrameLayout frameLayout = (FrameLayout) videoItemView.getParent();
                videoItemView.release();
                if (frameLayout != null && frameLayout.getChildCount() > 0) {
                    frameLayout.removeAllViews();
                    View itemView = (View) frameLayout.getParent();

                    if (itemView != null) {
                        itemView.findViewById(R.id.showview).setVisibility(View.VISIBLE);
                    }
                }
                lastPostion = -1;
            }
        });

        adapter.setClickListener(new VideoAdapter.onClick() {
            @Override
            public void onclick(int position) {
                MediaMainSActivity.this.postion = position;

                if (videoItemView.VideoStatus() == IjkVideoView.STATE_PAUSED) {
                    if (position != lastPostion) {

                        videoItemView.stop();
                        videoItemView.release();
                    }
                }

                if (smallLayout.getVisibility() == View.VISIBLE)

                {
                    smallLayout.setVisibility(View.GONE);
                    videoLayout.removeAllViews();
                    videoItemView.setShowContoller(true);
                }

                if (lastPostion != -1)

                {
                    ViewGroup last = (ViewGroup) videoItemView.getParent();//找到videoitemview的父类，然后remove
                    if (last != null) {
                        last.removeAllViews();
                        View itemView = (View) last.getParent();
                        if (itemView != null) {
                            try {
                                itemView.findViewById(R.id.showview).setVisibility(View.VISIBLE);
                            }catch (Exception e){}
                        }
                    }
                }

                View view = videoList.findViewHolderForAdapterPosition(postion).itemView;
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.layout_video);
                frameLayout.removeAllViews();
                frameLayout.addView(videoItemView);
                //videoItemView.start(listData.getList().get(position).getMp4_url());
                videoItemView.start(childDataList.get(position).getLinkUrl());
                lastPostion = position;
            }

            @Override
            public void onShare(int position) {
                String url = childDataList.get(position).getLinkUrl();
                if (TextUtils.isEmpty(url)) {
                    Toast.makeText(MediaMainSActivity.this, "视屏链接为空,无法分享", Toast.LENGTH_SHORT).show();
                    return;
                }
//                ShareContentText contentText = new ShareContentText(url);
//                WeixinShareManager.getInstance(MediaMainSActivity.this).shareByWeixin(contentText, ShareContentText.WEIXIN_SHARE_WAY_TEXT);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        videoList.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                int index = videoList.getChildAdapterPosition(view);
                view.findViewById(R.id.showview).setVisibility(View.VISIBLE);
                if (index == postion) {
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.layout_video);
                    frameLayout.removeAllViews();
                    if (videoItemView != null &&
                            ((videoItemView.isPlay()) || videoItemView.VideoStatus() == IjkVideoView.STATE_PAUSED)) {
                        view.findViewById(R.id.showview).setVisibility(View.GONE);
                    }

                    if (videoItemView.VideoStatus() == IjkVideoView.STATE_PAUSED) {
                        if (videoItemView.getParent() != null)
                            ((ViewGroup) videoItemView.getParent()).removeAllViews();
                        frameLayout.addView(videoItemView);
                        return;
                    }

                    if (smallLayout.getVisibility() == View.VISIBLE && videoItemView != null && videoItemView.isPlay()) {
                        smallLayout.setVisibility(View.GONE);
                        videoLayout.removeAllViews();
                        videoItemView.setShowContoller(true);
                        frameLayout.addView(videoItemView);
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                int index = videoList.getChildAdapterPosition(view);
                if (index == postion) {
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.layout_video);
                    frameLayout.removeAllViews();
                    if (smallLayout.getVisibility() == View.GONE && videoItemView != null
                            && videoItemView.isPlay()) {
                        smallLayout.setVisibility(View.VISIBLE);
                        videoLayout.removeAllViews();
                        videoItemView.setShowContoller(false);
                        videoLayout.addView(videoItemView);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoItemView == null) {
            videoItemView = new VideoPlayView(context);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (videoLayout == null)
            return;
        if (smallLayout.getVisibility() == View.VISIBLE) {
            smallLayout.setVisibility(View.GONE);
            videoLayout.removeAllViews();
        }

        if (postion != -1) {
            ViewGroup view = (ViewGroup) videoItemView.getParent();
            if (view != null) {
                view.removeAllViews();
            }
        }
        videoItemView.stop();
        videoItemView.release();
        videoItemView.onDestroy();
        videoItemView = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoItemView != null) {
            videoItemView.stop();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (videoItemView != null) {
            videoItemView.onChanged(newConfig);
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fullScreen.setVisibility(View.GONE);
                videoList.setVisibility(View.VISIBLE);
                fullScreen.removeAllViews();
                if (postion <= mLayoutManager.findLastVisibleItemPosition()
                        && postion >= mLayoutManager.findFirstVisibleItemPosition()) {
                    View view = videoList.findViewHolderForAdapterPosition(postion).itemView;
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.layout_video);
                    frameLayout.removeAllViews();
                    frameLayout.addView(videoItemView);
                    videoItemView.setShowContoller(true);
                } else {
                    videoLayout.removeAllViews();
                    videoLayout.addView(videoItemView);
                    videoItemView.setShowContoller(false);
                    smallLayout.setVisibility(View.VISIBLE);
                }
                videoItemView.setContorllerVisiable();
            } else {
                ViewGroup viewGroup = (ViewGroup) videoItemView.getParent();
                if (viewGroup == null)
                    return;
                viewGroup.removeAllViews();
                fullScreen.addView(videoItemView);
                smallLayout.setVisibility(View.GONE);
                videoList.setVisibility(View.GONE);
                fullScreen.setVisibility(View.VISIBLE);
            }
        } else {
            adapter.notifyDataSetChanged();
            videoList.setVisibility(View.VISIBLE);
            fullScreen.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    public String readTextFileFromRawResourceId(Context context, int resourceId) {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(
                resourceId)));

        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }
}
