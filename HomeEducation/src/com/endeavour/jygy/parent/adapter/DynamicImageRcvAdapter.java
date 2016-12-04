package com.endeavour.jygy.parent.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.parent.activity.ImageBrowserActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/11/17.
 */
public class DynamicImageRcvAdapter extends RecyclerView.Adapter<DynamicImageRcvAdapter.ViewHolder> {

    private int maxSize = 6;

    private List<String> paths = new ArrayList<String>();

    private ChooseImageAdapterCallBack chooseImageAdapterCallBack;

    public void setChooseImageAdapterCallBack(ChooseImageAdapterCallBack chooseImageAdapterCallBack) {
        this.chooseImageAdapterCallBack = chooseImageAdapterCallBack;
    }

    private Context context;

    private int type = 1; //1录音 2视频 3照片 4相册选择
    private int videoflag = 0; //0拍过 1视频 2录音 3拍照或者相册

    public DynamicImageRcvAdapter(Context context) {
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView;
        }

        public void rander(final int position) {
            if (imgSizeFull()) {
                showImg(position);
            } else {
                int size = getImgSize();
                if (position < size) {
                    showImg(position);
                } else {
                    iv.setImageResource(R.drawable.addphoto);
                }
            }

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imgSizeFull()) {
                        toImgDetialView();
                    } else {
                        int size = getImgSize();
                        if (position < size) {
                            toImgDetialView();
                        } else {
                            if (chooseImageAdapterCallBack != null) {
                                chooseImageAdapterCallBack.showArcLayout();
                            }
                        }
                    }

                }

                private void toImgDetialView() {
                    Intent intent = new Intent(context, ImageBrowserActivity.class);
                    intent.putExtra("paths", (Serializable) paths);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }

        private void showImg(int position) {
            iv.setVisibility(View.VISIBLE);
            if (type == 3 || type == 4 || type == 2) {
                ImageLoader.getInstance().displayImage(paths.get(position), iv, MainApp.getOptions());
            } else if (type == 1) {
                iv.setImageResource(R.drawable.sound);
            }
        }
    }

    public void addImage(String url) {
        paths.add(url);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_edit_dynamic_img, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.rander(i);
    }

    @Override
    public int getItemCount() {
        return imgSizeFull() ? getImgSize() : getImgSize() + 1;
    }

    private boolean imgSizeFull() {
        return getImgSize() == maxSize;
    }

    private int getImgSize() {
        return paths.size();
    }

    public void setDataChanged(List<String> paths) {
        this.paths = paths;
        this.notifyDataSetChanged();
    }

    public void addDataChanged(List<String> paths) {
        if (this.paths.size() >= maxSize) {
            this.paths.clear();
        }
        this.paths.addAll(paths);
        this.notifyDataSetChanged();
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setVideoFlag(int videoflag) {
        this.videoflag = videoflag;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVideFlag() {
        return videoflag;
    }

}
