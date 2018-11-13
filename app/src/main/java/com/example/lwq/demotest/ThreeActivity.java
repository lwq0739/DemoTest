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
public class ThreeActivity extends Activity {
    private static Intent sIntent;

    public static void launch(Context context,Intent routeIntent) {
        sIntent = routeIntent;
        Intent intent = new Intent(context, ThreeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three);



    }

    public void returnTwo(View view){
        startActivity(ThreeActivity.sIntent);
    }
}
