package com.wzc.chapter_1.activity36.part01_lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_1.R;

/**
 * @author wangzhichao
 * @since 2020/01/09
 */
public class LifecycleActivity extends Activity {
    public static void start(Context context) {
        Intent starter = new Intent(context, LifecycleActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lifecycle_activity);
        findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogActivity.start(LifecycleActivity.this);
            }
        });
        findViewById(R.id.btn_configchanges).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigChangeActivity.start(LifecycleActivity.this);
            }
        });
    }
}
