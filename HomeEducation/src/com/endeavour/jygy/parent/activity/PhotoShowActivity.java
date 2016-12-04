package com.endeavour.jygy.parent.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.view.DragImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PhotoShowActivity extends Activity {
	private int window_width, window_height;// 控件宽度
	private DragImageView dragImageView;// 自定义控件
	private int state_height;// 状态栏的高度

	private ViewTreeObserver viewTreeObserver;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoshow);
        dragImageView = (DragImageView)findViewById(R.id.imageview_show);  
        String imgpath = getIntent().getExtras().getString("imgpath");
        /** 获取可見区域高度 **/
		WindowManager manager = getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight();
        ImageLoader.getInstance().displayImage(imgpath, dragImageView, MainApp.getDynamicOptions());
        //myImageView.setImageDrawable(getResources().getDrawable(R.drawable.menu_background)); 
//        Bitmap bmp = BitmapUtil.ReadBitmapById(this, R.drawable.huoying,
//				window_width, window_height);
		// 设置图片
		//dragImageView.setImageBitmap(bmp);
		dragImageView.setmActivity(this);//注入Activity.
		/** 测量状态栏高度 **/
		viewTreeObserver = dragImageView.getViewTreeObserver();
		viewTreeObserver
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						if (state_height == 0) {
							// 获取状况栏高度
							Rect frame = new Rect();
							getWindow().getDecorView()
									.getWindowVisibleDisplayFrame(frame);
							state_height = frame.top;
							dragImageView.setScreen_H(window_height-state_height);
							dragImageView.setScreen_W(window_width);
						}

					}
				});
    }
}
