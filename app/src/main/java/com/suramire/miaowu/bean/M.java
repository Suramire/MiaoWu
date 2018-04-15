package com.suramire.miaowu.bean;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Suramire on 2017/10/22.
 * 数据传送时使用的结构体
 */

@SuppressWarnings("serial")
public class M implements Serializable {
	
	List<String> stringList;
    
	String[] strings;
	
	String stringx;
	
	String stringy;
	
	String stringz;
	
	int[] ints;
	
	int intx;
	
	Object[] objects;
	
	Object objectx;

	public String[] getStrings() {
		return strings;
	}

	public void setStrings(String[] strings) {
		this.strings = strings;
	}

	public int[] getInts() {
		return ints;
	}

	public void setInts(int[] ints) {
		this.ints = ints;
	}

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}

	public String getStringx() {
		return stringx;
	}

	public void setStringx(String stringx) {
		this.stringx = stringx;
	}

	public int getIntx() {
		return intx;
	}

	public void setIntx(int intx) {
		this.intx = intx;
	}

	public Object getObjectx() {
		return objectx;
	}

	public void setObjectx(Object objectx) {
		this.objectx = objectx;
	}

	public String getStringy() {
		return stringy;
	}

	public void setStringy(String stringy) {
		this.stringy = stringy;
	}

	public String getStringz() {
		return stringz;
	}

	public void setStringz(String stringz) {
		this.stringz = stringz;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}
	
	
	
	
}
