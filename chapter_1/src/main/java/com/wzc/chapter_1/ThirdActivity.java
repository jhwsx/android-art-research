package com.wzc.chapter_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author wzc
 * @date 2018/11/11
 */
public class ThirdActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void openMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
