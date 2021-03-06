package com.fengyang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import android.support.v4.view.ViewPager;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.customLib.DetailSildeView;
import com.fengyang.customLib.DetailSildeView.OnItemClickListener;
import com.fengyang.db.PublishHistoryDAO;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.entity.AppSecondHand;
import com.fengyang.entity.AppSecondHandImage;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.tool.Tool;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.ImmersionActivity;
import com.fengyang.util.ui.ZoomOutPageTransformer;
import com.fengyang.volleyTool.DiskBitmapCache;
import com.fengyang.volleyTool.FadeInImageListener;

public class SecondDetailHistoryActivity extends ImmersionActivity implements
		OnItemClickListener, OnClickListener {

	private AppSecondHand secondHand;
	private ArrayList<AppSecondHandImage> imgPath;

	private DetailSildeView slideView;
	private TextView titleTV;
	private TextView publisherTypeTV;
	private TextView priceTV;
	private TextView numTV;
	private TextView publishDateTV;
	private TextView visitedTV;
	private TextView phoneTV;
	private TextView locationTV;
	private LinearLayout addressView;
	private TextView addressTV;
	private TextView contentTV;
   // private ProgressDialog mProgressDialog;
	private RequestQueue mQueue;

	private ImageLoader imageLoader;

	private DiskBitmapCache diskCache;

	private LoadingDialog dialog;
	private PublishHistoryDAO mPublishHistoryDAO;

	private static final String TAG = "secondDetailActivity";

	public static final String KEY_SECOND_HAND_ID = "secondId";
	public static final String INTENT_SECOND_HAND_ID = "secondId";
	private static final Object TAG_GET_SECOND = "getSecond";
	private static final Object TAG_GET_PIC = "getPic";
	public static final String SendDetailHISTORY_DELETEACTION = "deleteSend";
	public static final String SendDetailHISTORY_DELETESENDHANDID = "deleteId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_detail_history);
		
		slideView = (DetailSildeView) findViewById(R.id.slideViewContainer);
		slideView.setTransformer(new ZoomOutPageTransformer());

		titleTV = (TextView) findViewById(R.id.second_detail_title);
		publisherTypeTV = (TextView) findViewById(R.id.second_detail_publisher_type);
		priceTV = (TextView) findViewById(R.id.second_detail_price);
		numTV = (TextView) findViewById(R.id.second_detail_num);
		publishDateTV = (TextView) findViewById(R.id.second_detail_publish_date);
		visitedTV = (TextView) findViewById(R.id.second_detail_visited);
		phoneTV = (TextView) findViewById(R.id.second_detail_phone);
		locationTV = (TextView) findViewById(R.id.second_detail_location);
		addressView = (LinearLayout) findViewById(R.id.second_detail_address_view);
		addressTV = (TextView) findViewById(R.id.second_detail_address);
		contentTV = (TextView) findViewById(R.id.second_detail_content);
		mPublishHistoryDAO = new PublishHistoryDAO(SecondDetailHistoryActivity.this);
		slideView.setOnItemClickListener(this);
		setActionBar();
		setStatusColor(getResources().getColor(R.color.immersionColor));
		preparUI();
		IntentFilter inf = new IntentFilter();
		inf.addAction(SecondDetailEditActivity.SECOND_HAND_DETAIL_EDITACTION);
		this.registerReceiver(receiver, inf);
	}
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(android.content.Context context, Intent intent) {
			if (SecondDetailEditActivity.SECOND_HAND_DETAIL_EDITACTION == intent
					.getAction()) {
				final int partId = intent
						.getIntExtra(
								SecondDetailEditActivity.SECOND_HAND_DETAIL_EDIT_SID,
								-1);
				freshData(partId);
				
			}
		};
	};
	private void preparUI() {
		mQueue = Volley.newRequestQueue(getApplicationContext());
		diskCache = new DiskBitmapCache(getCacheDir(), 10);
		imageLoader = new ImageLoader(mQueue, diskCache);
		getDate();
	}
    private void freshData(int sId){
    
		Log.i(TAG, "sId = " + sId);
		Uri.Builder builder = Uri.parse(Config.URL_GET_SECOND_DETAIL)
				.buildUpon();
		builder.appendQueryParameter("id", String.valueOf(sId));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response+++++ = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						dialog.dismiss();
						if (json.isSuccess()) {
							secondHand = JSON.parseObject(json.getObj()
									.toString(), AppSecondHand.class);
							fillDate(secondHand);
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
					}
				});
		request.setTag(TAG_GET_SECOND);
		mQueue.add(request);
		mQueue.start();
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
	private void setActionBar() {
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 如果parent为null 那么xml文件的FrameLayout布局参数会失效
		View v = inflator.inflate(R.layout.actionbar_center_title_layout,
				new LinearLayout(getApplicationContext()), false);
		TextView titleView = (TextView) v.findViewById(R.id.actionBar_titel);
		titleView.setText(R.string.title_activity_second_detail);

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
	/**
	 * 填充数据到组件中
	 */
	private void fillDate(AppSecondHand secondHand) {
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

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.second_detail_edit:
			secondEdit();
			break;
		case R.id.second_detail_delete:
			 secondDelete();
			break;
		}
	}
	public void secondEdit(){
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_BOTTOM,
				getString(R.string.dialog_title_edit),
				getString(R.string.dialog_second_edit),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					Intent intent = new Intent(SecondDetailHistoryActivity.this,
							SecondDetailEditActivity.class);
					intent.putExtra(INTENT_SECOND_HAND_ID, secondHand.getSecondHandId());
					//Tool.ToolToast(getApplicationContext(),secondHand.getSecondHandId()+"11111");
					//Tool.ToolToast(getApplicationContext(),secondHand.getSecondHandId()+"11111");
					startActivity(intent);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
		
	}
	
	public void secondDelete(){
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_BOTTOM,
				getString(R.string.dialog_title_delete),
				getString(R.string.dialog_second_delete),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					deleteDatas();
					//Tool.ToolToast(getApplicationContext(),"deleteDatas()" );
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
	
	public void deleteDatas(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isPrccessed",false);
		Uri.Builder userUrl = Uri.parse(
				Config.URL_GET_SECOND_HAND_INFORMATION_PART_UPDATE)
				.buildUpon();
		final int secondHandId = secondHand.getSecondHandId();
		Log.d("PART", secondHandId+"");
		//Tool.ToolToast(getApplicationContext(),secondHandId+"******");
		userUrl.appendQueryParameter("id",
				String.valueOf(secondHandId));
		userUrl.appendQueryParameter("jsonStr",
				FormatUtils.getJSONString(map));
		Log.d("PART", FormatUtils.getJSONString(map));
		Log.d("PART", userUrl.toString());
		StringRequest deleteSecondHandrequest = new StringRequest(Method.GET,
				userUrl.toString(),
				new Response.Listener<String>() {
					@Override
					public void onResponse(
							String response) {
						Log.d("PARTresponse", response);
						
						Json json = JSON.parseObject(
								response.toString(),
								Json.class);
						if (json.isSuccess()) {
							
							mPublishHistoryDAO.deleteSecond(secondHandId);
							Intent intent  = new Intent();
							intent.setAction(SendDetailHISTORY_DELETEACTION);
							intent.putExtra(SendDetailHISTORY_DELETESENDHANDID,secondHand.getSecondHandId());
							SecondDetailHistoryActivity.this.sendBroadcast(intent);
						    finish();
                            Tool.ToolToast(getApplicationContext(), getResources().getString(R.string.toast_delete_success));
						
						} else {
							 Tool.ToolToast(getApplicationContext(), getResources().getString(R.string.toast_delete_fail));
						}

						Log.d("PART", response);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(
							VolleyError error) {
					}

				});
		mQueue.add(deleteSecondHandrequest);
		mQueue.start();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SecondDetailHistoryActivity.this.unregisterReceiver(receiver);
	}
	/*public void showdialog(){
		mProgressDialog = new ProgressDialog(SecondDetailHistoryActivity.this);
		//mProgressDialog.setTitle()
		mProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_title));
		mProgressDialog.setCancelable(false);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.show();
	}*/
}
