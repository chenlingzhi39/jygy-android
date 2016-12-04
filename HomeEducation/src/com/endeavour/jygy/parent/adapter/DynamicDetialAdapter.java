package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.TimeUtils;
import com.endeavour.jygy.common.Unicoder;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.view.CommentTextView;
import com.endeavour.jygy.common.view.NineSquaresView;
import com.endeavour.jygy.common.view.PraiseTextView;
import com.endeavour.jygy.common.view.TextBlankClickListener;
import com.endeavour.jygy.login.activity.bean.UserInfo;
import com.endeavour.jygy.parent.activity.ImageBrowserActivity;
import com.endeavour.jygy.parent.activity.VideoShowActivity;
import com.endeavour.jygy.parent.bean.Dynamic;
import com.endeavour.jygy.parent.bean.DynamicDiscuss;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wizarpos.log.util.LogEx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DynamicDetialAdapter extends BaseAdapter {

    public interface DynamicDiscussListener {
        void onDiscussClicked(Dynamic dynamic, DynamicDiscuss dynamicDiscuss);

        void onDelClicked(Dynamic dynamic);

        void onDelDiscuss(DynamicDiscuss discuss);

        void onLikeCliked(Dynamic dynamic);
    }

    private Context context;

    private DynamicDiscussListener listener;

    public void setDynamicDiscussListener(DynamicDiscussListener listener) {
        this.listener = listener;
    }

    private List<Dynamic> dynamics = new ArrayList<Dynamic>();

    private List<Boolean> dynamicBools = new ArrayList<>();

    public DynamicDetialAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return dynamics.size();
    }

    @Override
    public Object getItem(int position) {
        return dynamics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_dynamic, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.lvRight = (LinearLayout) convertView.findViewById(R.id.lvRight);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            viewHolder.nsvDynamicImgs = (NineSquaresView) convertView.findViewById(R.id.nsvDynamicImgs);
//            viewHolder.igDynamicViewCon = (FrameLayout) convertView.findViewById(R.id.igDynamicViewCon);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.btnDiscuss = (TextView) convertView.findViewById(R.id.btnDiscuss);
            viewHolder.tvDiscussCount = (TextView) convertView.findViewById(R.id.tvDiscussCount);
            viewHolder.tvLikeCount = (TextView) convertView.findViewById(R.id.tvLikeCount);
            viewHolder.llDiscuss = (LinearLayout) convertView.findViewById(R.id.llDiscuss);
            viewHolder.tvLikeNum = (PraiseTextView) convertView.findViewById(R.id.tvLikeNum);
            viewHolder.llPopupActions = convertView.findViewById(R.id.llPopupActions);
            viewHolder.tvLike = convertView.findViewById(R.id.tvLike);
            viewHolder.tvDiscuss = convertView.findViewById(R.id.tvDiscuss);
            viewHolder.tvDel = convertView.findViewById(R.id.tvDel);
//            viewHolder.ivGradView = (ImageGridView) convertView.findViewById(R.id.ivGradView);
            convertView.setTag(viewHolder);
        }
        initializeViews(position, (Dynamic) getItem(position), (ViewHolder) convertView.getTag());
        return convertView;

    }

    public View newDiscussView(final Dynamic dynamic, final DynamicDiscuss discuss) {
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
                if (listener != null) {
                    if (UserInfo.ROLE_ADMIN.equals(AppConfigHelper.getConfig(AppConfigDef.roleValue))) { //院长端不能删评论
                        return;
                    } else if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(discuss.getReUserId())) { //自己的评论可以删除
                        listener.onDelDiscuss(discuss);
                    } else if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(dynamic.getUserId())) { //自己发的动态可以删除
                        listener.onDelDiscuss(discuss);
                    }
                }
            }
        });
        return tvDiscuss;
    }

    public View newDiscussLine() {
        return LayoutInflater.from(context).inflate(R.layout.item_disscuss_line, null);
    }

    private void initializeViews(final int position, final Dynamic dynamic, final ViewHolder holder) {
//        if (!TextUtils.isEmpty(dynamic.getLikes()) && !"0".equals(dynamic.getLikes())) {
//            holder.tvLikeNum.setPraiseNnm(dynamic.getLikes());
//        }
        ImageLoader.getInstance().displayImage(dynamic.getHeadPortrait(), holder.ivIcon);

        if (dynamic.getMsgLikes() != null && !dynamic.getMsgLikes().isEmpty()) {
            List<String> names = new ArrayList<>();
            for (DynamicDiscuss like : dynamic.getMsgLikes()) {
                names.add(like.getReUserName());
            }
            if (!names.isEmpty()) {
                holder.tvLikeNum.setPraiseName(names);
                holder.tvLikeNum.setVisibility(View.VISIBLE);
            } else {
                holder.tvLikeNum.setVisibility(View.GONE);
            }
        } else {
            holder.tvLikeNum.setVisibility(View.GONE);
        }

        if (dynamicBools.get(position)) {
            holder.llPopupActions.setVisibility(View.VISIBLE);
        } else {
            holder.llPopupActions.setVisibility(View.INVISIBLE);
        }

        holder.tvDiscussCount.setText("讨论: " + dynamic.getComments());
        holder.tvName.setText(dynamic.getUserName());
        holder.tvContent.setText(Unicoder.unicode2Emoji(dynamic.getContent()));
        holder.tvTime.setText(TimeUtils.getDescriptionTimeFromTimestamp(Long.parseLong(dynamic.getCreateTime())));

        if (UserInfo.ROLE_ADMIN.equals(AppConfigHelper.getConfig(AppConfigDef.roleValue)) || UserInfo.ROLE_RESTRICTED_ADMIN.equals(AppConfigHelper.getConfig(AppConfigDef.roleValue))) {
            holder.tvDel.setVisibility(View.VISIBLE);
        } else if (AppConfigHelper.getConfig(AppConfigDef.parentId).equals(dynamic.getUserId())) {
            holder.tvDel.setVisibility(View.VISIBLE);
        } else {
            holder.tvDel.setVisibility(View.INVISIBLE);
        }
        holder.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDelClicked(dynamic);
                }
            }
        });
        holder.btnDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamicBools.get(position)) {
                    holder.llPopupActions.setVisibility(View.INVISIBLE);
                    dynamicBools.set(position, false);
                } else {
                    holder.llPopupActions.setVisibility(View.VISIBLE);
                    dynamicBools.set(position, true);
                }
            }
        });

        holder.tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLikeCliked(dynamic);
                }
                holder.llPopupActions.setVisibility(View.INVISIBLE);
                dynamicBools.set(position, false);
            }
        });

        holder.tvDiscuss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDiscussClicked(dynamic, null);
                }
                holder.llPopupActions.setVisibility(View.INVISIBLE);
                dynamicBools.set(position, false);
            }
        });

//        holder.ivGradView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //图片格的点击
//            }
//        });
//        FrameLayout igDynamicViewCon = holder.igDynamicViewCon;
//        igDynamicViewCon.removeAllViews();
//        holder.ivGradView.setVisibility(View.GONE);
//        igDynamicViewCon.setVisibility(View.GONE);
        final List<String> attachs = dynamic.getAttachs();
        if (attachs == null || attachs.isEmpty()) {
//            igDynamicViewCon.setVisibility(View.GONE);
//            holder.ivGradView.setVisibility(View.GONE);
            LogEx.d("dynamic item", "noImgs");
            holder.nsvDynamicImgs.setVisibility(View.GONE);
            holder.nsvDynamicImgs.setOnImgClickedListener(null);
            holder.nsvDynamicImgs.setTag(null);
        } else {
            List<String> tempAttachs = new ArrayList<>();
            for (int i = 0; i < attachs.size(); i++) {
                tempAttachs.add(attachs.get(i));
            }
            if (tempAttachs.size() == 2) {
                Iterator<String> iterator = tempAttachs.iterator();
                while (iterator.hasNext()) {
                    String string = iterator.next();
                    if (string.endsWith(".mp4")) {
                        iterator.remove();
                    }
                }
            }
            holder.nsvDynamicImgs.setVisibility(View.VISIBLE);
            holder.nsvDynamicImgs.rander(attachs);
            holder.nsvDynamicImgs.setTag(attachs);
            holder.nsvDynamicImgs.setOnImgClickedListener(new NineSquaresView.OnImgClickedListener() {
                @Override
                public void onItemClicked(String attach) {
                    if (attach.endsWith(".mp4")) {
                        Intent intent = new Intent(context, VideoShowActivity.class);
                        intent.putExtra("videopath", attach);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ImageBrowserActivity.class);
                        int position = 0;
                        List<String> list = (List<String>) holder.nsvDynamicImgs.getTag();
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
                }
            });
//            holder.ivGradView.setVisibility(View.VISIBLE);
//            igDynamicViewCon.setVisibility(View.VISIBLE);
//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            int width = wm.getDefaultDisplay().getWidth();
//            float scale = DipHelper.px2dip(context, width) / (float) DipHelper.px2dip(context, 1080);
//            ImageGridView imageGridView = new ImageGridView(context, attachs.size(), DipHelper.dip2px(context, scale * 240));
//            FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            igDynamicViewCon.addView(imageGridView, imageParams);
//            imageGridView.updateWithImage(attachs);
//            holder.ivGradView.updateWithImage(attachs);
//            holder.ivGradView.updateWithImage(attachs);
        }
        //评论
        LinearLayout layout = holder.llDiscuss;
        layout.removeAllViews();
        List<DynamicDiscuss> msgComments = dynamic.getMsgComments();
        if (msgComments != null) {
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


    protected class ViewHolder {
        private ImageView ivIcon;
        private LinearLayout lvRight;
        private TextView tvName;
        private TextView tvContent;
        private NineSquaresView nsvDynamicImgs;
        //        private FrameLayout igDynamicViewCon;
        private TextView tvTime;
        private TextView btnDiscuss;
        private TextView tvDiscussCount;
        private TextView tvLikeCount;
        private LinearLayout llDiscuss;
        private PraiseTextView tvLikeNum;
        private View llPopupActions;
        private View tvLike;
        private View tvDiscuss;
        private View tvDel;
//        private ImageGridView ivGradView;
    }

    public void setDataChanged(List<Dynamic> dynamics) {
        try {
            this.dynamics.clear();
            this.dynamics.addAll(dynamics);
            this.dynamicBools.clear();
            for (Dynamic dynamic : dynamics) {
                dynamicBools.add(false);
            }
            this.notifyDataSetChanged();
        } catch (Exception e) {
        }

    }

    public void addDataChanged(List<Dynamic> dynamics) {
        try {
            this.dynamics.addAll(dynamics);
            for (Dynamic dynamic : dynamics) {
                dynamicBools.add(false);
            }
            this.notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

    public void clearData() {
        this.dynamics.clear();
        this.dynamicBools.clear();
        this.notifyDataSetChanged();
    }


}
