package com.wzc.chapter_12;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author wangzhichao
 * @since 2022/3/17
 */
public class PxUtils {
    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                Resources.getSystem().getDisplayMetrics());
    }
}
