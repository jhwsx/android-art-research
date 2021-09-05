package com.wzc.chapter_2.manualbinder;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * @author wangzhichao
 * @date 2020-9-3
 */
public interface IBookManager extends IInterface {
    String DESCRIPTOR = "com.wzc.chapter_2.manualbinder2.IBookManager";
    int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 0;
    int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 1;

    void addBook(Book book) throws RemoteException;

    List<Book> getBookList() throws RemoteException;
}
