package com.endeavour.jygy.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.ImageLoadApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wizarpos.log.util.LogEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/5.
 */
public class HtmlViewBak extends LinearLayout {

    public HtmlViewBak(Context context) {
        super(context);
    }

    public HtmlViewBak(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlViewBak(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void render(String str) {
        LogEx.d("att", str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        List<String> contents = prase(str);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setOrientation(LinearLayout.VERTICAL);
        for (String tempStr : contents) {
            if (tempStr.startsWith("http://") || tempStr.startsWith("https://")) {
                ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.view_imageview, null);
                ImageLoader.getInstance().displayImage(tempStr, imageView, ImageLoadApp.getOptions());
                this.addView(imageView, params);
            } else {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_text, null);
                textView.setText(tempStr);
                this.addView(textView, params);
            }
        }
    }

    @NonNull
    private List<String> prase(String str) {
        str = str.replaceAll("http://", " imgurl:http://").replaceAll("https://", " imgurl:https://").replaceAll(".jpg", ".jpg endimgurl").replaceAll(".JPG", ".JPG endimgurl").replaceAll(".png", ".png endimgurl").replaceAll(".PNG", ".PNG endimgurl");
        String[] results = str.split(" imgurl:");
        List<String> contents = new ArrayList<String>();
        for (String tempStr : results) {
            if (tempStr.startsWith("https://") || tempStr.startsWith("http://")) {
                String[] tempStr2 = tempStr.split(" endimgurl");
                for (String tempStr3 : tempStr2) {
                    contents.add(tempStr3);
                }
            } else {
                contents.add(tempStr);
            }
        }
        return contents;
    }

}
