package com.example.lwq.demotest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * @author lwq
 * @date 2018-10-31 16:18
 * introduction:
 */
public class WebActivity extends AppCompatActivity {

    private WebView mWebView;
    private String url;

    public static void launch(Context context,String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        url = getIntent().getStringExtra("url");
        setContentView(R.layout.activity_web);
        mWebView = findViewById(R.id.web);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.addJavascriptInterface(new JsInteration(this),JsInteration.NAME);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)){
                    setTitle(title);
                }
            }
        });
        mWebView.loadUrl(url);

    }
}
