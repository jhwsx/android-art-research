package com.wzc.chapter_1.activity36;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wzc.chapter_1.R;
import com.wzc.chapter_1.activity36.part01_lifecycle.LifecycleActivity;
import com.wzc.chapter_1.activity36.part02_launchmode.LaunchModeActivity;
import com.wzc.chapter_1.activity36.part03_data.DataActivity;

/**
 * @author wangzhichao
 * @since 2020/01/09
 */
public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        findViewById(R.id.btn_lifecycle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LifecycleActivity.start(HomeActivity.this);
            }
        });
        findViewById(R.id.btn_launchmode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchModeActivity.start(HomeActivity.this);
            }
        });
        findViewById(R.id.btn_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataActivity.start(HomeActivity.this);
            }
        });
    }
}
