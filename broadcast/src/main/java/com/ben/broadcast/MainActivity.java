package com.ben.broadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 1.动态注册网络网络变化的广播
 * 2.静态注册开启启动的广播
 * <p>
 * 动态注册广播的优点：
 * 可以灵活的对广播控制注册和注销
 * 缺点：
 * 只有在程序启动的时候才可以接受广播
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    private IntentFilter myIntentFilter;
    private MyBroadcastReceiver myBroadcastReceiver;

    private Button mButton;
    private Button mSendTo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.send_broadcast);
        mSendTo = findViewById(R.id.send_broadcast_to_another);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent("com.ben.broadcast.MY_BROADCAST"));
            }
        });
        mSendTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBroadcast(new Intent("com.ben.receiver.ANOTHER_BROADCAST"));
            }
        });
        //创建IntentFilter和NetworkChangeReceiver对象
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        //注册广播
//        registerReceiver(networkChangeReceiver, intentFilter);

        //创建和注册
        myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.ben.broadcast.MY_BROADCAST");
        myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, myIntentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
//        unregisterReceiver(networkChangeReceiver);

        unregisterReceiver(myBroadcastReceiver);
    }

    /**
     * 监听网络变化的广播接受器
     */
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "network changed!", Toast.LENGTH_LONG).show();
            Log.d(TAG, "");
        }
    }

}