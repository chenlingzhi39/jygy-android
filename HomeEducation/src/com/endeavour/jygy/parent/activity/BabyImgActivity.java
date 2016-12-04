package com.endeavour.jygy.parent.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import me.nereo.imagechoose.MultiImageSelectorActivity;

/**
 * Created by wu on 15/11/27.
 */
public class BabyImgActivity extends BaseViewActivity {
    private static final int REQUEST_IMAGE = 2001;
    private ImageView ivBabyIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("上传头像");
        showTitleBack();
        setTitleRight("确定");
        setMainView(R.layout.activity_img_baby);
        findViewById(R.id.btn_img).setOnClickListener(this);
        findViewById(R.id.btn_photo).setOnClickListener(this);
        ivBabyIcon = (ImageView) findViewById(R.id.ivBabyIcon);
        ImageLoader.getInstance().displayImage(AppConfigHelper.getConfig(AppConfigDef.headPortrait), ivBabyIcon, MainApp.getBabyImgOptions());
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if (v.getId() == R.id.btn_img) {
                    Intent intent = new Intent(BabyImgActivity.this, MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);// 是否显示拍摄图片
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_TEXT, false);// 显示文本
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);// 最大可选择图片数量
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI); // 选择模式
                    startActivityForResult(intent, REQUEST_IMAGE);
                } else if (v.getId() == R.id.btn_photo) {
                    Intent intent = new Intent(BabyImgActivity.this, MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DIRECT_CAMERA, true);
                    startActivityForResult(intent, REQUEST_IMAGE);
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(BabyImgActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
            }

        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("请开启权限")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    ArrayList<String> selectedPahts = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (selectedPahts != null && (!selectedPahts.isEmpty())) {
                        ivBabyIcon.setTag(selectedPahts.get(0));
                        for (int i = 0; i < selectedPahts.size(); i++) {
                            selectedPahts.set(i, "file://" + selectedPahts.get(i));
                        }

                        ImageLoader.getInstance().displayImage(selectedPahts.get(0), ivBabyIcon);
                    }
                }
                break;
        }
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        if (ivBabyIcon.getTag() != null) {
            String path = (String) ivBabyIcon.getTag();
            if (!TextUtils.isEmpty(path)) {
                Intent intent = new Intent();
                intent.putExtra("path", path);
                setResult(RESULT_OK, intent);
            }
        }
        this.finish();
    }
}
