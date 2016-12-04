package com.endeavour.jygy.common.file;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 15/12/18.
 */
public class FileZiper extends AsyncTask<String, String, Response> {

    private String cachePath = "cache";
    private String zipName;
    private List<String> paths;
    private BaseRequest.ResponseListener listener;

    public void setCachePaht(String path) {
        this.cachePath = path;
    }

    public void setZipName(String zipName) {
        this.zipName = zipName;
    }

    public void init(final List<String> paths, final BaseRequest.ResponseListener listener) {
        this.paths = paths;
        this.listener = listener;
    }

    @Override
    protected Response doInBackground(String... params) {
        try {
            if (paths == null || paths.isEmpty()) {
                return new Response(1, "文件不存在");
            }
            String time = System.currentTimeMillis() + "";
            File cacheFile = new File(Tools.getSDPath() + "/" + cachePath + "/" + time + "/");
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
            FileUtils.createNoMediaFile(cacheFile.getPath());
            List<File> cachePaths =  new ArrayList<>();
            //比对list中的数据，jpeg和mp4文件名相同，就筛选出来，保存为文件名相同
            List<String[]> filelist = new ArrayList<String[]>();
            for (int i = 0; i < paths.size()-1; i++) {
            	String path_i = paths.get(i).substring(0,paths.get(i).lastIndexOf("."));
            	for (int j = i+1; j < paths.size(); j++) {
            		String path_j = paths.get(j).substring(0,paths.get(j).lastIndexOf("."));
                	if(path_i.equals(path_j)){ //文件名比对
                		String[] one = {paths.get(i),i+""};
                		String[] two = {paths.get(j),j+""};
                		filelist.add(one);
                		filelist.add(two);
                	}
                }
            }
            if(filelist.size()>0){
            	for(int k=0;k<filelist.size();k++){
            		paths.remove(filelist.get(k)[0]);
            	}
            }
            //视频和图片同名的文件，单独处理，存储时，文件名设置相同
            for (int i = 0; i < filelist.size(); i++) {
                if (filelist.get(i)[0].startsWith("file://")) {
                    String path = filelist.get(i)[0].substring(6);
                    String cachePath = cacheFile.getAbsolutePath() + "/" + time;
                    if(path.contains(".jpeg") || path.contains(".jpg") || path.contains(".png"))
                    	cachePath+=".jpeg";
                    else if(path.contains(".mp4")) //小视频
                    	cachePath+=".mp4";
                    FileUtils.copyFile(path, cachePath);
                    cachePaths.add(new File(cachePath));
                }
            }
            for (int i = 0; i < paths.size(); i++) {
                if (paths.get(i).startsWith("file://")) {
                    String path = paths.get(i).substring(6);
                    String cachePath = cacheFile.getAbsolutePath() + "/" + time +i;
                    if(path.contains(".jpeg") || path.contains(".jpg") || path.contains(".png"))
                    	cachePath+=".jpeg";
                    else if(path.contains(".mp4")) //小视频
                    	cachePath+=".mp4";
                    else if(path.contains(".amr")) //录音文件
                    	cachePath+=".amr";
                    FileUtils.copyFile(path, cachePath);
                    cachePaths.add(new File(cachePath));
                }
            }
            if (TextUtils.isEmpty(zipName)) {
                zipName = System.currentTimeMillis() + ".zip";
            }
            String filePathName = cacheFile + "/" + zipName;
            File zipFile = new File(filePathName);
            ZipUtils.zipFiles(cachePaths, zipFile);
            for(File path : cachePaths){
                FileUtils.deleteAllFiles(path);
            }
            return new Response(0, "success", filePathName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(1, "文件压缩出错");
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (response.code == 0) {
            listener.onSuccess(response);
        } else {
            listener.onFaild(response);
        }
    }
}
