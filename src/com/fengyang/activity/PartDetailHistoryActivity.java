package com.fengyang.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.entity.AppPartTime;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.tool.Tool;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.ImmersionActivity;

public class PartDetailHistoryActivity extends ImmersionActivity implements
		OnClickListener {

	private AppPartTime partTime;
	private TextView titleTV;
	private TextView publisherTypeTV;
	private TextView payTV;
	private TextView payWayTV;
	// private TextView publisherTV;
	private TextView publishDateTV;
	private TextView visitedTV;
	private TextView partTimeTypeTV;
	private TextView workTimeTV;
	private TextView workTimeTitleTV;
	private TextView numTV;
	private TextView phoneTV;
	private TextView areaTV;
	private TextView addressTV;
	private TextView contentTV;
	private String[] publisherType;
	private String[] payUnit;
	private String[] payWay;
	private String[] workTimeType;
	private LoadingDialog dialog;
	private RequestQueue mQueue;
	int pId;
	// private
	public static final String KEY_PART_ID = "partId";
	private static final String TAG_GET_PART = "getPartDetail";
	private static final String TAG = "PartDetailHistoryActivity";
	public static final String PART_HISTORY_DELETE_ACTION = "PartTimeDelete";
	public static final String PART_DETAIL_HISTORY_PAETTIMEID = "parttimeid";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_part_detail_history);

		titleTV = (TextView) findViewById(R.id.part_detail_title);
		publisherTypeTV = (TextView) findViewById(R.id.part_detail_publisher_type);
		payTV = (TextView) findViewById(R.id.part_detail_pay);
		payWayTV = (TextView) findViewById(R.id.part_detail_payType);
		// publisherTV = (TextView) findViewById(R.id.part_detail_publisher);
		publishDateTV = (TextView) findViewById(R.id.part_detail_publish_date);
		visitedTV = (TextView) findViewById(R.id.part_detail_visited);
		partTimeTypeTV = (TextView) findViewById(R.id.part_detail_partType);
		workTimeTV = (TextView) findViewById(R.id.part_detail_workTime);
		workTimeTitleTV = (TextView) findViewById(R.id.part_detail_workTime_t);
		numTV = (TextView) findViewById(R.id.part_detail_num);
		phoneTV = (TextView) findViewById(R.id.part_detail_phone);
		areaTV = (TextView) findViewById(R.id.part_detail_area);
		addressTV = (TextView) findViewById(R.id.part_detail_address);
		contentTV = (TextView) findViewById(R.id.part_detail_content);
		setActionBar();
		setStatusColor(getResources().getColor(R.color.immersionColor));
		preparUI();
		IntentFilter inf = new IntentFilter();
		inf.addAction(PartTimeDetailEditActivity.PARTTIME_DETAIL_EDITACTION);
		this.registerReceiver(receiver, inf);
		pId = getIntent().getIntExtra(KEY_PART_ID, -1);
		getData(pId);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(android.content.Context context, Intent intent) {
			if (PartTimeDetailEditActivity.PARTTIME_DETAIL_EDITACTION == intent
					.getAction()) {
				final int partId = intent
						.getIntExtra(
								PartTimeDetailEditActivity.PARTTIME_DETAIL_EDITA_PARTTIMEID,
								-1);
				refreshData(partId);
				fillDate(partTime);
			}
		};
	};

	private void preparUI() {
		publisherType = getResources().getStringArray(R.array.publisher_type);
		payUnit = getResources().getStringArray(R.array.job_pay_unit);
		payWay = getResources().getStringArray(R.array.job_pay_type_entries);
		workTimeType = getResources().getStringArray(
				R.array.job_time_type_entries);
		mQueue = Volley.newRequestQueue(getApplicationContext());

	}

	private void getData(long pId) {

		Uri.Builder builder = Uri.parse(Config.URL_GET_PART_DETAIL).buildUpon();
		builder.appendQueryParameter("id", String.valueOf(pId));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						dialog.dismiss();
						if (json.isSuccess()) {
							partTime = JSON.parseObject(json.getObj()
									.toString(), AppPartTime.class);
							fillDate(partTime);
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
		request.setTag(TAG_GET_PART);
		mQueue.add(request);
		mQueue.start();

		dialog = new LoadingDialog(getString(R.string.dialog_loading));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {

			@Override
			public void onBackPressed() {
				dialog.dismiss();
				mQueue.cancelAll(TAG_GET_PART);
			}
		});
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		dialog.show(ft, TAG);
	}

	private void refreshData(long pId) {
		Uri.Builder builder = Uri.parse(Config.URL_GET_PART_DETAIL).buildUpon();
		builder.appendQueryParameter("id", String.valueOf(pId));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							// mDialog.dismiss();
							partTime = JSON.parseObject(json.getObj()
									.toString(), AppPartTime.class);
							fillDate(partTime);

						} else {
							MainActivity.showToast(getApplicationContext(),
									R.string.error_get_data);
						}
					}
				}, new VolleyErrorAdapter(getApplicationContext()) {
					@Override
					protected void onOccurError(VolleyError error) {
						super.onOccurError(error);
					}
				});
		request.setTag(TAG_GET_PART);
		mQueue.add(request);
		mQueue.start();
		// mDialog = new FreshDialog(getApplicationContext());
		// mDialog.show(PartDetailHistoryActivity.this, "xsddd");

	}

	private void fillDate(AppPartTime partTime) {
		titleTV.setText(partTime.getPartTimeName());
		publisherTypeTV.setText(publisherType[partTime.getPublisherType()]);
		payTV.setText(getString(R.string.detail_part_pay,
				FormatUtils.priceFormat(partTime.getPay()),
				payUnit[partTime.getPayUnit()]));
		payWayTV.setText(payWay[partTime.getPayWay()]);
		// publisherTV.setText(partTime.getNickname());
		publishDateTV.setText(getString(R.string.detail_part_publish_date,
				FormatUtils.dateFormat(partTime.getPublishTime())));
		visitedTV.setText(getString(R.string.detail_part_visited,
				partTime.getPartVisited()));
		partTimeTypeTV.setText(workTimeType[partTime.getTimeType()]);
		if (partTime.getTimeType() == 0) {
			StringBuffer sb = new StringBuffer();
			String[] week = getResources()
					.getStringArray(R.array.job_time_week);
			String[] work = partTime.getTimeDescription().split(
					Config.WORK_TIME_SPLIT);
			for (int i = 0; i < work.length; i++) {
				int index = Integer.parseInt(work[i]);
				sb.append(week[index] + getString(R.string.punctuation_pause));
			}
			sb.deleteCharAt(sb.length() - 1);
			workTimeTitleTV.setText(R.string.detail_part_workTime);
			workTimeTV.setText(sb.toString());
		} else if (partTime.getTimeType() == 1) {
			workTimeTitleTV.setText(R.string.detail_part_endTime);
			workTimeTV.setText(FormatUtils.dateFormat(partTime.getEndTime()));
		}
		numTV.setText(getString(R.string.detail_part_numUnit,
				partTime.getPartNum()));
		phoneTV.setText(partTime.getPhone());
		StringBuffer area = new StringBuffer();
		area.append(partTime.getOubaAreaByProId().getAreaName() + " ");
		area.append(partTime.getOubaAreaByCityId().getAreaName() + " ");
		area.append(partTime.getOubaAreaByDisId().getAreaName());
		areaTV.setText(area.toString());
		addressTV.setText(partTime.getAddress());
		contentTV.setText(partTime.getJobDescription());
	}

	private void setActionBar() {
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 如果parent为null 那么xml文件的FrameLayout布局参数会失效
		View v = inflator.inflate(R.layout.actionbar_center_title_layout,
				new LinearLayout(getApplicationContext()), false);
		TextView titleView = (TextView) v.findViewById(R.id.actionBar_titel);
		titleView.setText(R.string.title_activity_parttime_detail);

		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setCustomView(v);
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

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.part_detail_edit:
			parttimeEdit();
			break;
		case R.id.part_detail_delete:
			parttimeDelete();
			break;
		}
	}

	/*
	 * 兼职编辑 *
	 */
	public void parttimeEdit() {
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_BOTTOM,
				getString(R.string.dialog_title_edit),
				getString(R.string.dialog_parttime_edit),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					Intent intent = new Intent(PartDetailHistoryActivity.this,
							PartTimeDetailEditActivity.class);
					intent.putExtra("pId", pId);
					startActivity(intent);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);

	}

	/*
	 * 兼职删除 *
	 */
	public void parttimeDelete() {
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_BOTTOM,
				getString(R.string.dialog_title_delete),
				getString(R.string.dialog_parttime_delete),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					deleteDatas();
					break;

				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);

	}

	/**
	 * 将isPrccessed设置为false
	 * */

	public void deleteDatas() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isPrccessed", false);
		Uri.Builder userUrl = Uri.parse(Config.URL_DELETE_PARTTIME).buildUpon();
		final int parttimeid = partTime.getPartTimeId();
		Log.d("PART", parttimeid + "");
		userUrl.appendQueryParameter("id", String.valueOf(parttimeid));
		userUrl.appendQueryParameter("jsonStr", FormatUtils.getJSONString(map));
		Log.d("PART", FormatUtils.getJSONString(map));
		Log.d("PART", userUrl.toString());
		StringRequest deleteparttimequest = new StringRequest(Method.GET,
				userUrl.toString(),

				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							Intent intent = new Intent();
							intent.setAction(PART_HISTORY_DELETE_ACTION);
							intent.putExtra(PART_DETAIL_HISTORY_PAETTIMEID,
									parttimeid);
							PartDetailHistoryActivity.this
									.sendBroadcast(intent);
							finish();
							Tool.ToolToast(
									PartDetailHistoryActivity.this,
									getResources().getString(
											R.string.toast_delete_success));

						} else {
							Tool.ToolToast(
									PartDetailHistoryActivity.this,
									getResources().getString(
											R.string.toast_delete_fail));
						}

						Log.d("PART", response);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}

				});
		mQueue.add(deleteparttimequest);
		mQueue.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PartDetailHistoryActivity.this.unregisterReceiver(receiver);
	}
}
