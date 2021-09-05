package com.wzc.chapter_2.manualbinder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

/**
 * @author wangzhichao
 * @date 2020-9-3
 */
public abstract class Stub extends Binder implements IBookManager {
    private static final String TAG = "Stub";

    public Stub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    // 这个方法的参数至关重要
    public static IBookManager asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IInterface iInterface = obj.queryLocalInterface(DESCRIPTOR);
        if (iInterface != null && iInterface instanceof IBookManager) {
            return (IBookManager) iInterface;
        }
        return new Proxy(obj);
    }

    // 这个方法运行在服务端中的 Binder 线程池里面
    // 这个方法是在调用了 IBinder 的 transact 方法后调用的
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_addBook:
                Log.d(TAG, "onTransact: TRANSACTION_addBook， currThread="
                        + Thread.currentThread().getName());
                data.enforceInterface(DESCRIPTOR);
                Book book;
                if (0 != data.readInt()) {
                    book = Book.CREATOR.createFromParcel(data);
                } else {
                    book = null;
                }
                addBook(book);
                reply.writeNoException();
                return true;
            case TRANSACTION_getBookList:
                Log.d(TAG, "onTransact: TRANSACTION_getBookList, currThread="
                        + Thread.currentThread().getName());
                data.enforceInterface(DESCRIPTOR);
                List<Book> _result = getBookList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }
}
