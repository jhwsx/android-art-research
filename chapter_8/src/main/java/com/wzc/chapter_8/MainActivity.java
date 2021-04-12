package com.wzc.chapter_8;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
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
        // 使用 getApplicationContext() 的上下文，不加下面这行，会报错
        // Motorola 8.0 不加报错；加了不报错
//        Caused by: android.view.WindowManager$BadTokenException: Unable to add window -- token null is not valid; is your activity running?
//        at android.view.ViewRootImpl.setView(ViewRootImpl.java:789)
//        at android.view.WindowManagerGlobal.addView(WindowManagerGlobal.java:356)
//        at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:92)
//        at android.app.Dialog.show(Dialog.java:330)
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        // Redme 9 不加报错 和 Motorola 一样；加了不报错，不过也弹不出来, 需要手动打开悬浮窗权限才可以。
        // Honor6 6.0 不加报错；加了不报错
        // Dialog: show mWindowManager.addView RuntimeException
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();
    }

    public void toast(View view) {
//        for (int i = 0; i < 100; i++) {
            Toast toast = new Toast(getApplicationContext());
            View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.toast_content, null);
            TextView textView = (TextView) inflate.findViewById(R.id.tv);
            textView.setText("This is a toast " + 0);
            toast.setView(inflate);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
//        }

    }

    public void popupwindow(View view) {
        final PopupWindow popupWindow = new PopupWindow(this);
        View v = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
        v.findViewById(R.id.btn_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏 用的是 mWindowManager.removeViewImmediate(mPopupView);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(v);
        // 显示
        popupWindow.showAsDropDown(view);
    }

    public void popupmenu(View view) {
        final PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popupMenu.show();
    }

    public void goSecondActivity(View view) {
        SecondActivity.start(this);
    }
}
