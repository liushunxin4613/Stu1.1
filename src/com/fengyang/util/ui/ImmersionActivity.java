package com.fengyang.util.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * ����ʽ֪ͨ��Ч����Activity
 * 
 * @author HeJie
 * @version 1.0
 */
public class ImmersionActivity extends FragmentActivity {

	/**
	 * ��������������ɫ���Ƿ�Ϊ��ɫ��Ĭ��Ϊ��ɫ
	 */
	private boolean isLight = true;
	/**
	 * ״̬������ɫ
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
	 * @return ����֪ͨ������ɫ���Ƿ�Ϊ��ɫ
	 */
	public boolean isLight() {
		return isLight;
	}

	/**
	 * @param isLight
	 *            ����֪ͨ����������ɫ�Ƿ�Ϊ��ɫ������miui��Ч��,true ��ɫ; false ��ɫ
	 */
	public void setLight(boolean isLight) {
		this.isLight = isLight;
		KitKatUtils.setStatusBar(this, isLight);
	}

	/**
	 * ����֪ͨ������ɫ
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
	 * ����֪ͨ������ɫ
	 * 
	 * @param drawable
	 */
	public void setBackgroundDrawable(Drawable drawable) {
		this.getWindow().setBackgroundDrawable(drawable);
	}
}
