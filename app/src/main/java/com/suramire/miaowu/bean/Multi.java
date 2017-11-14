package com.suramire.miaowu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 对象集合 用于首页信息展示
 * 包含 用户 帖子 帖子第一张图片 以及所有图片 
 * @author Suramire
 * @since 2017-11-14 15:49:52
 */

@SuppressWarnings("serial")
public class Multi implements Serializable {
	private User mUser;
	private Note mNote;
	private NotePhoto mNotePhoto;
	private Reply mReply;
	List<String> picturesStrings;
	
	public Reply getmReply() {
		return mReply;
	}
	public void setmReply(Reply mReply) {
		this.mReply = mReply;
	}
	public List<String> getPicturesStrings() {
		return picturesStrings;
	}
	public void setPicturesStrings(List<String> picturesStrings) {
		this.picturesStrings = picturesStrings;
	}
	public User getmUser() {
		return mUser;
	}
	public void setmUser(User mUser) {
		this.mUser = mUser;
	}
	public Note getmNote() {
		return mNote;
	}
	public void setmNote(Note mNote) {
		this.mNote = mNote;
	}
	public NotePhoto getmNotePhoto() {
		return mNotePhoto;
	}
	public void setmNotePhoto(NotePhoto mNotePhoto) {
		this.mNotePhoto = mNotePhoto;
	}

}
