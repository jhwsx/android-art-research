package com.wzc.chapter_2.bundle;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * @author wzc
 * @date 2018/4/4
 */
public class MyIntentService extends IntentService {
    private static final String TAG = MyIntentService.class.getSimpleName();
    public MyIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 计算1到100的和
        try {
            int result = 0;
            for (int i = 1; i <= 100; i++) {
                result += i;
            }
            MySingleton.getInstance().result = result;
            Intent intent1 = new Intent(MyIntentService.this, ThirdActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        } catch (Exception e) {
            Log.e(TAG, "onHandleIntent: ", e);
        }
    }
}
