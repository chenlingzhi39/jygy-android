package com.endeavour.jygy.common.volley;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.wizarpos.log.util.LogEx;

import java.util.Map;

/**
 * Created by wu on 15/11/24.
 */
public class BaseRequest {

    private RetryPolicy retryPolicy;// 请求配置

    // private static BaseNetRequest request;

    private static int DEFAULT_SOCKET_TIMEOUT = 15 * 1000;// 单次请求超时时间
    private static int DEFAULT_MAX_RETRIES = 0;// 重连次数
    private static float DEFAULT_BACKOFF_MULT = 0;// 重连时间递增倍数
    public static final int NETWORK_ERR = 998899;

    protected BaseRequest() {
        retryPolicy = new DefaultRetryPolicy(DEFAULT_SOCKET_TIMEOUT, DEFAULT_MAX_RETRIES, DEFAULT_BACKOFF_MULT);
    }

    /**
     * 新增网络请求 <br>
     * post请求
     *
     * @param serverUrl 请求地址
     * @param reqStr    请求内容 可为空
     * @param header    头
     * @param tag       请求 标识 , 可用于撤销请求
     * @param listener  回调
     */
    public void addRequest(String serverUrl, String reqStr, Map<String, String> header, String tag, final ResponseListener listener) {
        LogEx.d("请求地址: ", serverUrl);
        CustomStringRequest request = new CustomStringRequest(serverUrl, reqStr, header, new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String result) {
                FileUtils.saveResp2Cache(result);
                LogEx.d("返回报文: ", result);
                try {
                    Response response = new Response();
                    JSONObject rjobj = JSON.parseObject(result);
                    int code = rjobj.containsKey("code") ? rjobj.getIntValue("code") : 1;
                    String msg = rjobj.getString("message");
                    Object obj = rjobj.containsKey("content") ? rjobj.get("content") : null;
                    if (obj == null) {
                        obj = rjobj.containsKey("result") ? rjobj.get("result") : null;
                    }

                    if (!TextUtils.isEmpty(msg) && msg.contains("For input string")) {
                        msg = "暂无数据";
                    }

                    response.setCode(code);
                    response.setMsg(msg);
                    response.setResult(obj);

                    if (response.code == 0) {
                        listener.onSuccess(response);
                    } else {
                        listener.onFaild(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFaild(new Response(1, "未知异常"));
                }
            }

        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                listener.onFaild(VolleyErrorHelper.getMessage(arg0));
            }
        });
        request.setRetryPolicy(retryPolicy);
        VolleyApplication.getInstance().addToRequestQueue(request, tag);
    }

    /**
     * 新增网络请求 <br>
     * post请求
     *
     * @param serverUrl 请求地址
     * @param reqStr    请求内容 可为空
     * @param header    头
     * @param tag       请求 标识 , 可用于撤销请求
     * @param listener  回调
     */
    public void addRequest(String serverUrl, String reqStr, Map<String, String> header, String tag, com.android.volley.Response.Listener<String> listener,
                           com.android.volley.Response.ErrorListener errorListener) {
        CustomStringRequest request = new CustomStringRequest(serverUrl, reqStr, header, listener, errorListener);
        request.setRetryPolicy(retryPolicy);
        VolleyApplication.getInstance().addToRequestQueue(request, tag);
    }

    public RetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    /**
     * 网络请求回调
     *
     * @author wu
     */
    public interface ResponseListener {

        void onSuccess(Response response);

        void onFaild(Response response);

    }


}
