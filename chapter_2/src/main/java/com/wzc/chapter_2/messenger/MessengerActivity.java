package com.wzc.chapter_2.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.wzc.chapter_2.R;
import com.wzc.chapter_2.util.MyConstants;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wzc
 * @date 2018/4/4
 */
public class MessengerActivity extends Activity {
    private static final String TAG = MessengerActivity.class.getSimpleName();
    private AtomicBoolean mBound = new AtomicBoolean(false);
    private Messenger mMessenger;

    // 3-1, 创建处理来自服务端消息的Handler
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage: currThread=" + Thread.currentThread().getName());
            // 2-6, 处理从服务端返回的消息
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVICE:
                    Log.d(TAG, "handleMessage: receive msg from service : " + msg.getData().getString(MyConstants.REPLY));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    // 3-2, 创建处理服务端消息的 Messenger
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 2-2, 绑定成功后，使用服务端返回过来的 IBinder 对象来初始化一个 Messenger 对象
            mMessenger = new Messenger(service);
            mBound.set(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
            mBound.set(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message sendMessage = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
                // 非系统的Parcelable对象不能通过obj传输  Caused by: java.lang.ClassNotFoundException: com.wzc.chapter_2_common_lib.Book
//                Book book = new Book(1, "wzc");
//                sendMessage.obj = book;
                // 系统的Parcelable对象能够通过obj传输
                sendMessage.obj =  new Point(4, 6);
                Bundle bundle = new Bundle();
                bundle.putString(MyConstants.MSG, "hello, this is client.");
                sendMessage.setData(bundle);
                // 3-3, 把处理服务端消息的 Messenger ,通过msg.replyTo,带给服务端
                Log.d(TAG, "mGetReplyMessenger=" + mGetReplyMessenger + ",looper="+ Looper.myLooper());
                sendMessage.replyTo = mGetReplyMessenger;
                try {
                    Field mTargetField = Messenger.class.getDeclaredField("mTarget");
                    mTargetField.setAccessible(true);
                    Object o = mTargetField.get(mGetReplyMessenger);
                    Log.d(TAG, "onClick: mTarget=" + o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onClick: mGetReplyMessenger.getBinder()=" + mGetReplyMessenger.getBinder());
                try {
                    // 2-3 通过创建好的 Messenger 对象，向服务端发送消息, 发起远程调用
                    mMessenger.send(sendMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 2-1, 在客户端进程中，首先要绑定服务端的 Service
        Intent service = new Intent(this, MessengerService.class);
        bindService(service, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound.get()) {
            unbindService(mConnection);
            mBound.set(false);
        }
    }
}
