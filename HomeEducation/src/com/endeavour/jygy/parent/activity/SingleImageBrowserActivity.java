package com.endeavour.jygy.parent.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.endeavour.jygy.common.view.bigimg.view.LargeImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by wu on 15/12/30.
 */
public class SingleImageBrowserActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> paths = (List<String>) getIntent().getSerializableExtra("paths");
        int position = getIntent().getIntExtra("position", 0);
        if (paths == null || paths.isEmpty() || position >= paths.size()) {
            this.finish();
        }
        setMainView(R.layout.layout_large_image_browser);
        final LargeImageView largeImageView = (LargeImageView) findViewById(R.id.idLargeImg);
        ImageLoader.getInstance().loadImage(paths.get(position), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                progresser.showContent();
                Toast.makeText(SingleImageBrowserActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                String bitName = System.currentTimeMillis() + ".jpg";
//                if (Tools.saveMyBitmap(Constants.SD_PIC_PATH, bitName, bitmap)) {
//                    Toast.makeText(SingleImageBrowserActivity.this, "图片保存到 " + bitName, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(SingleImageBrowserActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
//                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                InputStream isBm = new ByteArrayInputStream(baos.toByteArray());
                largeImageView.setInputStream(isBm);
                progresser.showContent();

            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                progresser.showContent();
            }
        });
    }


}
