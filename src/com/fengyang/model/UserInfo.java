package com.fengyang.model;

public class UserInfo {

	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public UserInfo(String key, String value) {
		this.key = key;
		this.value = value;
	}
	public UserInfo() {
	}


}
