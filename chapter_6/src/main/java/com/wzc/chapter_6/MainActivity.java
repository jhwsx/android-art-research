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
        Button btn_transitiondrawable = (Button) findViewById(R.id.btn_transitiondrawable);
        Button btn_insetdrawable = (Button) findViewById(R.id.btn_insetdrawable);
        Button btn_clipdrawable = (Button) findViewById(R.id.btn_clipdrawable);
        Button btn_scaledrawable = (Button) findViewById(R.id.btn_scaledrawable);
        Button btn_shapedrawable = (Button) findViewById(R.id.btn_shapedrawable);
        Button btn_circleimagedrawable = (Button) findViewById(R.id.btn_circleimagedrawable);
        Button btn_circleprogressdrawable = (Button) findViewById(R.id.btn_circleprogressdrawable);
        Button btn_circleprogressdrawable2 = (Button) findViewById(R.id.btn_circleprogressdrawable2);
        btnBitmapDrawable.setOnClickListener(this);
        btn_layerdrawable.setOnClickListener(this);
        btn_statelistdrawable.setOnClickListener(this);
        btn_levellistdrawable.setOnClickListener(this);
        btn_transitiondrawable.setOnClickListener(this);
        btn_insetdrawable.setOnClickListener(this);
        btn_clipdrawable.setOnClickListener(this);
        btn_scaledrawable.setOnClickListener(this);
        btn_shapedrawable.setOnClickListener(this);
        btn_circleimagedrawable.setOnClickListener(this);
        btn_circleprogressdrawable.setOnClickListener(this);
        btn_circleprogressdrawable2.setOnClickListener(this);
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
            case R.id.btn_transitiondrawable:
                TransitionDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_insetdrawable:
                InsetDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_clipdrawable:
                ClipDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_scaledrawable:
                ScaleDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_shapedrawable:
                ShapeDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_circleimagedrawable:
                CircleImageDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_circleprogressdrawable:
                CircleProgressDrawableActivity.start(MainActivity.this);
                break;
            case R.id.btn_circleprogressdrawable2:
                CircleProgressDrawable2Activity.start(MainActivity.this);
                break;
                default:
                    break;
        }
    }
}
