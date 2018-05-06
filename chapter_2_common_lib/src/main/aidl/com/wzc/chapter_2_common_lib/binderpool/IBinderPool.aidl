// IBinderPool.aidl
package com.wzc.chapter_2_common_lib.binderpool;

// Declare any non-default types here with import statements

interface IBinderPool {
    /**
    * @param binderCode 用来识别特定的Binder
    * @return binderCode指定的那个Binder
    */
   IBinder queryBinder(int binderCode);
}
