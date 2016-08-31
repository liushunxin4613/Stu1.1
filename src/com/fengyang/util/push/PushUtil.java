package com.fengyang.util.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * ������صĹ�����
 * 
 * @author HeJie
 *
 */
public class PushUtil {

	/**
	 * �ٶ�����API��Key
	 */
	public final static String KEY_BAIDU_PUSH_API = "api_key";
	/**
	 * ������Ϣ��Ϣ�ļ�·��
	 */
	public final static String PREF_PUSH_INFO = "pushInfo";
	/**
	 * Key �󶨵�userId
	 */
	public final static String KEY_PREF_PUSH_BIND_UID = "user_id";
	/**
	 * ���豸Ψһ��Ӧ��id
	 */
	public final static String KEY_PREF_PUSH_CAHNNEL_ID = "chanel_id";
	/**
	 * Key �����appId
	 */
	public final static String KEY_PREF_PUSH_APP_ID = "app_id";

	/**
	 * ��ȡManifest�ļ��ж����MetaValue
	 * 
	 * @param context
	 * @param metaKey
	 * @return
	 */
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	/**
	 * ���ð���Ϣ�����浽SharePreference�ļ���
	 * 
	 * @param context
	 * @param appId
	 * @param userId
	 */
	public static void setBind(Context context, String appId,
			String userId, String channelId) {
		SharedPreferences sp = context.getSharedPreferences(PREF_PUSH_INFO,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(KEY_PREF_PUSH_APP_ID, appId);
		editor.putString(KEY_PREF_PUSH_BIND_UID, userId);
		editor.putString(KEY_PREF_PUSH_CAHNNEL_ID, channelId);
		editor.commit();
	}

	/**
	 * ��ȡ���Ͱ󶨺��userId
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserId(Context context) {
		SharedPreferences sp = context.getSharedPreferences(PREF_PUSH_INFO,
				Context.MODE_PRIVATE);
		return sp.getString(KEY_PREF_PUSH_BIND_UID, null);
	}
	
	/**
	 * ��ȡ�豸Ψһ��Ӧ��Channel Id
	 * @param context
	 * @return
	 */
	public static String getChannelId (Context context) {
		SharedPreferences sp = context.getSharedPreferences(PREF_PUSH_INFO,
				Context.MODE_PRIVATE);
		return sp.getString(KEY_PREF_PUSH_CAHNNEL_ID, null);
		
	}

}