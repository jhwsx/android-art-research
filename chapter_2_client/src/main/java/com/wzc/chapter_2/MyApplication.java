package com.wzc.chapter_2;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.wzc.chapter_2.util.MyUtils;

/**
 * 多进程下，Application 类会多次创建
 * @author wzc
 * @date 2018/3/14
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        String processName = MyUtils.getProcessName(getApplicationContext(), Process.myPid());

        Log.d(TAG, "application start, process name : " + processName + ", process id : " + Process.myPid());
    }

    public static Context getContext() {
        return context;
    }
}
