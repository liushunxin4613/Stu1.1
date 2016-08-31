package com.fengyang.util.ui;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class KitKatUtils {

	/**
	 * 设置状态栏透明
	 * @param activity 目标Activity
	 * @param isLight 是否设置状态栏图标为白色，仅对MIUI有效
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static boolean setStatusBar(Activity activity, boolean isLight) {
		Window window = activity.getWindow();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Log.i("Phone", "is "+Build.MANUFACTURER +" Device");
			if ("xiaomi".equalsIgnoreCase(Build.MANUFACTURER)) {
				Class<?> clazz = window.getClass();
				try {
					int tranceFlag = 0;
					int darkModeFlag = 0;
					Class<?> layoutParams = Class
							.forName("android.view.MiuiWindowManager$LayoutParams");

					Field field = layoutParams
							.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
					tranceFlag = field.getInt(layoutParams);

					field = layoutParams
							.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
					darkModeFlag = field.getInt(layoutParams);

					Method extraFlagField = clazz.getMethod("setExtraFlags",
							int.class, int.class);

					if (isLight)
						// 只需要状态栏透明
						extraFlagField.invoke(window, tranceFlag, tranceFlag);
					else
						// 状态栏透明且黑色字体
						extraFlagField.invoke(window,
								tranceFlag | darkModeFlag, tranceFlag
										| darkModeFlag);
					// 清除黑色字体
					// extraFlagField.invoke(window, 0, darkModeFlag);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return true;
			}
			// Translucent status bar
			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			return true;
		}
		return false;
	}

	/**
	 * 设置导航栏透明
	 * @param activity 目标Activity
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void setNavigationBar(Activity activity) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = activity.getWindow();
			// Translucent navigation bar
			window.setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
}
