package com.endeavour.jygy.common.view;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;

import com.endeavour.jygy.R;

/**
 * Created by Abner on 15/6/17.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class TopicSpan extends ClickableSpan {

    private String topic;
    private StyleSpan boldSpan;
    private Resources resources;
    private TextTopicClickListener topicClickListener;


    public TopicSpan(String topic,Resources resources,TextTopicClickListener listener) {
        this.topic = topic;
        this.resources = resources;
        topicClickListener = listener;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setTypeface(Typeface.DEFAULT_BOLD);
        ds.setUnderlineText(false);
        ds.setColor(resources.getColor(R.color.blue_666e8a));
    }

    @Override
    public void onClick(View widget) {
        Log.d("click",topic);
        if(topicClickListener != null){
            topicClickListener.onTopicClick(widget,topic);
        }
    }
}
