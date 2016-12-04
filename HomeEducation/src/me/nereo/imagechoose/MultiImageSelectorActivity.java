package me.nereo.imagechoose;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import me.nereo.imagechoose.utils.FileUtils;
import me.nereo.imagechoose.utils.ImageCompress;

/**
 * 多图选择 Created by Nereo on 2015/4/7.
 */
public class MultiImageSelectorActivity extends BaseViewActivity implements MultiImageSelectorFragment.Callback {

    private static final String LOG_TAG = "MultiImageSelectorActivity";
    /**
     * 是否压缩图片
     */
    public static final String EXTRA_IMAGECOMPRESS = "image_compress";
    /**
     * 最大图片选择次数，int类型，默认9
     */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /**
     * 图片选择模式，默认多选
     */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /**
     * 是否显示相机，默认显示
     */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /**
     * 是否直接跳转到相机，boolean类型
     */
    public static final String EXTRA_DIRECT_CAMERA = "direct_camera";

    public static final String EXTRA_SHOW_TEXT = "show_text";
    /**
     * 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合
     */
    public static final String EXTRA_RESULT = "select_result";
    /**
     * 默认选择集
     */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    /**
     * 单选
     */
    public static final int MODE_SINGLE = 0;
    /**
     * 多选
     */
    public static final int MODE_MULTI = 1;

    private ArrayList<String> resultList = new ArrayList<>();
    private TextView mSubmitButton;
    private int mDefaultCount;
    private File mTmpFile;

    // 请求加载系统照相机
    private static final int REQUEST_CAMERA = 3023;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        boolean isShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        boolean isShowText = intent.getBooleanExtra(EXTRA_SHOW_TEXT, true);
        boolean isDirectCamera = intent.getBooleanExtra(EXTRA_DIRECT_CAMERA, false);
        if (isDirectCamera) {
            // 跳转到系统照相机
            final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        // 设置系统相机拍照后的输出路径
                        // 创建临时文件
                        mTmpFile = FileUtils.createTmpFile(MultiImageSelectorActivity.this);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                        startActivityForResult(cameraIntent, REQUEST_CAMERA);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(MultiImageSelectorActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                    }

                };
                new TedPermission(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("请开启权限")
                        .setPermissions(Manifest.permission.READ_PHONE_STATE)
                        .check();
            } else {
                Toast.makeText(this, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
            }
            return;
        }
        showTitleBack();
        setMainView(R.layout.choose_activity_default);
        setTitleRight("完成");

        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShowCamera);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_DIRECT_CAMERA, isDirectCamera);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_TEXT, isShowText);
        bundle.putStringArrayList(MultiImageSelectorFragment.EXTRA_DEFAULT_SELECTED_LIST, resultList);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();

        // 完成按钮
        mSubmitButton = (TextView) findViewById(R.id.tvToolbarRight);
        if (resultList == null || resultList.size() <= 0) {
            mSubmitButton.setText("完成");
            mSubmitButton.setEnabled(false);
        } else {
            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resultList != null && resultList.size() > 0) {
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if (!resultList.contains(path)) {
            resultList.add(path);
        }
        // 有图片之后，改变按钮状态
        if (resultList.size() > 0) {
            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
            if (!mSubmitButton.isEnabled()) {
                mSubmitButton.setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(String path) {
        if (resultList.contains(path)) {
            resultList.remove(path);
            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
        } else {
            mSubmitButton.setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
        }
        // 当为选择图片时候的状态
        if (resultList.size() == 0) {
            mSubmitButton.setText("完成");
            mSubmitButton.setEnabled(false);
        }
    }

    @Override
    public void onCameraShot(File imageFile) {
        if (imageFile != null) {
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 相机拍照完成后，返回图片路径
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    File newFile = FileUtils.createTmpFile(this);
                    ImageCompress.saveBitmapToFile(mTmpFile, newFile.getPath());
                    com.endeavour.jygy.common.file.FileUtils.deleteAllFiles(mTmpFile);
                    mTmpFile = newFile;
                    onCameraShot(mTmpFile);
                }
            } else {
                if (mTmpFile != null && mTmpFile.exists()) {
                    mTmpFile.delete();
                }
            }
        }
        this.finish();
    }
}
