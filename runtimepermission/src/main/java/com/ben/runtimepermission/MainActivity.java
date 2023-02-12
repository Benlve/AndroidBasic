package com.ben.runtimepermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LXP_MainActivity";

    private Button mMakeCall;

    private Intent mIntent;

    private static final int CALL_PHONE_REQUEST_CODE = 1698;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntent = new Intent(Intent.ACTION_CALL);
        mIntent.setData(Uri.parse("tel:17768060435"));

        mMakeCall = findViewById(R.id.make_call);

        mMakeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(ContextCompat.checkSelfPermission(MainActivity.this,
//                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(MainActivity.this,new String[]
//                            {Manifest.permission.CALL_PHONE},REQUEST_CODE);
//                }else {
//                   call();
//                }
                boolean b = PermissionManager.applyPermission(MainActivity.this,
                        MainActivity.this, Manifest.permission.CALL_PHONE, CALL_PHONE_REQUEST_CODE);
                if (!b) {
                    call();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "requestCode = " + requestCode);
        switch (requestCode) {
            case CALL_PHONE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    showConfirmDialog();
                }
                break;
            default:
                break;
        }

    }

    private void showConfirmDialog() {
       new AlertDialog.Builder(MainActivity.this).setTitle("获得拨号权限才可拨号")
               .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                        skipToAppDetailIntent(MainActivity.this);
                   }
               })
               .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               })
               .show();
    }

    private void call() {
        try {
            startActivity(mIntent);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void skipToAppDetailIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}