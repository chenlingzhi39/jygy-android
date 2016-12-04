package com.endeavour.jygy.common.file;

import android.os.AsyncTask;
import android.util.Log;

import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wu on 15/12/12.
 */
class  FileUploader extends AsyncTask<String, String, Response> {

    private static final String TAG = "FileUploader ";
    private BaseRequest.ResponseListener listener;
    private String serverUrl;
    private Map<String, String> headers;
    private String filePath;
    private boolean deleteFileWhenFaild;

    public void setDeleteFileWhenFaild(boolean deleteFileWhenFaild) {
        this.deleteFileWhenFaild = deleteFileWhenFaild;
    }

    public void init(final String serverUrl, final Map<String, String> headers, final String filePath, final BaseRequest.ResponseListener listener) {
        this.listener = listener;
        this.serverUrl = serverUrl;
        this.headers = headers;
        this.filePath = filePath;
    }

    @Override
    protected Response doInBackground(String... params) {
        FileInputStream fStream;
        try {
            fStream = new FileInputStream(filePath);
            // 请求数据
            HttpPost httpRequest = new HttpPost(serverUrl);
            // 创建参数
            InputStreamEntity inputStreamEntity = new InputStreamEntity(fStream, fStream.available());
            Iterator iter = headers.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                httpRequest.setHeader(entry.getKey(), entry.getValue());
            }
            // 对提交数据进行编码
            httpRequest.setEntity(inputStreamEntity);
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
            // 获取响应服务器的数据
            int httpCode = httpResponse.getStatusLine().getStatusCode();
            Log.d(TAG, "upload code--->" + httpCode);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                byte[] responseByte = EntityUtils.toByteArray(httpResponse.getEntity());
                String responseStr = new String(responseByte, 0, responseByte.length, "UTF-8");
                Log.d(TAG, "response JSON:\n" + new String(responseByte, 0, responseByte.length, "UTF-8"));
                Response response = new Response();
                JSONObject uploadFileRespJSON = new JSONObject(responseStr);
                if (uploadFileRespJSON.has("code")) {
                    response.setCode(uploadFileRespJSON.getInt("code"));
                } else {
                    response.setCode(-1);
                }
                if (uploadFileRespJSON.has("message")) {
                    response.setMsg(uploadFileRespJSON.getString("message"));
                }
                if (uploadFileRespJSON.has("content")) {
                    response.setResult(uploadFileRespJSON.getString("content"));
                }
                return response;
            } else {
                return new Response(1, "文件上传失败,网络连接异常");
            }
        } catch (ClientProtocolException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return new Response(1, "文件上传失败,网络连接异常");
        } catch (IOException e) {
            e.printStackTrace();
            return new Response(1, "文件上传失败,网络连接异常");
        } catch (JSONException e) {
            e.printStackTrace();
            return new Response(1, "文件上传失败,数据解析异常");
        } catch (Exception e){
            e.printStackTrace();
            return new Response(1, "文件上传失败,数据解析异常");
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        File file = new File(filePath);
        if (response.getCode() == 0) {
            listener.onSuccess(response);
            FileUtils.deleteAllFiles(file.getParentFile());
        } else {
            if(deleteFileWhenFaild){
                FileUtils.deleteAllFiles(file.getParentFile());
            }
            listener.onFaild(response);
        }
    }
}
