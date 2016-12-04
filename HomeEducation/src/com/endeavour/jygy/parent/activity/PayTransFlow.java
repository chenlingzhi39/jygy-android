package com.endeavour.jygy.parent.activity;

import android.os.Bundle;

import com.endeavour.jygy.common.base.TransFlowActivity;

/**
 * Created by wu on 16/7/22.
 */
public class PayTransFlow extends TransFlowActivity {

    public static final String TRNAFLOW_PAY = "com.endeavour.jygy.parent.activity.TRNAFLOW_CHANGE_CLASS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTransFlow(TRNAFLOW_PAY);
    }
}
