package com.rozkhabardar.newspaperportral.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;

import io.fabric.sdk.android.Fabric;

public class WebActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    WebView browser;
    String title,url;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_web);
        refreshLayout.setOnRefreshListener(this);
        try {
            Intent intent= getIntent();
            url   = intent.getStringExtra("url");
            title   = intent.getStringExtra("title");
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        upload();
        setuptoolbar();
    }

    private void upload() {
        try {
            if(Utility.checknetwork(getApplicationContext())) {
                if (url != null && !url.isEmpty()) {
                    browser = (WebView) findViewById(R.id.wv_see);
                    WebSettings webSettings = browser.getSettings();
                    webSettings.setLoadsImagesAutomatically(true);
                    webSettings.setSupportZoom(true);
                    webSettings.setBuiltInZoomControls(true);
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setUseWideViewPort(true);
                    webSettings.setJavaScriptEnabled(true);
                    browser.setWebViewClient(new mwebViewClient());
                    browser.setWebChromeClient(new WebChromeClient());
                    browser.getSettings().setJavaScriptEnabled(true);
                    browser.loadUrl(url);
                }
            }
            else
            {
                Utility.message(getApplicationContext(),"No Internet Connection");
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    private void setuptoolbar() {
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_webview);
        ImageView imageView= (ImageView) toolbar.findViewById(R.id.btnCloseBrowser);
        TextView textView= (TextView) toolbar.findViewById(R.id.tvTitle);
        if(title!=null && !title.isEmpty())
        {
            textView.setText(title);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onRefresh() {
        upload();
        refreshLayout.setRefreshing(false);

    }

    private class mwebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Utility.hidepopup();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Utility.showpop(WebActivity.this);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().getEncodedPath());
            }
            return true;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK) && browser.canGoBack())
        {
            try {
                browser.goBack();
            } catch (Exception e) {
                Crashlytics.logException(e);
            }
            return true;
        }
        else
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
