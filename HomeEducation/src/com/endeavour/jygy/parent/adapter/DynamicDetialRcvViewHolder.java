package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.TimeUtils;
import com.endeavour.jygy.common.Unicoder;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.view.CommentTextView;
import com.endeavour.jygy.common.view.NineSquaresView;
import com.endeavour.jygy.common.view.PraiseTextView;
import com.endeavour.jygy.common.view.TextBlankClickListener;
import com.endeavour.jygy.common.view.VideoSquaresView;
import com.endeavour.jygy.login.activity.bean.UserInfo;
import com.endeavour.jygy.parent.activity.ImageBrowserActivity;
import com.endeavour.jygy.parent.activity.VideoShowActivity;
import com.endeavour.jygy.parent.bean.Dynamic;
import com.endeavour.jygy.parent.bean.DynamicDiscuss;
import com.endeavour.jygy.parent.bean.RecommendContent;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wizarpos.log.util.LogEx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wu on 16/1/5.
 */
public class DynamicDetialRcvViewHolder extends RecyclerView.ViewHolder {

    private DynamicDiscussListener listener;
    private Context context;

    private ImageView ivIcon;
    private LinearLayout lvRight;
    private TextView tvName;
    private TextView tvContent;
    private NineSquaresView nsvDynamicImgs;
    private VideoSquaresView videoSquaresView;
    private TextView tvTime;
    private TextView btnDiscuss;
    private TextView tvDiscussCount;
    private TextView tvLikeCount;
    private TextView tvClassName;
    private TextView tvTaskDetial;
    private LinearLayout llDiscuss;
    private PraiseTextView tvLikeNum;
    private View llPopupActions;
    private View tvLike;
    private View tvDiscuss;
    private View tvDel;
    private View viewSpace;
    private View viewSpace2;
    private TextView tvDelPop;

    public DynamicDetialRcvViewHolder(Context context, View itemView, DynamicDiscussListener listener) {
        super(itemView);
        this.listener = listener;
        this.context = context;

        ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
        lvRight = (LinearLayout) itemView.findViewById(R.id.lvRight);
        tvName = (TextView) itemView.findViewById(R.id.tvName);
        tvClassName = (TextView) itemView.findViewById(R.id.tvClassName);
        tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        nsvDynamicImgs = (NineSquaresView) itemView.findViewById(R.id.nsvDynamicImgs);
        videoSquaresView = (VideoSquaresView) itemView.findViewById(R.id.videoSquaresView);
        tvTime = (TextView) itemView.findViewById(R.id.tvTime);
        btnDiscuss = (TextView) itemView.findViewById(R.id.btnDiscuss);
        tvDiscussCount = (TextView) itemView.findViewById(R.id.tvDiscussCount);
        tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
        llDiscuss = (LinearLayout) itemView.findViewById(R.id.llDiscuss);
        tvLikeNum = (PraiseTextView) itemView.findViewById(R.id.tvLikeNum);
        llPopupActions = itemView.findViewById(R.id.llPopupActions);
        tvLike = itemView.findViewById(R.id.tvLike);
        tvDiscuss = itemView.findViewById(R.id.tvDiscuss);
        tvDel = itemView.findViewById(R.id.tvDel);
        tvTaskDetial = (TextView) itemView.findViewById(R.id.tvTaskDetial);
        viewSpace = itemView.findViewById(R.id.viewSpace);
        viewSpace2 = itemView.findViewById(R.id.viewSpace2);
        tvDelPop = (TextView) itemView.findViewById(R.id.tvDelPop);
    }

    private static final String LOG_TAG = "DynamicDetial";

    public void rander(final Dynamic dynamic) {
        dynamic.adapte();
        if (dynamic.getHeadPortrait() != null && !dynamic.getHeadPortrait().equals(""))
            ImageLoader.getInstance().displayImage(dynamic.getHeadPortrait(), ivIcon);
        else {
            String sex = AppConfigHelper.getConfig(AppConfigDef.babySex);
            if (sex.equals("1")) {
                String path = "drawable://" + R.drawable.boy;
                ImageLoader.getInstance().displayImage(path, ivIcon);
            } else {
                String path = "drawable://" + R.drawable.girl;
                ImageLoader.getInstance().displayImage(path, ivIcon);
            }
        }
        if (dynamic.getMsgLikes() != null && !dynamic.getMsgLikes().isEmpty()) {
            List<String> names = new ArrayList<>();
            for (DynamicDiscuss like : dynamic.getMsgLikes()) {
                names.add(like.getReUserName());
            }
            if (!names.isEmpty()) {
                tvLikeNum.setPraiseName(names);
                tvLikeNum.setVisibility(View.VISIBLE);
            } else {
                tvLikeNum.setVisibility(View.GONE);
            }
        } else {
            tvLikeNum.setVisibility(View.GONE);
        }

        if ("1".equals(AppConfigHelper.getConfig(AppConfigDef.graduationFlag))) {
            llPopupActions.setVisibility(View.INVISIBLE);
        } else if (dynamic.getShowDiscussPopup() == 1) {
            llPopupActions.setVisibility(View.VISIBLE);
            if (dynamic.isTask() && MainApp.isTeacher()) {
                RecommendContent recommendContent = dynamic.toRecommendContent();
                if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(recommendContent.getTeacherId())) {
                    tvDelPop.setVisibility(View.VISIBLE);
                } else {
                    tvDelPop.setVisibility(View.GONE);
                }
            } else {
                tvDelPop.setVisibility(View.GONE);
            }
        } else {
            llPopupActions.setVisibility(View.INVISIBLE);
        }

        tvDiscussCount.setText("讨论: " + dynamic.getComments());
        tvName.setText(dynamic.getUserName());
        String unicode = Unicoder.unicode2Emoji(dynamic.getContent());
        tvContent.setText(unicode);
        tvTime.setText(TimeUtils.getDescriptionTimeFromTimestamp(Long.parseLong(dynamic.getCreateTime())));

        if (TextUtils.isEmpty(dynamic.getClassName())) {
            tvClassName.setVisibility(View.GONE);
        } else {
            tvClassName.setVisibility(View.VISIBLE);
            tvClassName.setText(" [" + dynamic.getClassName() + "]");
        }

        if (dynamic.isTask()) {
            tvTaskDetial.setVisibility(View.VISIBLE);
            viewSpace.setVisibility(View.VISIBLE);
            viewSpace2.setVisibility(View.VISIBLE);
        } else {
            tvTaskDetial.setVisibility(View.GONE);
            viewSpace.setVisibility(View.GONE);
            viewSpace2.setVisibility(View.GONE);
        }

        if (dynamic.isTask()) {
            tvDel.setVisibility(View.GONE);
        } else {
            if (UserInfo.ROLE_ADMIN.equals(AppConfigHelper.getConfig(AppConfigDef.roleValue)) || UserInfo.ROLE_RESTRICTED_ADMIN.equals(AppConfigHelper.getConfig(AppConfigDef.roleValue))) {
                tvDel.setVisibility(View.VISIBLE);
            } else if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(dynamic.getUserId())) {
                tvDel.setVisibility(View.VISIBLE);
            } else {
                tvDel.setVisibility(View.INVISIBLE);
            }
        }
        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDelClicked(dynamic);
                }
            }
        });
        tvDelPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDelClicked(dynamic);
                }
                llPopupActions.setVisibility(View.INVISIBLE);
            }
        });
        btnDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamic.getShowDiscussPopup() == 1) {
                    llPopupActions.setVisibility(View.INVISIBLE);
                    dynamic.setShowDiscussPopup(0);
                } else {
                    llPopupActions.setVisibility(View.VISIBLE);

                    if (dynamic.isTask() && MainApp.isTeacher()) {
                        RecommendContent recommendContent = dynamic.toRecommendContent();
                        if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(recommendContent.getTeacherId())) {
                            tvDelPop.setVisibility(View.VISIBLE);
                        } else {
                            tvDelPop.setVisibility(View.GONE);
                        }
                    } else {
                        tvDelPop.setVisibility(View.GONE);
                    }

                    dynamic.setShowDiscussPopup(1);
                }
            }
        });

        tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLikeCliked(dynamic);
                }
                llPopupActions.setVisibility(View.INVISIBLE);
                dynamic.setShowDiscussPopup(0);
            }
        });

        tvDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDiscussClicked(dynamic, null);
                }
                llPopupActions.setVisibility(View.INVISIBLE);
                dynamic.setShowDiscussPopup(0);
            }
        });

        tvTaskDetial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTaskDetialClicked(dynamic);
                }
                llPopupActions.setVisibility(View.INVISIBLE);
            }
        });

//        ivGradView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //图片格的点击
//            }
//        });
//        FrameLayout igDynamicViewCon = igDynamicViewCon;
//        igDynamicViewCon.removeAllViews();
//        ivGradView.setVisibility(View.GONE);
//        igDynamicViewCon.setVisibility(View.GONE);
        final List<String> attachs = dynamic.getAttachs();
        if (attachs == null || attachs.isEmpty()) {
//            igDynamicViewCon.setVisibility(View.GONE);
//            ivGradView.setVisibility(View.GONE);
            LogEx.d("dynamic item", "noImgs");
            hideImgs();
            hideVideos();
        } else {
            List<String> tempAttachs = new ArrayList<>();
            for (int i = 0; i < attachs.size(); i++) {
                tempAttachs.add(attachs.get(i));
            }
            if (tempAttachs.size() == 2) {
                if (containMP4(tempAttachs)) {
                    hideImgs();
                    showVideos(attachs);
                } else {
                    hideVideos();
                    showImgs(attachs);
                }
            } else {
                hideVideos();
                showImgs(attachs);
            }

        }
        //评论
        LinearLayout layout = llDiscuss;
        layout.removeAllViews();
        List<DynamicDiscuss> msgComments = dynamic.getMsgComments();
        if (msgComments != null) {
            Iterator<DynamicDiscuss> iterator = msgComments.iterator();
            while (iterator.hasNext()) {
                DynamicDiscuss dynamicDiscuss = iterator.next();
                if ("1".equals(dynamicDiscuss.getStatus())) {
                    iterator.remove();
                }
            }
        }
        if (msgComments != null && !msgComments.isEmpty()) {
            layout.setVisibility(View.VISIBLE);
            for (int i = 0; i < msgComments.size(); i++) {
                layout.addView(newDiscussView(dynamic, msgComments.get(i)));
                if (i < msgComments.size() - 1) {
                    layout.addView(newDiscussLine());
                }
            }
        } else {
            layout.setVisibility(View.GONE);
            LogEx.d("Dynamic", "dynamics discuss isEmpty");
        }
    }

    private void hideImgs() {
        nsvDynamicImgs.setVisibility(View.GONE);
        nsvDynamicImgs.setOnImgClickedListener(null);
        nsvDynamicImgs.setTag(null);
    }

    private void hideVideos() {
        videoSquaresView.setVisibility(View.GONE);
        videoSquaresView.setOnImgClickedListener(null);
        videoSquaresView.setTag(null);
    }

    private void showVideos(List<String> attachs) {
        videoSquaresView.setVisibility(View.VISIBLE);
        videoSquaresView.rander(attachs);
        videoSquaresView.setTag(attachs);
        videoSquaresView.setOnImgClickedListener(new VideoSquaresView.OnImgClickedListener() {
            @Override
            public void onItemClicked(String attach) {
                Intent intent = new Intent(context, VideoShowActivity.class);
                intent.putExtra("videopath", attach);
                context.startActivity(intent);
            }
        });
    }

    private void showImgs(List<String> attachs) {
        nsvDynamicImgs.setVisibility(View.VISIBLE);
        nsvDynamicImgs.rander(attachs);
        nsvDynamicImgs.setTag(attachs);
        nsvDynamicImgs.setOnImgClickedListener(new NineSquaresView.OnImgClickedListener() {
            @Override
            public void onItemClicked(String attach) {
                Intent intent = new Intent(context, ImageBrowserActivity.class);
                int position = 0;
                List<String> list = (List<String>) nsvDynamicImgs.getTag();
                for (int i = 0; i < list.size(); i++) {
                    if (attach.equals(list.get(i))) {
                        position = i;
                        break;
                    }
                }
                intent.putExtra("paths", (Serializable) list);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }


    private View newDiscussView(final Dynamic dynamic, final DynamicDiscuss discuss) {
        CommentTextView tvDiscuss = (CommentTextView) LayoutInflater.from(context).inflate(R.layout.item_tv_discuss, null);
        tvDiscuss.setReply(discuss);
        tvDiscuss.setListener(new TextBlankClickListener() {
            @Override
            public void onBlankClick(View view) {
                if (listener != null) {
                    listener.onDiscussClicked(dynamic, discuss);
                }
            }

            @Override
            public void onLongClick(View view) {
//                if (listener != null) {
//                    if (UserInfo.ROLE_ADMIN.equals(AppConfigHelper.getConfig(AppConfigDef.roleValue))) { //院长端不能删评论
//                        return;
//                    } else if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(discuss.getReUserId())) { //自己的评论可以删除
//                        listener.onDelDiscuss(discuss);
//                    } else if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(dynamic.getUserId())) { //自己发的动态可以删除
//                        listener.onDelDiscuss(discuss);
//                    }
//                }
            }
        });
        tvDiscuss.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    if (UserInfo.ROLE_ADMIN.equals(AppConfigHelper.getConfig(AppConfigDef.roleValue))) { //院长端不能删评论
                        return true;
                    } else if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(discuss.getReUserId())) { //自己的评论可以删除
                        listener.onDelDiscuss(discuss);
                        return true;
                    } else if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(dynamic.getUserId())) { //自己发的动态可以删除
                        listener.onDelDiscuss(discuss);
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });
        return tvDiscuss;
    }

    private View newDiscussLine() {
        return LayoutInflater.from(context).inflate(R.layout.item_disscuss_line, null);
    }

    private boolean containMP4(List<String> urls) {
        for (String str : urls) {
            if (str.endsWith(".mp4")) {
                return true;
            }
        }
        return false;
    }
}
