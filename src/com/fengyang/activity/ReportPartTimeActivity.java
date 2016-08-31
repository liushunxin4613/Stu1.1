package com.fengyang.activity;

import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.entity.AppPartTime;
import com.fengyang.entity.AppPartTimeReport;
import com.fengyang.entity.User;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.ImmersionActivity;

public class ReportPartTimeActivity extends ImmersionActivity implements
		OnClickListener {
	private AppPartTime appPartTime;
	private AppPartTimeReport appPartTimeReport;
	private LoadingDialog dialog;
	private Boolean parttimeFalseInformation = false;
	private Boolean parttimeFlaseContactWay = false;
	private Boolean parttimeFlaseCompany = false;
	private Boolean parttimeConduitConpany = false;
	private Boolean parttimeOtherReason = false;
	private CheckBox parttimeFalseInformationCheckBox;
	private CheckBox parttimeFlaseContactWayCheckBox;
	private CheckBox parttimeFlaseCompanyCheckBox;
	private CheckBox parttimeConduitConpanyCheckBox;
	private CheckBox parttimeOtherReasonCheckBox;
	private EditText parttimeDetailReportReasonEt;
	private EditText memberMobileEt;
	private Button reportCallTV;

	private RequestQueue mQueue;

	public static String TAG = "ReportPartTimeActivity";
	protected static final String TAG_REPORT = "reportPartTime";
	private static String TAG_GET_PART_TIME = "getPartTime";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_part_time);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		setStatusColor(getResources().getColor(R.color.immersionColor));
		PerpareUI();
		getData();

//		parttimeFalseInformationCheckBox.setOnCheckedChangeListener(this);
//		parttimeFlaseCompanyCheckBox.setOnCheckedChangeListener(this);
//		parttimeConduitConpanyCheckBox.setOnCheckedChangeListener(this);
//		parttimeFlaseContactWayCheckBox.setOnCheckedChangeListener(this);
//		parttimeOtherReasonCheckBox.setOnCheckedChangeListener(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

//	private void setActionBar() {
//		getActionBar().setDisplayShowHomeEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//		LayoutInflater inflator = (LayoutInflater) this
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		// 如果parent为null 那么xml文件的FrameLayout布局参数会失效
//		View v = inflator.inflate(R.layout.actionbar_center_title_layout,
//				new LinearLayout(getApplicationContext()), false);
//		TextView titleView = (TextView) v.findViewById(R.id.actionBar_titel);
//		titleView.setText(R.string.report_parttime);
//
//		getActionBar().setDisplayShowCustomEnabled(true);
//		getActionBar().setCustomView(v);
//	}

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
		parttimeFalseInformationCheckBox = (CheckBox) findViewById(R.id.parttime_falseimformation_checkbox);
		parttimeFlaseCompanyCheckBox = (CheckBox) findViewById(R.id.Parttime_FalseCompany_checkbox);
		parttimeConduitConpanyCheckBox = (CheckBox) findViewById(R.id.parttime_Conduit_Company_checkbox);
		parttimeFlaseContactWayCheckBox = (CheckBox) findViewById(R.id.parttime_falsephone_checkbox);
		parttimeOtherReasonCheckBox = (CheckBox) findViewById(R.id.parttime_other_checkbox);
		parttimeDetailReportReasonEt = (EditText) findViewById(R.id.parttime_detailreasonET);
		memberMobileEt = (EditText) findViewById(R.id.parttime_yourphoneET);
		//submitBT = (Button) findViewById(R.id.parttime_submitBT);
		reportCallTV = (Button) findViewById(R.id.parttime_reportphoneBT);
		parttimeFalseInformationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					parttimeFalseInformation = true;
				} else {
					parttimeFalseInformation = false;
				}
			}
		});
		parttimeFlaseCompanyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					parttimeFlaseCompany = true;
				} else {
					parttimeFlaseCompany = false;
				}
			}
		});
		parttimeConduitConpanyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					parttimeConduitConpany = true;
				} else {
					parttimeConduitConpany = false;
				}
			}
		});
		parttimeFlaseContactWayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					parttimeFlaseContactWay = true;
				} else {
					parttimeFlaseContactWay = false;
				}
			}
		});
		parttimeOtherReasonCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					parttimeOtherReason = true;
				} else {
					parttimeOtherReason = false;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.parttime_submitBT:
			//Log.d(TAG, "submitData();");
			submitData();
			break;
		case R.id.parttime_reportphoneBT:
			takeCall();
			break;

		}

	}

	/**
	 * 拨打电话给官方
	 */
	private void takeCall() {
		// 打电话
		// ACTION_DIAL --- 拨号
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_BOTTOM,
				getString(R.string.dialog_title_call), getString(
						R.string.dialog_take_call,
						getResources().getString(R.string.app_company)),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					startActivity(new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + reportCallTV.getText().toString())));
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		// String TAG;
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
	}
//
//	@Override
//	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
//		switch (arg0.getId()) {
//		case R.id.parttime_falseimformation_checkbox:
//			
//			break;
//		case R.id.Parttime_FalseCompany_checkbox:
//			
//			break;
//		case R.id.parttime_Conduit_Company_checkbox:
//			
//			break;
//		case R.id.parttime_falsephone_checkbox:
//			
//			break;
//		case R.id.parttime_other_checkbox:
//			
//			break;
//		}
//	}

	/**
	 * 获取举报数据
	 * 
	 * @return
	 */
	private void getData() {
		int sId = getIntent().getIntExtra("partTimeId", -1);
		Log.i(TAG, "sId = " + sId);
		Uri.Builder builder = Uri.parse(Config.URL_GET_PART_DETAIL).buildUpon();
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
							appPartTime = JSON.parseObject(json.getObj()
									.toString(), AppPartTime.class);
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
		request.setTag(TAG_GET_PART_TIME);
		mQueue = Volley.newRequestQueue(ReportPartTimeActivity.this);
		mQueue.add(request);
		mQueue.start();

		dialog = new LoadingDialog(getString(R.string.dialog_loading));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {

			@Override
			public void onBackPressed() {
				dialog.dismiss();
				mQueue.cancelAll(TAG_GET_PART_TIME);
			}
		});
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		dialog.show(ft, TAG);
	}

	/**
	 * 检查数据
	 */
	private boolean checkdata() {
		Log.d(TAG, "checkdata();");
		if ((!parttimeFlaseContactWayCheckBox.isChecked()) 
				&& !parttimeFalseInformationCheckBox.isChecked()
				&& !parttimeFlaseCompanyCheckBox.isChecked()
				&& !parttimeConduitConpanyCheckBox.isChecked()
				&& !parttimeOtherReasonCheckBox.isChecked()) {
			MainActivity.showToast(ReportPartTimeActivity.this,
					R.string.reprot_second_resource);
			return false;
		}
		if (parttimeDetailReportReasonEt.getText().length() == 0) {
			MainActivity.showToast(ReportPartTimeActivity.this,
					R.string.reprot_second_detail);
			return false;
		}
		if (memberMobileEt.getText().length() == 0) {
			MainActivity.showToast(ReportPartTimeActivity.this,
					R.string.reprot_second_phone);
			return false;
		}else if (FormatUtils.praseStringType(memberMobileEt.getText().toString()) != 1) {
			MainActivity.showToast(ReportPartTimeActivity.this,
					R.string.reprot_error_phone);
			return false;
		}
		return true;
	}

	/**
	 * 提交数据
	 */
	private void submitData() {
		Log.d(TAG, "checkdata()="+checkdata());
		
		if (checkdata()) {
			appPartTimeReport = new AppPartTimeReport();
			StuApplication application = (StuApplication) getApplication();
			User user = application.getUser();
			appPartTimeReport.setOubaMember(user);
			appPartTimeReport.setPartConduitCompany(parttimeConduitConpany);
			appPartTimeReport
					.setPartFalseInformation(parttimeFalseInformation);
			appPartTimeReport.setPartFalseCompany(parttimeFlaseCompany);
			appPartTimeReport
					.setPartFalseContactWay(parttimeFlaseContactWay);
			appPartTimeReport.setPartOtherReason(parttimeOtherReason);
			appPartTimeReport.setMemberMobile(memberMobileEt.getText()
					.toString());
			appPartTimeReport
					.setPartDetailReportReason(parttimeDetailReportReasonEt
							.getText().toString());
			appPartTimeReport.setPartReportTime(new Timestamp(new Date()
					.getTime()));
			appPartTimeReport.setIsSolved(false);
			appPartTimeReport.setAppPartTime(appPartTime);
			Uri.Builder builder = Uri.parse(Config.URL_REPORT_PART_TIME)
					.buildUpon();
			builder.appendQueryParameter("jsonStr",
					FormatUtils.getJSONString(appPartTimeReport));
			Log.d("appPartTimeReport",
					FormatUtils.getJSONString(appPartTimeReport));
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
			mQueue = Volley.newRequestQueue(ReportPartTimeActivity.this);
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
}
