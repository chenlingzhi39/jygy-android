package com.endeavour.jygy.common.base;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.endeavour.jygy.common.Tools;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getName();

    @Override
    protected void onCreate(Bundle arg0) {
        try {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    XGPushManager.registerPush(BaseActivity.this, Tools.getIMEI(BaseActivity.this));
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(BaseActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                }

            };
            new TedPermission(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("请开启权限")
                    .setPermissions(Manifest.permission.READ_PHONE_STATE)
                    .check();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        super.onCreate(arg0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
