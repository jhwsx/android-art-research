package com.wzc.chapter_2.manualbinder;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.wzc.chapter_2.MyApplication;
import com.wzc.chapter_2.util.MyUtils;

import java.util.List;

/**
 * @author wangzhichao
 * @date 2020-9-3
 */
public class Proxy implements IBookManager {

    private static final String TAG = "Proxy";
    private IBinder mRemote;

    public Proxy(IBinder remote) {
        this.mRemote = remote;
    }

    // 这个方法运行在客户端
    @Override
    public void addBook(Book book) throws RemoteException {
        Log.d(TAG, "addBook: book=" + book + ", currThread=" + Thread.currentThread().getName()+ ",processName=" + MyUtils.getProcessName(MyApplication.getContext(), Process.myPid()));
        // 输入型 Parcel 对象
        Parcel _data = Parcel.obtain();
        // 输出型 Parcel 对象
        Parcel _reply = Parcel.obtain();
        try {
            _data.writeInterfaceToken(IBookManager.DESCRIPTOR);
            if (book != null) {
                _data.writeInt(1);
                book.writeToParcel(_data, 0);
            } else {
                _data.writeInt(0);
            }
            Log.d(TAG, "addBook: book=" + book + ", currThread=" + Thread.currentThread().getName()
                    + ", 远程请求开始");
            mRemote.transact(TRANSACTION_addBook, _data, _reply, 0);
            Log.d(TAG, "addBook: book=" + book + ", currThread=" + Thread.currentThread().getName()
                    + ", 远程请求结束");
            _reply.readException();
        } finally {
            _data.recycle();
            _reply.recycle();
        }

    }

    // 这个方法运行在客户端
    @Override
    public List<Book> getBookList() throws RemoteException {
        Log.d(TAG, "getBookList: currThread=" + Thread.currentThread().getName());
        // 输入型 Parcel 对象
        Parcel _data = Parcel.obtain();
        // 输出型 Parcel 对象
        Parcel _reply = Parcel.obtain();

        List<Book> _result;
        try {
            _data.writeInterfaceToken(IBookManager.DESCRIPTOR);
            Log.d(TAG, "getBookList： currThread=" + Thread.currentThread().getName() + ", 远程请求开始");
            mRemote.transact(TRANSACTION_getBookList, _data, _reply, 0);
            Log.d(TAG, "getBookList： currThread=" + Thread.currentThread().getName() + ", 远程请求结束");
            _reply.readException();
            _result = _reply.createTypedArrayList(Book.CREATOR);
        } finally {
            _data.recycle();
            _reply.recycle();
        }
        return _result;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
