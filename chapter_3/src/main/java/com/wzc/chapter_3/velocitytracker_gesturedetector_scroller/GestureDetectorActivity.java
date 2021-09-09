package com.wzc.chapter_3.velocitytracker_gesturedetector_scroller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.wzc.chapter_3.R;

public class GestureDetectorActivity extends Activity
        implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private GestureDetector gestureDetector;

    private static final String TAG = "GestureDetector";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_detector);
        gestureDetector = new GestureDetector(this, this);
        gestureDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    // 手指轻轻触摸屏幕的一瞬间回调，由一个 ACTION_DOWN 事件触发
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown: " + MotionEvent.actionToString(e.getAction()));
        return false;
    }
    // 手指轻轻触摸屏幕，还未移动或抬起，由一个 ACTION_DOWN 事件触发
    // 作用：通常用于给用户提供视觉反馈，以便用户知道他们的动作已经被识别了，例如高亮一个元素。
    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress: " + MotionEvent.actionToString(e.getAction()));
    }

    // 手指（轻轻触摸屏幕后）松开，伴随着一个 ACTION_UP 事件而触发，这是单击行为
    // 注意：
    // 如果用户手指触摸屏幕一段事件再松开，不会回调此方法
    // 如果用户手指触摸屏幕后发生滑动后，不会回调此方法
    // 用户快速双击屏幕，第一次会回调此方法

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp: " + MotionEvent.actionToString(e.getAction()));
        return false;
    }

    // 手指按下屏幕并拖动，由一个 ACTION_DOWN, 以及多个 ACTION_MOVE 触发，这是拖动行为
    // e1 是 ACTION_DOWN 事件，触发滑动的第一个 ACTION_DOWN 事件
    // e2 是 ACTION_MOVE 事件，触发当前 onScroll 的 move 事件
    // distanceX，distanceY 是当前MOVE事件和上一个MOVE事件的位移量，向右下滑动为负，向左上滑动为正。
    // 会多次回调
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll: e1=" + MotionEvent.actionToString(e1.getAction()) + ",e2=" + MotionEvent.actionToString(e2.getAction()) + ",distanceX=" + distanceX + ",distanceY=" + distanceY);
        return false;
    }

    // 用户长按后触发
    // 此方法回调后，不会再触发其他回调
    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress: " + MotionEvent.actionToString(e.getAction()));
    }

    // 用户按下屏幕，快速滑动后松开，由一个 ACTION_DOWN，多个 ACTION_MOVE 和一个 ACTION_UP 触发，这是快速滑动行为。
    // e1: 是 ACTION_DOWN 事件，触发快速滑动的第一个 ACTION_DOWN 事件
    // e2: 是 ACTION_UP 事件，触发当前 onFling 的 up 事件
    // velocityX,velocityY 松开一瞬间的速度。向右下快速滑动为正，向左上快速滑动为负。
    // 只会回调一次
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: e1=" + MotionEvent.actionToString(e1.getAction()) + ",e2=" + MotionEvent.actionToString(e2.getAction()) + ",velocityX=" + velocityX + ",velocityY=" + velocityY);
        return false;
    }

    // 当确认用户的第一次点击不会跟着导致双击手势的第二次点击时回调
    // 即用户的点击不会成为双击手势的点击时回调。
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed: " + MotionEvent.actionToString(e.getAction()));
        return false;
    }

    // 当确认是双击事件时回调
    // e 是 双击的第一次点击的down事件
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: " + MotionEvent.actionToString(e.getAction()));
        return false;
    }
    // 在双击期间，发生的 down，move，up 事件都会回调此方法
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent: " + MotionEvent.actionToString(e.getAction()));
        return false;
    }
}
