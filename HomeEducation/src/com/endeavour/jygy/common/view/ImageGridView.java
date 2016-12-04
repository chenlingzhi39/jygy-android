package com.endeavour.jygy.common.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.DipHelper;
import com.endeavour.jygy.parent.activity.PhotoShowActivity;
import com.endeavour.jygy.parent.activity.VideoShowActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ImageGridView extends FrameLayout {
    private int maxWidth;
    private Context context;
    private List<ImageView> imageViews = new ArrayList<>();
    private ImageView imageViewOne;

    private int imgSize = -1;

    public ImageGridView(Context context, int size, int maxWidth) {
        super(context);
        this.imgSize = size;
        this.context = context;
        this.maxWidth = maxWidth;
        initView();
    }

    public ImageGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        float scale = DipHelper.px2dip(context, width) / (float) DipHelper.px2dip(context, 1080);
        this.maxWidth = DipHelper.dip2px(context, scale * 240);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dynamic_imgs, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(view, params);
        imageViewOne = (ImageView) view.findViewById(R.id.image_one);
        if (imgSize <= 1) {
            imageViewOne.setVisibility(View.VISIBLE);
            view.findViewById(R.id.llRow1).setVisibility(View.GONE);
            view.findViewById(R.id.llRow2).setVisibility(View.GONE);
            view.findViewById(R.id.llRow3).setVisibility(View.GONE);
        } else if (imgSize <= 3) {
            imageViewOne.setVisibility(View.GONE);
            view.findViewById(R.id.llRow1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.llRow2).setVisibility(View.GONE);
            view.findViewById(R.id.llRow3).setVisibility(View.GONE);
        } else if (imgSize <= 6) {
            imageViewOne.setVisibility(View.GONE);
            view.findViewById(R.id.llRow1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.llRow2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.llRow3).setVisibility(View.GONE);
        } else {
            imageViewOne.setVisibility(View.GONE);
            view.findViewById(R.id.llRow1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.llRow2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.llRow3).setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < 9; i++) {
            int id = getId("image_" + i);
            if (id != 0) {
                ImageView imageView = (ImageView) view.findViewById(id);
                imageView.setMinimumHeight((maxWidth - 6) / 3);
                imageView.setMinimumWidth((maxWidth - 6) / 3);
                imageViews.add(imageView);
                if (imgSize == 1) {
                    imageView.setVisibility(View.GONE);
                } else if (i <= imgSize) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
            }
        }

    }

    public void updateWithImage(List<String> images) {
        if (imgSize != images.size()) {
            return;
        }
        if (images.size() == 1) {
            ImageLoader.getInstance().displayImage(images.get(0), imageViewOne, MainApp.getOptions());
        }

        for (int i = 0; i < imgSize; i++) {
            ImageView imageView = imageViews.get(i);
            if (images.size() == 4) {
                if (i == 0 || i == 1) {
                    ImageLoader.getInstance().displayImage(images.get(i), imageView, MainApp.getDynamicOptions());
                } else if (i == 3 || i == 4) {
                    ImageLoader.getInstance().displayImage(images.get(i - 1), imageView, MainApp.getDynamicOptions());
                }
            } else {
                if (i < images.size()) {
                    ImageLoader.getInstance().displayImage(images.get(i), imageView, MainApp.getDynamicOptions());

                }
            }

        }
    }

    private void showVideoOrImg(final String imgpath, final ImageView imageView) {
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!imgpath.contains(".mp4")) {
                    Intent intent = new Intent(context, PhotoShowActivity.class);
                    intent.putExtra("imgpath", imgpath);
                    context.startActivity(intent);
                } else if (imgpath.contains(".mp4")) {
                    Intent intent = new Intent(context, VideoShowActivity.class);
                    intent.putExtra("videopath", imgpath);
                    context.startActivity(intent);
                }
            }
        });
    }

    private int getId(String name) {
        try {
            Field field = R.id.class.getField(name);
            return field.getInt(new R.drawable());
        } catch (Exception e) {
            return 0;
        }
    }
}
