package com.wzc.chapter_9;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

public class App extends Application {
    private static final String TAG = "App";
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        String processName = MyUtils.getProcessName(getApplicationContext(), Process.myPid());

        Log.d(TAG, "application start, process name : " + processName + ", process id : " + Process.myPid() + ", uid = " + Process.myUid());
    }
    public static Context getContext() {
        return context;
    }
}
