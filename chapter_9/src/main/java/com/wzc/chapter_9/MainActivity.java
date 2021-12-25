package com.wzc.chapter_9;

import static com.wzc.chapter_9.MyDynamicReceiver.ACTION_LAUNCH_DYNAMIC;
import static com.wzc.chapter_9.MyStaticReceiver.ACTION_LAUNCH_STATIC;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.UserDictionary;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.os.Process;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserHandle userHandle = Process.myUserHandle();
        Intent intent = new Intent();
        intent.setAction(ACTION_LAUNCH_DYNAMIC);
        String resolvedType = intent.resolveTypeIfNeeded(getContentResolver());
        Uri uri = ContentUris.withAppendedId(UserDictionary.Words.CONTENT_URI, 4);
        uri = ContentUris.appendId(new Uri.Builder().scheme("content").authority(UserDictionary.AUTHORITY).path("words"), 4).build();
        long id = ContentUris.parseId(uri);
        Log.d(TAG, "onCreate: uri = " + uri + ", id = " + id);
    }

    public void openWindow(View view) {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        View v = getLayoutInflater().inflate(R.layout.window_layout, null);
        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.start(v.getContext());
            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.TRANSPARENT);
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        if (windowManager != null) {
            windowManager.addView(v, layoutParams);
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    public void openSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        MainActivity.this.startActivity(intent);
    }

    public void startService(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);
    }

    public void stopService(View view) {
        Intent intent = new Intent(MainActivity.this, MyService.class);
        stopService(intent);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            ICompute iCompute = ICompute.Stub.asInterface(service);
            try {
                int result = iCompute.add(1, 1);
                Log.d(TAG, "onServiceConnected: result=" + result + ", processName=" + MyUtils.getProcessName(MainActivity.this, Process.myPid()) + ", threadName=" + Thread.currentThread().getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    public void bindService(View view) {
        Intent intent = new Intent(MainActivity.this, RemoteBindService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        unbindService(serviceConnection);
    }

    private MyDynamicReceiver receiver;
    public void registerReceiver(View view) {
        if (null == receiver) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION_LAUNCH_DYNAMIC);
            receiver = new MyDynamicReceiver();
            registerReceiver(receiver, intentFilter);
        }
    }

    public void unregisterReceiver(View view) {
        if (null != receiver) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction(ACTION_LAUNCH_STATIC);
        sendBroadcast(intent);
    }

    public void openUserDictionary(View view) {
        UserDictionaryActivity.start(this);
    }
}
