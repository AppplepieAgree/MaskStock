package com.appplepie.maskstock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MoreInfoWebView extends WebView {
    public MoreInfoWebView(Context context) {
        super(context);
        initDefaultSetting();
    }

    public MoreInfoWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultSetting();
    }

    public MoreInfoWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultSetting();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initDefaultSetting(){
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);

        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new InfoWebViewClient());
    }
}
