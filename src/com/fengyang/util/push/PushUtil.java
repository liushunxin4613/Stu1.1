package com.fengyang.util.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

/**
 * 推送相关的工具类
 * 
 * @author HeJie
 *
 */
public class PushUtil {

	/**
	 * 百度推送API的Key
	 */
	public final static String KEY_BAIDU_PUSH_API = "api_key";
	/**
	 * 推送消息信息文件路径
	 */
	public final static String PREF_PUSH_INFO = "pushInfo";
	/**
	 * Key 绑定的userId
	 */
	public final static String KEY_PREF_PUSH_BIND_UID = "user_id";
	/**
	 * 与设备唯一对应的id
	 */
	public final static String KEY_PREF_PUSH_CAHNNEL_ID = "chanel_id";
	/**
	 * Key 程序的appId
	 */
	public final static String KEY_PREF_PUSH_APP_ID = "app_id";

	/**
	 * 获取Manifest文件中定义的MetaValue
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
	 * 设置绑定信息，保存到SharePreference文件中
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
	 * 获取推送绑定后的userId
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
	 * 获取设备唯一对应的Channel Id
	 * @param context
	 * @return
	 */
	public static String getChannelId (Context context) {
		SharedPreferences sp = context.getSharedPreferences(PREF_PUSH_INFO,
				Context.MODE_PRIVATE);
		return sp.getString(KEY_PREF_PUSH_CAHNNEL_ID, null);
		
	}

}
