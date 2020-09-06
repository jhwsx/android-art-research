package com.wzc.chapter_2.manualbinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.wzc.chapter_2.R;

import java.util.List;

/**
 * @author wzc
 * @date 2020/9/3
 */
public class ManualBookManagerActivity extends Activity implements View.OnClickListener {
    private static final String TAG = ManualBookManagerActivity.class.getSimpleName();
    private boolean mIsBound;
    private IBookManager mBookManager;
    // 声明一个 DeathRecipient 对象
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        // 当 Binder 死亡的时候，系统就会回调 binderDied 方法
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: currentThread = " + Thread.currentThread().getName()); // Binder:25808_2

            if (mBookManager == null) {
                return;
            }
            // 移除之前设置的死亡代理
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookManager = null;
            // 重新绑定远程服务
            Intent intent = new Intent(ManualBookManagerActivity.this, ManualBookManagerService.class);
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            mIsBound = true;
        }
    };
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 跨进程时，这里回调的 IBinder service 对象 （打印：android.os.BinderProxy@a4991f4）
            // 和 BookManagerService 里的 onBind 方法返回的 IBinder 对象
            // （打印：com.wzc.chapter_2.aidl.BookManagerService$1@ecea4f7）不是一个对象。
            // 不是跨进程时，这里回调的 IBinder service 对象和 BookManagerService 里的 onBind 方法返回的 IBinder 对象
            // 是一个对象。
            Log.d(TAG, "onServiceConnected: service = " + service);
            Log.d(TAG, "onServiceConnected: currentThread = " + Thread.currentThread().getName()); // main
            Toast.makeText(ManualBookManagerActivity.this, "service is connected", Toast.LENGTH_SHORT).show();
            mBookManager = Stub.asInterface(service);
            try {
                // 在客户端绑定远程服务成功后，给 binder 设置死亡代理。
                mBookManager.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: currentThread = " + Thread.currentThread().getName()); // main
            Toast.makeText(ManualBookManagerActivity.this, "service is disconnected", Toast.LENGTH_SHORT).show();
            mIsBound = false;
            mBookManager = null;
        }
    };

    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_book_manager);

        findViewById(R.id.bind).setOnClickListener(this);

        findViewById(R.id.unbind).setOnClickListener(this);

        findViewById(R.id.add_book).setOnClickListener(this);

        findViewById(R.id.get_booklist).setOnClickListener(this);

        mListview = (ListView) findViewById(R.id.listview);

    }

    @Override
    protected void onDestroy() {
        if (mIsBound) {
            unbindService(mConnection);
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind:
                if (!mIsBound) {
                    Intent intent = new Intent(ManualBookManagerActivity.this, ManualBookManagerService.class);
                    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                    mIsBound = true;
                }
                break;
            case R.id.unbind:
                if (mIsBound) {
                    unbindService(mConnection);
                    mIsBound = false;
                    mBookManager = null;
                }
                break;
            case R.id.add_book:
                if (isBookManagerReady()) {
                    Book book = new Book(3, "Android art research");
                    try {
                        Log.d(TAG, "onClick: add book");
                        mBookManager.addBook( book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.get_booklist:
                if (isBookManagerReady()) {
                    try {
                        Log.d(TAG, "onClick: get booklist");
                        List<Book> bookList = mBookManager.getBookList();
                        ArrayAdapter<Book> bookArrayAdapter = new ArrayAdapter<>(ManualBookManagerActivity.this, android.R.layout.simple_list_item_1, bookList);
                        mListview.setAdapter(bookArrayAdapter);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:

        }
    }

    private boolean isBookManagerReady() {
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            return true;
        } else {
            Toast.makeText(this, "BookManager is not available yet!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
