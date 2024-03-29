package com.wzc.chapter_2.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.wzc.chapter_2_common_lib.Book;
import com.wzc.chapter_2_common_lib.IBookManager;
import com.wzc.chapter_2_common_lib.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wzc
 * @date 2018/4/4
 */
public class BookManagerService extends Service {
    private static final String TAG = BookManagerService.class.getSimpleName();
    // 用来标记服务是否销毁的flag,默认是false,表示服务没有销毁
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    // 这种方式解注册失败
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    // 这种方式解注册成功
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();
    private Binder mBinder = new IBookManager.Stub() {

        @Override
        public List<Book> getBookList() throws RemoteException {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Log.d(TAG, "getBookList: currentThread = " + Thread.currentThread().getName()); //  Binder:3584_3
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "addBook: currentThread = " + Thread.currentThread().getName()); //  Binder:3584_2

            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            // IOnNewBookArrivedListener 在注册与解注册是不同的，但是它们底层的 IBinder 是同一个。
            Log.d(TAG, "registerListener: listener = " + listener); // com.wzc.chapter_2_common_lib.IOnNewBookArrivedListener$Stub$Proxy@249af64
            Log.d(TAG, "registerListener: listener.asBinder() = " + listener.asBinder()); // android.os.BinderProxy@82855cd
            // 这种方式解注册失败
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//                Log.d(TAG, "registerListener: success");
//            } else {
//                Log.d(TAG, "registerListener: already exists");
//            }
//            Log.d(TAG, "registerListener: mListenerList.size() = " + mListenerList.size());

            // 这种方式解注册成功
            Log.d(TAG, "registerListener: currentThread = " + Thread.currentThread().getName()); // Binder:3584_1
            mListenerList.register(listener);
            Log.d(TAG, "registerListener: mListenerList.size() = " + mListenerList.beginBroadcast());
            mListenerList.finishBroadcast();
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            Log.d(TAG, "unregisterListener: listener = " + listener); // com.wzc.chapter_2_common_lib.IOnNewBookArrivedListener$Stub$Proxy@cdf2e82
            Log.d(TAG, "unregisterListener: listener.asBinder() = " + listener.asBinder()); // android.os.BinderProxy@82855cd
            // 这种方式解注册失败
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//                Log.d(TAG, "unregisterListener: success");
//            } else {
//                Log.d(TAG, "unregisterListener: not found, cannot unregister");
//            }
//            Log.d(TAG, "unregisterListener: mListenerList.size() = " + mListenerList.size());

            // 这种方式解注册成功
            Log.d(TAG, "unregisterListener: currentThread = " + Thread.currentThread().getName()); //  Binder:3584_2
            mListenerList.unregister(listener);
            Log.d(TAG, "unregisterListener: mListenerList.size() = " + mListenerList.beginBroadcast());
            mListenerList.finishBroadcast();
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Log.d(TAG, "onTransact: currentThread = " + Thread.currentThread().getName()); //  Binder:3584_1 Binder:3584_2 Binder:3584_3 Binder:3584_1  Binder:3584_2
            // 这里可以用来检验客户端的权限,如果权限不满足,就无法进行跨进程通信.
            int check = checkCallingOrSelfPermission("com.wzc.chapter_2_common_lib.permission.ACCESS_BOOK_SERVICE");
            if (check == PackageManager.PERMISSION_DENIED) {
                Log.d(TAG, "onTransact: permission denied");
                return false;
            }
            // 验证包名
            String packageName = null;
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                packageName = packages[0];
            }
            if (!packageName.startsWith("com.wzc")) {
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission("com.wzc.chapter_2_common_lib.permission.ACCESS_BOOK_SERVICE");
//        if (check == PackageManager.PERMISSION_DENIED) {
//            Log.d(TAG, "onBind: permission denied");
//            return null;
//        }
        Log.d(TAG, "onBind: mBinder = " + mBinder);
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "iOS"));

        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
        super.onDestroy();
    }

    private class ServiceWorker implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "run: currThread=" + Thread.currentThread().getName());
            // 每隔5s添加一本新书,并通知给注册了新书到达监听的客户端
            while (!mIsServiceDestroyed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onNewBookArrived(Book newBook) throws RemoteException {
        // 这种方式解注册失败
        // 把新书添加进书的列表中
//        mBookList.add(newBook);
//        Log.d(TAG, "onNewBookArrived: notify how many listeners " + mListenerList.size());
//        for (int i = 0; i < mListenerList.size(); i++) {
//            IOnNewBookArrivedListener onNewBookArrivedListener = mListenerList.get(i);
//            onNewBookArrivedListener.onNewBookArrived(newBook);
//        }

        // 这种方式解注册成功
        mBookList.add(newBook);
        final int n = mListenerList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                listener.onNewBookArrived(newBook);
            }
        }
        mListenerList.finishBroadcast();
    }
}
