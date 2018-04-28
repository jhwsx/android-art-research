package com.wzc.chapter_2_common_lib;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wzc
 * @date 2018/3/14
 */
public class Book implements Parcelable {

    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    // 从序列化后的对象中创建原始对象
    protected Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        // 从序列化后的对象中创建原始对象
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        // 创建指定长度的原始对象数组
        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    // 返回当前对象的描述
    @Override
    public int describeContents() {
        return 0;
    }
    // 将当前对象写入序列化结构中
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    @Override
    public String toString() {
        return "bookId = " + bookId + ", bookName = " + bookName;
    }
}
