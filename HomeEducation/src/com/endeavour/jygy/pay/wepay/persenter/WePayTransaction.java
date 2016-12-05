package com.endeavour.jygy.pay.wepay.persenter;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.endeavour.jygy.common.Tools;
import com.endeavour.jygy.common.volley.BaseRequest;
import com.endeavour.jygy.common.volley.NetRequest;
import com.endeavour.jygy.common.volley.Response;
import com.endeavour.jygy.pay.wepay.WePayConfig;
import com.endeavour.jygy.pay.wepay.bean.CreatePreOrderReq;
import com.endeavour.jygy.pay.wepay.bean.CreatePreOrderResp;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by wu on 16/6/5.
 */
public class WePayTransaction {

    public static final int PAY_SUCCESS = 0;
    public static final int PAY_FAILD = -1;

    private static final String LOG_TAG = WePayTransaction.class.getSimpleName();

    private IWXAPI api;

    public WePayTransaction(Context context) {
//        api = WXAPIFactory.createWXAPI(context.getApplicationContext(), WePayConfig.WEPAY_APPID);
        api = WXAPIFactory.createWXAPI(context, WePayConfig.WEPAY_APPID, false);
    }

    private boolean registerApp() {
        return api.registerApp(WePayConfig.WEPAY_APPID);
    }

    private boolean isSupportPay() {
        return api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    //http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android
    public void pay(Context context, int totalFee, String body, String attach, final BaseRequest.ResponseListener listener) {

        if (!registerApp()) {
            listener.onFailed(new Response(-1, "无法注册到微信"));
            return;
        }

        if (!isSupportPay()) {
            listener.onFailed(new Response(-1, "未安装微信 或 微信版本过低, 无法支付"));
            return;
        }

        CreatePreOrderReq preOrderReq = new CreatePreOrderReq();
        preOrderReq.setBody(body);
        preOrderReq.setAttach(attach);
        preOrderReq.setSpbillCreateIp(Tools.getWIFILocalIpAdress(context.getApplicationContext()));
        preOrderReq.setTotalFee(totalFee);

        NetRequest.getInstance().addRequest(preOrderReq, new BaseRequest.ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                CreatePreOrderResp resp = JSONObject.parseObject(String.valueOf(response.getResult()), CreatePreOrderResp.class);
                PayReq req = new PayReq();
                req.appId = resp.getAppid();
                req.partnerId = resp.getPartnerid();
                req.prepayId = resp.getPrepayid();
                req.packageValue = resp.getPackage_();
                req.nonceStr = resp.getNoncestr();
                req.timeStamp = resp.getTimestamp();
                req.sign = resp.getSign();

                api.sendReq(req);
                listener.onSuccess(new Response(0, "正在发起支付"));
            }

            @Override
            public void onFailed(Response response) {
                listener.onFailed(response);
            }
        });
    }

}
