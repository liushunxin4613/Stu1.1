package com.fengyang.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FormatUtils {

	protected static final String CHARSET = "utf-8";
	private static final String TAG = "FormatUtils";

	public static String priceFormat(double value) {
		if (value % 1.0 == 0)
			return String.valueOf((long) value);
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(value);
	}

	/**
	 * ��ʱ��������ַ�������ʽΪ yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormat(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return sdf.format(date);
	}

	/**
	 * ������ʽΪyyyy-MM-dd���ַ��������ؽ����õ���ʱ��
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ��ʱ��������ַ�������ʽΪ yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatWithTime(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		return sdf.format(date);
	}

	/**
	 * ������ʽΪyyyy-MM-dd HH:mm:ss���ַ��������ؽ����õ���ʱ��
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDateWithTime(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ������ת��ΪJson�ַ���
	 * 
	 * @param obj
	 *            ��Ҫת���Ķ���
	 * @return
	 */
	public static String getJSONString(Object obj) {
		return getJSONString(obj, null, null);
	}

	/**
	 * ������ת��ΪJson�ַ���
	 * 
	 * @param obj
	 *            ��Ҫת���Ķ���
	 * @param includesProperties
	 *            ��Ҫת��������
	 * @return
	 */
	public static String getJSONString(Object obj, String[] includesProperties) {
		return getJSONString(obj, includesProperties, null);
	}

	/**
	 * ������ת��ΪJson�ַ���
	 * 
	 * @param obj
	 *            ��Ҫת���Ķ���
	 * @param includesProperties
	 *            ��Ҫת��������
	 * @param excludesProperties
	 *            ����Ҫת��������
	 * @return
	 */
	public static String getJSONString(Object obj, String[] includesProperties,
			String[] excludesProperties) {
		FastjsonFilter filter = new FastjsonFilter();
		// excludes������includes
		if (excludesProperties != null && excludesProperties.length > 0) {
			filter.getExcludes().addAll(
					Arrays.<String> asList(excludesProperties));
		}
		if (includesProperties != null && includesProperties.length > 0) {
			filter.getIncludes().addAll(
					Arrays.<String> asList(includesProperties));
			Log.i(TAG, Arrays.toString(includesProperties));
		}
		// ʹ��SerializerFeature.WriteDateUseDateFormat����
		// �����л����ڸ�ʽ������Ϊyyyy-MM-dd hh24:mi:ss
		// ʹ��SerializerFeature.DisableCircularReferenceDetect���Թر����ü�������

		String json = JSON.toJSONString(obj, filter,
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.DisableCircularReferenceDetect);
		Log.i(TAG, "ת�����JSON�ַ�����" + json);

		return json;
	}

	/**
	 * �������ʽ�����ж��ַ���������
	 * 
	 * @param str
	 *            ��Ҫ������Ŀ���ַ���
	 * @return 0��δ֪��������<br/>
	 *         1���ֻ���<br/>
	 *         2�������ʼ�<br/>
	 *         3������<br/>
	 *         4������<br/>
	 */
	public static int praseStringType(String str) {
		//^(\\d{3,4}-?)\\d{7,8}|\\((\\d{3,4}\\)-?)\\d{7,8}$
		if (str.matches("1[3|5|7|8|][0-9]{9}")) {
			return 1;
		} else if (str
				.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)"
						+ "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
			return 2;
		} else if (str
				.matches("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}")) {
			if (str.subSequence(0, 1).equals(1)||!str.subSequence(0, 1).equals(0)) {
				return 0;
			}
			return 3;
		} else if (str
				.matches("^[a-zA-Z0-9`~!@#$%^&*()+=|_{}':;',//[//].\\<>/?]{6,20}$")) {
			return 4;
		}
		return 0;
	}

}