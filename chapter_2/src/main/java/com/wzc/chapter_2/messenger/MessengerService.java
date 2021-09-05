package com.wzc.chapter_2.messenger;

import android.app.Service;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.wzc.chapter_2.util.MyConstants;

import java.lang.reflect.Field;

/**
 * @author wzc
 * @date 2018/4/4
 */
// 1-1, 在服务端创建一个 Service，来处理客户端的连接请求
public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getSimpleName();

    // 服务端要持有一个 Handler 来处理来自客户端的调用
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 处理来自客户端的消息
                case MyConstants.MSG_FROM_CLIENT:
                    Log.d(TAG, "handleMessage: currThread=" + Thread.currentThread().getName());
                    Log.d(TAG, "handleMessage: receive msg from client : " + msg.getData().getString(MyConstants.MSG));
                    // 3-4, 从 msg.replyTo 中取出处理服务端消息的 Messenger 对象
                    Messenger replyMessenger = msg.replyTo;
                    Log.d(TAG, "handleMessage: replyMessenger=" + replyMessenger);
                    try {
                        Field mTargetField = Messenger.class.getDeclaredField("mTarget");
                        mTargetField.setAccessible(true);
                        Object o = mTargetField.get(replyMessenger);
                        Log.d(TAG, "handleMessage: mTarget=" + o);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "handleMessage: replyMessenger.getBinder()=" + replyMessenger.getBinder());
//                    Book book = (Book) msg.obj;
//                    Log.d(TAG, "handleMessage: book : " + book);
                    Point point = (Point) msg.obj;
                    Log.d(TAG, "handleMessage: point : " + point);
                    Message replyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString(MyConstants.REPLY, "嗯, 你的消息我已经收到, 稍后会回复你.");
                    replyMessage.setData(bundle);
                    // 3-5, 向客户端发送消息
                    try {
                        replyMessenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }

    // 1-2, 使用 Handler 来创建一个 Messenger 对象
    private Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        // 1-3, 返回 Messenger 对象所持有的 Binder 对象
        return mMessenger.getBinder();
    }
}
