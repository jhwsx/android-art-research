package com.wzc.chapter_3.slideconflict_practice1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.wzc.chapter_3.util.Utils;

/**
 * @author wzc
 * @date 2018/7/5
 */
public class HorizontalScrollViewExPractice extends ViewGroup {
    private static final String TAG = HorizontalScrollViewExPractice.class.getSimpleName();
    private int mScreenWidth;
    private VelocityTracker mVelocityTracker;
    private int mChildCount;
    private Scroller mScroller;

    public HorizontalScrollViewExPractice(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewExPractice(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewExPractice(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mVelocityTracker = VelocityTracker.obtain();
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        int width = resolveSize(measuredWidth, widthMeasureSpec);
        int height = resolveSize(measuredHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mChildCount = getChildCount();
        int childLeft = 0;
        mScreenWidth = Utils.getScreenMetrics(getContext()).widthPixels;
        int screenHeight = Utils.getScreenMetrics(getContext()).heightPixels;
        for (int i = 0; i < mChildCount; i++) {
            View view = getChildAt(i);
            if (view.getVisibility() != View.GONE) {
                view.layout(childLeft, 0, childLeft + mScreenWidth, screenHeight);
                childLeft += mScreenWidth;
            }
        }
    }

    // record last slide coordinate
    private int mLastX;
    private int mLastY;
    // record last slide coordinate (onInterceptTouchEvent)
    private int mLastInterceptedX;
    private int mLastInterceptedY;

    // 重写父容器的 onInterceptTouchEvent() 方法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                // 加入滑动没有结束时, 就把滑动终止掉, 这样的好处是当滑动正在进行时, 这时下次点击事件过来时, 就可以马上
                // 终止本次滑动, 开始下次事件的处理; 如果不这样处理的话, 那么当滑动正在进行时, 下次点击事件过来时, 比如
                // 采取横向滑动, 那么就要等滑动动画结束后, 才能横向滑动, 这种体验是不好的.
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    // 这句代码是为了处理一种情况(这种情况这是由于终止动画带来的), 如果此时用户正在水平滑动, 但是在水平滑动停止之前如果用户再迅速进行垂直
                    // 滑动, 就会导致界面在水平方向上无法滑动到终点(因为动画被终止了)从而处于一种中间状态.
                    // 为了避免这种不好的体验, 当水平方向正在滑动时, 下一个序列的点击事件仍然交给父容器, 这样水平方向就不会
                    // 停留在中间状态了.
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastInterceptedX;
                int deltaY = y - mLastInterceptedY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // 当横向滑动距离大于纵向滑动距离时, 父 ViewGroup 进行拦截
                    intercepted = true;
                } else {
                    // 否则, 不拦截
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
        }
        mLastInterceptedX = x;
        mLastInterceptedY = y;
        // 这是为了避免丢失起始点
        mLastX = x;
        mLastY = y;
        Log.d(TAG, "onInterceptTouchEvent: intercepted = " + intercepted + ",mLastInterceptedX="
                + mLastInterceptedX + ",mLastInterceptedY=" + mLastInterceptedY);
        return intercepted;
    }

    private int mCurrIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 如果写在这里, 是不会执行的, 因为 down 事件没有拦截, 是由子 View 来接收的
//                mLastX = x;
//                mLastY = y;
                Log.d(TAG, "onTouchEvent: ACTION_DOWN mLastX = " + mLastX + ", mLastY = " + mLastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                int destIndex;
                if (Math.abs(xVelocity) > 50) {
                    Log.d(TAG, "onTouchEvent: xVelocity > 50");
                    destIndex = xVelocity > 0 ? mCurrIndex - 1 : mCurrIndex + 1;
                } else {
                    Log.d(TAG, "onTouchEvent: xVelocity < 50");
                    destIndex = (int) ((scrollX + 0.5f * mScreenWidth) / mScreenWidth);
                }
                // 修正越界的情况
                destIndex = Math.min(Math.max(0, destIndex), mChildCount - 1);
                Log.d(TAG, "onTouchEvent: scrollX = " + scrollX + ", destIndex = " + destIndex);
//                scrollTo(destIndex * mScreenWidth, 0); // 向左移动, 应该为正
                smoothScrollBy(destIndex * mScreenWidth);
                // 如果不标记为当前索引, 那么按速率决定的索引就会错误
                mCurrIndex = destIndex;
                mVelocityTracker.clear();
                break;
            default:
        }
        return true;
    }

    private void smoothScrollBy(int destX) {
        int startX = getScrollX();
        int deltaX = destX - startX;
        // 参三: 正值向左
        mScroller.startScroll(getScrollX(), 0, deltaX, 0,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVelocityTracker.recycle();
    }
}
