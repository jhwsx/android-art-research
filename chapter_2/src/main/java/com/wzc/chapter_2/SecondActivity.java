package com.wzc.chapter_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author wzc
 * @date 2018/3/14
 */
public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void thirdActivity(View view) {
        startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
    }
}
