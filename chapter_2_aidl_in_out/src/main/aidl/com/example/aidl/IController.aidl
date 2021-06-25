// IController.aidl
package com.example.aidl;

// Declare any non-default types here with import statements
import com.example.aidl.ICallback;
import com.example.aidl.State;

interface IController {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void registerCallback(ICallback callback);

    int transIn(in State state);
    int transOut(out State state);
    int transInOut(inout State state);
}