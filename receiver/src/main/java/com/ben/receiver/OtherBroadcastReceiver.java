package com.ben.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class OtherBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "OtherBroadcastReceiver";

    private ReceiveListener receiveListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received in other Process.");
        if (receiveListener != null) {
            receiveListener.refresh("Received in other Process.");
        }
    }

    //接口
    interface ReceiveListener {
        void refresh(String info);
    }

    public void setReceiveListener(ReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }

    public ReceiveListener getReceiveListener() {
        return receiveListener;
    }
}