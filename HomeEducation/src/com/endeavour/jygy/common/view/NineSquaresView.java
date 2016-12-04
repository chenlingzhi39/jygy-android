package com.endeavour.jygy.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/23.
 */
public class NineSquaresView extends FrameLayout {

    public interface OnImgClickedListener {
        void onItemClicked(String attach);
    }

    private OnImgClickedListener onImgClickedListener;

    public void setOnImgClickedListener(OnImgClickedListener onImgClickedListener) {
        this.onImgClickedListener = onImgClickedListener;
    }

    public NineSquaresView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public NineSquaresView(Context context) {
        super(context);
        initView();
    }

    public NineSquaresView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View nineSquare = LayoutInflater.from(getContext()).inflate(R.layout.layout_nine_square, null);
        this.addView(nineSquare);
    }

    public void rander(final List<String> imgUrls) {
        if (imgUrls == null || imgUrls.isEmpty()) {
            return;
        }

        int imgSize = imgUrls.size();
        List<SquareImageView> sivs = new ArrayList<>(9);
        for (int i = 1; i <= 9; i++) {
            SquareImageView siv = (SquareImageView) findViewById(getId("siv" + i));
//            siv.setImageResource(0);
            siv.setVisibility(View.GONE);
            siv.setTag(null);
            siv.setOnClickListener(null);
            sivs.add(siv);
        }
        if (imgSize == 1) {
            showImg(sivs.get(0), imgUrls.get(0));
        } else if (imgSize == 2) {
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
                SquareImageView siv = sivs.get(0);
                showImg(siv, imgUrl);
                siv.setTag(mp4Url);
            } else {
                for (int i = 0; i < imgSize; i++) {
                    showImg(sivs.get(i), imgUrls.get(i));
                }
            }
        } else if (imgSize == 3 || imgSize == 5 || imgSize == 6) {
            for (int i = 0; i < imgSize; i++) {
                showImg(sivs.get(i), imgUrls.get(i));
            }
        } else if (imgSize == 4) {
            for (int i = 0; i < 2; i++) {
                showImg(sivs.get(i), imgUrls.get(i));
            }
            for (int i = 3; i < 5; i++) {
                showImg(sivs.get(i), imgUrls.get(i - 1));
            }
        } else if (imgSize >= 7) {
            int imgStep = imgSize > 9 ? 9 : imgSize;
            for (int i = 0; i < imgStep; i++) {
                showImg(sivs.get(i), imgUrls.get(i));
            }
            for (int i = imgSize; i < 9; i++) {
                sivs.get(i).setVisibility(View.INVISIBLE);
            }
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

    private int getId(String name) {
        try {
            Field field = R.id.class.getField(name);
            return field.getInt(new R.drawable());
        } catch (Exception e) {
            return 0;
        }
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
