package com.fengyang.entity;

import java.sql.Timestamp;

/**
 * AppApkVersion entity. @author MyEclipse Persistence Tools
 */

public class AppApkVersion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3058377361175739772L;
	// Fields

	private Integer versionId;
	private Integer versionCode;
	private String versionName;
	private String apkName;
	private Timestamp uploadTime;

	// Constructors

	/** default constructor */
	public AppApkVersion() {
	}

	/** full constructor */
	public AppApkVersion(Integer versionCode, String versionName,
			String apkName, Timestamp uploadTime) {
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.apkName = apkName;
		this.uploadTime = uploadTime;
	}

	// Property accessors

	public Integer getVersionId() {
		return this.versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public Integer getVersionCode() {
		return this.versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return this.versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkName() {
		return this.apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public Timestamp getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

}