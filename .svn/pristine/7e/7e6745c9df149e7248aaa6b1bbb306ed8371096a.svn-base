package com.fengyang.activity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.customLib.DetailSildeView;
import com.fengyang.customLib.DetailSildeView.OnItemClickListener;
import com.fengyang.db.CollectDAO;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.entity.AppSecondCollection;
import com.fengyang.entity.AppSecondHand;
import com.fengyang.entity.AppSecondHandImage;
import com.fengyang.entity.User;
import com.fengyang.fragment.SecondCollectFragment;
import com.fengyang.model.Json;
import com.fengyang.model.SecondCollection;
import com.fengyang.service.CollectionService;
import com.fengyang.service.InitUserService;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.ImmersionActivity;
import com.fengyang.util.ui.ZoomOutPageTransformer;
import com.fengyang.volleyTool.DiskBitmapCache;
import com.fengyang.volleyTool.FadeInImageListener;

public class SecondDetailActivity extends ImmersionActivity implements
		OnClickListener, OnItemClickListener {

	private AppSecondHand secondHand;
	private SecondCollection secondCollection;
	private ArrayList<AppSecondHandImage> imgPath;
	private ImageView collectView;
	private boolean isCollected;

	private DetailSildeView slideView;
	private TextView titleTV;
	private TextView publisherTypeTV;
	private TextView priceTV;
	private TextView numTV;
	private TextView publishDateTV;
	private TextView visitedTV;
	private TextView puplisherTV;
	private TextView phoneTV;
	private TextView locationTV;
	private LinearLayout addressView;
	private TextView addressTV;
	private TextView contentTV;

	private RequestQueue mQueue;

	private ImageLoader imageLoader;

	private DiskBitmapCache diskCache;

	private LoadingDialog dialog;

	private static final String TAG = "secondDetailActivity";

	public static final String KEY_SECOND_HAND_ID = "secondId";
	private static final String TAG_GET_SECOND = "getSecond";
	private static final String TAG_GET_PIC = "getPic";
	private static final String TAG_UPDATE_COLLECT = "updateCollect";

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (InitUserService.ACTION_LOGINED.equals(action)) {
				setUpCollectBtn();
			} else if (SecondCollectFragment.ACTION_SECOND_COLLECT_DELETE
					.equals(action)) {
				isCollected = !isCollected;
				collectView.clearAnimation();
				collectView.setBackgroundResource(R.drawable.ic_menu_collect);
				Toast.makeText(getApplicationContext(),
						R.string.detail_second_uncollect_succeed,
						Toast.LENGTH_SHORT).show();
			} else if (CollectionService.ACTION_SECOND_DELETE_COLLECT_FAILURE
					.equals(action)) {
				collectView.clearAnimation();
				collectView
						.setBackgroundResource(R.drawable.ic_menu_collect_checked);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_detail);
		slideView = (DetailSildeView) findViewById(R.id.slideViewContainer);

		slideView.setTransformer(new ZoomOutPageTransformer());

		titleTV = (TextView) findViewById(R.id.second_detail_title);
		publisherTypeTV = (TextView) findViewById(R.id.second_detail_publisher_type);
		priceTV = (TextView) findViewById(R.id.second_detail_price);
		numTV = (TextView) findViewById(R.id.second_detail_num);
		publishDateTV = (TextView) findViewById(R.id.second_detail_publish_date);
		visitedTV = (TextView) findViewById(R.id.second_detail_visited);
		puplisherTV = (TextView) findViewById(R.id.second_detail_publisher);
		phoneTV = (TextView) findViewById(R.id.second_detail_phone);
		locationTV = (TextView) findViewById(R.id.second_detail_location);
		addressView = (LinearLayout) findViewById(R.id.second_detail_address_view);
		addressTV = (TextView) findViewById(R.id.second_detail_address);
		contentTV = (TextView) findViewById(R.id.second_detail_content);

		slideView.setOnItemClickListener(this);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		setStatusColor(getResources().getColor(R.color.immersionColor));

		IntentFilter filter = new IntentFilter();
		filter.addAction(InitUserService.ACTION_LOGINED);
		filter.addAction(SecondCollectFragment.ACTION_SECOND_COLLECT_DELETE);
		filter.addAction(CollectionService.ACTION_SECOND_DELETE_COLLECT_FAILURE);
		registerReceiver(receiver, filter);

		preparUI();
	}

	/**
	 * 填充数据到组件中
	 */
	private void fillDate(AppSecondHand secondHand) {
		if (!secondHand.getIsPrccessed()) {
			ConfirmDialog dialog = new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					getString(R.string.detail_second_out_title),
					getString(R.string.detail_second_out_content),
					getString(R.string.dialog_sure), null);
			dialog.setOnclickListener(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (DialogInterface.BUTTON_POSITIVE == which) {
						SecondDetailActivity.this.finish();
					}
				}
			});
			dialog.setCancelable(false);
			dialog.show(getSupportFragmentManager(), TAG);
			return;
		}
		titleTV.setText(secondHand.getSecondHandName());
		String[] publisherType = getResources().getStringArray(
				R.array.publisher_type);
		String type = publisherType[secondHand.getPublisherType()];
		publisherTypeTV.setText(type);
		priceTV.setText(getString(R.string.detail_second_price,
				FormatUtils.priceFormat(secondHand.getSecondHandPrice())));
		numTV.setText(getString(R.string.detail_second_num,
				secondHand.getSecondCount()));
		publishDateTV.setText(getString(R.string.detail_second_publish_date,
				FormatUtils.dateFormat(secondHand.getPublishTime())));
		visitedTV.setText(getString(R.string.detail_second_visited,
				secondHand.getSecondVisited()));
		puplisherTV.setText(getString(R.string.detail_second_publisher,
				secondHand.getSecondPublisher()));
		phoneTV.setText(getString(R.string.detail_second_phone,
				secondHand.getSecondPhone()));
		locationTV.setText(secondHand.getOubaAreaByDisId().getAreaName());
		if (secondHand.getAddress() != null
				&& secondHand.getAddress().length() > 0) {
			addressTV.setText(secondHand.getAddress());
			addressView.setVisibility(View.VISIBLE);
		}
		contentTV.setText(secondHand.getSecondDescription());
	}

	private void preparUI() {
		mQueue = Volley.newRequestQueue(getApplicationContext());
		diskCache = new DiskBitmapCache(getCacheDir(), 10);
		imageLoader = new ImageLoader(mQueue, diskCache);

		getDate();
	}

	private void setUpCollectBtn() {
		if (collectView == null || secondHand == null)
			return;
		CollectDAO dao = new CollectDAO(getApplicationContext());
		StuApplication application = (StuApplication) getApplication();
		if (application.isLogin()) {
			User user = application.getUser();
			secondCollection = dao.querySecondById(user.getId(),
					secondHand.getSecondHandId());
			if (secondCollection != null) {
				isCollected = true;
				collectView
						.setBackgroundResource(R.drawable.ic_menu_collect_checked);
			}
		}
	}

	private void getDate() {
		int sId = getIntent().getIntExtra(KEY_SECOND_HAND_ID, -1);
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
							fillDate(secondHand);
							setUpCollectBtn();
							getPicPath();
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
		request.setTag(TAG_GET_SECOND);
		mQueue.add(request);
		mQueue.start();

		dialog = new LoadingDialog(getString(R.string.dialog_loading));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {

			@Override
			public void onBackPressed() {
				dialog.dismiss();
				mQueue.cancelAll(TAG_GET_SECOND);
			}
		});
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		dialog.show(ft, TAG);
	}

	/**
	 * 获取图片信息
	 */
	private void getPicPath() {
		Uri.Builder builder = Uri.parse(Config.URL_GET_SECOND_PIC).buildUpon();
		builder.appendQueryParameter("id",
				String.valueOf(secondHand.getSecondHandId()));
		JsonObjectRequest PicRequest = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							imgPath = new ArrayList<AppSecondHandImage>();
							@SuppressWarnings("unchecked")
							List<com.alibaba.fastjson.JSONObject> list = JSON
									.parseObject(json.getObj().toString(),
											List.class);
							slideView.setUp(list.size(),
									R.drawable.default_img, -1);
							for (com.alibaba.fastjson.JSONObject jsonObject : list) {
								imgPath.add(JSON.parseObject(
										jsonObject.toJSONString(),
										AppSecondHandImage.class));
							}
							getPic(imgPath);
						} else {
							MainActivity.showToast(getApplicationContext(),
									R.string.error_get_data);
						}
					}
				}, new VolleyErrorAdapter(getApplicationContext()) {
				});
		PicRequest.setTag(TAG_GET_PIC);
		mQueue.add(PicRequest);
		mQueue.start();
	}

	private void getPic(ArrayList<AppSecondHandImage> imgs) {
		for (int i = 0; i < imgs.size(); i++) {
			String imgUrl = Config.SECOND_PIC_PATH_THUM + "/"
					+ Config.THUM_PERFIX + imgs.get(i).getImagePath();
			imageLoader.get(imgUrl,
					new FadeInImageListener(slideView.getImageView(i), this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.second_detail, menu);

		collectView = (ImageView) menu.findItem(R.id.action_second_collect)
				.getActionView();
		collectView.setBackgroundResource(R.drawable.ic_menu_collect);
		collectView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				collectSecond(view);
			}
		});
		setUpCollectBtn();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_second_share:
			shareSecond();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (!MainActivity.checkUserLogin(this))
			return;
		int id = v.getId();
		switch (id) {
		case R.id.second_detail_report:
			reportSecond();
			break;
		case R.id.second_detail_sms:
			Log.w("phone", phoneTV.getText().toString());
			if (FormatUtils.praseStringType(secondHand.getSecondPhone()) == 1) {
				sendMsg();
			} else {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.unable_call), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.second_detail_call:
			takeCall();
			break;
		default:
			break;
		}
	}

	/**
	 * 收藏或者删除收藏物品
	 */
	private void collectSecond(View view) {
		if (!MainActivity.checkUserLogin(SecondDetailActivity.this)) {
			return;
		}
		collectView.setBackgroundResource(R.drawable.ic_loading_circle);
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rotate_refresh);
		collectView.startAnimation(anim);
		if (isCollected) { // 如果已经收藏，则进行取消操作
			// sendUnCollectRequest();
			Intent intent = new Intent(this, CollectionService.class);
			intent.putExtra(CollectionService.KEY_START_SERVICE_FOR,
					CollectionService.TASK_SECOND_DELETE_COLLECT);
			intent.putExtra(CollectionService.KEY_SECOND_HAND_ID,
					secondHand.getSecondHandId());
			startService(intent);
		} else { // 如果未收藏，则进行收藏操作
			sendCollectRequest();
		}
	}

	/**
	 * 发送收藏二手物品的请求
	 */
	private void sendCollectRequest() {
		Uri.Builder builder = Uri.parse(Config.URL_SECOND_COLLECT).buildUpon();
		StuApplication application = (StuApplication) getApplication();
		User user = application.getUser();
		final Date nowTime = new Date();
		secondCollection = new SecondCollection();
		secondCollection.setCollecterId(user.getId());
		AppSecondCollection collection = new AppSecondCollection();
		collection.setAppSecondHand(secondHand);
		collection.setOubaMember(user);
		collection.setScPublisherType(secondHand.getPublisherType());
		collection.setScDate(new Timestamp(nowTime.getTime()));
		builder.appendQueryParameter(
				"jsonStr",
				FormatUtils.getJSONString(collection, new String[] {
						"oubaMember", "id", "appSecondHand", "secondHandId",
						"scPublisherType", "scDate" }, null));
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
							secondCollection = SecondCollection.Parse(
									secondHand, user.getId(), nowTime);
							secondCollection.setScId((Integer) json.getObj());
							dao.insertSecond(secondCollection);
							Intent intent = new Intent(
									SecondCollectFragment.ACTION_SECOND_COLLECT_INSERT);
							intent.putExtra(
									SecondCollectFragment.KEY_UPDATE_SID,
									secondHand.getSecondHandId());
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
	private void shareSecond() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, R.string.detail_second_share);
		intent.putExtra(
				Intent.EXTRA_TEXT,
				getString(R.string.detail_second_share_msg,
						getString(R.string.app_name),
						secondHand.getSecondHandName(),
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
					Intent intent = new Intent(SecondDetailActivity.this,
							ReportSecondHandActivity.class);
					intent.putExtra("SecondHandId",
							secondHand.getSecondHandId());
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
						R.string.dialog_send_msg,
						secondHand.getSecondPublisher()),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					startActivity(new Intent(Intent.ACTION_SENDTO, Uri
							.parse("smsto:" + secondHand.getSecondPhone())));
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
				getString(R.string.dialog_title_call), getString(
						R.string.dialog_take_call,
						secondHand.getSecondPublisher()),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					startActivity(new Intent(Intent.ACTION_CALL, Uri
							.parse("tel:" + secondHand.getSecondPhone())));
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public void onItemClick(ViewPager pager, View view, int position) {
		if (imgPath != null) {
			Intent intent = new Intent(this, DetailImageActivity.class);
			intent.putExtra(DetailImageActivity.KEY_IMAGE_POS, position);
			ArrayList<String> path = new ArrayList<String>();
			for (AppSecondHandImage img : imgPath) {
				String imgUrl = Config.SECOND_PIC_PATH + "/"
						+ img.getImagePath();
				path.add(imgUrl);
			}
			intent.putStringArrayListExtra(DetailImageActivity.KEY_IMAGE_ARRAY,
					path);
			startActivity(intent);
		}
	}
}
