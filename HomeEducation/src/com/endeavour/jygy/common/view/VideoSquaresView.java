package com.endeavour.jygy.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by wu on 15/12/23.
 */
public class VideoSquaresView extends FrameLayout {

    public interface OnImgClickedListener {
        void onItemClicked(String attach);
    }

    private OnImgClickedListener onImgClickedListener;

    public void setOnImgClickedListener(OnImgClickedListener onImgClickedListener) {
        this.onImgClickedListener = onImgClickedListener;
    }

    public VideoSquaresView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public VideoSquaresView(Context context) {
        super(context);
        initView();
    }

    public VideoSquaresView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View nineSquare = LayoutInflater.from(getContext()).inflate(R.layout.layout_video_item, null);
        this.addView(nineSquare);
    }

    public void rander(final List<String> imgUrls) {
        if (imgUrls == null || imgUrls.isEmpty()) {
            return;
        }
        int imgSize = imgUrls.size();

        String mp4Url = null;
        String imgUrl = null;
        if (containMP4(imgUrls)) {
            for (int i = 0; i < imgSize; i++) {
                String imgTemp = imgUrls.get(i);
                if (imgTemp.endsWith(".mp4")) {
                    mp4Url = imgTemp;
                } else {
                    imgUrl = imgTemp;
                }
            }
            SquareImageView siv = (SquareImageView) findViewById(R.id.sivVideo);
            showImg(siv, imgUrl);
            siv.setTag(mp4Url);
        }
    }

    private void showImg(final SquareImageView siv, final String url) {
        siv.setVisibility(View.VISIBLE);
        siv.setTag(url);
        ImageLoader.getInstance().displayImage(url, siv, MainApp.getDynamicOptions());
        siv.setOnClickListener(new OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if (onImgClickedListener != null) {
                                           if (siv.getTag() != null) {
                                               onImgClickedListener.onItemClicked(String.valueOf(siv.getTag()));
                                           }
                                       }
                                   }
                               }

        );
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
