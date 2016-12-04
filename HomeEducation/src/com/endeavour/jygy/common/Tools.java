package com.endeavour.jygy.common;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wu on 15/11/10.
 */
public class Tools {

    public static void toActivity(Context context, Class<?> target) {
        Intent intent = new Intent(context, target);
        context.startActivity(intent);
    }

    public static void toastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static ArrayList<String> trans(String[] sa) {
        ArrayList<String> als = new ArrayList<String>(0);
        for (int i = 0; i < sa.length; i++) {
            als.add(sa[i]);
        }
        return als;
    }

    public static String getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT + "";
    }

    public static String getMobileName() {
        return android.os.Build.MANUFACTURER;
    }

    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    public static String getMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = info.getMacAddress();
        if (!TextUtils.isEmpty(macAddress)) {
            macAddress = macAddress.replaceAll(":", "");
            return macAddress;
        }
        return "";
    }

    /**
     * 获取sd卡的路径,最后没有"/"
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();

    }

    /*
     * 为照片命名
     */
    public static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();

    }

    public static String sha(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("SHA-256").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static void hideKeyBorad(Context context, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
    }

    public static void showKeyBorad(Context context, View view) {
        if (view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        view.requestFocus();
    }

    public static int getDeviceWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    public static int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }

    public static String getWIFILocalIpAdress(Context mContext) {

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return formatIpAddress(ipAddress);
    }

    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }


    public static String encodeUtf8(String msg) {
        try {
            return URLEncoder.encode(msg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static String decodeUtf8(String msg) {
        try {
            return URLDecoder.decode(msg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static boolean saveMyBitmap(String path, String bitName, Bitmap mBitmap) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(bitName) || mBitmap == null) {
            return false;
        }
        try {
            File root = Environment.getExternalStorageDirectory();
            File file = new File(root + "/jygy/");
            file.mkdirs();
            Date mDate = new Date();
            SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMdd_HHmmss");
            FileOutputStream fos = null;
            fos = new FileOutputStream(new File(root, fileName.format(mDate) + ".jpg"));
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            mBitmap.recycle();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 复制文件到指定文件
     *
     * @param fromPath 源文件
     * @param toPath   复制到的文件
     * @return true 成功，false 失败
     */
    public static boolean copy(String fromPath, String toPath) {
        boolean result = false;
        File from = new File(fromPath);
        if (!from.exists()) {
            return result;
        }

        File toFile = new File(toPath);
        deleteFileOrDir(toFile);
        File toDir = toFile.getParentFile();
        if (toDir.exists() || toDir.mkdirs()) {
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(from);
                out = new FileOutputStream(toFile);
                copy(in, out);
                result = true;
            } catch (Throwable ex) {
                result = false;
            } finally {
                closeQuietly(in);
                closeQuietly(out);
            }
        }
        return result;
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        if (!(in instanceof BufferedInputStream)) {
            in = new BufferedInputStream(in);
        }
        if (!(out instanceof BufferedOutputStream)) {
            out = new BufferedOutputStream(out);
        }
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
    }

    public static boolean deleteFileOrDir(File path) {
        if (path == null || !path.exists()) {
            return true;
        }
        if (path.isFile()) {
            return path.delete();
        }
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteFileOrDir(file);
            }
        }
        return path.delete();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable ignored) {

            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable ignored) {
            }
        }
    }
}
