package com.suramire.miaowu.bean;
// default package

/**
 * Catinfo entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class Catinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String type;
	private Integer sex;
	private Integer age;
	private Integer neutering;
	private Integer insecticide;
	private Integer contacttype;
	private String contact;
	private String conditions;

	// Constructors

	/** default constructor */
	public Catinfo() {
	}

	/** full constructor */
	public Catinfo(String type, Integer sex, Integer age, Integer neutering,
			Integer insecticide, Integer contacttype, String contact,
			String conditions) {
		this.type = type;
		this.sex = sex;
		this.age = age;
		this.neutering = neutering;
		this.insecticide = insecticide;
		this.contacttype = contacttype;
		this.contact = contact;
		this.conditions = conditions;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getNeutering() {
		return this.neutering;
	}

	public void setNeutering(Integer neutering) {
		this.neutering = neutering;
	}

	public Integer getInsecticide() {
		return this.insecticide;
	}

	public void setInsecticide(Integer insecticide) {
		this.insecticide = insecticide;
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

	public String getConditions() {
		return this.conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

}