package com.fengyang.util.ui;

import java.lang.ref.WeakReference;

import android.os.Handler;

public class BaseUIHandler<T> extends Handler {

	/**
	 * UI对象的弱引用
	 */
	protected WeakReference<T> reference;
	
	public BaseUIHandler(T target) {
		reference = new WeakReference<T>(target);
	}
	
	protected T get(){
		return reference.get();
	}
	
}
