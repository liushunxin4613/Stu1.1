package com.fengyang.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengyang.stu.R;

public class LoadingDialog extends BaseDialog {

	private CharSequence content;

	private TextView contentTV;

	private OnBackPressedLisener onBackPressedListener;

	public interface OnBackPressedLisener {
		
		public void onBackPressed();
	}

	public LoadingDialog() {
	}

	public LoadingDialog(CharSequence content) {
		this.content = content;
		this.theme = R.style.StuDialogTheme_Loading;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_loading, container,
				false);
		contentTV = (TextView) rootView.findViewById(R.id.dialog_loading_text);

		if (content != null) {
			contentTV.setText(content);
			contentTV.setVisibility(View.VISIBLE);
		}
		getDialog().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (KeyEvent.KEYCODE_BACK == keyCode) {
					switch (event.getAction()) {
					case KeyEvent.ACTION_DOWN:

						break;
					case KeyEvent.ACTION_UP:
						if (onBackPressedListener != null)
							onBackPressedListener.onBackPressed();
						break;
					}
				}
				return false;
			}
		});
		setCancelable(false);
		return rootView;
	}
	
	public OnBackPressedLisener getOnBackPressedListener() {
		return onBackPressedListener;
	}

	public void setOnBackPressedListener(
			OnBackPressedLisener onBackPressedListener) {
		this.onBackPressedListener = onBackPressedListener;
	}

}
