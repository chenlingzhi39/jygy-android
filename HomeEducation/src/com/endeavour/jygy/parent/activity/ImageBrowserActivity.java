package com.endeavour.jygy.parent.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.Constants;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.parent.adapter.TouchImageAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by wu on 15/12/30.
 */
public class ImageBrowserActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> paths = (List<String>) getIntent().getSerializableExtra("paths");
        int position = getIntent().getIntExtra("position", 0);
        if (paths == null || paths.isEmpty() || position >= paths.size()) {
            this.finish();
        }
        setMainView(R.layout.layout_image_browser);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TouchImageAdapter touchImageAdapter = new TouchImageAdapter(paths);
        touchImageAdapter.setOnSigleTabListener(new TouchImageAdapter.OnSigleTabListener() {
            @Override
            public void onSingleTapConfirmed() {
                ImageBrowserActivity.this.finish();
            }

            @Override
            public void onLongClick(String img) {
                progresser.showProgress();
                ImageLoader.getInstance().loadImage(img, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        progresser.showContent();
                        Toast.makeText(ImageBrowserActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        String bitName = System.currentTimeMillis() + ".jpg";
                        if (Tools.saveMyBitmap(Constants.SD_PIC_PATH, bitName, bitmap)) {
                            Toast.makeText(ImageBrowserActivity.this, "图片保存到 " + bitName, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ImageBrowserActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                        progresser.showContent();

                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        progresser.showContent();
                    }
                });
            }
        });
        viewPager.setAdapter(touchImageAdapter);
        viewPager.setCurrentItem(position);
    }


}
