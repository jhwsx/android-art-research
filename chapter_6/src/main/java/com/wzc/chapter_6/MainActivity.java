package com.wzc.chapter_6;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnBitmapDrawable = (Button) findViewById(R.id.btn_bitmapdrawable);
        Button btn_layerdrawable = (Button) findViewById(R.id.btn_layerdrawable);
        Button btn_statelistdrawable = (Button) findViewById(R.id.btn_statelistdrawable);
        Button btn_levellistdrawable = (Button) findViewById(R.id.btn_levellistdrawable);
        btnBitmapDrawable.setOnClickListener(this);
        btn_layerdrawable.setOnClickListener(this);
        btn_statelistdrawable.setOnClickListener(this);
        btn_levellistdrawable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bitmapdrawable:
                BitmapDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_layerdrawable:
                LayerDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_statelistdrawable:
                StateListDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_levellistdrawable:
                LevelListDrawableActivity.start(MainActivity.this);
                break;
                default:
                    break;
        }
    }
}
