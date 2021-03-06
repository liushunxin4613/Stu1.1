package com.fengyang.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.fengyang.stu.R;

public class PersonDialog extends BaseDialog implements android.view.View.OnClickListener{

	protected TextView manTv;
	protected TextView womanTv;

	Context context;
	public PersonDialog(Context context) {
		super(context);
		this.context=context;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Dialog dialog = getDialog();
		if (dialog != null) {
			Window window = dialog.getWindow();
			window.setGravity(Gravity.CENTER);
			window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_publish_user_man,
				container, false);
		manTv = (TextView)rootView.findViewById(R.id.Tv_dialog_man);
		womanTv = (TextView)rootView.findViewById(R.id.Tv_dialog_woman);
		manTv.setOnClickListener(this);
		womanTv.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.Tv_dialog_man:
			this.onclickListener.onClick(this, 1);
			dismiss();
			break;
		case R.id.Tv_dialog_woman:
			this.onclickListener.onClick(this, 0);
			dismiss();
			break;
		}
	}
}
