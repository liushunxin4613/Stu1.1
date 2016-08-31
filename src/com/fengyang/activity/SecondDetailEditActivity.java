package com.fengyang.activity;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
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
import com.fengyang.db.AreaDAO;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.entity.AppSecondHand;
import com.fengyang.entity.OubaArea;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.ImmersionActivity;

public class SecondDetailEditActivity extends ImmersionActivity implements OnClickListener {
    
	private TextView  appSecondClassByFirstTypeTV;
	private TextView  secondHandNameTV;
	private EditText secondDescriptionEt;
	private EditText secondHandPriceEt;
	private EditText secondCountEt;
	private EditText secondPhoneEt;
	private TextView locationshowEt;
	private EditText addressEt;
	private RequestQueue mQueue;
	private LoadingDialog dialog;
	private AppSecondHand secondHand;
	private OubaArea province;
	private OubaArea city;
	private OubaArea distrct;
//	private ImageLoader imageLoader;
//	private DetailSildeView slideView;
//	private ImageView  mImageView;
//	private PhotoAdapter adapter;
//	private String[] imageUrls;
	//private RequestQueue getImageQueue;
//	private DiskBitmapCache diskCache;
			// 显示图片的设置
//	private ArrayList<AppSecondHandImage> imgPath;
	private static String TAG = "SecondDetailEditActivity";
	private static String TAG_GET_SECOND_HAND ="getSecondHand";
	public static String SECOND_HAND_DETAIL_EDITACTION = "second_hand_update_action";
	public static String SECOND_HAND_DETAIL_EDIT_SID = "second_hand_id";
	private static int SECOND_REQUEST = 2;
	private static boolean isReSelectArea = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_detail_edit);
		 mQueue = Volley.newRequestQueue(SecondDetailEditActivity.this);
		getData();
		repareUI();
//		getActionBar().setDisplayShowHomeEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//		getActionBar().setDisplayShowTitleEnabled(true);
		setActionBar();
		setStatusColor(getResources().getColor(R.color.immersionColor));
		locationshowEt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chooseArea();
			}
		});
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
		titleView.setText(R.string.title_activity_second_detail_edit);

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

	private void repareUI(){
		appSecondClassByFirstTypeTV = (TextView) findViewById(R.id.edit_second_class_show);
		secondHandNameTV = (TextView) findViewById(R.id.edit_input_second_hand_name);
		secondDescriptionEt = (EditText) findViewById(R.id.edit_second_hand_detail);
		secondHandPriceEt = (EditText) findViewById(R.id.edit_input_second_hand_price);
		secondCountEt = (EditText) findViewById(R.id.edit_input_second_hand_num);
		secondPhoneEt = (EditText) findViewById(R.id.edit_input_second_hand_phone);
		locationshowEt = (TextView) findViewById(R.id.edit_second_location_show);
		addressEt =(EditText) findViewById(R.id.edit_input_place);
		//adapter = new PhotoAdapter(mGridView, data);
//		diskCache = new DiskBitmapCache(getCacheDir(), 10);
//		imageLoader = new ImageLoader(mQueue, diskCache);
//		slideView = (DetailSildeView) findViewById(R.id.editslideViewContainer);
//		slideView.setTransformer(new ZoomOutPageTransformer());
//		slideView.setImageClickListener(this);
	   
	}
	    /*
	     * 从数据库获取数据
	     * */
		private void getData() {
			
			int  SecondHandID = getIntent().getIntExtra(SecondDetailHistoryActivity.INTENT_SECOND_HAND_ID, -1);
			Log.d(TAG, " SecondHandID = " + String.valueOf(SecondHandID));
			Uri.Builder builder = Uri.parse(Config.URL_GET_SECOND_DETAIL).buildUpon();
			builder.appendQueryParameter("id", String.valueOf(SecondHandID));
			Log.d(TAG, " builder = " + builder.toString());
			JsonObjectRequest request = new JsonObjectRequest(Method.GET,
					builder.toString(), null, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							Log.d(TAG, " response secondhand= " + response);
							Json json = JSON.parseObject(response.toString(),
									Json.class);
							dialog.dismiss();
							if (json.isSuccess()) {
								secondHand= JSON.parseObject(json.getObj()
										.toString(),AppSecondHand.class );
								fillData(secondHand);
								//getPicPath();
								
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
		/*
		 * 将数据填充到页面上
		 * */
		private void fillData(AppSecondHand secondHand){
			StringBuffer type = new StringBuffer();
			type.append(secondHand.getAppSecondClassByFirstType().getClass_()+" ");
			type.append(secondHand.getAppSecondClassBySecondType().getClass_());
			appSecondClassByFirstTypeTV.setText(type);
			secondHandNameTV.setText(secondHand.getSecondHandName());
			secondDescriptionEt.setText(secondHand.getSecondDescription());
			//Log.d("secondHandPriceEt",secondHand.getSecondHandPrice()+"");
			secondHandPriceEt.setText(String.valueOf(secondHand.getSecondHandPrice()));
			secondCountEt.setText(String.valueOf(secondHand.getSecondCount()));
			secondPhoneEt.setText(secondHand.getSecondPhone());
			StringBuffer area  = new StringBuffer();
			area.append(secondHand.getOubaAreaByProId().getAreaName()+" ");
			area.append(secondHand.getOubaAreaByCityId().getAreaName()+" ");
			area.append(secondHand.getOubaAreaByDisId().getAreaName());
			locationshowEt.setText(area);
			addressEt.setText(secondHand.getAddress());
		}
		
		/**
		 * 选择地区
		 */
		private void chooseArea() {
			Intent intent = new Intent(SecondDetailEditActivity.this,
					ChooseListActivity.class);
			intent.putExtra(ChooseListActivity.KEY_DAO_CLASS, AreaDAO.class);
			startActivityForResult(intent, SECOND_REQUEST);
		}
		@Override
		/**
		 * 设置地区
		 * */
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			if (resultCode == Activity.RESULT_OK && requestCode == SECOND_REQUEST) {
				ArrayList<Integer> choosedId = null;
				choosedId = data
						.getIntegerArrayListExtra(ChooseListActivity.KEY_CHOOSED_LIST);
				StringBuffer sb = new StringBuffer();
				AreaDAO areaDAO = new AreaDAO(this);
				for (int i = 0; i < choosedId.size(); i++) {
					if (i == 0) {
						province = areaDAO.queryById(choosedId.get(i));
					} else if (i == 1) {
						city = areaDAO.queryById(choosedId.get(i));
					} else if (i == 2) {
						distrct = areaDAO.queryById(choosedId.get(i));
					}
					sb.append(areaDAO.queryById(choosedId.get(i)).getAreaName()
							+ " ");
				}
				sb.deleteCharAt(sb.length() - 1);
				locationshowEt.setText(sb.toString());
				isReSelectArea = true;
			}

		}
		/*
		 * 检查地域是否选择
		 * */

		private void checkArea() {
			if (isReSelectArea == false) {
				province = secondHand.getOubaAreaByProId();
				city = secondHand.getOubaAreaByCityId();
				distrct = secondHand.getOubaAreaByDisId();
				//Tool.ToolToast(getApplicationContext(), province+"");
			}
		}

		
		/**
		 * 检查用户输入合法性
		 * 
		 * @return
		 */
		private boolean checkData() {
			checkArea();
			if (secondDescriptionEt.getText().length() == 0) {
				MainActivity.showToast(this, R.string.publish_second_tips_detail);
				secondDescriptionEt.requestFocus();
				secondDescriptionEt.requestFocusFromTouch();
				return false;
			}
			if (secondHandPriceEt.getText().length() == 0) {
				MainActivity.showToast(this, R.string.publish_second_tips_price);
				secondHandPriceEt.requestFocus();
				secondHandPriceEt.requestFocusFromTouch();
				return false;
			}
			if (secondCountEt.getText().length() == 0) {
				MainActivity.showToast(this, R.string.publish_second_tips_num);
				secondCountEt.requestFocus();
				secondCountEt.requestFocusFromTouch();
				return false;
			}
			if (secondPhoneEt.getText().length() == 0) {
				MainActivity.showToast(this, R.string.publish_second_tips_phone);
				secondPhoneEt.requestFocus();
				secondPhoneEt.requestFocusFromTouch();
				return false;
			}
			if (province == null) {
				MainActivity
						.showToast(this, R.string.publish_second_tips_location);
				return false;
			}
			if (addressEt.getText().length() == 0) {
				MainActivity.showToast(this, R.string.publish_second_tips_location);
				addressEt.requestFocus();
				addressEt.requestFocusFromTouch();
				return false;
			}
			return true;
		}
		@Override
		public void onClick(View v) {
			 switch (v.getId()) {
			
			 case R.id.save_second_btn:
				
				savadata();
				
				break;
				
			
			}
		}
		/**
		 * 提交数据到服务器
		 */
		private void submitData() {
			if (checkData()) {
				secondHand.setSecondHandId(secondHand.getSecondHandId());
				secondHand.setSecondDescription(secondDescriptionEt.getText().toString());
				secondHand.setSecondHandPrice(Double.valueOf(secondHandPriceEt.getText().toString()));
				secondHand.setSecondCount(Integer.valueOf(secondCountEt.getText().toString()));
				secondHand.setSecondPhone(secondPhoneEt.getText().toString());
				secondHand.setAddress(addressEt.getText().toString());
				secondHand.setOubaAreaByProId(province);
				secondHand.setOubaAreaByCityId(city);
				secondHand.setOubaAreaByDisId(distrct);
				Uri.Builder builder = Uri.parse(
						Config.URL_GET_SECOND_HAND_INFORMATION_UPDATE).buildUpon();
				builder.appendQueryParameter("jsonStr",
						FormatUtils.getJSONString(secondHand));
				StringRequest updaterequest = new StringRequest(Method.GET,
						builder.toString(), new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {

								Json json = JSON.parseObject(response.toString(),
										Json.class);
								if (json.isSuccess()) {
									Intent intent = new Intent();
									intent.setAction(SECOND_HAND_DETAIL_EDITACTION);
									intent.putExtra(
											SECOND_HAND_DETAIL_EDIT_SID,
											secondHand.getSecondHandId());
									SecondDetailEditActivity.this
											.sendBroadcast(intent);
									finish();
									MainActivity.showToast(
											SecondDetailEditActivity.this,
											R.string.publish_job_time_edit_success);
								} else {
									MainActivity.showToast(
											SecondDetailEditActivity.this,
											R.string.publish_job_time_edit_error);

								}


							}
						}, new VolleyErrorAdapter(this) {
						});
				mQueue.add(updaterequest);
				mQueue.start();

			}

		}
		private void savadata(){
			ConfirmDialog dialog = new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_BOTTOM,
					getString(R.string.dialog_title_save),
					getString(R.string.dialog_save),
					getString(R.string.dialog_sure),
					getString(R.string.dialog_cancel));
			dialog.setOnclickListener(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						submitData();
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			});
			dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
		}
		
	/*	*//**
		 * 获取图片信息
		 *//*
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
			//PicRequest.setTag(TAG_GET_PIC);
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
		public void onImageClick(ViewPager viewPager, int pos) {
			if (imgPath != null) {
				Intent intent = new Intent(this, DetailImageActivity.class);
				intent.putExtra(DetailImageActivity.KEY_IMAGE_POS, pos);
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
		
	
	 
		}*/
		}
