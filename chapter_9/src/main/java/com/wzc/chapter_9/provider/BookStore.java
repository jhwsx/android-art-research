package com.wzc.chapter_9.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.wzc.chapter_9.db.Book;

public class BookStore {
    public static final String AUTHORITY = "com.wzc.chapter_9.bookprovider";

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY);

    public static class Books implements BaseColumns {
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + Book.TABLE_NAME);

        public static final String _ID = BaseColumns._ID;

        public static final String NAME = "name";

        public static final String PRICE = "price";
    }
}
