package com.ben.receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private OtherBroadcastReceiver broadcastReceiver;

    private TextView content;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);

        intentFilter = new IntentFilter();
        intentFilter.addAction("com.ben.receiver.ANOTHER_BROADCAST");
        broadcastReceiver = new OtherBroadcastReceiver();
        broadcastReceiver.setReceiveListener(new OtherBroadcastReceiver.ReceiveListener() {
            @Override
            public void refresh(String info) {
                content.setText(info);
            }
        });
        registerReceiver(broadcastReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}