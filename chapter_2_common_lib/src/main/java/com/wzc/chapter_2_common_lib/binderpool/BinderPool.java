package com.wzc.chapter_2_common_lib.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * 单例模式
 * @author wzc
 * @date 2018/5/4
 */
public class BinderPool {
    private static final String TAG = BinderPool.class.getSimpleName();

    public static final int BINDER_NONE = -1;
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECURITY_CENTER = 1;
    private static volatile BinderPool sInstance;
    private Context mContext;
    private IBinderPool mBinderPool;
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connectBinderPoolService() {
        Log.d(TAG, "connectBinderPoolService: current thread = " + Thread.currentThread().getName());

        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.wzc.chapter_2.service", "com.wzc.chapter_2.service.binderpool.BinderPoolService"));
        mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        try {
            mConnectBinderPoolCountDownLatch.await(); // 等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 从 Binder 连接池中，根据 binderCode 获取对应的 Binder 对象
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        if (mBinderPool != null) {
            try {
                binder = mBinderPool.queryBinder(binderCode);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: current thread = " + Thread.currentThread().getName());
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                // 给 Binder 设置一个死亡代理
                mBinderPool.asBinder().linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBinderPool == null) {
                return;
            }
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService(); // 重新绑定远程服务
        }
    };

}
