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
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            onUserInteraction();
        }
        Log.d(TAG, "Activity: dispatchTouchEvent," + MotionEvent.actionToString(ev.getAction()) + ",getWindow().superDispatchTouchEvent(ev) called");
        if (getWindow().superDispatchTouchEvent(ev)) {
            Log.d(TAG, "Activity: dispatchTouchEvent," + MotionEvent.actionToString(ev.getAction()) + ",getWindow().superDispatchTouchEvent(ev)=true");
            return true;
        }
        Log.d(TAG, "Activity: dispatchTouchEvent," + MotionEvent.actionToString(ev.getAction()) + ",getWindow().superDispatchTouchEvent(ev)=false");
        return onTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = super.onTouchEvent(event);
        Log.d(TAG, "Activity: onTouchEvent: " + MotionEvent.actionToString(event.getAction()) + ",handled=" + handled);
        return handled;
    }
}
