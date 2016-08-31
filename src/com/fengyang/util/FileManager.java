package com.fengyang.util;

import android.net.Uri;
import android.util.Log;

/**
 * �ļ�������
 * 
 * @author HeJie
 *
 */
public class FileManager {

	private static final String TAG = "FileManager";
	/**
	 * �û�ͷ������
	 */
	public static final int USER_PHOTO = 0;
	/**
	 * ������ƷͼƬ
	 */
	public static final int SECOND_HAND_IMAGE = 1;
	/**
	 * ��ְͼƬ
	 */
	public static final int PART_TIME_IMAGE = 2;
	/**
	 * ��������ͼƬ
	 */
	public static final int SECOND_COMMENT_IMAGE = 3;
	/**
	 * ��ְ����ͼƬ
	 */
	public static final int PART_COMMENT_IMAGE = 4;
	/**
	 * ѧ��֤��֤ͼƬ
	 */
	public static final int STU_CARD_IMAGE = 5;
	/**
	 * �̼�Ӫҵִ����֤ͼƬ
	 */
	public static final int MERCHANT_LICENSE_IMAGE = 6;
	/**
	 * ���֤��֤ͼƬ
	 */
	public static final int IDCARD_IMAGE = 7;
	/**
	 * ��ҳ����
	 */
	public static final int HOME_PUSH_IMAGE = 8;
	/**
	 * ��ͨͼƬ
	 */
	public static final int NORMAL_IMAGE = 100;
	/**
	 * �ļ�
	 */
	public static final int NORMAL_FILE = 101;
	/**
	 * apk��װ���ļ�
	 */
	public static final int APK_FILE = 110;

	/**
	 * ��ȡ�����ļ���url
	 * @param type ��������
	 * @param fileName �ļ���
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
