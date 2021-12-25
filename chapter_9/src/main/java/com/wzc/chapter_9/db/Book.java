package com.wzc.chapter_9.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.wzc.chapter_9.provider.BookStore;

@Entity(tableName = Book.TABLE_NAME)
public class Book {
    public static final String TABLE_NAME = "books";
    @PrimaryKey
    @ColumnInfo(name = BookStore.Books._ID)
    private long id;
    @ColumnInfo(name = BookStore.Books.NAME)
    private String name;
    @ColumnInfo(name = BookStore.Books.PRICE)
    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
