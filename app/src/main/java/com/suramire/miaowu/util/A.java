package com.suramire.miaowu.util;

import com.suramire.miaowu.bean.Note;
import com.suramire.miaowu.bean.Notification;
import com.suramire.miaowu.bean.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Suramire on 2018/1/27.
 */

public class A {
    public static Notification getNotification(){
        Notification notification = new Notification();
        notification.setId(1);
        notification.setUid1(2);
        notification.setUid2(1);
        notification.setContent("有用户关注了你");
        notification.setTime(getTimeStamp());
        notification.setIsread(0);
        notification.setType(1);
        return notification;
    }

    public static List<Notification> getNotifications(int count){
        List<Notification> notifications = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Notification notification = new Notification();
            notification.setId(i+1);
            notification.setUid1(i+1);
            notification.setUid2(1);
            notification.setContent("通知内容"+(i+1)+"，有用户关注了你");
            notification.setTime(getTimeStamp());
            notification.setIsread(0);
            notification.setType(1);
            notifications.add(notification);
        }
        return notifications;
    }

    public static Note getNote(){
        Note note = new Note();
        note.setId(1);
        note.setUid(1);
        note.setTitle("这是帖子的标题");
        note.setContent("这是帖子的内容");
        note.setThumbs(20);
        note.setViewcount(200);
        note.setPublish(getTimeStamp());
        return note;
    }

    public static List<Note> getNotes(int count){
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Note note = getNote();
            note.setId(i+1);
            note.setTitle("这是标题"+(i+1));
            notes.add(note);
        }
        return notes;
    }

    public static List<User> getUsers(int count){
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setNickname("用户"+(i+1));
            user.setIcon((i+1)+".png");
            users.add(user);
        }
        return users;
    }


    /**
     * 得到当前时间戳
     * @return
     */
    public static Timestamp getTimeStamp(){
        return new Timestamp(new Date().getTime());
    }


    public static String timeStampToDateTimeString(Timestamp timestamp){
        SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sSimpleDateFormat.format(timestamp);
    }

}
