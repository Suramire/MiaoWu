package com.suramire.miaowu.bean;

import java.io.Serializable;

/**
 * 对象集合
 * 用于评论显示
 * 包含评论对话的两个用户信息以及评论本体
 */

public class Multi0 implements Serializable {
	private Reply reply;
    private User user;
    private User user2;
    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
