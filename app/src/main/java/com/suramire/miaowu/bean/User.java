package com.suramire.miaowu.bean;

import java.util.Date;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String phonenumber;
	private String nickname;
	private String password;
	private String icon;
	private Date birthday;

	// Constructors

	/** default_icon constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String phonenumber, String nickname, String password) {
		this.phonenumber = phonenumber;
		this.nickname = nickname;
		this.password = password;
	}

	/** full constructor */
	public User(String phonenumber, String nickname, String password,
			String icon, Date birthday) {
		this.phonenumber = phonenumber;
		this.nickname = nickname;
		this.password = password;
		this.icon = icon;
		this.birthday = birthday;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}