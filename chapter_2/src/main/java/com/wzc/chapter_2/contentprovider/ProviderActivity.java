package com.wzc.chapter_2.contentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wzc.chapter_2.R;
import com.wzc.chapter_2.model.User;
import com.wzc.chapter_2_common_lib.Book;

/**
 * 有些手机会禁止关联启动，需要手动打开。
 *
 * @author wzc
 * @date 2018/4/12
 */
public class ProviderActivity extends Activity implements View.OnClickListener {
    private static final String TAG = ProviderActivity.class.getSimpleName();
    private Uri mBookUri;
    private Uri mUserUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
//        Uri uri = Uri.parse("content://com.wzc.chapter_2_provider.book.provider");
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);
//        getContentResolver().query(uri, null, null, null, null);

        mBookUri = Uri.parse("content://com.wzc.chapter_2_provider.book.provider/book");
        mUserUri = Uri.parse("content://com.wzc.chapter_2_provider.book.provider/user");

        findViewById(R.id.btn_insert_book).setOnClickListener(this);
        findViewById(R.id.btn_delete_book).setOnClickListener(this);
        findViewById(R.id.btn_update_book).setOnClickListener(this);
        findViewById(R.id.btn_query_book).setOnClickListener(this);

        findViewById(R.id.btn_insert_user).setOnClickListener(this);
        findViewById(R.id.btn_delete_user).setOnClickListener(this);
        findViewById(R.id.btn_update_user).setOnClickListener(this);
        findViewById(R.id.btn_query_user).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert_book:
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", 6);
                contentValues.put("name", "程序设计的艺术");
                getContentResolver().insert(mBookUri, contentValues);
                break;
            case R.id.btn_delete_book:
                int delete = getContentResolver().delete(mBookUri, "_id=?", new String[]{"6"});
                if (delete > 0) {
                    Toast.makeText(ProviderActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProviderActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_book:
                ContentValues update = new ContentValues();
                update.put("_id", 6);
                update.put("name", "程序设计的艺术(新)");
                int count = getContentResolver().update(mBookUri, update, "_id=?", new String[]{"6"});
                if (count > 0) {
                    Toast.makeText(ProviderActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProviderActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_query_book:
                Cursor bookCursor = getContentResolver().query(mBookUri, null, null, null, null);
                if (bookCursor != null) {
                    try {
                        bookCursor.moveToFirst();
                        while (!bookCursor.isAfterLast()) {
                            int bookId = bookCursor.getInt(bookCursor.getColumnIndex("_id"));
                            String bookName = bookCursor.getString(bookCursor.getColumnIndex("name"));
                            Book book = new Book(bookId, bookName);
                            Log.d(TAG, "query book:" + book.toString());
                            bookCursor.moveToNext();
                        }
                    } finally {
                        bookCursor.close();
                    }
                }
                break;
            case R.id.btn_insert_user:
                ContentValues userInsert = new ContentValues();
                userInsert.put("_id", 7);
                userInsert.put("name", "Carter");
                userInsert.put("sex", 1);
                getContentResolver().insert(mUserUri, userInsert);
                break;
            case R.id.btn_delete_user:
                int delete1 = getContentResolver().delete(mUserUri, "_id=?", new String[]{"7"});
                if (delete1 > 0) {
                    Toast.makeText(ProviderActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProviderActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_update_user:
                ContentValues userUpdate = new ContentValues();
                userUpdate.put("sex",0);
                userUpdate.put("name", "Lucy");
                int update1 = getContentResolver().update(mUserUri, userUpdate, "_id=?", new String[]{"7"});
                if (update1 > 0) {
                    Toast.makeText(ProviderActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProviderActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_query_user:
                Cursor userCursor = getContentResolver().query(mUserUri, null, null, null, null);
                if (userCursor != null) {
                    try {
                        userCursor.moveToFirst();
                        while (!userCursor.isAfterLast()) {
                            int userId = userCursor.getInt(userCursor.getColumnIndex("_id"));
                            String userName = userCursor.getString(userCursor.getColumnIndex("name"));
                            boolean isMale = userCursor.getInt(userCursor.getColumnIndex("sex")) == 0 ? false : true;
                            User user = new User(userId, userName, isMale);
                            Log.d(TAG, "query user:" + user.toString());
                            userCursor.moveToNext();
                        }
                    } finally {
                        userCursor.close();
                    }
                }
                break;
            default:
                break;
        }

    }
}
