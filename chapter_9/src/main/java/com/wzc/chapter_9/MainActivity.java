package com.wzc.chapter_9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openWindow(View view) {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        View v = getLayoutInflater().inflate(R.layout.window_layout, null);
        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(v.getContext());
            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,
                0,0, PixelFormat.TRANSPARENT);
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        if (windowManager != null) {
            windowManager.addView(v, layoutParams);
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    public void openSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        MainActivity.this.startActivity(intent);
    }

    public void startService(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
    }

    public void stopService(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        stopService(intent);
    }
}
