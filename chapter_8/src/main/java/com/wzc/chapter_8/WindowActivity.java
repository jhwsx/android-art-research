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
 * 问：
 * Caused by: android.view.WindowManager$BadTokenException:
 * Unable to add window android.view.ViewRootImpl$W@e24f6a1 -- permission denied for window type 2010
 * 答：
 * 需要权限  <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
 *
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
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        // 不加下面这行会报错：Caused by: android.view.WindowManager$InvalidDisplayException:
        // Unable to add window android.view.ViewRootImpl$W@93fb623 -- the specified window type 0 is not valid
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
                mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
                break;
            default:
                break;
        }
        return false;
    }
}
