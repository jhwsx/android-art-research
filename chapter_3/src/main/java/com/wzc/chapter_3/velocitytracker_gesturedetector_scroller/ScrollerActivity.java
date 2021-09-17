package com.wzc.chapter_3.velocitytracker_gesturedetector_scroller;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import com.wzc.chapter_3.R;
// https://stackoverflow.com/questions/5495855/android-scroller-animation/6219382#6219382
public class ScrollerActivity extends Activity {

    private VelocityTracker velocityTracker;
    private float xVelocity;
    private float yVelocity;
    private View view;
    private Flinger flinger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        view = findViewById(R.id.ll);
        final MarqueeTextView marqueeTextView = (MarqueeTextView) findViewById(R.id.mtv);
        final MarqueeTextView2 marqueTextView2 = (MarqueeTextView2) findViewById(R.id.mtv2);
        marqueeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marqueeTextView.startScroll();
                marqueTextView2.startScroll();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain();
                } else {
                    velocityTracker.clear();
                }
                // 1, 把用户的动作添加给 VelocityTracker 对象
                velocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                // 把用户的动作添加给 VelocityTracker 对象
                velocityTracker.addMovement(event);
                // 2, 获取当前的滑动速度
                // 2.1, 获取速度之前必须先计算速度
                // 传入 1000，表示在 1000ms 内手指所滑过的像素数。
                velocityTracker.computeCurrentVelocity(1000);
                // 2.2 获取计算出来的速度
                xVelocity = velocityTracker.getXVelocity();
                yVelocity = velocityTracker.getYVelocity();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 3, 调用 clear 方法来重置并回收内存
                velocityTracker.recycle();
                velocityTracker = null;
                if (flinger == null) {
                    flinger = new Flinger(ScrollerActivity.this, view);
                }
                flinger.forceFinished();
                flinger.start((int) xVelocity, (int) yVelocity);
                xVelocity = 0;
                yVelocity = 0;
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

    static class Flinger implements Runnable {

        private static final String TAG = "Flinger";
        private final Scroller scroller;
        private final View target;

        public Flinger(Context context, View target) {
            scroller = new Scroller(context);
            this.target = target;
        }

        public void start(int velocityX, int velocityY) {
            int scrollX = target.getScrollX();
            int scrollY = target.getScrollY();
            Log.d(TAG, "start: scrollX=" + scrollX + ",scrollY=" + scrollY);
            //startX – Starting point of the scroll (X)
            //startY – Starting point of the scroll (Y)
            //velocityX – Initial velocity of the fling (X) measured in pixels per second.
            //velocityY – Initial velocity of the fling (Y) measured in pixels per second
            //minX – Minimum X value. The scroller will not scroll past this point.
            //maxX – Maximum X value. The scroller will not scroll past this point.
            //minY – Minimum Y value. The scroller will not scroll past this point.
            //maxY – Maximum Y value. The scroller will not scroll past this point.
            scroller.fling(scrollX, scrollY, -velocityX, -velocityY,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            target.post(this);
        }

        @Override
        public void run() {
            if (scroller.isFinished()) {
                Log.d(TAG, "run: scroller is finished, done with fling.");
                return;
            }
            if (scroller.computeScrollOffset()) {
                int currX = scroller.getCurrX();
                int currY = scroller.getCurrY();
                Log.d(TAG, "run: currX=" + currX + ",currY=" + currY);
                target.scrollTo(currX, currY);
                target.post(this);
            }
        }

        public void forceFinished() {
            if (!scroller.isFinished()) {
                scroller.forceFinished(true);
            }
        }
    }
}
