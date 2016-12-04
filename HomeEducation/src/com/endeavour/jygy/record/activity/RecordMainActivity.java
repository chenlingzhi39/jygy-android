package com.endeavour.jygy.record.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.endeavour.jygy.R;
import com.endeavour.jygy.record.util.MediaManager;
import com.endeavour.jygy.record.view.AudioRecordButton;
import com.endeavour.jygy.record.view.AudioRecordButton.AudioFinishRecorderListener;

import java.util.ArrayList;
import java.util.List;

/**
 * wxf
 * @author lenovo
 *
 */
public class RecordMainActivity extends Activity {
	AudioRecordButton button;

	private ListView mlistview;
	private ArrayAdapter<Recorder> mAdapter;
	private View viewanim;
	private List<Recorder> mDatas = new ArrayList<RecordMainActivity.Recorder>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_main);

		mlistview = (ListView) findViewById(R.id.listview);

		button = (AudioRecordButton) findViewById(R.id.recordButton);
		button.setAudioFinishRecorderListener(new AudioFinishRecorderListener() {

			@Override
			public void onFinished(float seconds, String filePath,String filename) {

				Recorder recorder = new Recorder(seconds, filePath,filename);
				mDatas.add(recorder);
				mAdapter.notifyDataSetChanged();
				mlistview.setSelection(mDatas.size() - 1);
				// 返回到播放页面
				Intent intent = new Intent();
				intent.putExtra("soundname", recorder.getFileName());
				intent.putExtra("soundpath", recorder.getFilePathString());
				setResult(RESULT_OK, intent);
				RecordMainActivity.this.finish();
			}
		});

		mAdapter = new RecorderAdapter(this, mDatas);
		mlistview.setAdapter(mAdapter);

		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// 播放动画
				if (viewanim != null) {// 让第二个播放的时候第一个停止播放
					viewanim.setBackgroundResource(R.drawable.adj);
					viewanim = null;
				}
				viewanim = view.findViewById(R.id.id_recorder_anim);
				viewanim.setBackgroundResource(R.drawable.play);
				AnimationDrawable drawable = (AnimationDrawable) viewanim
						.getBackground();
				drawable.start();

				// 播放音频
				MediaManager.playSound(mDatas.get(position).filePathString,
						new MediaPlayer.OnCompletionListener() {
							@Override
							public void onCompletion(MediaPlayer mp) {
								viewanim.setBackgroundResource(R.drawable.adj);

							}
						});
			}
		});
		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
		p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.6
		p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.95
		getWindow().setAttributes(p); // 设置生效
	}

	@Override
	protected void onPause() {

		super.onPause();
		MediaManager.pause();
	}

	@Override
	protected void onResume() {

		super.onResume();
		MediaManager.resume();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		MediaManager.release();
	}

	class Recorder{
		float time;
		String filePathString;
		String fileName;
		public Recorder(float time, String filePathString,String filename) {
			super();
			this.time = time;
			this.filePathString = filePathString;
			this.fileName = filename;
		}

		public float getTime() {
			return time;
		}

		public void setTime(float time) {
			this.time = time;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		
		public String getFilePathString() {
			return filePathString;
		}

		public void setFilePathString(String filePathString) {
			this.filePathString = filePathString;
		}

	}

}
