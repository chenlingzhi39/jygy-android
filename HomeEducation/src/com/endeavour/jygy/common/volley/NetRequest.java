package com.endeavour.jygy.common.volley;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.endeavour.jygy.common.base.BaseReq;
import com.endeavour.jygy.common.db.AppConfigDef;
import com.endeavour.jygy.common.db.DefaultAppConfigHelper;
import com.wizarpos.log.util.LogEx;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据业务封装请求.
 *
 * @author wu
 */
public class NetRequest extends BaseRequest {

    public static String serverUrl;// 网络请求地址
    //    public static String serverUrl = "http://train.wizarpos.com:80" + "/jygy-service/v1_0/";// 网络请求地址
    //    public static final String serverUrl = "http://192.168.1.16/jygy-service/v1_0/";// 网络请求地址
//    public static final String serverUrl = "http://192.168.1.64:8080"+"/jygy-service/v1_0/";// 网络请求地址
    private Map<String, String> header;
    protected static NetRequest request;

    private NetRequest() {
    }

    public static NetRequest getInstance() {
        if (request == null) {
            request = new NetRequest();
        }
        return request;
    }


    /**
     * post 请求
     *
     * @param msgRequest 请求实体
     * @param header     头
     * @param tag        标识
     * @param listener   回调
     */
    public void addRequest(BaseReq msgRequest, HashMap<String, String> header, String tag, ResponseListener listener) {
        String reqParam = JSON.toJSONStringWithDateFormat(msgRequest, "utf-8",SerializerFeature.WriteDateUseDateFormat);
        LogEx.d("请求报文: ", reqParam);
        if (TextUtils.isEmpty(serverUrl)) {
            serverUrl = bundleServerUrl();
        }
        super.addRequest(serverUrl, reqParam, header, tag, listener);
    }

    /**
     * post 请求
     *
     * @param msgRequest 请求实体
     * @param listener   回调
     */
    public void addRequest(BaseReq msgRequest, ResponseListener listener) {
        String reqParam = JSON.toJSONStringWithDateFormat(msgRequest, "utf-8",SerializerFeature.WriteDateUseDateFormat);
        LogEx.d("请求报文: ", reqParam);
        if (TextUtils.isEmpty(serverUrl)) {
            serverUrl = bundleServerUrl();
        }
        super.addRequest(serverUrl + msgRequest.getUrl(), reqParam, getDefaultHeader(), msgRequest.getUrl(), listener);
    }

    private String bundleServerUrl() {
        String prot = DefaultAppConfigHelper.getConfig(AppConfigDef.port);
        return "http://" + DefaultAppConfigHelper.getConfig(AppConfigDef.ip) + (TextUtils.isEmpty(prot) ? "" : (":" + prot)) + "/jygy-service/v1_0/";
    }

    public Map<String, String> getDefaultHeader() {
        if (header == null) {
            header = new HashMap<>();
            header.put("charset", "UTF-8");
            header.put("Content-Type", "application/json");
        }
        return header;
    }

    /**
     * 读取application 节点  meta-data 信息
     */
    private String readMetaDataFromApplication(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("channel;");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
