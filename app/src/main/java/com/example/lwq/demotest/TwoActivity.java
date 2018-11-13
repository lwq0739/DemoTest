package com.example.lwq.demotest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * @author lwq
 * @date 2018-10-22 14:03
 * introduction:
 */
public class TwoActivity extends Activity {

    public static void launch(Context context,int num,String s) {
        Intent intent = new Intent(context, TwoActivity.class);
        intent.putExtra("two_num",num);
        intent.putExtra("two_string",s);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        TextView tv = findViewById(R.id.tv);
        int two_num = getIntent().getIntExtra("two_num",0);
        String two_string = getIntent().getStringExtra("two_string");
        tv.setText(two_num+"  "+two_string);

    }

    public void startThree(View view) {
        ThreeActivity.launch(this,getIntent());
    }
}
