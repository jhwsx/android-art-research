package com.wzc.chapter_4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MyView mMyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyView = (MyView) findViewById(R.id.myview);

//        getSize();

//        mMyView.post(new Runnable() {
//            @Override
//            public void run() {
//                getSize();
//            }
//        });

//        final ViewTreeObserver observer = mMyView.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                getSize();
//                mMyView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            getSize();
//        }
//    }

    private void getSize() {
        int width = mMyView.getWidth();
        int height = mMyView.getHeight();
        int measuredWidth = mMyView.getMeasuredWidth();
        int measuredHeight = mMyView.getMeasuredHeight();
        Log.d(TAG, "width = " + width + ", height = " + height
                + ", measuredWidth = " + measuredWidth + ", measuredHeight = " + measuredHeight);
    }


    public void labelview(View view) {
        startActivity(new Intent(MainActivity.this, LabelViewActivity.class));
    }
}
