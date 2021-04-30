package com.wzc.chapter_8;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

public class ContextUtils {

    public static boolean checkContext(Context context) {
        if (null == context) {
            return false;
        } else if (context instanceof Activity) {
            Activity activity = (Activity)context;
            if (Build.VERSION.SDK_INT < 17) {
                return !activity.isFinishing();
            } else {
                return !activity.isDestroyed() && !activity.isFinishing();
            }
        } else {
            return true;
        }
    }

}