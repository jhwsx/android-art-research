package com.wzc.chapter_2.bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.TextView;

import com.wzc.chapter_2.R;
import com.wzc.chapter_2.manager.UserManager;
import com.wzc.chapter_2.util.MyUtils;

/**
 * @author wzc
 * @date 2018/3/14
 */
public class ThirdActivity extends Activity {
    private static final String TAG = ThirdActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        TextView tv1 = (TextView) findViewById(R.id.tv);
        TextView tvProcessName = (TextView) findViewById(R.id.tv_process_name);

        MySingleton instance = MySingleton.getInstance();
        Log.d(TAG, "onCreate: MySingleton instance=" + instance);
        int result = instance.result;
        Log.d(TAG, "onCreate: 获取到的计算结果是 " + result);
//
        tv1.setText("获取到的计算结果是: " + result);
        Log.d(TAG, "onCreate: UserManager.class=" + UserManager.class.hashCode());

        String processName = MyUtils.getProcessName(getApplicationContext(), Process.myPid());
        tvProcessName.setText("processName: " + processName);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MySingleton.getInstance().result = 0;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ThirdActivity.class);
        context.startActivity(starter);
    }
}
