package com.suramire.miaowu.bean;

import java.util.Date;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String phonenumber;
	private String nickname;
	private String password;
	private String icon;
	private Date birthday;
	private Integer role;
	private Integer contacttype;
	private String contact;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String phonenumber, String nickname, String password,
			Integer role, Integer contacttype) {
		this.phonenumber = phonenumber;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
		this.contacttype = contacttype;
	}

	/** full constructor */
	public User(String phonenumber, String nickname, String password,
			String icon, Date birthday, Integer role, Integer contacttype,
			String contact) {
		this.phonenumber = phonenumber;
		this.nickname = nickname;
		this.password = password;
		this.icon = icon;
		this.birthday = birthday;
		this.role = role;
		this.contacttype = contacttype;
		this.contact = contact;
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

	public Integer getRole() {
		return this.role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getContacttype() {
		return this.contacttype;
	}

	public void setContacttype(Integer contacttype) {
		this.contacttype = contacttype;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}