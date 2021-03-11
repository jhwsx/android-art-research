package com.wzc.chapter_2_socket_server;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * @author wzc
 * @date 2018/5/4
 */
public class TCPServerService extends Service {
    private static final String TAG = TCPServerService.class.getSimpleName();
    private boolean mIsServiceDestroyed = false;
    private String[] mDefinedMessages = new String[]{
            "你好啊，哈哈",
            "请问你叫什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是可以和多个人同时聊天的哦",
            "给你讲个笑话吧：据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // 在线程中建立TCP服务
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;

            try {
                // 服务器创建一个ServerSocket的实例
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.e(TAG, "run: " + "establish tcp server failed, port: 8688");
                e.printStackTrace();
                return;
            }
            while (!mIsServiceDestroyed) {
                try {
                    // 调用accept()方法，将进入阻塞状态，等待客户端的连接请求。
                    //  * Waits for an incoming request and blocks until the connection is opened.
                    //  * This method returns a socket object representing the just opened
                    //  * connection.
                    final Socket client = serverSocket.accept();
                    Log.d(TAG, "run: " + "accept");
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void responseClient(Socket client) throws IOException {
        // 用于接收客户端消息
        InputStream inputStream = client.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // 用于向客户端发送消息
        OutputStream outputStream = client.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter, true);
        printWriter.println("欢迎来到聊天室！");
        while (!mIsServiceDestroyed) { // 当服务还在时
            String string = bufferedReader.readLine();
            Log.d(TAG, "responseClient: " + "msg from client: " + string);
            if (string == null) {
                // 客户端断开连接
                break;
            }
            int i = new Random().nextInt(mDefinedMessages.length);
            String msg = mDefinedMessages[i];
            printWriter.println(msg);
            Log.d(TAG, "responseClient: " + "send to client: " + msg);
        }
        Log.d(TAG, "responseClient: " + "client quit.");
        closeQuietly(bufferedReader);
        closeQuietly(bufferedWriter);
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
