// IRemoteService.aidl
package com.wzc.chapter_2_supplement;

// Declare any non-default types here with import statements
import com.wzc.chapter_2_supplement.IParticipateCallback;

interface IRemoteService {
    int someOperate(int a, int b);
    // 加入游戏
    void join(IBinder token, String username);
    // 退出游戏
    void leave(IBinder token);
    // 获取当前参与进来的成员列表
    List<String> getParticipators();
    void registerParticipateCallback(IParticipateCallback cb);
    void unregisterParticipateCallback(IParticipateCallback cb);
}
