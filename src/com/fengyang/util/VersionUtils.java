package com.fengyang.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.fengyang.stu.R;

public class VersionUtils {

	/**
	 * ��ȡ�汾���� VersionName
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context)// ��ȡ�汾�� eg:1.1
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return context.getString(R.string.version_unknown);
		}
	}

	/**
	 * ��ȡ�汾�� VersionCode
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context)// ��ȡ�汾��(�ڲ�ʶ���)eg:2
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * ��ȡApp����
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context){
        String appName = null;
        try {
        	PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
            return pi.applicationInfo.loadLabel(context.getPackageManager()).toString(); 
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
		return appName;
     }
}