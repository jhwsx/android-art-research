// IParticipateCallback.aidl
package com.wzc.chapter_2_supplement;

// Declare any non-default types here with import statements

interface IParticipateCallback {
   // 用户加入或离开的回调
   void onParticipate(String name, boolean joinOrLeave);
}
