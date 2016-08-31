package com.fengyang.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.fengyang.stu.R;
import com.fengyang.util.ui.UIUtils;

/**
 * 显示选择按钮列的对话框
 * 
 * @author HeJie
 *
 */
public class ChooseListDialog extends BaseDialog implements OnClickListener {

	ArrayList<Button> buttons;

	LinearLayout buttonContainer;

	public interface OnPhotoOptionClickListener {
		/**
		 * 点击拍照时调用的回调函数
		 */
		public void onTakePhotoClicked();

		/**
		 * 点击从系统选择图片时调用
		 */
		public void onChoosePhotoClicked();
	}

	public ChooseListDialog() {
	}

	public ChooseListDialog(Context context) {
		super(context);
		buttons = new ArrayList<Button>();
		this.theme = R.style.StuDialogTheme_Bottom;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_choose_list,
				container, false);

		buttonContainer = (LinearLayout) rootView
				.findViewById(R.id.dialog_button_container);
		nevigativeBtn = (Button) rootView.findViewById(R.id.dialog_cancel_btn);

		for (Button button : buttons) {
			buttonContainer.addView(button);
		}
		nevigativeBtn.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		Dialog dialog = getDialog();
		if (dialog != null) {
			Window window = dialog.getWindow();
			window.setGravity(Gravity.BOTTOM);
			window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		}
	}

	public Button addButton(CharSequence text) {
		Button button = new Button(context);
		int height = context.getResources().getDimensionPixelSize(
				R.dimen.radiu_button_height);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				height);
		int margin = UIUtils.dip2px(context, 6);
		params.setMargins(margin, margin, margin, margin);
		button.setLayoutParams(params);
		button.setText(text);
		button.setTextColor(context.getResources().getColor(R.color.text_blue));
		button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		button.setOnClickListener(this);
		button.setBackgroundResource(R.drawable.dialog_white_button_selector);
		if (buttonContainer != null) {
			buttonContainer.addView(button);
		}
		buttons.add(button);
		return button;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(nevigativeBtn)) {
			this.dismiss();
		} else {
			int index = buttons.indexOf(v);
			if (index != -1 && onclickListener != null) {
				this.onclickListener.onClick(this, index);
			}
		}
		this.dismiss();
	}

}
