package com.fengyang.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AppSecondHand entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("rawtypes")
public class AppSecondHand implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7926970868950603896L;
	private Integer secondHandId;
	private AppSecondClass appSecondClassByFirstType;
	private OubaArea oubaAreaByProId;
	private OubaArea oubaAreaByCityId;
	private AppSecondClass appSecondClassBySecondType;
	private User oubaMember;
	private OubaArea oubaAreaByDisId;
	private String secondHandName;
	private String secondKeyword;
	private String secondPublisher;
	private Integer publisherType;
	private String imagePath;
	private Timestamp publishTime;
	private Date endTime;
	private Double secondHandPrice;
	private String secondDescription;
	private Integer secondCount;
	private Boolean isPrccessed;
	private Integer secondVisited;
	private Integer secondCollected;
	private String secondPhone;
	private String address;

	private Set appSecondCollections = new HashSet(0);
	private Set appSecondHandImages = new HashSet(0);
	private Set appSecondHandReports = new HashSet(0);

	// Constructors

	/** default constructor */
	public AppSecondHand() {
	}

	/** minimal constructor */
	public AppSecondHand(User oubaMember, String secondHandName,
			Integer publisherType, Integer secondVisited,
			Integer secondCollected, String secondPhone) {
		this.oubaMember = oubaMember;
		this.secondHandName = secondHandName;
		this.publisherType = publisherType;
		this.secondVisited = secondVisited;
		this.secondCollected = secondCollected;
		this.secondPhone = secondPhone;
	}

	/** full constructor */
	public AppSecondHand(AppSecondClass appSecondClassByFirstType,
			OubaArea oubaAreaByProId, OubaArea oubaAreaByCityId,
			AppSecondClass appSecondClassBySecondType, User oubaMember,
			OubaArea oubaAreaByDisId, String secondHandName,
			String secondKeyword, String secondPublisher,
			Integer publisherType, String imagePath, Timestamp publishTime,
			Date endTime, Double secondHandPrice, String secondDescription,
			Integer secondCount, Boolean isPrccessed, Integer secondVisited,
			Integer secondCollected, String secondPhone, String address,
			Set appSecondCollections, Set appSecondHandImages,
			Set appSecondHandReports) {
		this.appSecondClassByFirstType = appSecondClassByFirstType;
		this.oubaAreaByProId = oubaAreaByProId;
		this.oubaAreaByCityId = oubaAreaByCityId;
		this.appSecondClassBySecondType = appSecondClassBySecondType;
		this.oubaMember = oubaMember;
		this.oubaAreaByDisId = oubaAreaByDisId;
		this.secondHandName = secondHandName;
		this.secondKeyword = secondKeyword;
		this.secondPublisher = secondPublisher;
		this.publisherType = publisherType;
		this.imagePath = imagePath;
		this.publishTime = publishTime;
		this.endTime = endTime;
		this.secondHandPrice = secondHandPrice;
		this.secondDescription = secondDescription;
		this.secondCount = secondCount;
		this.isPrccessed = isPrccessed;
		this.secondVisited = secondVisited;
		this.secondCollected = secondCollected;
		this.secondPhone = secondPhone;
		this.address = address;
		this.appSecondCollections = appSecondCollections;
		this.appSecondHandImages = appSecondHandImages;
		this.appSecondHandReports = appSecondHandReports;
	}

	// Property accessors

	public Integer getSecondHandId() {
		return this.secondHandId;
	}

	public void setSecondHandId(Integer secondHandId) {
		this.secondHandId = secondHandId;
	}

	public AppSecondClass getAppSecondClassByFirstType() {
		return this.appSecondClassByFirstType;
	}

	public void setAppSecondClassByFirstType(
			AppSecondClass appSecondClassByFirstType) {
		this.appSecondClassByFirstType = appSecondClassByFirstType;
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

	public AppSecondClass getAppSecondClassBySecondType() {
		return this.appSecondClassBySecondType;
	}

	public void setAppSecondClassBySecondType(
			AppSecondClass appSecondClassBySecondType) {
		this.appSecondClassBySecondType = appSecondClassBySecondType;
	}

	public User getOubaMember() {
		return this.oubaMember;
	}

	public void setOubaMember(User oubaMember) {
		this.oubaMember = oubaMember;
	}

	public OubaArea getOubaAreaByDisId() {
		return this.oubaAreaByDisId;
	}

	public void setOubaAreaByDisId(OubaArea oubaAreaByDisId) {
		this.oubaAreaByDisId = oubaAreaByDisId;
	}

	public String getSecondHandName() {
		return this.secondHandName;
	}

	public void setSecondHandName(String secondHandName) {
		this.secondHandName = secondHandName;
	}

	public String getSecondKeyword() {
		return this.secondKeyword;
	}

	public void setSecondKeyword(String secondKeyword) {
		this.secondKeyword = secondKeyword;
	}

	public String getSecondPublisher() {
		return this.secondPublisher;
	}

	public void setSecondPublisher(String secondPublisher) {
		this.secondPublisher = secondPublisher;
	}

	public Integer getPublisherType() {
		return this.publisherType;
	}

	public void setPublisherType(Integer publisherType) {
		this.publisherType = publisherType;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Timestamp getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getSecondHandPrice() {
		return this.secondHandPrice;
	}

	public void setSecondHandPrice(Double secondHandPrice) {
		this.secondHandPrice = secondHandPrice;
	}

	public String getSecondDescription() {
		return this.secondDescription;
	}

	public void setSecondDescription(String secondDescription) {
		this.secondDescription = secondDescription;
	}

	public Integer getSecondCount() {
		return this.secondCount;
	}

	public void setSecondCount(Integer secondCount) {
		this.secondCount = secondCount;
	}

	public Boolean getIsPrccessed() {
		return this.isPrccessed;
	}

	public void setIsPrccessed(Boolean isPrccessed) {
		this.isPrccessed = isPrccessed;
	}

	public Integer getSecondVisited() {
		return this.secondVisited;
	}

	public void setSecondVisited(Integer secondVisited) {
		this.secondVisited = secondVisited;
	}

	public Integer getSecondCollected() {
		return this.secondCollected;
	}

	public void setSecondCollected(Integer secondCollected) {
		this.secondCollected = secondCollected;
	}

	public String getSecondPhone() {
		return this.secondPhone;
	}

	public void setSecondPhone(String secondPhone) {
		this.secondPhone = secondPhone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set getAppSecondCollections() {
		return this.appSecondCollections;
	}

	public void setAppSecondCollections(Set appSecondCollections) {
		this.appSecondCollections = appSecondCollections;
	}

	public Set getAppSecondHandImages() {
		return this.appSecondHandImages;
	}

	public void setAppSecondHandImages(Set appSecondHandImages) {
		this.appSecondHandImages = appSecondHandImages;
	}

	public Set getAppSecondHandReports() {
		return this.appSecondHandReports;
	}

	public void setAppSecondHandReports(Set appSecondHandReports) {
		this.appSecondHandReports = appSecondHandReports;
	}

}