package com.wzc.chapter_3.eventdispatch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wzc.chapter_3.R;

/**
 * Created by wzc on 2018/5/25.
 */

public class EventDispatchActivity extends Activity {
    public static final String TAG = EventDispatchActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);

//        View decorView = getWindow().getDecorView();
//        ViewGroup viewGroup = (ViewGroup) decorView.findViewById(android.R.id.content);
//        MyViewGroup myViewGroup = (MyViewGroup) viewGroup.getChildAt(0);
//        myViewGroup.setEnabled(false);
//        myViewGroup.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//        myViewGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(EventDispatchActivity.this, "onClick", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onClick: ");
//            }
//        });
        MyButton myButton = (MyButton) findViewById(R.id.myview);
        myButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "MyButton:onTouch: ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "MyButton:onTouch: ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "MyButton:onTouch: ACTION_UP");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: MyButton");
            }
        });

        myButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: MyButton");
                return false;
            }
        });
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            onUserInteraction();
//        }
//        if (getWindow().superDispatchTouchEvent(ev)) {
//            Log.d(TAG, "EventDispatchActivity: dispatchTouchEvent, getWindow().superDispatchTouchEvent(ev) = true");
//            return true;
//        }
//        Log.d(TAG, "EventDispatchActivity: dispatchTouchEvent, getWindow().superDispatchTouchEvent(ev) = false");
//        return onTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "EventDispatchActivity: onTouchEvent");
//        return super.onTouchEvent(event);
//    }
}
