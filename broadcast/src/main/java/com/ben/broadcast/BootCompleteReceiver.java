package com.ben.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 开机广播较慢，在进入主页后大概 1 分钟后才收到
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    private static final String TAG = "LXP_BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Boot complete!", Toast.LENGTH_LONG).show();
        Log.d(TAG, "current thread = " + Thread.currentThread());
    }
}