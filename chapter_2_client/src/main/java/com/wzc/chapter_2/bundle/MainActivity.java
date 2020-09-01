package com.wzc.chapter_2.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wzc.chapter_2.R;
import com.wzc.chapter_2.manager.UserManager;
import com.wzc.chapter_2.util.MyUtils;

/**
 * 多进程下：
 * 静态成员和单例模式完全失效（不是同一块内存，会产生不同的副本）
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: taskId = "+getTaskId());
        TextView tv = (TextView) findViewById(R.id.tv);
        String processName = MyUtils.getProcessName(getApplicationContext(), Process.myPid());
        tv.setText("processName: " + processName);
        UserManager.sUserId = 2;
        Log.d(TAG, "onCreate: UserManager.class=" + UserManager.class.hashCode());
        TextView tvUserId = (TextView) findViewById(R.id.tv_user_id);
        tvUserId.setText("UserManager.sUserId = " + UserManager.sUserId);
    }

    public void secondActivity(View view) {
        int result = 0;
        for (int i = 1; i <= 10; i++) {
            result += i;
        }
        MySingleton instance = MySingleton.getInstance();
        Log.d(TAG, "onCreate: MySingleton instance=" + instance);
        instance.result = result;
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("extra_data", "this is data from MainActivity");
        startActivity(intent);
    }
}
