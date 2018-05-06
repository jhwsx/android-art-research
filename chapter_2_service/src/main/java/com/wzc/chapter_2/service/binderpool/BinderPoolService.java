package com.wzc.chapter_2.service.binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.wzc.chapter_2_common_lib.binderpool.BinderPoolImpl;

/**
 * @author wzc
 * @date 2018/5/4
 */
public class BinderPoolService extends Service {
    private static final String TAG = BinderPoolService.class.getSimpleName();
    private Binder mBinderPool = new BinderPoolImpl();
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }
}
