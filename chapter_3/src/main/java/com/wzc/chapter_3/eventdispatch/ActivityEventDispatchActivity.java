package com.wzc.chapter_3.eventdispatch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wzc.chapter_3.R;

/**
 * Created by wzc on 2018/5/25.
 */

public class ActivityEventDispatchActivity extends Activity {
    public static final String TAG = "ActivityEventDispatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_event_dispatch);
        View decorView = getWindow().getDecorView();
        Log.d(TAG, "onCreate: decorView=" + decorView.toString());
        ViewGroup content = (ViewGroup) decorView.findViewById(android.R.id.content);
        View contentView = content.getChildAt(0);
        Log.d(TAG, "onCreate: contentView=" + contentView);
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
