// IParticipateCallback.aidl
package com.wzc.chapter_2_supplement;

// Declare any non-default types here with import statements
// 因为普通的interface对象不能通过AIDL注册到Service中，我们需要定义一个AIDL接口
interface IParticipateCallback {
   // 用户加入或离开的回调
   void onParticipate(String name, boolean joinOrLeave);
}
