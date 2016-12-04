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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.Toast;

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
import com.endeavour.jygy.parent.adapter.DynamicImageRcvAdapter;
import com.endeavour.jygy.parent.adapter.TaskEditImgAdapter;
import com.endeavour.jygy.parent.adapter.TaskEditImgAdapter.ChooseImageAdapterCallBack;
import com.endeavour.jygy.parent.bean.GetStudenTasksResp;
import com.endeavour.jygy.parent.bean.ReplyStudentTasksReq;
import com.endeavour.jygy.parent.fragment.ParentTaskFragment;
import com.endeavour.jygy.video.Utils;
import com.endeavour.jygy.video.VideoMainActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ogaclejapan.arclayout.ArcLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.imagechoose.MultiImageSelectorActivity;

public class EditTaskActivity extends BaseViewActivity implements
        ChooseImageAdapterCallBack {
    private static final int REQUEST_IMAGE = 2001;
    Toast toast = null;
    private EditText etContent;
    private GridView gvImgs;
    @SuppressWarnings("unused")
    private DynamicImageRcvAdapter adapter;
    private TaskEditImgAdapter chooseImgAdapter;
    View fab;
    View menuLayout;
    ArcLayout arcLayout;
    ImageTextButton arc_btn1, arc_btn2, arc_btn3, arc_btn4;
    private Animation Anim_Alpha;
    // 文件路径
    private String path = "";
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private String picpath = Environment.getExternalStorageDirectory()
            + "/jygy/pic/";
    String sdpath = Environment.getExternalStorageDirectory().getPath();
    private String picname = ""; // 每次拍照的图片名称
    private String mp4path = ""; //视频
    private String photopath = ""; //图片
    private String soundpath = ""; //声音
    private GetStudenTasksResp task;
    private String attachs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText(R.string.newTask);
        showTitleBack();
        task = (GetStudenTasksResp) getIntent().getSerializableExtra(
                "GetStudenTasksResp");
        if (task == null) {
            progresser.showError("暂无数据", false);
            return;
        }
        setTitleRight(R.string.send);
        setMainView(R.layout.activity_edit_task);
        chooseImgAdapter = new TaskEditImgAdapter(this);
        gvImgs = (GridView) findViewById(R.id.gvImgs);
        chooseImgAdapter.setChooseImageAdapterCallBack(this);
        gvImgs.setAdapter(chooseImgAdapter);
        etContent = (EditText) findViewById(R.id.etContent);
        initArcLayout();
        initfilepath(picpath);
    }

    private void initfilepath(String sdpath) {
        File dirFile = new File(sdpath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
    }

    private void initArcLayout() {
        ActionBar bar = getSupportActionBar();
        // bar.setTitle(demo.titleResId);
        bar.setDisplayHomeAsUpEnabled(true);
        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        menuLayout = findViewById(R.id.menu_layout);
        arcLayout = (ArcLayout) findViewById(R.id.arc_layout);
        arc_btn1 = (ImageTextButton) findViewById(R.id.arc_btn1);
        arc_btn1.setText("文字");
        arc_btn1.setTextColor(Color.rgb(0, 0, 0));
        arc_btn1.setImgResource(R.drawable.menu_dongtai);
        arc_btn2 = (ImageTextButton) findViewById(R.id.arc_btn2);
        arc_btn2.setText("小视频");
        arc_btn2.setTextColor(Color.rgb(0, 0, 0));
        arc_btn2.setImgResource(R.drawable.news_add_video);
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
        arc_btn1.setOnClickListener(this);
        arc_btn2.setOnClickListener(this);
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
    public void onClick(View v) {
        super.onClick(v);
        Anim_Alpha = AnimationUtils.loadAnimation(EditTaskActivity.this,
                R.anim.alpha_action);
        v.startAnimation(Anim_Alpha);
        if (v.getId() == R.id.fab) {
            onFabClick(v);
            return;
        } else if (v.getId() == R.id.arc_btn1) {
            etContent.requestFocus();
        } else if (v.getId() == R.id.arc_btn2) { // 视频
            if (!mp4path.equals("")) {
                Tools.toastMsg(this, "不能重复拍小视频");
                return;
            }
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Intent intent = new Intent(EditTaskActivity.this, VideoMainActivity.class);
                    EditTaskActivity.this.startActivityForResult(intent, 200);
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(EditTaskActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                }

            };
            new TedPermission(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("请开启权限")
                    .setPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .check();
        } else if (v.getId() == R.id.arc_btn3) { // 拍照
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    picname = getStringToday() + ".jpg";
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(picpath, picname)));
                    startActivityForResult(intent, PHOTO_GRAPH);
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(EditTaskActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                }

            };
            new TedPermission(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("请开启权限")
                    .setPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .check();
        } else if (v.getId() == R.id.arc_btn4) { // 相册选择
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Intent intent = new Intent(EditTaskActivity.this, MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);// 是否显示拍摄图片
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_TEXT, false);// 显示文本
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 6 - chooseImgAdapter.getPathSize());// 最大可选择图片数量
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI); // 选择模式
                    startActivityForResult(intent, REQUEST_IMAGE);
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(EditTaskActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
                }

            };
            new TedPermission(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("请开启权限")
                    .setPermissions(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .check();
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
        // if (v.isSelected()) {
        v.setVisibility(View.GONE);
        hideMenu();
        // } else {
        // showMenu();
        // }
        // v.setSelected(!v.isSelected());
    }

    @Override
    public void showArcLayout() {
        Tools.hideKeyBorad(this, etContent); // 隐藏键盘
        fab.setVisibility(View.VISIBLE);
        showMenu();
    }

    @Override
    public void takePic() {
        soundpath = "";
    }

    @Override
    public void choosePic() {

    }

    @Override
    public void takeVideo() {
        mp4path = "";
    }

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
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
     * @throws IOException
     */
    public String saveFile(Bitmap bm, String fpath) throws IOException {
        String[] patharray = fpath.split("\\/");
        String filepath = patharray[patharray.length - 1];
        String path = sdpath + "/jygy/video/" + filepath.split("\\.")[0]
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
            case 300:
                if (data != null) {
                    String sname = data.getStringExtra("soundname");
                    String spath = data.getStringExtra("soundpath");
                    soundpath = spath;
                    String[] arr = new String[2];
                    arr[0] = "1";
                    arr[1] = "file:/" + soundpath;
                    ArrayList<String[]> pathlist = new ArrayList<String[]>();
                    pathlist.add(arr);
                    chooseImgAdapter.addDataChanged(pathlist);
                    chooseImgAdapter.setVideoFlag(2);// 录音只能拍一次
                }
                break;
            case 200:
                if (resultCode == RESULT_OK) {
                    // 成功
                    path = data.getStringExtra("path");
                    mp4path = path;
                    // 通过路径获取第一帧的缩略图并显示
                    Bitmap bitmap = Utils.createVideoThumbnail(path);
                    BitmapDrawable drawable = new BitmapDrawable(bitmap);
                    drawable.setTileModeXY(Shader.TileMode.REPEAT,
                            Shader.TileMode.REPEAT);
                    drawable.setDither(true);
                    if (bitmap != null) {
                        Tools.toastMsg(EditTaskActivity.this, "视频录制成功!");
                        try {
                            path = saveFile(bitmap, mp4path);
                            chooseImgAdapter.setVideoFlag(1);// 视频只能拍一次
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String[] arr = new String[2];
                        arr[0] = "2";
                        arr[1] = "file:/" + path;
                        ArrayList<String[]> videopath = new ArrayList<String[]>();
                        videopath.add(arr);
                        chooseImgAdapter.addDataChanged(videopath);
                    } else {
                        mp4path = "";
                        Tools.toastMsg(EditTaskActivity.this, "视频录制失败，请重新录制!");
                    }
                } else {
                    // 失败
                }
                break;
            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                    chooseImgAdapter.setVideoFlag(3);
                    ArrayList<String> selectedPahts = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (selectedPahts != null && (selectedPahts.isEmpty() == false)) {
                        ArrayList<String[]> selectpath = new ArrayList<String[]>();
                        for (int i = 0; i < selectedPahts.size(); i++) {
                            String[] arr = new String[2];
                            arr[0] = "4";
                            arr[1] = "file:/" + selectedPahts.get(i);
                            //selectedPahts.set(i, "file:/" + selectedPahts.get(i));
                            selectpath.add(arr);
                        }
                        chooseImgAdapter.addDataChanged(selectpath);
                    }
                }

                // else {
                //
                // if (ImageChooseApp.getInstance().getSingleChooseFile() != null
                // && ImageChooseApp.getInstance().getSingleChooseFile()
                // .getTotalSpace() > 0) {
                // Bitmap loacalBitmap = getLoacalBitmap(ImageChooseApp
                // .getInstance().getSingleChooseFile());
                // ImageChooseApp.getInstance().setSingleChooseFile(null);
                //
                // } else {
                // Toast.makeText(this, "裁切完成 空文件", Toast.LENGTH_SHORT).show();
                // }
                // }
                break;
            case PHOTO_GRAPH:
                if (resultCode == RESULT_OK) {
                    // 设置文件保存路径
                    chooseImgAdapter.setVideoFlag(3);
                    File picture = new File(picpath + picname);
                    String[] arr = new String[2];
                    arr[0] = "3";
                    arr[1] = "file:/" + picture;
                    ArrayList<String[]> videopath = new ArrayList<String[]>();
                    videopath.add(arr);
                    chooseImgAdapter.addDataChanged(videopath);
                }
                // startPhotoZoom(Uri.fromFile(picture));
                break;
            case PHOTO_ZOOM:
                startPhotoZoom(data.getData());
                break;
            case PHOTO_RESOULT:
                // Bundle extras = data.getExtras();
                // if (extras != null) {
                // Bitmap photo = extras.getParcelable("data");
                // ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // photo.compress(Bitmap.CompressFormat.JPEG, 80, stream);//
                // (0-100)压缩文件
                // }
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

    public static Bitmap getLoacalBitmap(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            return;
        }
        progresser.showProgress();
        if (hasAttachs()) {
            ConnectivityManager connectMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("当前是移动网络");
                ad.setMessage("是否确定上传,是否继续?");
                ad.setPositiveButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                progresser.showContent();
                            }
                        });
                ad.setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                uploadAttachs(new BaseRequest.ResponseListener() {
                                    @Override
                                    public void onSuccess(Response response) {
                                        attachs = String.valueOf(response
                                                .getResult());
                                        sendFeedbacks();
                                    }

                                    @Override
                                    public void onFaild(Response response) {
                                        progresser.showContent();
                                        Tools.toastMsg(EditTaskActivity.this,
                                                response.getMsg());
                                    }
                                });
                            }
                        });
                ad.show();
            } else {
                uploadAttachs(new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        attachs = String.valueOf(response.getResult());
                        sendFeedbacks();
                    }

                    @Override
                    public void onFaild(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(EditTaskActivity.this, response.getMsg());
                    }
                });
            }

        } else {// 没有附件,直接上送文字
            sendFeedbacks();
        }

    }

    private void sendFeedbacks() {
        String content = etContent.getText().toString();
        ReplyStudentTasksReq req = new ReplyStudentTasksReq();
        req.setClassId(AppConfigHelper.getConfig(AppConfigDef.classID));
        req.setKinderId(AppConfigHelper.getConfig(AppConfigDef.schoolID));
        req.setStudentId(AppConfigHelper.getConfig(AppConfigDef.studentID));
        req.setParentId(AppConfigHelper.getConfig(AppConfigDef.parentId));
        req.setContent(content);
        req.setTaskId(task.getTaskId());
        req.setUserTaskId(task.getUserTasksId());
        req.setAttachement(attachs);
        NetRequest.getInstance().addRequest(req,
                new BaseRequest.ResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(EditTaskActivity.this, "任务开始成功");
                        setResult(RESULT_OK);
                        LocalBroadcastManager
                                .getInstance(EditTaskActivity.this)
                                .sendBroadcast(
                                        new Intent(ParentTaskFragment.TaskBroadCastReceiver.TASK_BROAD_CAST_RECEIVER_FILTER));
                        EditTaskActivity.this.finish();
                    }

                    @Override
                    public void onFaild(Response response) {
                        progresser.showContent();
                        Tools.toastMsg(EditTaskActivity.this, "暂时不支持个别特殊表情");
                    }
                });
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
        List<String[]> path_list = chooseImgAdapter.getPaths();
        List<String> pathlist = new ArrayList<String>();
        for (int i = 0; i < path_list.size(); i++)
            pathlist.add(path_list.get(i)[1]);
        if (!mp4path.equals(""))
            pathlist.add("file:/" + mp4path);
//		if (!soundpath.equals(""))
//			pathlist.add("file:/" + soundpath);
        for (int i = 0; i < pathlist.size(); i++) { // 录音小图标是本地了，adpter中塞了个空路径，所以去掉空路径再上传
            if (pathlist.get(i).length() < 8) {
                pathlist.remove(i);
                break;
            }
        }
        //Log.d("PathList"," " + pathlist.toString());
        proxy.uploadZipFile(pathlist, listener);
    }
}
