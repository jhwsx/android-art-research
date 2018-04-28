// IOnNewBookArrivedListener.aidl
package com.wzc.chapter_2_common_lib;

// Declare any non-default types here with import statements
import com.wzc.chapter_2_common_lib.Book;
interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
