package com.fengyang.customLib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.fengyang.stu.R;

public class ActionBarCheckBox extends ActionProvider {

	private LayoutInflater inflater;

	private CheckBox cb;

	private boolean isChecked;

	private OnCheckListener onChecklistener;
	/**
	 * 标记点击事件监听器是否可用
	 */
	private boolean isCheckListenerEnable = true;

	public interface OnCheckListener {
		public void checkButton(CompoundButton buttonView, boolean isChecked);
	}

	public ActionBarCheckBox(Context context) {
		super(context);
		inflater = LayoutInflater.from(context);
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateActionView() {
		View view = inflater.inflate(R.layout.menu_actionprovider_layout, null);
		cb = (CheckBox) view.findViewById(R.id.second_detail_collect);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (onChecklistener != null && isCheckListenerEnable)
					onChecklistener.checkButton(buttonView, isChecked);
			}
		});
		cb.setChecked(isChecked);
		isCheckListenerEnable = true;
		return view;
	}

	public CheckBox getCb() {
		return cb;
	}

	public void setCb(CheckBox cb) {
		this.cb = cb;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
		if (cb != null)
			cb.setChecked(isChecked);
	}
	
	public boolean isChecked() {
		return isChecked;
	}

	/**
	 * 设置按钮选中状态，不会触发事件监听器
	 * @param isChecked
	 */
	public void setCheckedNoListener(boolean isChecked) {
		this.isChecked = isChecked;
		if (cb != null){
			isCheckListenerEnable = false;
			cb.setChecked(isChecked);
			isCheckListenerEnable = true;
		} else {
			isCheckListenerEnable = false;
		}
	}

	public boolean isCheckListenerEnable() {
		return isCheckListenerEnable;
	}

	/**
	 * 设置点击事件监听器是否可用
	 * 
	 * @param isCheckListenerEnable
	 */
	public void setCheckListenerEnable(boolean isCheckListenerEnable) {
		this.isCheckListenerEnable = isCheckListenerEnable;
	}

	public OnCheckListener getOnChecklistener() {
		return onChecklistener;
	}

	public void setOnChecklistener(OnCheckListener onChecklistener) {
		this.onChecklistener = onChecklistener;
	}

}
