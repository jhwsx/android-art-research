package com.wzc.chapter_2.binderpool;

import android.app.Activity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.wzc.chapter_2.R;
import com.wzc.chapter_2_common_lib.binderpool.ICompute;
import com.wzc.chapter_2_common_lib.binderpool.ISecurityCenter;

/**
 * @author wzc
 * @date 2018/5/4
 */
public class BinderPoolActivity extends Activity {
    private static final String TAG = BinderPoolActivity.class.getSimpleName();
    private ISecurityCenter mSecurityCenter;
    private ICompute mCompute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binderpool);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doWork();
                    }
                }).start();
            }
        });

    }

    private void doWork() {
        Log.d(TAG, "doWork: current thread = " + Thread.currentThread().getName());
        BinderPool binderPool = BinderPool.getInstance(BinderPoolActivity.this);

        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        mSecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.d(TAG, "visit ISecurityCenter");

        String msg = "helloworld-安卓";
        System.out.println("content:" + msg);

        try {
            String encrypt = mSecurityCenter.encrypt(msg);
            Log.d(TAG, "encrypt：" + encrypt);
            String decrypt = mSecurityCenter.decrypt(encrypt);
            Log.d(TAG, "decrypt: " + decrypt);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        mCompute = ComputeImpl.asInterface(computeBinder);
        Log.d(TAG, "visti ICompute");
        try {
            Log.d(TAG, "3 + 5 = " + mCompute.add(3, 5));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
