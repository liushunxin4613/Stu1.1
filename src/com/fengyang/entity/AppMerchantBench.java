package com.fengyang.entity;

import java.sql.Timestamp;

/**
 * AppMerchantBench entity. @author MyEclipse Persistence Tools
 */

public class AppMerchantBench implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5610124726997778090L;
	private Integer verifyId;
	private User oubaMember;
	private OubaArea oubaAreaByProId;
	private OubaArea oubaAreaByCityId;
	private OubaArea oubaAreaByDisId;
	private String merchantName;
	private String trueName;
	private String idcard;
	private String licenseId;
	private String idcardImg;
	private String licenseImg;
	private Float longitude;
	private Float latitude;
	private Timestamp applyDate;
	private Boolean isPass;

	// Constructors

	/** default constructor */
	public AppMerchantBench() {
	}

	/** minimal constructor */
	public AppMerchantBench(User oubaMember, String merchantName,
			String idcard, String licenseId, Timestamp applyDate) {
		this.oubaMember = oubaMember;
		this.merchantName = merchantName;
		this.idcard = idcard;
		this.licenseId = licenseId;
		this.applyDate = applyDate;
	}

	/** full constructor */
	public AppMerchantBench(User oubaMember, OubaArea oubaAreaByProId,
			OubaArea oubaAreaByCityId, OubaArea oubaAreaByDisId,
			String merchantName, String trueName, String idcard,
			String licenseId, String idcardImg, String licenseImg,
			Float longitude, Float latitude, Timestamp applyDate, Boolean isPass) {
		this.oubaMember = oubaMember;
		this.oubaAreaByProId = oubaAreaByProId;
		this.oubaAreaByCityId = oubaAreaByCityId;
		this.oubaAreaByDisId = oubaAreaByDisId;
		this.merchantName = merchantName;
		this.trueName = trueName;
		this.idcard = idcard;
		this.licenseId = licenseId;
		this.idcardImg = idcardImg;
		this.licenseImg = licenseImg;
		this.longitude = longitude;
		this.latitude = latitude;
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

	public OubaArea getOubaAreaByProId() {
		return this.oubaAreaByProId;
	}

	public void setOubaAreaByProId(OubaArea oubaAreaByProId) {
		this.oubaAreaByProId = oubaAreaByProId;
	}

	public OubaArea getOubaAreaByCityId() {
		return this.oubaAreaByCityId;
	}

	public void setOubaAreaByCityId(OubaArea oubaAreaByCityId) {
		this.oubaAreaByCityId = oubaAreaByCityId;
	}

	public OubaArea getOubaAreaByDisId() {
		return this.oubaAreaByDisId;
	}

	public void setOubaAreaByDisId(OubaArea oubaAreaByDisId) {
		this.oubaAreaByDisId = oubaAreaByDisId;
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public String getLicenseId() {
		return this.licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getIdcardImg() {
		return this.idcardImg;
	}

	public void setIdcardImg(String idcardImg) {
		this.idcardImg = idcardImg;
	}

	public String getLicenseImg() {
		return this.licenseImg;
	}

	public void setLicenseImg(String licenseImg) {
		this.licenseImg = licenseImg;
	}

	public Float getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
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