package com.endeavour.jygy.parent.activity;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.file.FileUploadProxy;
import com.endeavour.jygy.common.ui.AnimatorUtils;
import com.endeavour.jygy.common.ui.ImageTextButton;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.parent.adapter.ChooseImgAdapter;
import com.endeavour.jygy.parent.adapter.ChooseImgAdapter.ChooseImageAdapterCallBack;
import com.endeavour.jygy.teacher.bean.AddRecipeReq;
import com.endeavour.jygy.video.Utils;
import com.endeavour.jygy.video.VideoMainActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ogaclejapan.arclayout.ArcLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.imagechoose.MultiImageSelectorActivity;


/**
 * Created by wu on 16/7/24.
 */
public class EditFoodActivity extends BaseViewActivity implements ChooseImageAdapterCallBack {
    private static final int REQUEST_IMAGE = 2001;
    Toast toast = null;
    private EditText etContent;
    private EditText etWeek;
    private EditText etData;
    private GridView gvImgs;
    private ChooseImgAdapter chooseImgAdapter;
    View fab;
    View menuLayout;
    ArcLayout arcLayout;
    ImageTextButton arc_btn3, arc_btn4;
    private Animation Anim_Alpha;
    // 文件路径
    private String path = "";
    private String videopath = "";
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private String attachs;
    private LinearLayout choose_imgs_lay;

    private OptionsPickerView pwWeeks, pwData;
    private ArrayList<String> optsWeeks = new ArrayList<>();

    {
        optsWeeks.add("第一周");
        optsWeeks.add("第二周");
        optsWeeks.add("第三周");
        optsWeeks.add("第四周");
    }

    // <img src="http://image.rulingtong.cn/photo/14537758179700.jpeg" alt="" />

    private static final String IMG_PER = "<img src=\"";
    private static final String IMG_AFTER = "\" alt=\"\" />";

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, EditFoodActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText("新增食谱");
        setTitleRight("保存");
        showTitleBack();
        setMainView(R.layout.activity_edit_schoolfood);

        chooseImgAdapter = new ChooseImgAdapter(this);
        gvImgs = (GridView) findViewById(R.id.gvImgs);
        choose_imgs_lay = (LinearLayout) findViewById(R.id.choose_imgs_lay);
        chooseImgAdapter.setChooseImageAdapterCallBack(this);
        gvImgs.setAdapter(chooseImgAdapter);
        etContent = (EditText) findViewById(R.id.etContent);
        etWeek = (EditText) findViewById(R.id.etWeek);
        etData = (EditText) findViewById(R.id.etData);
        initArcLayout();
        initWeeks();
        initData();
    }

    private void initData() {
        final TimePickerView timePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH);
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                String dateStr = format.format(date);
                etData.setText(dateStr);
            }
        });
        etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerView.show();
            }
        });
    }

    private void initWeeks() {
        pwWeeks = new OptionsPickerView(this);
        pwWeeks.setPicker(optsWeeks);
        pwWeeks.setLabels("");
        pwWeeks.setCyclic(false);
        pwWeeks.setSelectOptions(0);
        //监听确定选择按钮
        pwWeeks.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = optsWeeks.get(options1);
                etWeek.setText(tx);
            }
        });
        etWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwWeeks.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tools.hideKeyBorad(this, etContent);
    }

    private void initArcLayout() {
        ActionBar bar = getSupportActionBar();
        // bar.setTitle(demo.titleResId);
        bar.setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        menuLayout = findViewById(R.id.menu_layout);
        arcLayout = (ArcLayout) findViewById(R.id.arc_layout);
        arc_btn3 = (ImageTextButton) findViewById(R.id.arc_btn3);
        arc_btn3.setText("拍照");
        arc_btn3.setTextColor(Color.rgb(0, 0, 0));
        arc_btn3.setImgResource(R.drawable.news_add_camera);
        arc_btn4 = (ImageTextButton) findViewById(R.id.arc_btn4);
        arc_btn4.setText("从相册选择");
        arc_btn4.setTextColor(Color.rgb(0, 0, 0));
        arc_btn4.setImgResource(R.drawable.news_add_photo);
        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }
        arc_btn3.setOnClickListener(this);
        arc_btn4.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @SuppressWarnings("NewApi")
    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);

        List<Animator> animList = new ArrayList<>();

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator());
        animSet.playTogether(animList);
        animSet.start();
    }

    @SuppressWarnings("NewApi")
    private void hideMenu() {

        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new AnticipateInterpolator());
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
            }
        });
        animSet.start();

    }

    private Animator createShowItemAnimator(View item) {

        float dx = fab.getX() - item.getX();
        float dy = fab.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f));

        return anim;
    }

    private Animator createHideItemAnimator(final View item) {
        float dx = fab.getX() - item.getX();
        float dy = fab.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy));

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        if (hasAttachs() || !TextUtils.isEmpty(etData.getText()) || !TextUtils.isEmpty(etWeek.getText())) {
            ConnectivityManager connectMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info != null
                    && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("当前是移动网络");
                ad.setMessage("是否确定上传,是否继续?");
                ad.setPositiveButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        });
                ad.setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                sendFood();
                            }
                        });
                ad.show();
            } else
                sendFood();
        } else
            Tools.toastMsg(this, "请填写内容!");
    }

    /**
     * 发送动态
     */
    private void sendFood() {
        hideKeyboard();
        progresser.showProgress();
        if (hasAttachs()) {
            uploadAttachs(new BaseRequest.ResponseListener() {
                @Override
                public void onSuccess(Response response) {
                    attachs = String.valueOf(response.getResult());
                    videopath = "";
                    doSendFoodAciton(attachs);
                }

                @Override
                public void onFaild(Response response) {
                    progresser.showContent();
                    Tools.toastMsg(EditFoodActivity.this, response.getMsg());
                }
            });
        } else {// 没有附件,直接上送文字
            if (TextUtils.isEmpty(etContent.getText().toString())) {
                return;
            }
            doSendFoodAciton(null);
        }
    }

    private boolean hasAttachs() {
        return chooseImgAdapter.getPaths() != null
                && !chooseImgAdapter.getPaths().isEmpty();
    }

    /**
     * 上传附件
     */
    private void uploadAttachs(BaseRequest.ResponseListener listener) {
        FileUploadProxy proxy = new FileUploadProxy();
        List<String> pathlist = chooseImgAdapter.getPaths();
        if (!videopath.equals(""))
            pathlist.add("file:/" + videopath);
        proxy.uploadZipFile(pathlist, listener);
    }

    /**
     * 发布动态
     */
    private void doSendFoodAciton(String attachs) {
        String content = etContent.getText().toString();
        String week = etWeek.getText().toString();
        String data = etData.getText().toString();
        if (TextUtils.isEmpty(week) || TextUtils.isEmpty(data)) {
            Toast.makeText(EditFoodActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        progresser.showProgress();

        String weekInt = getWeekInt(week);

        AddRecipeReq req = new AddRecipeReq();
        req.setLeaderId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setWeek(weekInt);
        req.setYearMonth(data);
        String[] attachList = praseAttach(attachs);
        String imgs = "";
        for (String attach : attachList) {
            imgs += IMG_PER + attach + IMG_AFTER;
        }
        req.setContent(imgs);
        NetRequest.getInstance().addRequest(req, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                LocalBroadcastManager.getInstance(EditFoodActivity.this).sendBroadcast(new Intent(SchoolBossFoodHistoryActivity.REFRESH));
                EditFoodActivity.this.finish();
            }

            @Override
            public void onFaild(Response response) {
                progresser.showContent();
                if (response.getMsg().equals("could not execute statement"))
                    Tools.toastMsg(EditFoodActivity.this, "非法输入，请检查是否输入非法字符!");
                else
                    Tools.toastMsg(EditFoodActivity.this,
                            response.getMsg());
            }
        });
    }

    @NonNull
    private String[] praseAttach(String attachs) {
        attachs = attachs.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "").replaceAll("\\\\", "");
        return attachs.split(",");
    }

    @NonNull
    private String getWeekInt(String week) {
        String weekInt = "1";
        for (int i = 0; i < optsWeeks.size(); i++) {
            if (week.equals(optsWeeks.get(i))) {
                weekInt = (i + 1) + "";
            }
        }
        return weekInt;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Anim_Alpha = AnimationUtils.loadAnimation(EditFoodActivity.this,
                R.anim.alpha_action);
        v.startAnimation(Anim_Alpha);
        if (v.getId() == R.id.fab) {
            onFabClick(v);
            return;
        } else if (v.getId() == R.id.arc_btn1) { // 文字
            choose_imgs_lay.setVisibility(View.GONE);
            //chooseImgAdapter.setType(1);
        } else if (v.getId() == R.id.arc_btn2) { // 视频
            if (chooseImgAdapter.getVideFlag() == 3) {
                Toast.makeText(this, "拍照和视频只能有一种!", Toast.LENGTH_SHORT).show();
            } else if (chooseImgAdapter.getVideFlag() == 1) {
                Toast.makeText(this, "视频每次只能上传一份!", Toast.LENGTH_SHORT).show();
            } else {
                if (chooseImgAdapter.getCount() > 1) {
                    Toast.makeText(this, "拍照和视频只能有一种!", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    PermissionListener permissionlistener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            chooseImgAdapter.setType(2);
                            Intent intent = new Intent(EditFoodActivity.this, VideoMainActivity.class);
                            EditFoodActivity.this.startActivityForResult(intent, 200);
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            Toast.makeText(EditFoodActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                        }

                    };
                    new TedPermission(this)
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("请开启权限")
                            .setPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)
                            .check();
                }
            }
        } else if (v.getId() == R.id.arc_btn3) { // 拍照
            if (chooseImgAdapter.getVideFlag() == 1) {
                Toast.makeText(this, "拍照和视频只能有一种!", Toast.LENGTH_SHORT).show();
            } else {
                // etContent.setVisibility(View.GONE);
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        chooseImgAdapter.setType(3);
                        Intent intent = new Intent(EditFoodActivity.this, MultiImageSelectorActivity.class);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_DIRECT_CAMERA, true);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 6 - chooseImgAdapter.getPathSize());// 最大可选择图片数量
                        startActivityForResult(intent, REQUEST_IMAGE);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(EditFoodActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                    }

                };
                new TedPermission(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("请开启权限")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();

            }
        } else if (v.getId() == R.id.arc_btn4) { // 相册选择

            if (chooseImgAdapter.getVideFlag() == 1) {
                Toast.makeText(this, "拍照,视频只能有一种!", Toast.LENGTH_SHORT).show();
            } else {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        chooseImgAdapter.setType(4);
                        // etContent.setVisibility(View.GONE);
                        Intent intent = new Intent(EditFoodActivity.this, MultiImageSelectorActivity.class);
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);// 是否显示拍摄图片
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_TEXT, false);// 显示文本
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 6 - chooseImgAdapter.getPathSize());// 最大可选择图片数量
                        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI); // 选择模式
                        startActivityForResult(intent, REQUEST_IMAGE);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        Toast.makeText(EditFoodActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                    }

                };
                new TedPermission(this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("请开启权限")
                        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .check();
            }
        }
        fab.setVisibility(View.GONE);
        hideMenu();
    }

    /**
     * 收缩图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 600);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }

    private void onFabClick(View v) {
        v.setVisibility(View.GONE);
        hideMenu();
    }

    @Override
    public void showArcLayout() {
        fab.setVisibility(View.VISIBLE);
        Tools.hideKeyBorad(this, etContent);
        showMenu();
    }

    @Override
    public void takePic() {

    }

    @Override
    public void choosePic() {

    }

    @Override
    public void takeVideo() {
        videopath = "";
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws java.io.IOException
     */
    public String saveFile(Bitmap bm, String fileName, String sdpath)
            throws IOException {
        // 用户自己只能发一个视频，所以视频第一帧截图的文件名可以固定
        File myCaptureFile = new File(sdpath + "/" + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return sdpath + "/" + fileName;
    }

    /**
     * 保存文件
     *
     * @param bm
     * @throws java.io.IOException
     */
    public String saveFile(Bitmap bm) throws IOException {
        // 用户自己只能发一个视频，所以视频第一帧截图的文件名可以固定
        String path = "/storage/sdcard0/jygy/video/" + getStringToday()
                + ".jpeg";
        File myCaptureFile = new File(path);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return path;
    }

    /**
     * 保存文件
     *
     * @param bm
     * @throws java.io.IOException
     */
    public String saveFile(Bitmap bm, String fpath) throws IOException {
        // 用户自己只能发一个视频，所以视频第一帧截图的文件名可以固定
        String[] patharray = fpath.split("\\/");
        String filepath = patharray[patharray.length - 1];
        String path = "/storage/sdcard0/jygy/video/" + filepath.split("\\.")[0]
                + ".jpeg";
        File myCaptureFile = new File(path);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 200:
                if (resultCode == RESULT_OK) {
                    // 成功
                    path = data.getStringExtra("path"); // 先得到小视频路径
                    videopath = path;
                    // 通过路径获取第一帧的缩略图并显示
                    Bitmap bitmap = Utils.createVideoThumbnail(path);
                    BitmapDrawable drawable = new BitmapDrawable(bitmap);
                    drawable.setTileModeXY(Shader.TileMode.REPEAT,
                            Shader.TileMode.REPEAT);
                    drawable.setDither(true);
                    if (bitmap != null) {
//					Toast.makeText(EditFoodActivity.this, "存储路径为:" + path,
//							Toast.LENGTH_SHORT).show();
                        Tools.toastMsg(EditFoodActivity.this, "视频录制成功!");
                        try {
                            path = saveFile(bitmap, videopath); // 复用path变量，赋值第一帧图片
                            chooseImgAdapter.setVideoFlag(1);// 视频只能拍一次
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ArrayList<String> videopath = new ArrayList<String>();
                        videopath.add("file:/" + path);
                        chooseImgAdapter.addDataChanged(videopath);
                    } else {
                        videopath = "";
                        Tools.toastMsg(EditFoodActivity.this, "视频录制失败，请重试或检查权限是否开启!");
                    }
                } else {
                    // 失败
                    videopath = "";
                    Tools.toastMsg(EditFoodActivity.this, "视频录制失败，请重试!");
                }
                break;
            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    chooseImgAdapter.setVideoFlag(3);
                    ArrayList<String> selectedPahts = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (selectedPahts != null && (selectedPahts.isEmpty() == false)) {
                        for (int i = 0; i < selectedPahts.size(); i++) {
                            selectedPahts.set(i, "file:/" + selectedPahts.get(i));
                        }
                        chooseImgAdapter.addDataChanged(selectedPahts);
                    }
                }
                break;
            case PHOTO_ZOOM:
                startPhotoZoom(data.getData());
                break;
        }
    }

    /*
     * 为照片命名
     */
    public String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}

