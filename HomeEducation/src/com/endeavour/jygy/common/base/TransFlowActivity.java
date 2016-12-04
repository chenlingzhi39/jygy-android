package com.endeavour.jygy.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 16/7/22.
 */
public class TransFlowActivity extends BaseViewActivity {

    private List<String> transFlows = new ArrayList<>();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TransFlowActivity.this.finish();
        }
    };

    protected final void setTransFlow(String... transFlow) {
        if (transFlow == null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        for (String temp : transFlow) {
            transFlows.add(temp);
            intentFilter.addAction(temp);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
    }

    protected final void finishTransFlowActivities() {
        if (transFlows == null) {
            return;
        }
        for (String transFlow : transFlows) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(transFlow));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        }
        transFlows.clear();
        transFlows = null;
    }
}
