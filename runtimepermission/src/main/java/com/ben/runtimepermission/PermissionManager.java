package com.ben.runtimepermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {

    public static boolean applyPermission(Context context, Activity activity,String permission, int REQUEST_CODE) {
        if(ContextCompat.checkSelfPermission(context,
                permission) != PackageManager.PERMISSION_GRANTED) {
            //没有申请对应权限，申请对应权限
            ActivityCompat.requestPermissions(activity,new String[]
                    {Manifest.permission.CALL_PHONE},REQUEST_CODE);
            return true;
        }else {
            return false;
        }
    }

}
