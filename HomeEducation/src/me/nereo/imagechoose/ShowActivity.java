package me.nereo.imagechoose;

import android.os.Bundle;
import android.text.TextUtils;

import com.endeavour.jygy.R;
import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.base.BaseViewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoView;

public class ShowActivity extends BaseViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setMainView(R.layout.choose_show);
        PhotoView ivPhotoView = (PhotoView) findViewById(R.id.mScaleImageView);
        String path = getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(path)) {
            this.finish();
            return;
        }
        if (path.startsWith("http")) {
            ImageLoader.getInstance().displayImage(path, ivPhotoView, MainApp.getOptions());
        } else {
            String imgPath = "file://" + path;
            ImageLoader.getInstance().displayImage(imgPath, ivPhotoView, MainApp.getOptions());
        }
    }
}
