package com.wzc.chapter_3;

import android.app.Application;

/**
 * @author wzc
 * @date 2018/7/6
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler.getInstance().init(this);
    }
}
