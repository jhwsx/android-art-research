package com.wzc.chapter_2_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * @author wzc
 * @date 2018/4/12
 */
public class BookProvider extends ContentProvider {
    private static final String TAG = BookProvider.class.getSimpleName();
    public static final String AUTHORITY = "com.wzc.chapter_2_provider.book.provider";
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // 把Uri和Uri_Code关联在一起,作用是当外界请求访问BookProvider时，可以根据请求的Uri，获取对应的Uri_Code, 继而找到对应的表。
        sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private Context mContext;
    private SQLiteDatabase mDb;

    /**
     * 根据 Uri 获取表名
     *
     * @param uri
     * @return
     */
    private String getTableName(Uri uri) {
        Log.d(TAG, "getTableName: uri=" + uri);
        String tableName = null;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case BOOK_URI_CODE:
                tableName = DBOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DBOpenHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    @Override
    public boolean onCreate() {
        // 主线程
        Log.d(TAG, "onCreate: current thread:" + Thread.currentThread().getName());

        mContext = getContext();

        initProviderData();

        return true;
    }

    private void initProviderData() {
        mDb = new DBOpenHelper(mContext).getWritableDatabase();
        mDb.execSQL("delete from " + DBOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " + DBOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into " + DBOpenHelper.BOOK_TABLE_NAME + " values(3, 'Android');");
        mDb.execSQL("insert into " + DBOpenHelper.BOOK_TABLE_NAME + " values(4, 'Ios');");
        mDb.execSQL("insert into " + DBOpenHelper.BOOK_TABLE_NAME + " values(5, 'Html5');");
        mDb.execSQL("insert into " + DBOpenHelper.USER_TABLE_NAME + " values(1, 'jake',1);");
        mDb.execSQL("insert into " + DBOpenHelper.USER_TABLE_NAME + " values(2, 'jasmine',0);");

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: current thread:" + Thread.currentThread().getName());

        String tableName = getTableName(uri);

        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return mDb.query(tableName, projection, selection, selectionArgs, sortOrder, null, null);
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG, "getType: current thread:" + Thread.currentThread().getName());
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: current thread:" + Thread.currentThread().getName());
        String tableName = getTableName(uri);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        mDb.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: current thread:" + Thread.currentThread().getName());
        String tableName = getTableName(uri);

        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int delete = mDb.delete(tableName, selection, selectionArgs);

        if (delete > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return delete;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update: current thread:" + Thread.currentThread().getName());
        String tableName = getTableName(uri);

        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        int update = mDb.update(tableName, values, selection, selectionArgs);
        if (update > 0) {
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return update;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if ("add".equals(method)) {
            int num1 = extras.getInt("num1");
            int num2 = extras.getInt("num2");
            int result = num1 + num2;
            Bundle bundle = new Bundle();
            bundle.putInt("result", result);
            return bundle;
        }
        return super.call(method, arg, extras);
    }
}
