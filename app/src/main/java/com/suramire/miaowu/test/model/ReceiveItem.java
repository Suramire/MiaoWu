package com.suramire.miaowu.test.model;

import java.util.Date;

/**
 * Created by Suramire on 2017/10/17.
 */

public class ReceiveItem {
//    所回复帖子的id
    private int nId;
//    用户id
    private int uId;
//    所回复的用户id
    private int rId;
    private String uName;
    private String uContent;
    private Date uDatetime;


    public ReceiveItem(int nId, int uId, int rId, String uName, String uContent, Date uDatetime) {
        this.nId = nId;
        this.uId = uId;
        this.rId = rId;
        this.uName = uName;
        this.uContent = uContent;
        this.uDatetime = uDatetime;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuContent() {
        return uContent;
    }

    public void setuContent(String uContent) {
        this.uContent = uContent;
    }

    public Date getuDatetime() {
        return uDatetime;
    }

    public void setuDatetime(Date uDatetime) {
        this.uDatetime = uDatetime;
    }



}
