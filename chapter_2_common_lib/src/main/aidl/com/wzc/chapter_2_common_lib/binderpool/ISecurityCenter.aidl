// ISecurityCenter.aidl
package com.wzc.chapter_2_common_lib.binderpool;

// Declare any non-default types here with import statements
interface ISecurityCenter {
   String encrypt(String content);
   String decrypt(String password);
}
