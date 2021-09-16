package com.wzc.chapter_3.velocitytracker_gesturedetector_scroller;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.TextView;

import com.wzc.chapter_3.R;

/**
 * 参考：
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2012/1117/574.html
 * https://developer.android.google.cn/training/gestures/movement?hl=en#velocity
 *
 * @author wangzhichao
 * @since 2021/9/7
 */
public class VelocityTrackerActivity extends Activity {
    private VelocityTracker velocityTracker;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velocity_tracker);
        tv = (TextView) findViewById(R.id.tv_velocity_tracker);

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
                float xVelocity = velocityTracker.getXVelocity();
                float yVelocity = velocityTracker.getYVelocity();
                tv.setText("xVelocity:"+xVelocity+",\nyVelocity:"+yVelocity);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 3, 调用 clear 方法来重置并回收内存
                velocityTracker.recycle();
                velocityTracker = null;
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }
}
