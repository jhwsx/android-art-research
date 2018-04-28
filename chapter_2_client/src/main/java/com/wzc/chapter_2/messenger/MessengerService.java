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

/**
 * @author wzc
 * @date 2018/4/4
 */
public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getSimpleName();

    // 1-1, 服务端要持有一个Handler来处理来自客户端的调用
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 1-6 处理来自客户端的消息
                case MyConstants.MSG_FROM_CLIENT:
                    Log.d(TAG, "handleMessage: receive msg from client : " + msg.getData().getString(MyConstants.MSG));
                    // 2-4, 从msg.replyTo中取出处理服务端消息的Messenger对象
                    Messenger replyMessenger = msg.replyTo;
//                    Book book = (Book) msg.obj;
//                    Log.d(TAG, "handleMessage: book : " + book);
                    Point point = (Point) msg.obj;
                    Log.d(TAG, "handleMessage: point : " + point);
                    Message replyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString(MyConstants.REPLY, "嗯, 你的消息我已经收到, 稍后会回复你.");
                    replyMessage.setData(bundle);
                    // 2-5, 向客户端发送消息
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

    // 1-2, 使用Handler来创建一个Messenger对象
    private Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        // 1-3, 返回Messenger对象所持有的Binder对象
        return mMessenger.getBinder();
    }
}
