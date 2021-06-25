package com.example.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @author wangzhichao
 * @since 2021/6/25
 */
public class State implements Parcelable {
    private static final String TAG = "MyState";
    public static final int DEFAULT_VALUE = -1000;
    private int value;

    public State(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    protected State() {
        value = DEFAULT_VALUE;
    }

    protected State(Parcel in) {
        value = in.readInt();
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }

    public void readFromParcel(Parcel reply) {
        int temp = reply.readInt();
        Log.d(TAG, "read new value  " + temp);
        value = temp;
    }
}
