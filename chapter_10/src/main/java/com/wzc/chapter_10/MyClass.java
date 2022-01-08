package com.wzc.chapter_10;

import android.util.Log;
// https://blog.csdn.net/barryhappy/article/details/24442953/
public class MyClass {
    private static final String TAG = "MyClass";
    private static final Boolean FIND_POTENTIAL_LEAKS = false;

    public MyClass() {
        if (FIND_POTENTIAL_LEAKS) {
            Log.d(TAG, "MyClass: FIND_POTENTIAL_LEAKS = true");
        } else {
            Log.d(TAG, "MyClass: FIND_POTENTIAL_LEAKS = false");
        }
    }
}
