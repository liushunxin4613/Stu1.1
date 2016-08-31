package com.fengyang.activity;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.entity.AppSecondHand;
import com.fengyang.entity.AppSecondHandReport;
import com.fengyang.entity.User;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.ImmersionActivity;

public class ReportSecondHandActivity extends ImmersionActivity implements
		OnClickListener {
	private LoadingDialog dialog;
	private CheckBox falseimformation;
	private CheckBox falseCompan;
	private CheckBox conduit_Company;
	private EditText detailreasonET;
	private EditText yourphoneET;
	private Button submitBT;
	private Button reportphoneBT;
	private boolean Isfalseimformation = false;
	private boolean IsfalseCompan = false;
	private boolean Isconduit_Company = false;
	private AppSecondHandReport appSecondHandReport;
	private AppSecondHand secondHand;
	private RequestQueue mQueue;

	public static final String TAG = "ReportSecondFragment";
	protected static final String TAG_REPORT = "reportSecond";
	private static String TAG_GET_SECOND_HAND ="getSecondHand";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_second_hand);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		setStatusColor(getResources().getColor(R.color.immersionColor));
		PerpareUI();
		getDate();

		submitBT.setOnClickListener(this);
		reportphoneBT.setOnClickListener(this);
		falseimformation
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Isfalseimformation = true;
						} else {
							Isfalseimformation = false;
						}
					}
				});
		falseCompan
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							IsfalseCompan = true;
						} else {
							IsfalseCompan = false;
						}
					}
				});
		conduit_Company
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Isconduit_Company = true;
						} else {
							Isconduit_Company = false;
						}
					}
				});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}

	private void PerpareUI() {
		falseimformation = (CheckBox) findViewById(R.id.parttime_falseimformation_checkbox);
		falseCompan = (CheckBox) findViewById(R.id.Parttime_FalseCompany_checkbox);
		conduit_Company = (CheckBox) findViewById(R.id.parttime_Conduit_Company_checkbox);
		detailreasonET = (EditText) findViewById(R.id.parttime_detailreasonET);
		yourphoneET = (EditText) findViewById(R.id.parttime_yourphoneET);
		submitBT = (Button) findViewById(R.id.parttime_submitBT);
		reportphoneBT = (Button) findViewById(R.id.parttime_reportphoneBT);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.parttime_submitBT:
			submitData();
			break;
		case R.id.parttime_reportphoneBT:

			break;
		}
	}

	/**
	 * 检查数据
	 * 
	 * @return
	 */
	private boolean checkdata() {
		if (Isfalseimformation == false && IsfalseCompan == false
				&& Isconduit_Company == false) {
			MainActivity.showToast(ReportSecondHandActivity.this,
					R.string.reprot_second_resource);
			return false;
		}
		if (detailreasonET.getText().length() == 0) {
			MainActivity.showToast(ReportSecondHandActivity.this,
					R.string.reprot_second_detail);
			return false;
		}
		if (yourphoneET.getText().length() == 0) {
			MainActivity.showToast(ReportSecondHandActivity.this,
					R.string.reprot_second_phone);
			return false;
		}else if (FormatUtils.praseStringType(yourphoneET.getText().toString()) != 1) {
			MainActivity.showToast(ReportSecondHandActivity.this,
					R.string.reprot_error_phone);
			return false;
		}
		return true;
	}

	/**
	 * 提交数据
	 */
	private void submitData() {
		if (checkdata()) {
			appSecondHandReport = new AppSecondHandReport();
			StuApplication application = (StuApplication) getApplication();
			User user = application.getUser();
			//UserDetail userDetail = application.getUserDetail();
			appSecondHandReport.setOubaMember(user);
			appSecondHandReport.setSecondFalseInformation(Isfalseimformation);
			appSecondHandReport.setSecondFalseContactWay(IsfalseCompan);
			appSecondHandReport.setSecondOtherReason(Isconduit_Company);
			appSecondHandReport.setSecondDetailReportReason(detailreasonET
					.getText().toString());
			appSecondHandReport.setMemberMobile(yourphoneET.getText()
					.toString());
			appSecondHandReport.setSecondReportTime(new Timestamp(new Date()
					.getTime()));
			appSecondHandReport.setAppSecondHand(secondHand);
			appSecondHandReport.setIsSolved(false);

			Uri.Builder builder = Uri.parse(Config.URL_REPORT_SECOND_HAND)
					.buildUpon();
			builder.appendQueryParameter("jsonStr",
					FormatUtils.getJSONString(appSecondHandReport));
			Log.d("appSecondHandReport", FormatUtils.getJSONString(appSecondHandReport));
			JsonObjectRequest request = new JsonObjectRequest(Method.GET,
					builder.toString(), null, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Json json = JSON.parseObject(response.toString(),
									Json.class);
							if (json.isSuccess()) {
								dialog.dismiss();
								MainActivity.showToast(getApplicationContext(),
										R.string.report_second_success);
								finish();
							} else {
								dialog.dismiss();
								MainActivity.showToast(getApplicationContext(),
										R.string.report_second_error);
							}
						}

					}, new VolleyErrorAdapter(this) {
						@Override
						protected void onOccurError(VolleyError error) {
							super.onOccurError(error);
							dialog.dismiss();
						}
					});
			request.setTag(TAG_REPORT);
			mQueue = Volley.newRequestQueue(ReportSecondHandActivity.this);
			mQueue.add(request);
			mQueue.start();
			dialog = new LoadingDialog(
					getString(R.string.report_second_reporting));
			dialog.setOnBackPressedListener(new OnBackPressedLisener() {
				@Override
				public void onBackPressed() {
					dialog.dismiss();
					mQueue.cancelAll(TAG_REPORT);
				}
			});
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			dialog.show(ft, TAG);
		}
	}
	
	/**
	 * 获取数据
	 */
	private void getDate() {
		int sId = getIntent().getIntExtra("SecondHandId", -1);
		Log.i(TAG, "sId = " + sId);
		Uri.Builder builder = Uri.parse(Config.URL_GET_SECOND_DETAIL)
				.buildUpon();
		builder.appendQueryParameter("id", String.valueOf(sId));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						dialog.dismiss();
						if (json.isSuccess()) {
							secondHand = JSON.parseObject(json.getObj()
									.toString(), AppSecondHand.class);
						} else {
							MainActivity.showToast(getApplicationContext(),
									R.string.error_get_data);
						}
					}
				}, new VolleyErrorAdapter(getApplicationContext()) {
					@Override
					protected void onOccurError(VolleyError error) {
						super.onOccurError(error);
						dialog.dismiss();
					}
				});
		request.setTag(TAG_GET_SECOND_HAND);
		mQueue = Volley.newRequestQueue(ReportSecondHandActivity.this);
		mQueue.add(request);
		mQueue.start();

		dialog = new LoadingDialog(getString(R.string.dialog_loading));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {

			@Override
			public void onBackPressed() {
				dialog.dismiss();
				mQueue.cancelAll(TAG_GET_SECOND_HAND);
			}
		});
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		dialog.show(ft, TAG);
	}
}
