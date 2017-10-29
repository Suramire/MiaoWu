package com.suramire.miaowu.pojo;

import java.sql.Timestamp;

/**
 * Note entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Note implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uid;
	private String title;
	private String content;
	private Timestamp publish;
	private Integer viewcount;

	// Constructors

	/** default constructor */
	public Note() {
	}

	/** minimal constructor */
	public Note(Integer uid, String title, String content, Timestamp publish) {
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.publish = publish;
	}

	/** full constructor */
	public Note(Integer uid, String title, String content, Timestamp publish,
			Integer viewcount) {
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.publish = publish;
		this.viewcount = viewcount;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getPublish() {
		return this.publish;
	}

	public void setPublish(Timestamp publish) {
		this.publish = publish;
	}

	public Integer getViewcount() {
		return this.viewcount;
	}

	public void setViewcount(Integer viewcount) {
		this.viewcount = viewcount;
	}

}