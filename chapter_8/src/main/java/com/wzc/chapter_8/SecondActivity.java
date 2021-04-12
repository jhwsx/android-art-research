package com.wzc.chapter_8;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author wangzhichao
 * @since 2021/4/12
 */
public class SecondActivity extends Activity {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        // 弹出一个 Dialog，然后关闭页面
        dialog = new Dialog(this);
        dialog.setTitle("Title");
        dialog.show();
        findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000L);
    }

    @Override
    protected void onDestroy() {
        // 打开这行不会抛异常
         dialog.dismiss();
        super.onDestroy();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SecondActivity.class);
        context.startActivity(starter);
    }
}
// 演示异常：
// 2021-04-25 22:00:32.172 20594-20594/com.wzc.chapter_8 E/WindowManager: android.view.WindowLeaked: Activity com.wzc.chapter_8.SecondActivity has leaked window DecorView@ead1dd0[Title] that was originally added here
//        at android.view.ViewRootImpl.<init>(ViewRootImpl.java:645)
//        at android.view.WindowManagerGlobal.addView(WindowManagerGlobal.java:377)
//        at android.view.WindowManagerImpl.addView(WindowManagerImpl.java:95)
//        at android.app.Dialog.show(Dialog.java:344)
//        at com.wzc.chapter_8.SecondActivity.onCreate(SecondActivity.java:25)
//        at android.app.Activity.performCreate(Activity.java:7893)
//        at android.app.Activity.performCreate(Activity.java:7880)
//        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1306)
//        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3310)
//        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3484)
//        at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:83)
//        at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
//        at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
//        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2068)
//        at android.os.Handler.dispatchMessage(Handler.java:107)
//        at android.os.Looper.loop(Looper.java:224)
//        at android.app.ActivityThread.main(ActivityThread.java:7551)
//        at java.lang.reflect.Method.invoke(Native Method)
//        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:539)
//        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:995)

// 抛出异常的位置：WindowManagerGlobal.closeAll() 方法
