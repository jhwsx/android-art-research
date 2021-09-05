// IBookManager.aidl
package com.wzc.chapter_2_common_lib;

// Declare any non-default types here with import statements
import com.wzc.chapter_2_common_lib.Book;
import com.wzc.chapter_2_common_lib.IOnNewBookArrivedListener;
interface IBookManager {


    List<Book> getBookList();

    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);
}
