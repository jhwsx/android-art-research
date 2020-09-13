// IOnNewBookArrivedListener.aidl
package com.wzc.chapter_2_common_lib;

// Declare any non-default types here with import statements
import com.wzc.chapter_2_common_lib.Book;
// 当服务端有新书到来时，就通知每一个已经申请了提醒功能的用户，就是说，会调用 IOnNewBookArrivedListener 的
// onNewBookArrived 方法，并把新书的对象通过参数传递给客户端
interface IOnNewBookArrivedListener {
    // in 表示输入型参数
    void onNewBookArrived(in Book newBook);
}
