package com.wzc.chapter_3.eventdispatch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.wzc.chapter_3.R;

/**
 * View 事件分发机制
 * 参考：
 * Android View 事件分发机制 源码解析 （上）- 鸿洋
 * https://blog.csdn.net/lmj623565791/article/details/38960443
 */
public class ViewEventDispatchActivity extends Activity {
    private static final String TAG = "OurButton";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_dispatch);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "onTouch: ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "onTouch: ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "onTouch: ACTION_UP");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
}
