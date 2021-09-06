package com.wzc.chapter_3.viewslide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 这种方式先移动，屏幕灭屏再亮屏后，会恢复原位置。
 *
 * @author wzc
 * @date 2018/5/13
 */
public class DragView2 extends TextView {

    private static final String TAG = DragView2.class.getSimpleName();

    public DragView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int mLastX;
    private int mLastY;
    private int left;
    private int top;
    private int right;
    private int bottom;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow: ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: ");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = x;
                mLastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int dx = x - mLastX;
                int dy = y - mLastY;

                layout(
                        left = getLeft() + dx,
                        top = getTop() + dy,
                        right = getRight() + dx,
                        bottom = getBottom() + dy
                );

                // 真正改变了 View 的位置参数:left,right,top和bottom，不变的是translationX和translationY,
                // x和y也变化了
                Log.d(TAG, "onTouchEvent: getLeft() = " + getLeft()
                        + ", getTop() = " + getTop()
                        + ", getRight() = " + getRight()
                        + ", getBottom() = " + getBottom()
                        + ", getTranslationX() = " + getTranslationX()
                        + ", getTranslationY() = " + getTranslationY()
                        + ", getX() = " + getX()
                        + ", getY() = " + getY()
                );
                mLastX = x;
                mLastY = y;
                break;
            }
            default:

        }
        return true;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        Parcelable superState = bundle.getParcelable("superState");
        left = bundle.getInt("left");
        top = bundle.getInt("top");
        right = bundle.getInt("right");
        bottom = bundle.getInt("bottom");
        super.onRestoreInstanceState(superState);

        Log.d(TAG, "onRestoreInstanceState: left=" + left + ",top=" + top + ",right=" + right + ",bottom=" + bottom);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Log.d(TAG, "onSaveInstanceState: ");
        Bundle bundle = new Bundle();
        Parcelable superState = super.onSaveInstanceState();
        bundle.putParcelable("superState", superState);

        bundle.putInt("left", getLeft());
        bundle.putInt("top", getTop());
        bundle.putInt("right", getRight());
        bundle.putInt("bottom", getBottom());
        return bundle;
    }
}
//D/DragView2: onTouchEvent: getLeft() = 2, getTop() = 275, getRight() = 277, getBottom() = 550, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 2.0, getY() = 275.0
//D/DragView2: onTouchEvent: getLeft() = 5, getTop() = 275, getRight() = 280, getBottom() = 550, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 5.0, getY() = 275.0
//D/DragView2: onTouchEvent: getLeft() = 11, getTop() = 275, getRight() = 286, getBottom() = 550, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 11.0, getY() = 275.0
//D/DragView2: onTouchEvent: getLeft() = 15, getTop() = 275, getRight() = 290, getBottom() = 550, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 15.0, getY() = 275.0
//D/DragView2: onTouchEvent: getLeft() = 20, getTop() = 276, getRight() = 295, getBottom() = 551, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 20.0, getY() = 276.0
//D/DragView2: onTouchEvent: getLeft() = 26, getTop() = 277, getRight() = 301, getBottom() = 552, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 26.0, getY() = 277.0
//D/DragView2: onTouchEvent: getLeft() = 33, getTop() = 280, getRight() = 308, getBottom() = 555, getTranslationX() = 0.0, getTranslationY() = 0.0, getX() = 33.0, getY() = 280.0
