package com.fengyang.activity;

import java.sql.Timestamp;
import java.util.Date;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.db.CollectDAO;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.entity.AppPartCollection;
import com.fengyang.entity.AppPartTime;
import com.fengyang.entity.User;
import com.fengyang.fragment.PartCollectFragment;
import com.fengyang.model.Json;
import com.fengyang.model.PartCollection;
import com.fengyang.service.CollectionService;
import com.fengyang.service.InitUserService;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.ImmersionActivity;

public class PartDetailActivity extends ImmersionActivity implements
		OnClickListener {

	private AppPartTime partTime;
	private ImageView collectView;
	private boolean isCollected;
	private PartCollection partCollection;

	private TextView titleTV;
	private TextView publisherTypeTV;
	private TextView payTV;
	private TextView payWayTV;
	private TextView publisherTV;
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

	public static final String KEY_PART_ID = "partId";
	private static final String TAG_GET_PART = "getPartDetail";
	private static final String TAG = "partDetailActivity";
	private static final String TAG_UPDATE_COLLECT = "updatePartCollection";

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (InitUserService.ACTION_LOGINED.equals(action)) {// 登陆
				setUpCollectBtn();
			} else if (PartCollectFragment.ACTION_PART_COLLECT_DELETE
					.equals(action)) {// 删除兼职的广播
				isCollected = !isCollected;
				collectView.clearAnimation();
				collectView.setBackgroundResource(R.drawable.ic_menu_collect);
				Toast.makeText(getApplicationContext(),
						R.string.detail_second_uncollect_succeed,
						Toast.LENGTH_SHORT).show();
			} else if (CollectionService.ACTION_PART_DELETE_COLLECT_FAILURE
					.equals(action)) { // 删除兼职收藏失败
				collectView.clearAnimation();
				collectView
						.setBackgroundResource(R.drawable.ic_menu_collect_checked);
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_part_detail);

		titleTV = (TextView) findViewById(R.id.part_detail_title);
		publisherTypeTV = (TextView) findViewById(R.id.part_detail_publisher_type);
		payTV = (TextView) findViewById(R.id.part_detail_pay);
		payWayTV = (TextView) findViewById(R.id.part_detail_payType);
		publisherTV = (TextView) findViewById(R.id.part_detail_publisher);
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

		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		setStatusColor(getResources().getColor(R.color.immersionColor));

		IntentFilter filter = new IntentFilter();
		filter.addAction(InitUserService.ACTION_LOGINED);
		filter.addAction(PartCollectFragment.ACTION_PART_COLLECT_DELETE);
		filter.addAction(CollectionService.ACTION_PART_DELETE_COLLECT_FAILURE);
		registerReceiver(receiver, filter);

		preparUI();
	}

	/**
	 * 设置ui
	 */
	private void preparUI() {
		publisherType = getResources().getStringArray(R.array.publisher_type);
		payUnit = getResources().getStringArray(R.array.job_pay_unit);
		payWay = getResources().getStringArray(R.array.job_pay_type_entries);
		workTimeType = getResources().getStringArray(
				R.array.job_time_type_entries);
		mQueue = Volley.newRequestQueue(getApplicationContext());

		getData();
	}

	private void setUpCollectBtn() {
		if (collectView == null || partTime == null)
			return;
		CollectDAO dao = new CollectDAO(getApplicationContext());
		StuApplication application = (StuApplication) getApplication();
		if (application.isLogin()) {
			User user = application.getUser();
			partCollection = dao.queryPartById(user.getId(),
					partTime.getPartTimeId());
			if (partCollection != null) {
				isCollected = true;
				collectView
						.setBackgroundResource(R.drawable.ic_menu_collect_checked);
			}
		}
	}

	private void fillDate(AppPartTime partTime) {
		if (!partTime.getIsPrccessed()) {
			ConfirmDialog dialog = new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					getString(R.string.detail_part_out_title),
					getString(R.string.detail_part_out_content),
					getString(R.string.dialog_sure), null);
			dialog.setOnclickListener(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (DialogInterface.BUTTON_POSITIVE == which) {
						PartDetailActivity.this.finish();
					}
				}
			});
			dialog.setCancelable(false);
			dialog.show(getSupportFragmentManager(), TAG);
			return;
		}
		titleTV.setText(partTime.getPartTimeName());
		publisherTypeTV.setText(publisherType[partTime.getPublisherType()]);
		payTV.setText(getString(R.string.detail_part_pay,
				FormatUtils.priceFormat(partTime.getPay()),
				payUnit[partTime.getPayUnit()]));
		payWayTV.setText(payWay[partTime.getPayWay()]);
		publisherTV.setText(partTime.getPartPublisher());
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

	private void getData() {
		int pId = getIntent().getIntExtra(KEY_PART_ID, -1);
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
							setUpCollectBtn();
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

//	private void setActionBar() {
//		ActionBar actionBar = getActionBar();
//		actionBar.setDisplayShowTitleEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(true);
//		actionBar.setHomeButtonEnabled(true);
//
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.part_detail, menu);
		collectView = (ImageView) menu.findItem(R.id.action_part_collect)
				.getActionView();
		collectView.setBackgroundResource(R.drawable.ic_menu_collect);
		collectView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				collectPart(view);
			}
		});
		return true;
	}

	/**
	 * 收藏兼职
	 * 
	 * @param view
	 */
	private void collectPart(View view) {
		if (!MainActivity.checkUserLogin(this)) {
			return;
		}
		collectView.setBackgroundResource(R.drawable.ic_loading_circle);
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rotate_refresh);
		collectView.startAnimation(anim);
		if (isCollected) { // 如果已经收藏，则进行取消操作
			// sendUncollectRequest();
			Intent intent = new Intent(this, CollectionService.class);
			intent.putExtra(CollectionService.KEY_START_SERVICE_FOR,
					CollectionService.TASK_PART_DELETE_COLLECT);
			intent.putExtra(CollectionService.KEY_PART_TIME_ID,
					partTime.getPartTimeId());
			startService(intent);
		} else { // 如果未收藏，则进行收藏操作
			sendCollectRequest();
		}
	}

	private void sendCollectRequest() {
		Uri.Builder builder = Uri.parse(Config.URL_PART_COLLECT).buildUpon();
		StuApplication application = (StuApplication) getApplication();
		User user = application.getUser();
		final Date nowTime = new Date();
		partCollection = new PartCollection();
		partCollection.setCollecterId(user.getId());
		AppPartCollection collection = new AppPartCollection();
		collection.setAppPartTime(partTime);
		collection.setOubaMember(user);
		collection.setPcPublisherType(partTime.getPublisherType());
		collection.setPcDate(new Timestamp(nowTime.getTime()));
		builder.appendQueryParameter(
				"jsonStr",
				FormatUtils.getJSONString(collection, new String[] {
						"oubaMember", "id", "appPartTime", "partTimeId",
						"pcPublisherType", "pcDate" }, null));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						collectView.clearAnimation();
						if (json.isSuccess()) {
							isCollected = !isCollected;
							collectView
									.setBackgroundResource(R.drawable.ic_menu_collect_checked);
							CollectDAO dao = new CollectDAO(
									getApplicationContext());
							StuApplication application = (StuApplication) getApplication();
							User user = application.getUser();
							partCollection = PartCollection.Parse(partTime,
									user.getId(), nowTime);
							partCollection.setPcId((Integer) json.getObj());
							dao.insertPart(partCollection);
							Intent intent = new Intent(
									PartCollectFragment.ACTION_PART_COLLECT_INSERT);
							intent.putExtra(PartCollectFragment.KEY_UPDATE_PID,
									partTime.getPartTimeId());
							sendBroadcast(intent);
							Toast.makeText(getApplicationContext(),
									R.string.detail_second_collect_succeed,
									Toast.LENGTH_SHORT).show();
						} else {
							collectView
									.setBackgroundResource(R.drawable.ic_menu_collect);
							Toast.makeText(getApplicationContext(),
									R.string.detail_second_collect_failure,
									Toast.LENGTH_SHORT).show();
						}
					}
				}, new VolleyErrorAdapter(getApplicationContext()) {
					@Override
					protected void onOccurError(VolleyError error) {
						super.onOccurError(error);
						collectView.clearAnimation();
						collectView
								.setBackgroundResource(R.drawable.ic_menu_collect);
					}
				});
		request.setTag(TAG_UPDATE_COLLECT);
		mQueue.add(request);
		mQueue.start();
	}

	/**
	 * 分享二手物品
	 */
	private void sharePart() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, R.string.detail_part_share);
		intent.putExtra(
				Intent.EXTRA_TEXT,
				getString(R.string.detail_part_share_msg,
						getString(R.string.app_name),
						partTime.getPartTimeName(),
						getString(R.string.app_download_site)));

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));
	}

	/**
	 * 举报二手物品
	 */
	private void reportSecond() {
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_BOTTOM,
				getString(R.string.dialog_title_report),
				getString(R.string.dialog_report_second),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					Intent intent = new Intent(PartDetailActivity.this,
							ReportPartTimeActivity.class);
					intent.putExtra("partTimeId", partTime.getPartTimeId());
					startActivity(intent);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
	}

	/**
	 * 发送短信给发布者
	 */
	private void sendMsg() {
		// 发短信
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_BOTTOM,
				getString(R.string.dialog_title_sms), getString(
						R.string.dialog_send_msg, partTime.getPartPublisher()),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					startActivity(new Intent(Intent.ACTION_SENDTO, Uri
							.parse("smsto:" + partTime.getPhone())));
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
	}

	/**
	 * 拨打电话给发布者
	 */
	private void takeCall() {
		// 打电话
		// ACTION_DIAL --- 拨号
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_BOTTOM,
				getString(R.string.dialog_title_call),
				getString(R.string.dialog_take_call,
						partTime.getPartPublisher()),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					startActivity(new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + partTime.getPhone())));
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_part_share:
			sharePart();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if (!MainActivity.checkUserLogin(this))
			return;
		int id = v.getId();
		switch (id) {
		case R.id.part_detail_report:
			reportSecond();
			break;
		case R.id.part_detail_sms:
			if (FormatUtils.praseStringType(phoneTV.getText().toString()) == 1) {
				Log.d("return",FormatUtils.praseStringType(phoneTV.getText().toString())+"" );
				sendMsg();
			} else {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.unable_call),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.part_detail_call:
			takeCall();
			break;
		default:
			break;
		}
	}
}
