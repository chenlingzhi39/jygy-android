package com.endeavour.jygy.record.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.record.util.MediaManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecordShowActivity extends Activity implements OnClickListener {
	private String islocal="";
	private String recordpath="";
	private View recodeView;
	private RelativeLayout id_recorder_lay;
	private String picpath = Environment.getExternalStorageDirectory()
			+ "/jygycache/";
	private String fpath = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recordshow);
		recodeView = (View) findViewById(R.id.id_recorder_anim);
		recordpath = getIntent().getExtras().getString("recordpath");
		islocal = getIntent().getExtras().getString("islocal");
        if(islocal!=null){
        	playvideo(recordpath.replace("file:/", ""));
        	return;
        }
		String[] filearr = recordpath.split("\\/");
		String fileName = filearr[filearr.length - 1];
		String pathName = picpath + fileName;// 文件存储路径
		File file = new File(pathName);
		if (file.exists()) { // 如果本地存在 就播放本地录音
			playvideo(pathName);
		} else { // 在费流量的情况下，用户同意播放，就开启下载,下载完成后再播放
			ConnectivityManager connectMgr = (ConnectivityManager) this
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = connectMgr.getActiveNetworkInfo();
			if (info != null
					&& info.getType() == ConnectivityManager.TYPE_MOBILE) {
				AlertDialog.Builder ad = new AlertDialog.Builder(this);
				ad.setTitle("当前是移动网络");
				ad.setMessage("是否确定播放录音,是否继续?");
				ad.setPositiveButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								RecordShowActivity.this.finish();
							}
						});
				ad.setNegativeButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Tools.toastMsg(RecordShowActivity.this, "缓冲完成后自动播放，稍等~");
								//先下载，再播放，只有第一次看视频才会走这里哦
								new Thread(new Runnable() {
									@Override
									public void run() {
										download(recordpath);
									}
								}).start();
							}
						});
				ad.show();
			}else{
				Tools.toastMsg(RecordShowActivity.this, "缓冲完成后自动播放，稍等~");
				//先下载，再播放，只有第一次看视频才会走这里哦
				new Thread(new Runnable() {
					@Override
					public void run() {
						download(recordpath);
					}
				}).start();
			}
		}
	}

	private void playvideo(String videopath) {
		try {
			fpath = videopath;
			// 播放动画
			if (recodeView != null) {// 让第二个播放的时候第一个停止播放
				recodeView.setBackgroundResource(R.drawable.sound);
				recodeView = null;
			}
			recodeView = findViewById(R.id.id_recorder_anim);
			recodeView.setBackgroundResource(R.drawable.play);
			recodeView.setVisibility(View.VISIBLE);
			AnimationDrawable drawable = (AnimationDrawable) recodeView
					.getBackground();
			drawable.start();
			recodeView.setOnClickListener(this);
			// 播放音频
			MediaManager.playSound(videopath,
					new MediaPlayer.OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							recodeView.setBackgroundResource(R.drawable.sound);
						}
					});
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
				//现在完成，可以播放
				Message msgMessage = handler.obtainMessage(1);
				msgMessage.obj = pathName;
				handler.sendMessage(msgMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(final View v) {
		// 播放音频
		v.setBackgroundResource(R.drawable.play);
		AnimationDrawable drawable = (AnimationDrawable) v
				.getBackground();
		drawable.start();
		MediaManager.playSound(fpath,
				new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						v.setBackgroundResource(R.drawable.sound);
					}
				});
	}
}
