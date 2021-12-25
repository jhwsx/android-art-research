package com.wzc.chapter_9.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.wzc.chapter_9.App;

@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "book.db";
    private static volatile AppDatabase sInstance;

    public static AppDatabase getInstance() {
        if (null == sInstance) {
            synchronized (AppDatabase.class) {
                if (null == sInstance) {
                    sInstance = Room.databaseBuilder(App.getContext(), AppDatabase.class, DB_NAME).build();
                }
            }
        }
        return sInstance;
    }
    public abstract BookDao bookDao();
}
