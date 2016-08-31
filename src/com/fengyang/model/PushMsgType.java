package com.fengyang.model;

import com.alibaba.fastjson.JSON;

/**
 * ������Ϣ����ģ����
 * 
 * @author HeJie
 *
 */
public class PushMsgType {

	/**
	 * ��Ϣ���ͣ������û���Ϣ
	 */
	public static final int TYPE_UPDATE_USER = 0x1;
	/**
	 * ��Ϣ���ͣ������ְ����
	 */
	public static final int TYPE_OPEN_PART_TIME = 0x2;
	/**
	 * ��Ϣ���ͣ������������
	 */
	public static final int TYPE_OPEN_SECOND_HAND = 0x4;
	/**
	 * ��Ϣ���ͣ���URL
	 */
	public static final int TYPE_OPEN_URL = 0x8;
	/**
	 * ��Ϣ����
	 */
	public Integer type;
	/**
	 * �Ƿ񵯳��Ի�����ʾ��Ϣ
	 */
	public Boolean showDialog;
	/**
	 * ��Ϣ����1
	 */
	public Integer arg1;
	/**
	 * ��Ϣ����2
	 */
	public Integer arg2;
	/**
	 * ��Ϣ���ɶ��󣬴���κ����͵�����
	 */
	public Object obj;

	/**
	 * ת����Json�ַ���
	 * 
	 * @return
	 */
	public Object toJSON() {
		return JSON.toJSON(this);
	}
	
	/**
	 * ��Json�ַ���ת���ɶ���
	 * 
	 * @param jsonStr
	 * @return
	 */
	public PushMsgType parseMsg(String jsonStr) {
		return JSON.parseObject(jsonStr, PushMsgType.class);
	}

}
