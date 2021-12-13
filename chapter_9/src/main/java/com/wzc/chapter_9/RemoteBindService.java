package com.wzc.chapter_9;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

public class RemoteBindService extends Service {

    private static final String TAG = "RemoteBindService";

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: processName=" + MyUtils.getProcessName(this, Process.myPid()));
        return stub.asBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: processName=" + MyUtils.getProcessName(this, Process.myPid()));
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: processName=" + MyUtils.getProcessName(this, Process.myPid()));
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: processName=" + MyUtils.getProcessName(this, Process.myPid()));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: processName=" + MyUtils.getProcessName(this, Process.myPid()));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: processName=" + MyUtils.getProcessName(this, Process.myPid()));
    }

    private final ICompute stub = new ICompute.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    };
}
