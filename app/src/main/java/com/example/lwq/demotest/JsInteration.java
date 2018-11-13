package com.example.lwq.demotest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;

import static android.text.InputType.TYPE_CLASS_TEXT;

/**
 * @author lwq
 * @date 2018-11-10 16:01
 * introduction:
 */
public class JsInteration {
    public static final String NAME = "js";

    private Context mContext;

    public JsInteration(Context context) {
        mContext = context;
    }

    @JavascriptInterface
    public void showToast(String text){
        Toast
            .makeText(mContext,text,Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showWebActivity(String url){
        WebActivity.launch(mContext,url);
    }

    @JavascriptInterface
    public void showDialog(String title,String text){
        new MaterialDialog.Builder(mContext)
            .title(title)
            .content(text)
            .positiveText("确定")
            .show();
    }
}
