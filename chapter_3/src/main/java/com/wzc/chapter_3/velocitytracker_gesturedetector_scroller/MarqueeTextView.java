package com.wzc.chapter_3.velocitytracker_gesturedetector_scroller;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import com.wzc.chapter_3.R;

public class MarqueeTextView extends TextView {

    private Scroller scroller;
    /**
     * 起始滚动的位置
     */
    private int startX;
    /**
     * 是否正在滚动
     */
    private boolean scrolling;

    /**
     * 是否是第一次滚动
     */
    private boolean firstScroll = true;

    private final int scrollInterval;
    private final int scrollMode;
    private final int scrollFirstDelay;
    private final int scrollDirection;

    public static final int SCROLL_MODE_FOREVER = 0;
    public static final int SCROLL_MODE_ONESHOT = 1;

    public static final int SCROLL_DIRECTION_LEFT = 1;
    public static final int SCROLL_DIRECTION_RIGHT = -1;


    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MarqueeTextView);
        scrollInterval = ta.getInt(R.styleable.MarqueeTextView_scrollInterval, 1000);
        scrollMode = ta.getInt(R.styleable.MarqueeTextView_scrollMode, SCROLL_MODE_FOREVER);
        scrollFirstDelay = ta.getInt(R.styleable.MarqueeTextView_scrollFirstDelay, 0);
        scrollDirection = ta.getInt(R.styleable.MarqueeTextView_scrollDirection, SCROLL_DIRECTION_LEFT);
        ta.recycle();
        setSingleLine();
        setEllipsize(null);
    }

    /**
     * 开始滚动
     */
    public void startScroll() {
        startX = 0;
        scrolling = false;
        firstScroll = true;
        resumeScroll();
    }

    /**
     * 继续滚动
     */
    public void resumeScroll() {

        if (scrolling) return;

        setHorizontallyScrolling(true);

        if (scroller == null) {
            scroller = new Scroller(getContext(), new LinearInterpolator());
            setScroller(scroller);
        }

        if (firstScroll && scrollFirstDelay > 0) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    startScrollInternal();
                }
            }, scrollFirstDelay);
        } else {
            startScrollInternal();
        }
    }

    private void startScrollInternal() {
        int scrollingLen = calculateScrollingLen();
        int distance = scrollDirection == SCROLL_DIRECTION_LEFT ? scrollingLen - startX : getWidth() + startX;
        int duration = (int) (scrollInterval * distance * 1f / scrollingLen);
        scroller.startScroll(startX, 0, distance * scrollDirection, 0, duration);
        scrolling = true;
        invalidate();
    }

    /**
     * 暂停滚动
     */
    public void pauseScroll() {

        if (scroller == null) return;

        if (!scrolling) return;

        scrolling = false;

        startX = scroller.getCurrX();

        scroller.abortAnimation();
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {

        if (scroller == null) return;

        if (!scrolling) return;

        scrolling = false;

        scroller.startScroll(0, 0, 0, 0, 0);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller == null) {
            return;
        }
        if (scroller.isFinished() && scrolling) {
            if (scrollMode == SCROLL_MODE_ONESHOT) {
                stopScroll();
                return;
            }
            scrolling = false;
            startX = scrollDirection == SCROLL_DIRECTION_LEFT ? -getWidth() : calculateScrollingLen();
            firstScroll = false;
            resumeScroll();
        }
    }

    /**
     * 计算滚动的距离
     *
     * @return 滚动的距离
     */
    private int calculateScrollingLen() {
        TextPaint paint = getPaint();
        String text = getText().toString();
        Rect r = new Rect();
        paint.getTextBounds(text, 0, text.length(), r);
        return r.width();
    }
}
