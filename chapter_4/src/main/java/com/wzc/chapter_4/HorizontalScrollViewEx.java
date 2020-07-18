package com.wzc.chapter_4;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 假设所有子元素的宽和高都是一样的.
 *
 * @author wzc
 * @date 2018/8/11
 */
public class HorizontalScrollViewEx extends ViewGroup {
    private static final String TAG = HorizontalScrollViewEx.class.getSimpleName();
    private int mChildWidth;
    private VelocityTracker velocityTracker;
    private Scroller scroller;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        velocityTracker = VelocityTracker.obtain();
        scroller = new Scroller(getContext());
    }

    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        int childCount = getChildCount();
        // 测量每个子 View, 这样子 View 的 getMeasuredWidth() 和 getMeasuredHeight() 才会有值
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 取出 measureSpec 中保存的值, 代表了父容器对子 View 的要求
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (childCount == 0) {
            // 没有任何子元素
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST
                && heightMode == MeasureSpec.AT_MOST) {
            // 宽和高都采用了 wrap_content
            View child = getChildAt(0);
            final int childMeasuredWidth = child.getMeasuredWidth();
            final int childMeasuredHeight = child.getMeasuredHeight();
            measuredWidth = childMeasuredWidth * childCount;
            measuredHeight = childMeasuredHeight;
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            // 宽采用了 wrap_content, 宽度就是所有子元素的宽度之和
            View child = getChildAt(0);
            final int childMeasuredWidth = child.getMeasuredWidth();
            measuredWidth = childMeasuredWidth * childCount;
            measuredHeight = heightSize;
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            // 高采用了 wrap_content, 高度就是第一个子元素的高度
            View child = getChildAt(0);
            final int childMeasuredHeight = child.getMeasuredHeight();
            measuredWidth = widthSize;
            measuredHeight = childMeasuredHeight;
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    private int mChildrenSize;

    // 布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        int childLeft = 0;
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                mChildWidth = childView.getMeasuredWidth();
                childView.layout(childLeft, 0, childLeft + mChildWidth,
                        childView.getMeasuredHeight());
                childLeft += mChildWidth;
            }
        }
    }

    // 交互
    // 可以左右滑动
    private int lastX;
    private int lastY;
    /**
     * 当前显示的子元素的索引
     */
    private int currChildIndex;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = lastX - x;
                int deltaY = lastY - y;
                Log.d(TAG, "onTouchEvent: ACTION_MOVE, deltaX=" + deltaX + ",deltaY=" + deltaY);
                // deltaX 大于 0, 则 View 的内容向左滑动
                scrollBy(deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                // 进行页面控制, 只能停在某一个子元素上
                // 设置时间间隔: 设置为 1000ms
                velocityTracker.computeCurrentVelocity(1000);
                int targetChildIndex;
                // 获取速度: 就是在上一步代码中设置的时间间隔内滑动的像素数目.
                int xVelocity = (int) velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50) {
                    // 如果速度大于 50, 就滑动到下一个页面或上一个页面
                    targetChildIndex = xVelocity > 0 ? currChildIndex - 1 : currChildIndex + 1;
                } else {
                    // 如果速度小于 50,
                    // 加上 mChildWidth / 2 ,是为了处理当前页面已经滑过去了大于一半的宽度, UP 后就应该滑动到下一个页面的情况.
                    targetChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                // 进行边界控制:
                // 向左滑动: View 内容的左边缘位于 View 左边缘的左边, 距离最多只能是 (子 View 个数 - 1)) 个子 View 的宽度
                // 向右滑动: View 内容的左边缘最多是和 View 左边缘重合
                targetChildIndex = Math.max(0, Math.min(targetChildIndex, mChildrenSize - 1));
                int dx = targetChildIndex * mChildWidth - scrollX;
                // dx 大于 0, 则 View 的内容向左滑动
                // scrollBy(dx, 0);
                smoothScrollBy(dx); // 增加弹性滑动,使用 Scroller 来实现
                velocityTracker.clear();
                currChildIndex = targetChildIndex;
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

    private void smoothScrollBy(int dx) {
        scroller.startScroll(getScrollX(), 0, dx, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    // 解决滑动冲突
    // 使用外部拦截法
    private int lastInterceptedX;
    private int lastInterceptedY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean intercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = lastInterceptedX - x;
                int deltaY = lastInterceptedY - y;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // 是左右滑动, 父 View 需要, 进行拦截
                    intercepted = true;
                } else {
                    // 是上下滑动, 子 View 需要, 不进行拦截
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        lastInterceptedX = x;
        lastInterceptedY = y;
        // 这个代码必须有
        lastX = x;
        lastY = y;
        return intercepted;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        velocityTracker.recycle();
    }
}
