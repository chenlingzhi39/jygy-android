package com.endeavour.jygy.app;

import android.graphics.Bitmap;
import android.os.Environment;

import com.endeavour.jygy.R;
import com.endeavour.jygy.common.volley.VolleyApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

public class ImageLoadApp extends VolleyApplication {
    public static String fileDir = Environment.getExternalStorageDirectory() + File.separator + "pay2" + File.separator;
    public static DisplayImageOptions commonOptions, dynamicOptions, userIconOptions, babyImgOptions, teacherIconOptions, schoolBossOptions, bobyOptions, grilOptions, classOptions;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    private void initImageLoader() {
        mkdir();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(320, 540).diskCacheExtraOptions(320, 540, null)
                .threadPoolSize(4).threadPriority(Thread.NORM_PRIORITY - 2).tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory().memoryCache(new LruMemoryCache(4 * 1024 * 1024)).memoryCacheSize(4 * 1024 * 1024)
                .memoryCacheSizePercentage(15).diskCache(new UnlimitedDiscCache(new File(fileDir))).diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(1000)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//				.writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static DisplayImageOptions getOptions() {
        if (commonOptions == null) {
            commonOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.white) // resource or drawable
                    .showImageForEmptyUri(R.drawable.white) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.white) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(100)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return commonOptions;
    }


    public static DisplayImageOptions getHtmlOptions() {
        if (commonOptions == null) {
            commonOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.white) // resource or drawable
                    .showImageForEmptyUri(R.drawable.white) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.white) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(100)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .build();
        }
        return commonOptions;
    }

    public static DisplayImageOptions getDynamicOptions() {
        if (dynamicOptions == null) {
            dynamicOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.white)
                    .showImageForEmptyUri(R.drawable.white)
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(100)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return dynamicOptions;
    }

    public static DisplayImageOptions getIconOptions() {
        if (userIconOptions == null) {
            userIconOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.white) // resource or drawable
                    .showImageForEmptyUri(R.drawable.white) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.white) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return userIconOptions;
    }

    public static DisplayImageOptions getBabyImgOptions() {
        if (babyImgOptions == null) {
            babyImgOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_new) // resource or drawable
                    .showImageForEmptyUri(R.drawable.icon_new) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.icon_new) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return babyImgOptions;
    }

    public static DisplayImageOptions getClassOptions() {
        if (classOptions == null) {
            classOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.class_icon) // resource or drawable
                    .showImageForEmptyUri(R.drawable.class_icon) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.class_icon) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return classOptions;
    }

    public static DisplayImageOptions getTeacherOptions() {
        if (teacherIconOptions == null) {
            teacherIconOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.teacher) // resource or drawable
                    .showImageForEmptyUri(R.drawable.teacher) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.teacher) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return teacherIconOptions;
    }

    public static DisplayImageOptions getSchoolBoosOptions() {
        if (schoolBossOptions == null) {
            schoolBossOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.icon_new) // resource or drawable
                    .showImageForEmptyUri(R.drawable.icon_new) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.icon_new) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return schoolBossOptions;
    }

    public static DisplayImageOptions getBobyOptions() {
        if (bobyOptions == null) {
            bobyOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.boy) // resource or drawable
                    .showImageForEmptyUri(R.drawable.boy) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.boy) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return bobyOptions;
    }

    public static DisplayImageOptions getGrilsOptions() {
        if (grilOptions == null) {
            grilOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.girl) // resource or drawable
                    .showImageForEmptyUri(R.drawable.girl) // resource or
                    // drawable
                    .showImageOnFail(R.drawable.girl) // resource or drawable
                    .resetViewBeforeLoading(false) // default
//					.delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .considerExifParams(true) // default
                    .build();
        }
        return grilOptions;
    }

    private void mkdir() {
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

}
