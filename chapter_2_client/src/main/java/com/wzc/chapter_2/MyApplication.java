package com.wzc.chapter_2;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.wzc.chapter_2.util.MyUtils;

/**
 * @author wzc
 * @date 2018/3/14
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = MyUtils.getProcessName(getApplicationContext(), Process.myPid());

        Log.d(TAG, "application start, process name : " + processName);
    }
}
