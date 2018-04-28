package com.wzc.chapter_2.manualbinder;

import android.os.IInterface;

/**
 * @author wzc
 * @date 2018/4/3
 */
public interface IBookManager extends IInterface {
    String DESCRIPTOR = "com.wzc.chapter_2.aidl.IBookManager";
    int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);

    int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    // 从远程服务器获取图书列表
    java.util.List<Book> getBookList() throws android.os.RemoteException;

    // 往图书列表中添加一本书
    void addBook(Book book) throws android.os.RemoteException;
}
