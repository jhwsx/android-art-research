package com.wzc.chapter_10.idlehandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wzc.chapter_10.R;

// 参考：
// IdleHandler你会用吗？记一次IdleHandler使用误区，导致ANR
// https://juejin.cn/post/7041576680648867877
public class IdleHandlerActivity extends Activity {

    private static final String TAG = "IdleHandlerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idle_handler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: start");
        //1 //关键代码处 延迟 3s执行的delay msg
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "delay: msg");
            }
        }, 3000);
        //关键代码处 添加到IdleHandler里的三个任务
//        UIManager.run(() -> test(1));
//        UIManager.run(() -> test(2));
//        UIManager.run(() -> test(3));

        UIManager.addTask(() -> test(1));
        UIManager.addTask(() -> test(2));
        UIManager.addTask(() -> test(3));
        UIManager.runUiTasks();
    }

    //延迟任务
    private void test(final int i) {

        try {
            Log.e(TAG, "queueIdle:test start " + i);
            Thread.sleep(3000);
            Log.e(TAG, "queueIdle:test end " + i);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickMe(View view) {
        Log.d(TAG, "clickMe: btn clicked");
        Toast.makeText(this, "btn clicked", Toast.LENGTH_SHORT).show();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, IdleHandlerActivity.class);
        context.startActivity(starter);
    }
}
