package com.wzc.chapter_2_common_lib.binderpool;

import android.os.RemoteException;

/**
 * 相当于之前在 Service 中创建的 Binder 实例
 * @author wzc
 * @date 2018/5/4
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return (a + b);
    }
}
