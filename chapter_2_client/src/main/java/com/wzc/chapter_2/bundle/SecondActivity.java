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
 * @author wzc
 * @date 2018/3/14
 */
public class SecondActivity extends Activity {
    private static final String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView tv = (TextView) findViewById(R.id.tv);
        if (getIntent() != null) {
            String extra_data = getIntent().getStringExtra("extra_data");
            Log.d(TAG, "onCreate: extra_data = " + extra_data);
            tv.setText(extra_data);
        }
        TextView tvProcessName = (TextView) findViewById(R.id.tv_process_name);
        String processName = MyUtils.getProcessName(getApplicationContext(), Process.myPid());
        tvProcessName.setText("processName: " + processName);

        TextView tvUserId = (TextView) findViewById(R.id.tv_user_id);
        Log.d(TAG, "onCreate: UserManager.class=" + UserManager.class.hashCode());
        tvUserId.setText("UserManager.sUserId = " + UserManager.sUserId);

        MySingleton instance = MySingleton.getInstance();
        Log.d(TAG, "onCreate: MySingleton instance=" + instance);
        int result = instance.result;
        Log.d(TAG, "onCreate: 获取到的计算结果是 " + result);
        TextView tvResult = (TextView) findViewById(R.id.tv_result);
        tvResult.setText("获取到的计算结果是: " + result);

        findViewById(R.id.btn_third_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 0;
                for (int i = 1; i <= 100; i++) {
                    result += i;
                }
                MySingleton.getInstance().result = result;
                // 跨进程无法把计算好的数据传递过去
                Intent intent1 = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent1);
            }
        });

        findViewById(R.id.btn_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(SecondActivity.this, MyIntentService.class));
            }
        });
    }

}
