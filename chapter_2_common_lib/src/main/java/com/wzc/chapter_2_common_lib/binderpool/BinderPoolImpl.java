package com.wzc.chapter_2_common_lib.binderpool;

import android.os.IBinder;
import android.os.RemoteException;

import static com.wzc.chapter_2_common_lib.binderpool.BinderPool.BINDER_COMPUTE;
import static com.wzc.chapter_2_common_lib.binderpool.BinderPool.BINDER_SECURITY_CENTER;

public  class BinderPoolImpl extends IBinderPool.Stub {

        public BinderPoolImpl() {
            super(); // 这个super不可以去掉
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_COMPUTE:
                    binder = new ComputeImpl();
                    break;
                case BINDER_SECURITY_CENTER:
                    binder = new SecurityCenterImpl();
                    break;
                default:
                    break;
            }
            return binder;
        }
    }