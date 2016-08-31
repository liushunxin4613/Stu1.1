package com.fengyang.util;

import android.net.Uri;
import android.util.Log;

/**
 * 文件管理类
 * 
 * @author HeJie
 *
 */
public class FileManager {

	private static final String TAG = "FileManager";
	/**
	 * 用户头像类型
	 */
	public static final int USER_PHOTO = 0;
	/**
	 * 二手物品图片
	 */
	public static final int SECOND_HAND_IMAGE = 1;
	/**
	 * 兼职图片
	 */
	public static final int PART_TIME_IMAGE = 2;
	/**
	 * 二手评论图片
	 */
	public static final int SECOND_COMMENT_IMAGE = 3;
	/**
	 * 兼职评论图片
	 */
	public static final int PART_COMMENT_IMAGE = 4;
	/**
	 * 学生证验证图片
	 */
	public static final int STU_CARD_IMAGE = 5;
	/**
	 * 商家营业执照验证图片
	 */
	public static final int MERCHANT_LICENSE_IMAGE = 6;
	/**
	 * 身份证验证图片
	 */
	public static final int IDCARD_IMAGE = 7;
	/**
	 * 首页推送
	 */
	public static final int HOME_PUSH_IMAGE = 8;
	/**
	 * 普通图片
	 */
	public static final int NORMAL_IMAGE = 100;
	/**
	 * 文件
	 */
	public static final int NORMAL_FILE = 101;
	/**
	 * apk安装包文件
	 */
	public static final int APK_FILE = 110;

	/**
	 * 获取下载文件的url
	 * @param type 请求类型
	 * @param fileName 文件名
	 * @return
	 */
	public static String getUrl(int type, String fileName) {
		String url = null;
		switch (type) {
		case USER_PHOTO:
			Uri.Builder builder = Uri.parse(Config.SERVER_URL
					+ "downloadAction.action").buildUpon();
			builder.appendQueryParameter("type",
					String.valueOf(USER_PHOTO));
			builder.appendQueryParameter("fileName", fileName);
			url = builder.toString();
			break;

		default:
			break;
		}
		Log.d(TAG, "type = " + type + "  url=" + url);
		return url;
	}
}
