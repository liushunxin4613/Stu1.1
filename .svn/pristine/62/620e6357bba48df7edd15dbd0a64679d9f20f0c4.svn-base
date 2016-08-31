package com.fengyang.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fengyang.stu.R;

public class ConfirmDialog extends BaseDialog {

	private TextView msgView;

	private CharSequence msg;
	/**
	 * 对话框的风格
	 */
	private int dialogStyle;

	/**
	 * 对话框风格，居中
	 */
	public static final int CONFIRM_STYLE_CENTER = 0;
	/**
	 * 对话框风格，底部
	 */
	public static final int CONFIRM_STYLE_BOTTOM = 1;

	public ConfirmDialog() {
	}

	public ConfirmDialog(int dialogStyle, String title,
			String msg, String posText, String nevText) {
		this.dialogStyle = dialogStyle;
		if (dialogStyle == CONFIRM_STYLE_BOTTOM)
			this.theme = R.style.StuDialogTheme_Bottom;
		else if (dialogStyle == CONFIRM_STYLE_CENTER)
			this.theme = R.style.StuDialogTheme_Center;
		this.title = title;
		this.msg = msg;
		this.positiveText = posText;
		this.nevigativeText = nevText;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		if (dialogStyle == CONFIRM_STYLE_BOTTOM)
			rootView = inflater.inflate(R.layout.dialog_confirm, container,
					false);
		else if (dialogStyle == CONFIRM_STYLE_CENTER)
			rootView = inflater.inflate(R.layout.dialog_confirm_center,
					container, false);
		titleView = (TextView) rootView.findViewById(R.id.dialog_confirm_title);
		msgView = (TextView) rootView.findViewById(R.id.dialog_confirm_msg);
		positiveBtn = (Button) rootView
				.findViewById(R.id.dialog_positive_button);
		nevigativeBtn = (Button) rootView
				.findViewById(R.id.dialog_nevigative_button);

		if (title != null) {
			titleView.setVisibility(View.VISIBLE);
			titleView.setText(title);
		}
		if (msg != null) {
			msgView.setVisibility(View.VISIBLE);
			msgView.setText(msg);
		}
		if (positiveText != null){
			positiveBtn.setText(positiveText);
			positiveBtn.setVisibility(View.VISIBLE);
		}
		if (nevigativeText != null){
			nevigativeBtn.setText(nevigativeText);
			nevigativeBtn.setVisibility(View.VISIBLE);
		}
		if (positiveText == null || nevigativeText == null){
			View line = rootView.findViewById(R.id.dialog_button_ling);
			line.setVisibility(View.GONE);
		}

		positiveBtn.setOnClickListener(this);
		nevigativeBtn.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (dialogStyle == CONFIRM_STYLE_BOTTOM) {
			Dialog dialog = getDialog();
			if (dialog != null) {
				Window window = dialog.getWindow();
				window.setGravity(Gravity.BOTTOM);
				window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.WRAP_CONTENT);
				window.setBackgroundDrawable(new ColorDrawable(
						Color.TRANSPARENT));
			}
		}
	}

	public void setContentMsg(int resId) {
		msg = context.getString(resId);
		if (msgView != null) {
			msgView.setText(msg);
		}
	}
}
