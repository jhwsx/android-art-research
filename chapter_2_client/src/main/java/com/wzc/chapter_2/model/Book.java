package com.wzc.chapter_2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private Person person;
    private String name;

    public Book() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Book(Parcel in) {
        person = in.readParcelable(Person.class.getClassLoader());
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(person, flags);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "Book{" +
                "person=" + person +
                ", name='" + name + '\'' +
                '}';
    }
}
