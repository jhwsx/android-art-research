package com.wzc.chapter_2.manualbinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wangzhichao
 * @date 2020-9-3
 */
public class ManualBookManagerService extends Service {

    private static final String TAG = "ManualBookManagerServic";
    private CopyOnWriteArrayList<Book> bookList = new CopyOnWriteArrayList<>();

    private IBinder binder = new Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "addBook: book=" + book + ", currThread=" + Thread.currentThread().getName());
            bookList.add(book);
        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "getBookList: currThread=" + Thread.currentThread().getName());
            return bookList;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        bookList.add(new Book(1, "Java"));
        bookList.add(new Book(2, "Android"));
    }
}
