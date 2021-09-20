package com.wzc.chapter_3.slideconflict.type2.external;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wzc.chapter_3.R;


public class StickyLayout extends LinearLayout {

    private static final String TAG = "StickyLayout";
    private View header;
    private View content;
    private int originalHeaderHeight;
    private int currHeaderHeight;
    private int touchSlop;
    private boolean initDataSuccess;
    private int status = STATUS_EXPANDED;
    private static final int STATUS_EXPANDED = 1;
    private static final int STATUS_COLLAPSED = 2;
    private static final  int STATUS_MIDDLE = 3;

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        int headerId = getResources().getIdentifier("sticky_header", "id", getContext().getPackageName());
//        int contentId = getResources().getIdentifier("sticky_content", "id", getContext().getPackageName());
//        if (headerId != 0 && contentId != 0) {
//            header = findViewById(headerId);
//            content = findViewById(contentId);
    // TODO 这里获取的为什么是 0 ？
//            originalHeaderHeight = header.getHeight();
//            currHeaderHeight = originalHeaderHeight;
//            touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
//            if (currHeaderHeight > 0) {
//                initDataSuccess = true;
//            }
//        } else {
//            throw new IllegalArgumentException("没有在布局中配置 sticky_header 或 sticky_content");
//        }
//    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && header == null) {
            int headerId = getResources().getIdentifier("sticky_header", "id", getContext().getPackageName());
            int contentId = getResources().getIdentifier("sticky_content", "id", getContext().getPackageName());
            if (headerId != 0 && contentId != 0) {
                header = findViewById(headerId);
                content = findViewById(contentId);
                originalHeaderHeight = header.getHeight();
                currHeaderHeight = originalHeaderHeight;
                touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (currHeaderHeight > 0) {
                    initDataSuccess = true;
                }
            } else {
                throw new IllegalArgumentException("没有在布局中配置 sticky_header 或 sticky_content");
            }
        }

    }

    private int lastInterceptX;
    private int lastInterceptY;
    private int lastX;
    private int lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - lastInterceptX;
                int dy = y - lastInterceptY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    Log.d(TAG, "onInterceptTouchEvent: 当竖直滑动距离差小于水平滑动距离差，StickyLayout 不拦截事件");
                    // 当竖直滑动距离差小于水平滑动距离差，StickyLayout 不拦截事件
                    intercepted = false;
                } else if (status == STATUS_MIDDLE) {
                    Log.d(TAG, "onInterceptTouchEvent: 当 Header 时中间状态，StickyLayout 拦截事件");
                    intercepted = true;
                } else if (status == STATUS_EXPANDED ) {
                    Log.d(TAG, "onInterceptTouchEvent: 当 Header 时展开状态");
                    if (dy < 0) {
                        Log.d(TAG, "onInterceptTouchEvent:      当前手势是向上滑动，StickyLayout 拦截事件");
                        intercepted = true;
                    } else {
                        intercepted = false;
                    }
                    Log.d(TAG, "onInterceptTouchEvent:      当前手势是向上滑动，tickyLayout 不拦截事件");
                } else if (status == STATUS_COLLAPSED) {
                    Log.d(TAG, "onInterceptTouchEvent: Header 隐藏");
                    if (dy > 0) {
                        Log.d(TAG, "onInterceptTouchEvent:      当前手势是向下滑动时，StickyLayout 拦截事件；");
                        intercepted = true;
                    } else {
                        Log.d(TAG, "onInterceptTouchEvent:      当前手势是向上滑动时，StickyLayout 不拦截事件；");
                        intercepted = false;
                    }
                } else {
                    Log.d(TAG, "onInterceptTouchEvent: else");
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        lastX = x;
        lastY = y;
        lastInterceptX = x;
        lastInterceptY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int currHeaderHeight = getCurrHeaderHeight();
                int dy = y - lastY;
                setCurrHeaderHeight(currHeaderHeight + dy);
                break;
            case MotionEvent.ACTION_UP:
                final int destHeaderHeight;
                final int ch = getCurrHeaderHeight();
                if (ch < originalHeaderHeight /2) {
                    destHeaderHeight = 0;
                } else {
                    destHeaderHeight = originalHeaderHeight;
                }
                ValueAnimator va = ValueAnimator.ofFloat(0, 1);
                va.setDuration(200);
                va.setInterpolator(new AccelerateInterpolator());
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        int newCurrHeaderHeight = (int) (ch + (destHeaderHeight - ch) * value);
                        setCurrHeaderHeight(newCurrHeaderHeight);
                    }
                });
                va.start();
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

    public int getCurrHeaderHeight() {
        return currHeaderHeight;
    }

    public void setCurrHeaderHeight(int height) {
        currHeaderHeight = Math.min(Math.max(0, height), originalHeaderHeight);
        if (currHeaderHeight == 0) {
            status = STATUS_COLLAPSED;
        } else if (currHeaderHeight == originalHeaderHeight){
            status = STATUS_EXPANDED;
        } else {
            status = STATUS_MIDDLE;
        }
        header.getLayoutParams().height = currHeaderHeight;
        header.requestLayout();
    }
}
