package com.endeavour.jygy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Toast;

import com.endeavour.jygy.welcome.MoaWelcomeActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class StartActivity extends Activity{
	private boolean isFirst = false;
	private Intent intent;
	private Handler handler;
	private SharedPreferences sf;
	
	@Override
    public void onDestroy() {
        super.onDestroy();
    }
	
    @Override  
    protected void onCreate(Bundle savedInstanceState) {          
        super.onCreate(savedInstanceState);    
        final View view = View.inflate(this, R.layout.welcome, null);
		setContentView(view);
		//渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
			
		}); 
		intent = new Intent();
		sf = getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		isFirst = sf.getBoolean("isFirstLoginApp", true);
//        imageView = (ImageView)findViewById(R.id.welcome_image_view);   
//        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);   
//        alphaAnimation.setFillEnabled(true); //启动Fill保持   
//        alphaAnimation.setFillAfter(true);  //设置动画的最后一帧是保持在View上面   
//        imageView.setAnimation(alphaAnimation);   
//        alphaAnimation.setAnimationListener(this);  //为动画设置监听

		PermissionListener permissionlistener = new PermissionListener() {
			@Override
			public void onPermissionGranted() {

			}

			@Override
			public void onPermissionDenied(ArrayList<String> deniedPermissions) {
				Toast.makeText(StartActivity.this, "权限拒绝, 请开启权限", Toast.LENGTH_SHORT).show();
			}

		};
		new TedPermission(this)
				.setPermissionListener(permissionlistener)
				.setDeniedMessage("请开启权限")
				.setPermissions(Manifest.permission.READ_PHONE_STATE)
				.check();
    }   
    
    public boolean isFirstLoginApp(){
		if(isFirst){
			SharedPreferences.Editor editor = sf.edit();
    		intent.setClass(StartActivity.this, MoaWelcomeActivity.class);
    		startActivity(intent);
    		editor.putBoolean("isFirstLoginApp", false);
    		editor.commit();
    		finish();
    	}else{
    		intent.setClass(StartActivity.this, LoginActivity.class);
    		startActivity(intent);
    		finish();
    	}
		return false;
	}
    /**
     * 跳转到...
     */
    private void redirectTo(){        
    	isFirstLoginApp();
    }
} 