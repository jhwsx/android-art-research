package com.wzc.chapter_2_common_lib.binderpool;

import android.os.RemoteException;

/**
 * 相当于之前在 Service 中创建的 Binder 实例
 * @author wzc
 * @date 2018/5/4
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECRET_CODE = '^';
    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
