package com.endeavour.jygy.common.view;


import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.endeavour.jygy.R;

import java.util.List;

/**
 * 点赞人数显示控件(名字不可点击)
 */
public class PraiseTextView extends TextView {

    private Context mContext;
    private StyleSpan boldSpan;

    public PraiseTextView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public PraiseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public PraiseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        setHighlightColor(getResources().getColor(R.color.dynamic_high));
        boldSpan = new StyleSpan(Typeface.BOLD);
    }

    /**
     * 设置点赞的名字
     *
     * @param names
     * @return
     */
    public void setPraiseName(List<String> names) {
        if (names == null || names.isEmpty()) {
            return;
        }
        setText("");
        StringBuilder sBuilder = new StringBuilder();
        String nameStr;
        String lengthStr;
        if (names.size() > 3) {
            for (int i = 0; i < 3; i++) {
                sBuilder.append(names.get(i));
                sBuilder.append("、");
            }
            lengthStr = "等" + names.size() + "人";
        } else {
            for (int i = 0; i < names.size(); i++) {
                sBuilder.append(names.get(i));
                sBuilder.append("、");
            }
            lengthStr = "";
        }
        nameStr = sBuilder.substring(0, sBuilder.length() - 1);
        nameStr += lengthStr;
        SpannableString mSpannableString = new SpannableString(nameStr + " 赞");
        int start = nameStr.indexOf(lengthStr);
        mSpannableString.setSpan(boldSpan
                , 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //加粗
        append(mSpannableString);
    }

}