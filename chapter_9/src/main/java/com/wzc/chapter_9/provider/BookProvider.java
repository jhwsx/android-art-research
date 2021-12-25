package com.wzc.chapter_9.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wzc.chapter_9.MyUtils;
import com.wzc.chapter_9.db.AppDatabase;
import com.wzc.chapter_9.db.Book;
import com.wzc.chapter_9.db.BookDao;

public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int CODE_BOOK_DIR = 1;
    public static final int CODE_BOOK_ITEM = 2;

    static {
        MATCHER.addURI(BookStore.AUTHORITY, Book.TABLE_NAME, CODE_BOOK_DIR);
        MATCHER.addURI(BookStore.AUTHORITY, Book.TABLE_NAME + "/*", CODE_BOOK_ITEM); // *:表示匹配任意长度的任意字符
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: processName=" + MyUtils.getProcessName(getContext(), Process.myPid()) + ", threadName=" + Thread.currentThread().getName());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
//        Log.d(TAG, "query: processName=" + MyUtils.getProcessName(getContext(), Process.myPid()) + ", threadName=" + Thread.currentThread().getName());
//        final int code = MATCHER.match(uri);
//        if (getContext() == null) {
//            return null;
//        }
//        BookDao bookDao = AppDatabase.getInstance().bookDao();
//        if (code == CODE_BOOK_DIR) {
//            return bookDao.queryAll();
//        } else if (code == CODE_BOOK_ITEM) {
//            return bookDao.queryById(ContentUris.parseId(uri));
//        } else {
//            throw new IllegalArgumentException("Unknown URI: " + uri);
//        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType: processName=" + MyUtils.getProcessName(getContext(), Process.myPid()) + ", threadName=" + Thread.currentThread().getName());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: processName=" + MyUtils.getProcessName(getContext(), Process.myPid()) + ", threadName=" + Thread.currentThread().getName());
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "delete: processName=" + MyUtils.getProcessName(getContext(), Process.myPid()) + ", threadName=" + Thread.currentThread().getName());
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: processName=" + MyUtils.getProcessName(getContext(), Process.myPid()) + ", threadName=" + Thread.currentThread().getName());
        return 0;
    }
}
