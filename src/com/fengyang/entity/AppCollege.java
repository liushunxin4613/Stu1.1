package com.fengyang.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * AppCollege entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("rawtypes")
public class AppCollege implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7860678712645717633L;
	private Integer CUniversityId;
	private OubaArea oubaArea;
	private String CUniversityName;
	private String CUniversityPinyin;
	private String CProvinceName;
	private Timestamp CCreateTime;
	
	private Set appStudentBenchs = new HashSet(0);

	// Constructors

	/** default constructor */
	public AppCollege() {
	}

	/** minimal constructor */
	public AppCollege(OubaArea oubaArea, String CUniversityName) {
		this.oubaArea = oubaArea;
		this.CUniversityName = CUniversityName;
	}

	/** full constructor */
	public AppCollege(OubaArea oubaArea, String CUniversityName,
			String CUniversityPinyin, String CProvinceName,
			Timestamp CCreateTime, Set appStudentBenchs) {
		this.oubaArea = oubaArea;
		this.CUniversityName = CUniversityName;
		this.CUniversityPinyin = CUniversityPinyin;
		this.CProvinceName = CProvinceName;
		this.CCreateTime = CCreateTime;
		this.appStudentBenchs = appStudentBenchs;
	}

	// Property accessors

	public Integer getCUniversityId() {
		return this.CUniversityId;
	}

	public void setCUniversityId(Integer CUniversityId) {
		this.CUniversityId = CUniversityId;
	}

	public OubaArea getOubaArea() {
		return this.oubaArea;
	}

	public void setOubaArea(OubaArea oubaArea) {
		this.oubaArea = oubaArea;
	}

	public String getCUniversityName() {
		return this.CUniversityName;
	}

	public void setCUniversityName(String CUniversityName) {
		this.CUniversityName = CUniversityName;
	}

	public String getCUniversityPinyin() {
		return this.CUniversityPinyin;
	}

	public void setCUniversityPinyin(String CUniversityPinyin) {
		this.CUniversityPinyin = CUniversityPinyin;
	}

	public String getCProvinceName() {
		return this.CProvinceName;
	}

	public void setCProvinceName(String CProvinceName) {
		this.CProvinceName = CProvinceName;
	}

	public Timestamp getCCreateTime() {
		return this.CCreateTime;
	}

	public void setCCreateTime(Timestamp CCreateTime) {
		this.CCreateTime = CCreateTime;
	}

	public Set getAppStudentBenchs() {
		return this.appStudentBenchs;
	}

	public void setAppStudentBenchs(Set appStudentBenchs) {
		this.appStudentBenchs = appStudentBenchs;
	}

}