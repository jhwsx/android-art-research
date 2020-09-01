// IBookManager.aidl
package com.wzc.chapter_2_common_lib;

// Declare any non-default types here with import statements
import com.wzc.chapter_2_common_lib.Book;
import com.wzc.chapter_2_common_lib.IOnNewBookArrivedListener;
interface IBookManager {
    int calculate(int a, int b);
    // 从远程服务端获取图书列表
    List<Book> getBookList();
    // 往图书列表中添加一本书
    void addBook(in Book book);
    // 注册新书到达的监听器
    void registerListener(IOnNewBookArrivedListener listener);
    // 反注册新书到达的监听器
    void unregisterListener(IOnNewBookArrivedListener listener);
}
