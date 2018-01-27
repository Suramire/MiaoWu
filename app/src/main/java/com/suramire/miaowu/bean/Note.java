package com.suramire.miaowu.bean;

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
	private Integer thumbs;
	private Integer type;//1=寻找猫进行收养 2=捡到猫等待别人收养

	// Constructors

	/** default constructor */
	public Note() {
	}

	/** minimal constructor */
	public Note(Integer uid, String title, String content, Timestamp publish,
			Integer type) {
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.publish = publish;
		this.type = type;
	}

	/** full constructor */
	public Note(Integer uid, String title, String content, Timestamp publish,
			Integer viewcount, Integer thumbs, Integer type) {
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.publish = publish;
		this.viewcount = viewcount;
		this.thumbs = thumbs;
		this.type = type;
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

	public Integer getThumbs() {
		return this.thumbs;
	}

	public void setThumbs(Integer thumbs) {
		this.thumbs = thumbs;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}