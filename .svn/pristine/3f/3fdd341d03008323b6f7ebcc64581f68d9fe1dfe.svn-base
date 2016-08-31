package com.fengyang.entity;

/**
 * 用户数据模型抽象类
 * 
 * @author HeJie
 * 
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 8990738576222263790L;
	/**
	 * 用户验证状态，未验证
	 */
	public static final short VERIFY_TYPE_NUVERIFY = 0;
	/**
	 * 用户验证状态，审核中
	 */
	public static final short VERIFY_TYPE_COMMIT = 1;
	/**
	 * 用户验证状态，已通过
	 */
	public static final short VERIFY_TYPE_PASSED = 2;
	/**
	 * 用户验证状态，未通过
	 */
	public static final short VERIFY_TYPE_REJECT = 3;
	/**
	 * 用户类型，学生
	 */
	public static final int USER_TYPE_STU = 0;
	/**
	 * 用户类型，商家
	 */
	public static final int USER_TYPE_MER = 1;
	/**
	 * 用户类型，官方
	 */
	public static final int USER_TYPE_FY = 2;
	/**
	 * 用户id
	 */
	private Integer id;
	/**
	 * 用户昵称
	 */
	private String name;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 是否绑定邮箱
	 */
	private Boolean isEmailBind;
	/**
	 * 用户电话号码
	 */
	private String phone;
	/**
	 * 是否绑定手机号
	 */
	private Boolean isPhoneBind;
	/**
	 * 头像路径
	 */
	private String photoPath;
	/**
	 * 会员开启状态
	 */
	private Boolean state;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 支付密码
	 */
	private String paypwd;
	/**
	 * 是否验证
	 */
	private Short isVerify;
	/**
	 * 当前登录ip
	 */
	private String ip;
	/**
	 * 上次登录ip
	 */
	private String lastIp;
	/**
	 * 登陆次数
	 */
	private Integer loginNum;
	/**
	 * 当前登陆时间
	 */
	private String loginTime;
	/**
	 * 上次登录时间
	 */
	private String lastLoginTime;
	/**
	 * 会员积分
	 */
	private Integer points;
	/**
	 * 会员经验值
	 */
	private Integer exppoints;
	// /**
	// * 会员信用度
	// */
	// private int credit;
	/**
	 * 是否可以举报
	 */
	private Boolean informAllow;
	/**
	 * 是否有购买权限
	 */
	private Boolean isBuy;
	/**
	 * 会员是否有咨询和发送站内信的权限
	 */
	private Boolean isAllowTalk;
	/**
	 * 用户类型，0：学生，1：商家，2：官方用户
	 */
	private Integer userType;
	// ****************** 对应关系 *********************//

	/**
	 * 用户详细信息
	 */
	private UserDetail userDetail;

	/**
	 * 默认无参构造函数
	 */
	public User() {
	}
	
	public User(Integer id){
		this.id = id;
	}

	/**
	 * 最简构造函数
	 * 
	 * @param id
	 * @param name
	 * @param email
	 * @param phone
	 * @param state
	 * @param password
	 * @param isVerify
	 * @param ip
	 * @param loginNum
	 * @param loginTime
	 * @param lastLoginTime
	 * @param lastIp
	 * @param points
	 */
	public User(Integer id, String name, String email, String phone,
			Boolean state, String password, Short isVerify, String ip,
			Integer loginNum, String loginTime, String lastLoginTime,
			String lastIp, Integer points) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.state = state;
		this.password = password;
		this.isVerify = isVerify;
		this.ip = ip;
		this.loginNum = loginNum;
		this.loginTime = loginTime;
		this.lastLoginTime = lastLoginTime;
		this.lastIp = lastIp;
		this.points = points;
	}

	public User(Integer id, String name, String email, Boolean isEmailBind,
			String phone, Boolean isPhoneBind, String photoPath, Boolean state,
			String password, String paypwd, Short isVerify, String ip,
			String lastIp, Integer loginNum, String loginTime,
			String lastLoginTime, Integer points, Integer exppoints,
			Boolean informAllow, Boolean isBuy, Boolean isAllowTalk,
			Integer userType) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.isEmailBind = isEmailBind;
		this.phone = phone;
		this.isPhoneBind = isPhoneBind;
		this.photoPath = photoPath;
		this.state = state;
		this.password = password;
		this.paypwd = paypwd;
		this.isVerify = isVerify;
		this.ip = ip;
		this.lastIp = lastIp;
		this.loginNum = loginNum;
		this.loginTime = loginTime;
		this.lastLoginTime = lastLoginTime;
		this.points = points;
		this.exppoints = exppoints;
		this.informAllow = informAllow;
		this.isBuy = isBuy;
		this.isAllowTalk = isAllowTalk;
		this.userType = userType;
	}

	/**
	 * 复制对象，不包括UserDetail
	 * 
	 * @return 复制的对象实例
	 */
	public User copy() {
		return new User(id, name, email, isEmailBind, phone, isPhoneBind,
				photoPath, state, password, paypwd, isVerify, ip, lastIp,
				loginNum, loginTime, lastLoginTime, points, exppoints,
				informAllow, isBuy, isAllowTalk, userType);
	}
	
	/**
	 * 复制对象，只复制id
	 * @return
	 */
	public User copyId(){
		return new User(id);
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
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * get 用户昵称
	 * 
	 * @return name 用户昵称
	 */
	public String getName() {
		return name;
	}

	/**
	 * set 用户昵称
	 * 
	 * @param name
	 *            用户昵称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get 用户邮箱
	 * 
	 * @return email 用户邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set 用户邮箱
	 * 
	 * @param email
	 *            用户邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsEmailBind() {
		return this.isEmailBind;
	}

	public void setIsEmailBind(Boolean isEmailBind) {
		this.isEmailBind = isEmailBind;
	}

	/**
	 * get 用户电话号码
	 * 
	 * @return phone 用户电话号码
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * set 用户电话号码
	 * 
	 * @param phone
	 *            用户电话号码
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getIsPhoneBind() {
		return this.isPhoneBind;
	}

	public void setIsPhoneBind(Boolean isPhoneBind) {
		this.isPhoneBind = isEmailBind;
	}

	/**
	 * get 用户头像路径
	 * 
	 * @return photoPath
	 */
	public String getPhotoPath() {
		return photoPath;
	}

	/**
	 * set 用户头像路径
	 * 
	 * @param photoPath
	 */
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	/**
	 * get 会员开启状态
	 * 
	 * @return state 会员开启状态
	 */
	public Boolean getState() {
		return state;
	}

	/**
	 * set 会员开启状态
	 * 
	 * @param state
	 *            会员开启状态
	 */
	public void setState(Boolean state) {
		this.state = state;
	}

	/**
	 * get 密码
	 * 
	 * @return password 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set 密码
	 * 
	 * @param password
	 *            密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPaypwd() {
		return this.paypwd;
	}

	public void setPaypwd(String memberPaypwd) {
		this.paypwd = memberPaypwd;
	}

	/**
	 * get 是否验证
	 * 
	 * @return isVerify 是否验证
	 */
	public Short getIsVerify() {
		return isVerify;
	}

	/**
	 * set 是否验证
	 * 
	 * @param isVerify
	 *            是否验证
	 */
	public void setIsVerify(Short isVerify) {
		this.isVerify = isVerify;
	}

	/**
	 * get 会员积分
	 * 
	 * @return points 会员积分
	 */
	public Integer getPoints() {
		return points;
	}

	/**
	 * set 会员积分
	 * 
	 * @param points
	 *            会员积分
	 */
	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getExppoints() {
		return this.exppoints;
	}

	public void setExppoints(Integer memberExppoints) {
		this.exppoints = memberExppoints;
	}

	/**
	 * get 当前登录ip
	 * 
	 * @return ip 当前登录ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * set 当前登录ip
	 * 
	 * @param ip
	 *            当前登录ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * get 登陆次数
	 * 
	 * @return loginNum 登陆次数
	 */
	public Integer getLoginNum() {
		return loginNum;
	}

	/**
	 * set 登陆次数
	 * 
	 * @param loginNum
	 *            登陆次数
	 */
	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	/**
	 * get 当前登陆时间
	 * 
	 * @return loginTime 当前登陆时间
	 */
	public String getLoginTime() {
		return loginTime;
	}

	/**
	 * set 当前登陆时间
	 * 
	 * @param loginTime
	 *            当前登陆时间
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * get 上次登录时间
	 * 
	 * @return lastLoginTime 上次登录时间
	 */
	public String getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * set 上次登录时间
	 * 
	 * @param lastLoginTime
	 *            上次登录时间
	 */
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * get 上次登录ip
	 * 
	 * @return lastIp 上次登录ip
	 */
	public String getLastIp() {
		return lastIp;
	}

	/**
	 * set 上次登录ip
	 * 
	 * @param lastIp
	 *            上次登录ip
	 */
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	/**
	 * get 是否可以举报
	 * 
	 * @return informAllow 是否可以举报
	 */
	public Boolean getInformAllow() {
		return informAllow;
	}

	/**
	 * set 是否可以举报
	 * 
	 * @param informAllow
	 *            是否可以举报
	 */
	public void setInformAllow(Boolean informAllow) {
		this.informAllow = informAllow;
	}

	/**
	 * get 是否有购买权限
	 * 
	 * @return isBuy 是否有购买权限
	 */
	public Boolean getIsBuy() {
		return isBuy;
	}

	/**
	 * set 是否有购买权限
	 * 
	 * @param isBuy
	 *            是否有购买权限
	 */
	public void setIsBuy(Boolean isBuy) {
		this.isBuy = isBuy;
	}

	/**
	 * get 会员是否有咨询和发送站内信的权限
	 * 
	 * @return isAllowTalk 会员是否有咨询和发送站内信的权限
	 */
	public Boolean getIsAllowTalk() {
		return isAllowTalk;
	}

	/**
	 * set 会员是否有咨询和发送站内信的权限
	 * 
	 * @param isAllowTalk
	 *            会员是否有咨询和发送站内信的权限
	 */
	public void setIsAllowTalk(Boolean isAllowTalk) {
		this.isAllowTalk = isAllowTalk;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer memberType) {
		this.userType = memberType;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
