package com.endeavour.jygy.common.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.endeavour.jygy.R;
import com.endeavour.jygy.app.ImageLoadApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wizarpos.log.util.LogEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/5.
 */
public class HtmlView extends LinearLayout {

    private static final String LOG_TAG = HtmlView.class.getSimpleName();

    public HtmlView(Context context) {
        super(context);
    }

    public HtmlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private OnImageClickListener onImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public void render_str(String str) {
        setClickable(true);
        LogEx.d("att", str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        List<String> contents = prase(str);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setOrientation(LinearLayout.VERTICAL);
        for (final String tempStr : contents) {
            if (tempStr.startsWith("http://") || tempStr.startsWith("https://")) {
                ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.view_imageview, null);
                ImageLoader.getInstance().displayImage(tempStr, imageView, ImageLoadApp.getOptions());
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onImageClickListener != null) {
                            onImageClickListener.onImageClick(tempStr);
                        }
                    }
                });
                this.addView(imageView, params);
            } else {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_text, null);
                textView.setText(tempStr);
                this.addView(textView, params);
            }
        }
    }

    public void render(String strs) {
        setClickable(true);
        try {
            JSONArray str = JSONArray.parseArray(strs);
            if (str == null || str.size() <= 0) {
                return;
            }
            List<List<String>> contents = new ArrayList<List<String>>();
            for (int i = 0; i < str.size(); i++) {
                contents.add(prase(str.getString(i)));
            }
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            this.setOrientation(LinearLayout.VERTICAL);
            for (List<String> tempStrs : contents) {
                for (final String tempStr : tempStrs) {
                    if (tempStr.startsWith("http://") || tempStr.startsWith("https://")) {
                        final WebView ivWeb = (WebView) LayoutInflater.from(getContext()).inflate(R.layout.view_big_iv, null);
                        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                        int width = wm.getDefaultDisplay().getWidth();
                        if (width >= 1080) {
                            ivWeb.setInitialScale(210);
                        } else if (width > 920) {
                            ivWeb.setInitialScale(180);
                        } else if (width > 720) {
                            ivWeb.setInitialScale(160);
                        } else if (width > 650) {
                            ivWeb.setInitialScale(140);
                        } else if (width > 520) {
                            ivWeb.setInitialScale(120);
                        } else {
                            ivWeb.setInitialScale(100);
                        }
                        ivWeb.setWebViewClient(new MyWebViewClient());
                        ivWeb.loadUrl(tempStr);
                        this.addView(ivWeb, params);
                    } else {
                        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_text, null);
                        textView.setText(tempStr);
                        this.addView(textView, params);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void renderFood(String strs) {
        setClickable(true);
        try {
            JSONArray str = JSONArray.parseArray(strs);
            if (str == null || str.size() <= 0) {
                return;
            }
            List<List<String>> contents = new ArrayList<List<String>>();
            for (int i = 0; i < str.size(); i++) {
                contents.add(prase(str.getString(i)));
            }
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            this.setOrientation(LinearLayout.VERTICAL);
            for (List<String> tempStrs : contents) {
                for (final String tempStr : tempStrs) {
                    if (tempStr.startsWith("http://") || tempStr.startsWith("https://")) {
                        ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.view_imageview, null);
                        ImageLoader.getInstance().displayImage(tempStr, imageView, ImageLoadApp.dynamicOptions);
                        imageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (onImageClickListener != null) {
                                    onImageClickListener.onImageClick(tempStr);
                                }
                            }
                        });
                        this.addView(imageView, params);
                    } else {
                        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_text, null);
                        textView.setText(tempStr);
                        this.addView(textView, params);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
                String picpath = "";
                for (String tempStr3 : tempStr2) {
                    picpath += tempStr3;
                }
                contents.add(picpath);
            } else {
                contents.add(tempStr);
            }
        }
        return contents;
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.view_text, null);
        textView.setText(text);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(textView, params);
    }

    public interface OnImageClickListener {

        void onImageClick(String img);
    }

}
