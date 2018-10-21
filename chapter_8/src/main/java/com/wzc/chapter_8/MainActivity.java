package com.wzc.chapter_8;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void window(View view) {
        WindowActivity.start(this);
    }

    public void dialog(View view) {
        Dialog dialog = new Dialog(getApplicationContext());
        dialog.setTitle("Title");
        dialog.setContentView(R.layout.dialog_content);
        // 使用 getApplicationContext() 的上下文，不加下面这行，会报错：
        // Dialog: show mWindowManager.addView RuntimeException
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();
    }

    public void toast(View view) {
        Toast toast = new Toast(getApplicationContext());
        toast.setView(LayoutInflater.from(MainActivity.this).inflate(R.layout.toast_content, null));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
