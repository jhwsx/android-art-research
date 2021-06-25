// ICallback.aidl
package com.example.aidl;

// Declare any non-default types here with import statements

interface ICallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     * int数据的directional tag只能是in
     */
    void onResult(int result);
}