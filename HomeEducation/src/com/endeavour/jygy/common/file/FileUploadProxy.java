package com.endeavour.jygy.common.file;

import android.text.TextUtils;
import android.util.Log;

import com.endeavour.jygy.app.MainApp;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.AppConfigHelper;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.wizarpos.log.util.LogEx;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wu on 15/12/18.
 */
public class FileUploadProxy {

    private static final String TAG = "uploadfile";

    private String mutilFileOssOriginUrl; //如包含此内容则会删除OSS服务原来的内容

    private String otherUploadUrl = NetRequest.serverUrl + "mutliFile/other";
    private String picUploadUrl = NetRequest.serverUrl + "mutliFile/pic";
    private String zipUploadUrl = NetRequest.serverUrl + "mutliFile/uploadFile";

    private static final String SD_PATH = "jygy";
    private static final String FILE_DIR = Tools.getSDPath() + "/" + SD_PATH + "/";

    public void setMutilFileOssOriginUrl(String mutilFileOssOriginUrl) {
        this.mutilFileOssOriginUrl = mutilFileOssOriginUrl;
    }

    private void uploadSignleImg(String filePath, String picType, BaseRequest.ResponseListener listener) {
        if (TextUtils.isEmpty(filePath) || !FileUtils.isFileExists(filePath)) {
            if (listener != null) {
                listener.onFaild(new Response(1, "文件不存在"));
            }
        } else {
            File dirFile = new File(FILE_DIR);
            if(!dirFile.exists()){
                dirFile.mkdirs();
            }
            FileUtils.createNoMediaFile(FILE_DIR);
            String fileType = FileUtils.getFileType(filePath);
            String newFileName = System.currentTimeMillis() +"." + fileType;
            String cachePath = FILE_DIR + newFileName;
            LogEx.d(TAG, cachePath);
            FileUtils.copyFile(filePath, cachePath);
            Map<String, String> header = new HashMap<>();
            header.put("mutilFileOssOriginUrl", mutilFileOssOriginUrl);
            header.put("Content-Type", "application/x-www-form-urlencoded");
            header.put("photoName", newFileName);
            header.put("picType", picType);
            if(MainApp.isParent()){
                header.put("userId", AppConfigHelper.getConfig(AppConfigDef.studentID));
            }
            FileUploader uploader = new FileUploader();
            uploader.setDeleteFileWhenFaild(true);
            uploader.init(picUploadUrl, header, cachePath, listener);
            uploader.execute();
        }
    }

    public void uploadImgFile(String filePath, BaseRequest.ResponseListener listener) {
        uploadSignleImg(filePath, "2", listener);
    }

    public void uploadUserIconFile(String filePath, BaseRequest.ResponseListener listener) {
        uploadSignleImg(filePath, "1", listener);
    }

    public void uploadZipFile(List<String> paths, final BaseRequest.ResponseListener listener) {
        FileZiper ziper = new FileZiper();
        ziper.setCachePaht("jygy");
        ziper.init(paths, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                String filePath = String.valueOf(response.getResult());
                String fileName = FileUtils.getFileName(filePath);
                Map<String, String> header = new HashMap<String, String>();
                header.put("mutilFileOssOriginUrl", mutilFileOssOriginUrl);
                header.put("Content-Type", "application/x-www-form-urlencoded");
                header.put("fileName", fileName);
                header.put("picType", "2");
                if(MainApp.isParent()){
                    header.put("userId", AppConfigHelper.getConfig(AppConfigDef.studentID));
                }
                FileUploader uploader = new FileUploader();
                uploader.setDeleteFileWhenFaild(true);
                uploader.init(zipUploadUrl, header, filePath, listener);
                uploader.execute();
            }

            @Override
            public void onFaild(Response response) {
                Log.d(TAG, "zip faild");
                listener.onFaild(response);
            }
        });
        ziper.execute();
    }

}
