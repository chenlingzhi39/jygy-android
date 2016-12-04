package com.endeavour.jygy.common.view;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by wu on 16/5/24.
 */
public class MyWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
        view.loadUrl("javascript:var imgs = document.getElementsByTagName('img');for(var i = 0; i<imgs.length; i++){imgs[i].style.width = '100%';imgs[i].style.height = 'auto';}");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }


}
