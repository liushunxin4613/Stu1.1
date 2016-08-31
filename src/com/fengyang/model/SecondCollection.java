package com.fengyang.model;

import java.sql.Timestamp;
import java.util.Date;

import com.fengyang.entity.AppSecondCollection;
import com.fengyang.entity.AppSecondHand;

public class SecondCollection implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 4743696180818693141L;
	/**
	 * 收藏二手物品的id
	 */
	private Integer scId;
	/**
	 * 二手物品id
	 */
	private Integer secondHandId;
	/**
	 * 收藏者id
	 */
	private Integer collecterId;
	private Integer publisherType;
	private Timestamp publishTime;
	private Timestamp collectDate;
	private Double secondPrice;
	private String secondName;
	private String imagePath;

	// Constructors

	/** default constructor */
	public SecondCollection() {
	}

	public SecondCollection(Integer scId, Integer secondHandId,
			Integer collecterId, Integer publisherType, Timestamp publishTime,
			Timestamp collectDate, Double secondPrice, String secondName,
			String imagePath) {
		super();
		this.scId = scId;
		this.secondHandId = secondHandId;
		this.collecterId = collecterId;
		this.publisherType = publisherType;
		this.publishTime = publishTime;
		this.collectDate = collectDate;
		this.secondPrice = secondPrice;
		this.secondName = secondName;
		this.imagePath = imagePath;
	}

	/**
	 * 将AppSecondHand对象解析成SecondCollection对象
	 * 
	 * @param second
	 *            AppSecondHand对象实例
	 * @param userId
	 *            收藏者id
	 * @param collectTime
	 *            收藏时间
	 * @return
	 */
	public static SecondCollection Parse(AppSecondHand second, int userId,
			Date collectTime) {
		SecondCollection collection = new SecondCollection(null,
				second.getSecondHandId(), userId, second.getPublisherType(),
				second.getPublishTime(), new Timestamp(collectTime.getTime()),
				second.getSecondHandPrice(), second.getSecondHandName(),
				second.getImagePath());

		return collection;
	}

	public static SecondCollection Parse(AppSecondCollection appCollection) {
		AppSecondHand second = appCollection.getAppSecondHand();
		SecondCollection collection = new SecondCollection(null,
				second.getSecondHandId(),
				appCollection.getOubaMember().getId(),
				appCollection.getScPublisherType(), second.getPublishTime(),
				appCollection.getScDate(), second.getSecondHandPrice(),
				second.getSecondHandName(), second.getImagePath());

		return collection;
	}

	public Integer getScId() {
		return scId;
	}

	public void setScId(Integer scId) {
		this.scId = scId;
	}

	public Integer getSecondHandId() {
		return secondHandId;
	}

	public void setSecondHandId(Integer secondHandId) {
		this.secondHandId = secondHandId;
	}

	public Integer getCollecterId() {
		return collecterId;
	}

	public void setCollecterId(Integer collecterId) {
		this.collecterId = collecterId;
	}

	public Integer getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(Integer publisherType) {
		this.publisherType = publisherType;
	}

	public Timestamp getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}

	public Timestamp getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Timestamp collectDate) {
		this.collectDate = collectDate;
	}

	public Double getSecondPrice() {
		return secondPrice;
	}

	public void setSecondPrice(Double secondPrice) {
		this.secondPrice = secondPrice;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}