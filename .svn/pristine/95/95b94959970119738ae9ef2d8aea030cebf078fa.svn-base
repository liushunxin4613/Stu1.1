package com.fengyang.entity;

import java.util.Date;

/**
 * 用户详情信息数据模型抽象类
 * 
 * @author HeJie
 * 
 */
public class UserDetail implements java.io.Serializable {

	private static final long serialVersionUID = -5972590488407664338L;

	/**
	 * 用户id
	 */
	private Integer id;

	// ***************** 详细信息 *******************//
	/**
	 * 用户真实姓名
	 */
	private String trueName;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * qq号
	 */
	private String qqNO;
	/**
	 * 注册时间
	 */
	private String registTime;
	/**
	 * 个人简介
	 */
	private String brifIntrodction;

	// ****************** 不常用信息 ********************//
	/**
	 * 预存款可用金额
	 */
	private Double availablePredeposit;
	/**
	 * 预存款冻结金额
	 */
	private Double freezePredeposit;
	/**
	 * 可用充值卡余额
	 */
	private Double availableRcBalance;
	/**
	 * 冻结充值卡余额
	 */
	private Double freezeRcBalance;
	/**
	 * qq关联账号
	 */
	private String qqOpenId;
	/**
	 * qq关联账号信息
	 */
	private String qqInfo;
	/**
	 * 新浪关联账号
	 */
	private String sinaOpenId;
	/**
	 * 新浪关联账号信息
	 */
	private String sinaInfo;
	/**
	 * 阿里旺旺
	 */
	private String ww;
	/**
	 * sns访问量
	 */
	private Integer snsVisitNum;
	/**
	 * 用户常用操作
	 */
	private String memberQuicklink;

	// /**
	// * 隐私设置
	// */
	// private String privacy;

	public UserDetail() {
	}

	/** minimal constructor */
	public UserDetail(String trueName, Integer sex, Date birthday, String qqNO,
			String registTime, String brifIntrodction,
			Double availablePredeposit, Double freezePredeposit) {
		super();
		this.trueName = trueName;
		this.sex = sex;
		this.birthday = birthday;
		this.qqNO = qqNO;
		this.registTime = registTime;
		this.brifIntrodction = brifIntrodction;
		this.availablePredeposit = availablePredeposit;
		this.freezePredeposit = freezePredeposit;
	}

	public UserDetail(Integer id, String trueName, Integer sex, Date birthday,
			String qqNO, String registTime, String brifIntrodction,
			Double availablePredeposit, Double freezePredeposit,
			Double availableRcBalance, Double freezeRcBalance, String qqOpenId,
			String qqInfo, String sinaOpenId, String sinaInfo, String ww,
			Integer snsVisitNum, String memberQuicklink) {
		super();
		this.id = id;
		this.trueName = trueName;
		this.sex = sex;
		this.birthday = birthday;
		this.qqNO = qqNO;
		this.registTime = registTime;
		this.brifIntrodction = brifIntrodction;
		this.availablePredeposit = availablePredeposit;
		this.freezePredeposit = freezePredeposit;
		this.availableRcBalance = availableRcBalance;
		this.freezeRcBalance = freezeRcBalance;
		this.qqOpenId = qqOpenId;
		this.qqInfo = qqInfo;
		this.sinaOpenId = sinaOpenId;
		this.sinaInfo = sinaInfo;
		this.ww = ww;
		this.snsVisitNum = snsVisitNum;
		this.memberQuicklink = memberQuicklink;
	}

	/**
	 * 复制对象
	 * 
	 * @return 复制的对象实例
	 */
	public UserDetail copy() {
		return new UserDetail(id, trueName, sex, birthday, qqNO, registTime,
				brifIntrodction, availablePredeposit, freezePredeposit,
				availableRcBalance, freezeRcBalance, qqOpenId, qqInfo,
				sinaOpenId, sinaInfo, ww, snsVisitNum, memberQuicklink);
	}

	/**
	 * get 用户id
	 * 
	 * @return id 用户id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * set 用户id
	 * 
	 * @param id
	 *            用户id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * get 用户真实姓名
	 * 
	 * @return trueName 用户真实姓名
	 */
	public String getTrueName() {
		return trueName;
	}

	/**
	 * set 用户真实姓名
	 * 
	 * @param trueName
	 *            用户真实姓名
	 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/**
	 * get 性别
	 * 
	 * @return sex 性别
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * set 性别
	 * 
	 * @param sex
	 *            性别
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * get 生日
	 * 
	 * @return birthday 生日
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * set 生日
	 * 
	 * @param birthday
	 *            生日
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * get qq号
	 * 
	 * @return qqNO qq号
	 */
	public String getQqNO() {
		return qqNO;
	}

	/**
	 * set qq号
	 * 
	 * @param qqNO
	 *            qq号
	 */
	public void setQqNO(String qqNO) {
		this.qqNO = qqNO;
	}

	/**
	 * get 注册时间
	 * 
	 * @return registTime 注册时间
	 */
	public String getRegistTime() {
		return registTime;
	}

	/**
	 * set 注册时间
	 * 
	 * @param registTime
	 *            注册时间
	 */
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	/**
	 * get 个人简介
	 * 
	 * @return brifIntrodction 个人简介
	 */
	public String getBrifIntrodction() {
		return brifIntrodction;
	}

	/**
	 * set 个人简介
	 * 
	 * @param brifIntrodction
	 *            个人简介
	 */
	public void setBrifIntrodction(String brifIntrodction) {
		this.brifIntrodction = brifIntrodction;
	}

	/**
	 * get 预存款可用金额
	 * 
	 * @return availablePredeposit 预存款可用金额
	 */
	public Double getAvailablePredeposit() {
		return availablePredeposit;
	}

	/**
	 * set 预存款可用金额
	 * 
	 * @param availablePredeposit
	 *            预存款可用金额
	 */
	public void setAvailablePredeposit(double availablePredeposit) {
		this.availablePredeposit = availablePredeposit;
	}

	/**
	 * get 预存款冻结金额
	 * 
	 * @return freezePredeposit 预存款冻结金额
	 */
	public Double getFreezePredeposit() {
		return freezePredeposit;
	}

	/**
	 * set 预存款冻结金额
	 * 
	 * @param freezePredeposit
	 *            预存款冻结金额
	 */
	public void setFreezePredeposit(double freezePredeposit) {
		this.freezePredeposit = freezePredeposit;
	}

	public Double getAvailableRcBalance() {
		return this.availableRcBalance;
	}

	public void setAvailableRcBalance(Double availableRcBalance) {
		this.availableRcBalance = availableRcBalance;
	}

	public Double getFreezeRcBalance() {
		return this.freezeRcBalance;
	}

	public void setFreezeRcBalance(Double freezeRcBalance) {
		this.freezeRcBalance = freezeRcBalance;
	}

	/**
	 * get
	 * 
	 * @return qqOpenId
	 */
	public String getQqOpenId() {
		return qqOpenId;
	}

	/**
	 * set
	 * 
	 * @param qqOpenId
	 */
	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	/**
	 * get qq关联账号信息
	 * 
	 * @return qqInfo qq关联账号信息
	 */
	public String getQqInfo() {
		return qqInfo;
	}

	/**
	 * set qq关联账号信息
	 * 
	 * @param qqInfo
	 *            qq关联账号信息
	 */
	public void setQqInfo(String qqInfo) {
		this.qqInfo = qqInfo;
	}

	/**
	 * get 新浪关联账号
	 * 
	 * @return sinaOpenId 新浪关联账号
	 */
	public String getSinaOpenId() {
		return sinaOpenId;
	}

	/**
	 * set 新浪关联账号
	 * 
	 * @param sinaOpenId
	 *            新浪关联账号
	 */
	public void setSinaOpenId(String sinaOpenId) {
		this.sinaOpenId = sinaOpenId;
	}

	/**
	 * get 新浪关联账号信息
	 * 
	 * @return sinaInfo 新浪关联账号信息
	 */
	public String getSinaInfo() {
		return sinaInfo;
	}

	/**
	 * set 新浪关联账号信息
	 * 
	 * @param sinaInfo
	 *            新浪关联账号信息
	 */
	public void setSinaInfo(String sinaInfo) {
		this.sinaInfo = sinaInfo;
	}

	/**
	 * get 阿里旺旺
	 * 
	 * @return ww 阿里旺旺
	 */
	public String getWw() {
		return ww;
	}

	/**
	 * set 阿里旺旺
	 * 
	 * @param ww
	 *            阿里旺旺
	 */
	public void setWw(String ww) {
		this.ww = ww;
	}

	/**
	 * get sns访问量
	 * 
	 * @return snsVisitNum sns访问量
	 */
	public Integer getSnsVisitNum() {
		return snsVisitNum;
	}

	/**
	 * set sns访问量
	 * 
	 * @param snsVisitNum
	 *            sns访问量
	 */
	public void setSnsVisitNum(int snsVisitNum) {
		this.snsVisitNum = snsVisitNum;
	}

	public String getMemberQuicklink() {
		return this.memberQuicklink;
	}

	public void setMemberQuicklink(String memberQuicklink) {
		this.memberQuicklink = memberQuicklink;
	}
}
