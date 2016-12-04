package com.endeavour.jygy.parent.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.view.TouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/30.
 */
public class TouchImageAdapter extends PagerAdapter {

    public interface OnSigleTabListener {
        void onSingleTapConfirmed();

        void onLongClick(String img);
    }

    private OnSigleTabListener onSigleTabListener;


    public void setOnSigleTabListener(OnSigleTabListener onSigleTabListener) {
        this.onSigleTabListener = onSigleTabListener;
    }

    private List<String> imgPaths = new ArrayList<>();

    public TouchImageAdapter(List<String> imgPaths) {
        if (imgPaths != null) {
            this.imgPaths = imgPaths;
        }
    }

    @Override
    public int getCount() {
        return imgPaths.size();
    }


    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        TouchImageView img = new TouchImageView(container.getContext());
        ImageLoader.getInstance().displayImage(imgPaths.get(position), img, MainApp.getOptions());
        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onSigleTabListener != null) {
                    onSigleTabListener.onLongClick(imgPaths.get(position));
                }
                return true;
            }
        });
        img.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (onSigleTabListener != null) {
                    onSigleTabListener.onSingleTapConfirmed();
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return false;
            }
        });
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
