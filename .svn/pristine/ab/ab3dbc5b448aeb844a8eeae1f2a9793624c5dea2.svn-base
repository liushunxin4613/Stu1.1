package com.fengyang.model;

import com.alibaba.fastjson.JSON;

/**
 * 推送消息类型模型类
 * 
 * @author HeJie
 *
 */
public class PushMsgType {

	/**
	 * 消息类型，更新用户信息
	 */
	public static final int TYPE_UPDATE_USER = 0x1;
	/**
	 * 消息类型，进入兼职详情
	 */
	public static final int TYPE_OPEN_PART_TIME = 0x2;
	/**
	 * 消息类型，进入二手详情
	 */
	public static final int TYPE_OPEN_SECOND_HAND = 0x4;
	/**
	 * 消息类型，打开URL
	 */
	public static final int TYPE_OPEN_URL = 0x8;
	/**
	 * 消息类型
	 */
	public Integer type;
	/**
	 * 是否弹出对话框显示消息
	 */
	public Boolean showDialog;
	/**
	 * 消息参数1
	 */
	public Integer arg1;
	/**
	 * 消息参数2
	 */
	public Integer arg2;
	/**
	 * 消息自由对象，存放任何类型的数据
	 */
	public Object obj;

	/**
	 * 转化成Json字符串
	 * 
	 * @return
	 */
	public Object toJSON() {
		return JSON.toJSON(this);
	}
	
	/**
	 * 将Json字符串转换成对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public PushMsgType parseMsg(String jsonStr) {
		return JSON.parseObject(jsonStr, PushMsgType.class);
	}

}
