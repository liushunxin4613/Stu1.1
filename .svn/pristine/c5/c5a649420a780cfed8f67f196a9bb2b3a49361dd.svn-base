package com.fengyang.dialog;

import java.util.Date;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.fengyang.stu.R;

/**
 * 时间选择对话框
 * 
 * @author HeJie
 *
 */
public class DatePickerDialog extends BaseDialog {

	private DatePicker datePicker;
	private OnDateSetListener dateSetListener;
	private int year;
	private int month;
	private int day;
	private Date minDate;
	private Date maxDate;

	public DatePickerDialog() {
		
	}
	
	public DatePickerDialog(OnDateSetListener dateSetListener,
			int year, int monthOfYear, int dayOfMonth) {
		this.dateSetListener = dateSetListener;
		this.year = year;
		this.month = monthOfYear;
		this.day = dayOfMonth;
		this.theme = R.style.StuDialogTheme_Center;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialog_time_picker,
				container, false);
		titleView = (TextView) rootView.findViewById(R.id.dialog_title);
		datePicker = (DatePicker) rootView.findViewById(R.id.dialog_datePicker);
		datePicker.updateDate(year, month, day);
		if (minDate != null)
			datePicker.setMinDate(minDate.getTime());
		if (maxDate != null)
			datePicker.setMaxDate(maxDate.getTime());

		positiveBtn = (Button) rootView
				.findViewById(R.id.dialog_positive_button);

		if (title != null)
			titleView.setText(title);
		positiveBtn.setOnClickListener(this);
		return rootView;
	}

	public void setMinDate(long minDate) {
		this.minDate = new Date(minDate);
	}

	public void setMaxDate(long maxDate) {
		this.maxDate = new Date(maxDate);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.dialog_positive_button:
			this.year = datePicker.getYear();
			this.month = datePicker.getMonth();
			this.day = datePicker.getDayOfMonth();
			if (dateSetListener != null) {
				dateSetListener.onDateSet(datePicker, year, month, day);
			}
			this.dismiss();
			break;
		}
	}

}
