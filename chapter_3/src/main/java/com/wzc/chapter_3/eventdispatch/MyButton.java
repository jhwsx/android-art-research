package com.wzc.chapter_3.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by wzc on 2018/5/26.
 */

public class MyButton extends Button {


    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.TAG, "MyButton:onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.TAG, "MyButton:onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.TAG, "MyButton:onTouchEvent: ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(EventDispatchActivity.TAG, "MyButton:onTouchEvent: ACTION_CANCEL");
                break;
            default:
                break;
        }
        Log.d(EventDispatchActivity.TAG, "MyButton:onTouchEvent: handled = " + handled);
        return handled;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(EventDispatchActivity.TAG, "MyButton:dispatchTouchEvent: start");
        boolean handled = super.dispatchTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(EventDispatchActivity.TAG, "MyButton:dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(EventDispatchActivity.TAG, "MyButton:dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(EventDispatchActivity.TAG, "MyButton:dispatchTouchEvent: ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(EventDispatchActivity.TAG, "MyButton:dispatchTouchEvent: ACTION_CANCEL");
                break;
            default:
                break;
        }
        Log.d(EventDispatchActivity.TAG, "MyButton:dispatchTouchEvent: handled = " + handled);
        return handled;
    }
}
