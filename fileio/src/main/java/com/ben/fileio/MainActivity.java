package com.ben.fileio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LXP_MainActivity";

    private static final int READ_FILE_REQUEST_CODE = 1698;

    private Button createDir;
    private Button createFile;
    private Button fileIsExist;
    SDCardIOManager sdCardIOManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionManager.applyPermission(MainActivity.this, MainActivity
                .this, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_FILE_REQUEST_CODE);
        PermissionManager.applyPermission(MainActivity.this, MainActivity
                .this, Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_FILE_REQUEST_CODE);

        createDir = findViewById(R.id.create_dir);
        createFile = findViewById(R.id.create_file);
        fileIsExist = findViewById(R.id.file_is_exist);
        createDir.setOnClickListener(this);
        createFile.setOnClickListener(this);
        fileIsExist.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_dir:
                sdCardIOManager = SDCardIOManager.getInstance();
                sdCardIOManager.createDirOnSDCard("David Tao");
                break;
            case R.id.create_file:
                break;
            case R.id.file_is_exist:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sdCardIOManager = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "requestCode = " + requestCode);
        switch (requestCode) {
            case READ_FILE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    showConfirmDialog();
                }
                break;
            default:
                break;
        }
    }

    private void showConfirmDialog() {
        new AlertDialog.Builder(MainActivity.this).setTitle("获得文件读写权限")
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
    private void skipToAppDetailIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}