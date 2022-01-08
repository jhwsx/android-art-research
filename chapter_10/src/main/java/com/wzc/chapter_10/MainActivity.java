package com.wzc.chapter_10;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wzc.chapter_10.idlehandler.IdleHandlerActivity;

import java.lang.reflect.Field;

// 系统源码 jni 实现包：E:\android-5.1.1_r1\frameworks\base\core\jni
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    // define a ThreadLocal object
    private ThreadLocal<Boolean> mBooleanThreadLocal = new ThreadLocal<>();
    // 四种构造
    private Handler mainHandler1 = new Handler();
    private Handler mainHandler2 = new Handler(Looper.getMainLooper());
    private Handler mainHandler3 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    private Handler mainHandler4 = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    private Handler handler;
    private HandlerThread handlerThread;


    class MyHandler extends Handler {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Field field = MyClass.class.getDeclaredField("FIND_POTENTIAL_LEAKS");
            field.setAccessible(true);
//            //去除final修饰符的影响，将字段设为可修改的
//            Field artFieldField = Field.class.getDeclaredField("artField");
//            artFieldField.setAccessible(true);
//            Object artFieldObj = artFieldField.get(field);
//            Field accessFlagsField = artFieldObj.getClass().getDeclaredField("accessFlags");
//            accessFlagsField.setAccessible(true);
//            accessFlagsField.set(artFieldObj,((int) accessFlagsField.get(artFieldObj) )& ~Modifier.FINAL);
            field.set(null, true);
            Object o = field.get(null);
            Log.d(TAG, "onCreate: " + o);
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + e);
            e.printStackTrace();
        }
        MyClass myClass = new MyClass();
        MyHandler myHandler = new MyHandler();
        mBooleanThreadLocal.set(true);
        log();

        new Thread("Thread#1") {
            @Override
            public void run() {
                super.run();
                mBooleanThreadLocal.set(false);
                log();
            }
        }.start();

        new Thread("Thread#2"){
            @Override
            public void run() {
                super.run();
                log();
            }
        }.start();
        handlerThread = new HandlerThread("wzc");
        handlerThread.start();
//        handlerThread.getLooper().setMessageLogging(new PrintWriterPrinter(new PrintWriter(new OutputStreamWriter(System.out))));
        handlerThread.getLooper().setMessageLogging((new LogPrinter(Log.DEBUG, "WZC")));
        Looper.getMainLooper().setMessageLogging(new LogPrinter(Log.DEBUG, "UI-THREAD"));

        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch(msg.what) {
                    case 1:
                        String obj = (String) msg.obj;
                        Log.d(TAG, "handleMessage: obj = " + obj);
                        break;
                    default:
                        break;
                }
            }
        };
        startWorkThread();
    }
    private Handler mainThreadHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3) {
                Log.d(TAG, "handleMessage: msg.what=" + msg.what + ",msg.obj=" +
                        msg.obj + ",threadName=" + Thread.currentThread().getName());
            }
        }
    };
    public void sendMessage2UIThread(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int what = 3;
                String obj = "hello, ui thread!";
                Log.d(TAG, "sendMessage2UIThread: what="+ what +",obj=" +
                        obj + ",threadName=" + Thread.currentThread().getName());
                mainThreadHandler.obtainMessage(what, obj).sendToTarget();
            }
        }, "work-thread").start();
    }


    private Handler workThreadHandler;
    private void startWorkThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                workThreadHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 2) {
                            Log.d(TAG, "handleMessage: msg.what=" + msg.what + ",msg.obj=" +
                                    msg.obj + ",threadName=" + Thread.currentThread().getName());
                        }
                    }
                };
                Looper.loop();
            }
        }, "work-thread").start();
    }
    public void sendMessage2WorkThread(View view) {
        int what = 2;
        String obj = "hello, work thread!";
        Log.d(TAG, "sendMessage2WorkThread: what="+ what +",obj=" +
                obj + ",threadName=" + Thread.currentThread().getName());
        workThreadHandler.sendMessage(workThreadHandler.obtainMessage(what, obj));
    }


    public void openCheckThreadNotWorkingActivity(View view) {
        CheckThreadNotWorkingActivity.start(this);
    }

    public void createUIInWorkThread(View view) {
        new Thread(() -> {
            // 这里要由 Looper 对象，因为在 ViewRootImpl 里面会创建 ViewRootHandler 对象。
            Looper.prepare();
            TextView tv = new TextView(MainActivity.this);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    tv.setBackgroundColor(Color.GRAY);
                    tv.setTextColor(Color.RED);
                    tv.setTextSize(40);
                    tv.setText("i am text created in " + Thread.currentThread().getName());
                }
            }, "child-thread");
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WindowManager windowManager = MainActivity.this.getWindowManager();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                    0, 0, PixelFormat.TRANSPARENT);
            layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            windowManager.addView(tv, layoutParams);
            Looper.loop();
        }, "work-thread").start();
    }
    public void openIdleHandlerActivity(View view) {
        IdleHandlerActivity.start(this);
    }

    public void sendMessageUsingHandlerThread(View view) {
        handler.obtainMessage(1, "wzc").sendToTarget();
       // Message.obtain(handler, 1, "wzc").sendToTarget();
    }

    private void log() {
        Log.d(TAG, "["+ getCurrThread() +"]"+ "mBooleanThreadLocal.get()=" + mBooleanThreadLocal.get());
    }

    private String getCurrThread() {
        return Thread.currentThread().getName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quitSafely();
    }
}
