package com.wzc.chapter_1.activity36.part02_launchmode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_1.R;

/**
 * @author wangzhichao
 * @since 2020/01/10
 */
public class LaunchModeActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, LaunchModeActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launchmode_activity);
        findViewById(R.id.btn_standard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StandardActivity.start(LaunchModeActivity.this);
            }
        });
        findViewById(R.id.btn_singletop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTopActivity.start(LaunchModeActivity.this);
            }
        });
        findViewById(R.id.btn_singletask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleTaskActivity.start(LaunchModeActivity.this);
            }
        });
        findViewById(R.id.btn_singleinstance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleInstanceActivity.start(LaunchModeActivity.this);
            }
        });
    }
}
