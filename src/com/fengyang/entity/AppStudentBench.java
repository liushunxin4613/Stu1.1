package com.fengyang.entity;

import java.sql.Timestamp;

/**
 * AppStudentBench entity. @author MyEclipse Persistence Tools
 */

public class AppStudentBench implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2538487406170117140L;
	private Integer verifyId;
	private User oubaMember;
	private AppCollege appCollege;
	private OubaArea oubaArea;
	private String trueName;
	private String idcard;
	private String stuId;
	private String idcardImg;
	private String stuImg;
	private Timestamp applyDate;
	private Boolean isPass;

	// Constructors

	/** default constructor */
	public AppStudentBench() {
	}

	/** minimal constructor */
	public AppStudentBench(User oubaMember, Timestamp applyDate) {
		this.oubaMember = oubaMember;
		this.applyDate = applyDate;
	}

	/** full constructor */
	public AppStudentBench(User oubaMember, AppCollege appCollege,
			OubaArea oubaArea, String trueName, String idcard, String stuId,
			String idcardImg, String stuImg, Timestamp applyDate, Boolean isPass) {
		this.oubaMember = oubaMember;
		this.appCollege = appCollege;
		this.oubaArea = oubaArea;
		this.trueName = trueName;
		this.idcard = idcard;
		this.stuId = stuId;
		this.idcardImg = idcardImg;
		this.stuImg = stuImg;
		this.applyDate = applyDate;
		this.isPass = isPass;
	}

	// Property accessors

	public Integer getVerifyId() {
		return this.verifyId;
	}

	public void setVerifyId(Integer verifyId) {
		this.verifyId = verifyId;
	}

	public User getOubaMember() {
		return this.oubaMember;
	}

	public void setOubaMember(User oubaMember) {
		this.oubaMember = oubaMember;
	}

	public AppCollege getAppCollege() {
		return this.appCollege;
	}

	public void setAppCollege(AppCollege appCollege) {
		this.appCollege = appCollege;
	}

	public OubaArea getOubaArea() {
		return this.oubaArea;
	}

	public void setOubaArea(OubaArea oubaArea) {
		this.oubaArea = oubaArea;
	}

	public String getTrueName() {
		return this.trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getIdcard() {
		return this.idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getStuId() {
		return this.stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getIdcardImg() {
		return this.idcardImg;
	}

	public void setIdcardImg(String idcardImg) {
		this.idcardImg = idcardImg;
	}

	public String getStuImg() {
		return this.stuImg;
	}

	public void setStuImg(String stuImg) {
		this.stuImg = stuImg;
	}

	public Timestamp getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public Boolean getIsPass() {
		return this.isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

}