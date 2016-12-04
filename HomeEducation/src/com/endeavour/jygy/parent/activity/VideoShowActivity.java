package com.endeavour.jygy.parent.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VideoShowActivity extends BaseViewActivity {
    private String videopath = null;
    private String islocal = "";
    private VideoView mVideoView;
    private Button btnClose;
    private String picpath = Environment.getExternalStorageDirectory()
            + "/jygycache/";

    boolean needSave = false;
    boolean isDownladed = false;
    String fileName;
    String pathName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleRight("保存");
        setMainView(R.layout.activity_videoshow);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        btnClose = (Button) findViewById(R.id.btn_close);
        videopath = getIntent().getExtras().getString("videopath");
        islocal = getIntent().getExtras().getString("islocal");
        if (islocal != null) {
            playvideo(videopath.replace("file:/", ""));
            return;
        }
        String[] filearr = videopath.split("\\/");
        fileName = filearr[filearr.length - 1];
        pathName = picpath + fileName;// 文件存储路径
        File file = new File(pathName);
        if (file.exists()) { // 如果本地存在 就播放本地视频
            isDownladed = true;
            playvideo(pathName);
        } else { // 在费流量的情况下，用户同意播放，就开启下载,下载完成后再播放
            ConnectivityManager connectMgr = (ConnectivityManager) this
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectMgr.getActiveNetworkInfo();
            if (info != null
                    && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle("当前是移动网络");
                ad.setMessage("是否确定观看视频,是否继续?");
                ad.setPositiveButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                VideoShowActivity.this.finish();
                            }
                        });
                ad.setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Tools.toastMsg(VideoShowActivity.this, "缓冲完成后自动播放，稍等~");
                                //先下载，再播放，只有第一次看视频才会走这里哦
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        download(videopath);
                                    }
                                }).start();
                            }
                        });
                ad.show();
            } else {
                Tools.toastMsg(VideoShowActivity.this, "缓冲完成后自动播放，稍等~");
                //先下载，再播放，只有第一次看视频才会走这里哦
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download(videopath);
                    }
                }).start();
            }
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoShowActivity.this.finish();
            }
        });
        mVideoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                needSave = true;
                if (isDownladed) {
                    String toPath = Constants.SD_VIDEO_PATH + fileName;
                    Tools.copy(pathName, toPath);
                    Toast.makeText(VideoShowActivity.this, "视频保存在 " + toPath, Toast.LENGTH_SHORT);
                }
                return true;
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                VideoShowActivity.this.finish();
            }
        });
    }

    @Override
    protected void onTitleRightClicked() {
        super.onTitleRightClicked();
        needSave = true;
        if (isDownladed) {
            String toPath = Constants.SD_VIDEO_PATH + fileName;
            Tools.copy(pathName, toPath);
            Toast.makeText(VideoShowActivity.this, "视频保存在 " + toPath, Toast.LENGTH_SHORT).show();
        }
    }

    private void playvideo(String videopath) {
        try {
            Uri uri = Uri.parse(videopath);
            // 播放相应的视频
            mVideoView.setMediaController(new MediaController(
                    VideoShowActivity.this));
            mVideoView.setVideoURI(uri);
            mVideoView.start();
            mVideoView.requestFocus();
        } catch (Exception ex) {

        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    playvideo(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };

    private void download(String fpath) {
        String[] filearr = fpath.split("\\/");
        String fileName = filearr[filearr.length - 1];
        OutputStream output = null;
        try {
            /*
             * 通过URL取得HttpURLConnection 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
			 * <uses-permission android:name="android.permission.INTERNET" />
			 */
            URL url = new URL(fpath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 取得inputStream，并将流中的信息写入SDCard
            String pathName = picpath + fileName;// 文件存储路径
            File file = new File(pathName);
            InputStream input = conn.getInputStream();
            if (file.exists()) {
                return;
            } else {
                String dir = picpath;
                new File(dir).mkdir();// 新建文件夹
                file.createNewFile();// 新建文件
                output = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }
                output.flush();
                Thread.sleep(100L);
                isDownladed = true;
                if (needSave) {
                    Tools.copy(videopath, Constants.SD_VIDEO_PATH + fileName);
                }
                //现在完成，可以播放
                Message msgMessage = handler.obtainMessage(1);
                msgMessage.obj = pathName;
                handler.sendMessage(msgMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
