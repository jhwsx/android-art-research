package com.wzc.chapter_8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * https://blog.csdn.net/zhangweiwtmdbf/article/details/71172319
 * https://blog.csdn.net/qq_33275597/article/details/78429818
 * https://blog.csdn.net/zyjzyj2/article/details/53819964
 * @author wzc
 * @date 2018/10/20
 */
public class WindowActivity extends Activity implements View.OnTouchListener {

    private WindowManager mWindowManager;
    private Button mFloatingButton;
    private WindowManager.LayoutParams mLayoutParams;

    public static void start(Context context) {
        Intent starter = new Intent(context, WindowActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    protected void onDestroy() {
        try {
            // 删除
            mWindowManager.removeView(mFloatingButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void button(View view) {
        mFloatingButton = new Button(this);
        mFloatingButton.setText("click me");
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        // 如果不写下面这行，那么锁屏后竟然无法解锁了。
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL // 设置了这个标记，即便当前 Window 具有焦点，
                // 也允许将当前 Window 区域以外的单击事件传递给底层的 Window；否则，当前窗口将消费所有的点击事件，不管点击事件是否在窗口之内。
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // 表示 Window 不需要获取焦点，也不需要接收各种输入事件，
                // 此标记会同时启用 FLAG_NOT_TOUCH_MODAL，最终事件会直接传递给下层的具有焦点的 Window
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED; // 让 Window 显示在锁屏的界面上。
        // 1，声明 SYSTEM_ALERT_WINDOW 权限，但不指定下面这行，会报错：Caused by: android.view.WindowManager$InvalidDisplayException:
        // Unable to add window android.view.ViewRootImpl$W@dda8c68 -- the specified window type is not valid
        // 2, 不声明 SYSTEM_ALERT_WINDOW 权限，但指定下面这行，会报错: Caused by: android.view.WindowManager$BadTokenException:
        // Unable to add window android.view.ViewRootImpl$W@dda8c68 -- permission denied for this window type
        // 3，如果不声明 SYSTEM_ALERT_WINDOW 权限，也不指定下面这行也会报错：Caused by: android.view.WindowManager$InvalidDisplayException:
        // Unable to add window android.view.ViewRootImpl$W@dda8c68 -- the specified window type is not valid
        // 4，如果不声明 SYSTEM_ALERT_WINDOW 权限，下面这行指定为 TYPE_APPLICATION, 不会报错
        // 5，如果声明 SYSTEM_ALERT_WINDOW 权限，下面这行指定为 TYPE_APPLICATION, 不会报错
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        mFloatingButton.setOnTouchListener(this);
        // 添加
        mWindowManager.addView(mFloatingButton, mLayoutParams);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mLayoutParams.x = rawX;
                mLayoutParams.y = rawY;
                // 更新
                mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
                break;
            default:
                break;
        }
        return false;
    }
}
