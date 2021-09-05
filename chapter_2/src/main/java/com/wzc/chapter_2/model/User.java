package com.wzc.chapter_2.model;

import java.io.Serializable;

/**
 * @author wzc
 * @date 2018/3/14
 */
public class User implements Serializable {
    private static final long serialVersionUID = 2L;
    public int userId;
    public String userName;
    public boolean isMale;

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
