package com.fengyang.util.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 沉浸式通知栏效果的Activity
 * 
 * @author HeJie
 * @version 1.0
 */
public class ImmersionActivity extends FragmentActivity {

	/**
	 * 标题栏的字体颜色，是否为白色，默认为白色
	 */
	private boolean isLight = true;
	/**
	 * 状态栏背景色
	 */
	private int statusColor;
	
	private SystemBarTintManager systemBarManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		KitKatUtils.setStatusBar(this, isLight);
//		systemBarManager = new SystemBarTintManager(this);
//		systemBarManager.setStatusBarTintEnabled(true);
//		systemBarManager.setStatusBarTintColor(statusColor);
	}

	/**
	 * @return 返回通知栏的颜色，是否为白色
	 */
	public boolean isLight() {
		return isLight;
	}

	/**
	 * @param isLight
	 *            设置通知栏的字体颜色是否为白色（仅对miui有效）,true 白色; false 黑色
	 */
	public void setLight(boolean isLight) {
		this.isLight = isLight;
		KitKatUtils.setStatusBar(this, isLight);
	}

	/**
	 * 设置通知栏背景色
	 * 
	 * @param resid
	 */
	public void setStatusColor(int color) {
//		this.getWindow().setBackgroundDrawableResource(resid);
		this.statusColor = color;
		systemBarManager = new SystemBarTintManager(this);
		systemBarManager.setStatusBarTintEnabled(true);
		systemBarManager.setStatusBarTintColor(statusColor);
	}

	/**
	 * 设置通知栏背景色
	 * 
	 * @param drawable
	 */
	public void setBackgroundDrawable(Drawable drawable) {
		this.getWindow().setBackgroundDrawable(drawable);
	}
}
