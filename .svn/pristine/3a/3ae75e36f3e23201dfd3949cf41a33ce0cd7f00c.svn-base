package com.fengyang.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BaseDialog extends DialogFragment implements OnClickListener,
		DialogInterface {

	protected Context context;
	protected TextView titleView;
	protected Button positiveBtn;
	protected Button nevigativeBtn;

	protected int theme = 0;
	protected CharSequence title;
	protected CharSequence positiveText;
	protected CharSequence nevigativeText;
	protected DialogInterface.OnClickListener onclickListener;
	
	protected static final String KEY_TITLE = "title";
	protected static final String KEY_POSITIVE_BUTTON = "positiveText";
	protected static final String KEY_NEVIGATIVE_BUTTON = "nevigativeText";

	public BaseDialog() {
	}

	public BaseDialog(Context context) {
		this.context = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, theme);
		if (savedInstanceState != null) {
			title = savedInstanceState.getCharSequence(KEY_TITLE);
			positiveText = savedInstanceState.getCharSequence(KEY_POSITIVE_BUTTON);
			nevigativeText = savedInstanceState.getCharSequence(KEY_NEVIGATIVE_BUTTON);
		}
		this.context = getActivity();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putCharSequence(KEY_TITLE, title);
		outState.putCharSequence(KEY_POSITIVE_BUTTON, positiveText);
		outState.putCharSequence(KEY_NEVIGATIVE_BUTTON, nevigativeText);
		super.onSaveInstanceState(outState);
	}

	public DialogInterface.OnClickListener getOnclickListener() {
		return onclickListener;
	}

	public void setOnclickListener(
			DialogInterface.OnClickListener onclickListener) {
		this.onclickListener = onclickListener;
	}

	public CharSequence getTitle() {
		return title;
	}

	public void setTitle(CharSequence title) {
		this.title = title;
		if (titleView != null) {
			this.titleView.setVisibility(View.VISIBLE);
			this.titleView.setText(title);
		}
	}

	public void setPositiveButtonText(CharSequence text) {
		positiveText = text;
		if (positiveBtn != null) {
			positiveBtn.setText(positiveText);
		}
	}

	public void setNevigativeButtonText(CharSequence text) {
		nevigativeText = text;
		if (nevigativeBtn != null)
			nevigativeBtn.setText(nevigativeText);
	}

	
	@Override
	public void dismiss() {
		super.dismissAllowingStateLoss();
	}

	@Override
	public void cancel() {
		super.dismissAllowingStateLoss();
	}
	
	@Override
	public void onClick(View v) {
		if (v.equals(positiveBtn)) {
			if (onclickListener != null)
				onclickListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
			this.dismiss();
		} else if (v.equals(nevigativeBtn)) {
			if (onclickListener != null)
				onclickListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
			this.dismiss();
		}
	}
}
