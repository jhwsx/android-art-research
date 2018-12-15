package com.wzc.chapter_3.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by wzc on 2018/5/26.
 */

public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context) {
        this(context,null);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        requestDisallowInterceptTouchEvent(true);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:dispatchTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onInterceptTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.TAG, "MyLinearLayout:onTouchEvent: ACTION_UP");
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.d(EventDispatchActivity.TAG, "MyLinearLayout:requestDisallowInterceptTouchEvent: " +
                "disallowIntercept="+disallowIntercept);
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }
}
