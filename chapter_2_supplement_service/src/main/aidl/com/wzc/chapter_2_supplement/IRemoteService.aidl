// IRemoteService.aidl
package com.wzc.chapter_2_supplement;

// Declare any non-default types here with import statements
import com.wzc.chapter_2_supplement.IParticipateCallback;

interface IRemoteService {
    int someOperate(int a, int b);
    void join(IBinder token, String username);
    void leave(IBinder token);
    List<String> getParticipators();
    void registerParticipateCallback(IParticipateCallback cb);
    void unregisterParticipateCallback(IParticipateCallback cb);
}
