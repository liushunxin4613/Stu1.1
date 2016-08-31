package com.fengyang.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.entity.User;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.tool.Tool;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.ImmersionActivity;

@SuppressLint({ "ShowToast", "SimpleDateFormat", "HandlerLeak" })
public class SignActivity extends ImmersionActivity implements OnClickListener {

	// ��һ��ǩ��ʱ��
	private static String THE_LAST_TIME_SIGN = "the_last_sign_time";
	// ��һ��ǩ������
	// private static String INITIAL_SIGN_CORE = "the_last_sign_core";
	// ��һ��ǩ��ʱ���ӵķ���
	// private static String THE_LAST_SIGN_CORE = "the_last_sign_increate_core";
	private static String SIGN_LAG = "SignActivity";
	private AnimationDrawable anim;
	private Button signBt;
	private TextView mTextView;
	private int count = 0;
	private int initialsigncore;
	private int everysigncore;
	private StuApplication application;
	public StringRequest request;
	public StringRequest updaterequest;
	public RequestQueue mQueue;
	public User user;
	public Uri.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign);
		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		setActionBar();
		PrepareUI();
		getGifView();
		judge();
		//judge();
		application = (StuApplication) getApplication();
		user = application.getUser();
		initialsigncore = user.getPoints();
		mTextView.setText(getResources().getText(R.string.signtv)
				+ String.valueOf(initialsigncore));
		
		

	}
    Handler myHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		super.handleMessage(msg);
    		switch (msg.what) {
			case 0x123:
				anim.stop();
				break;

			}
    	}
    	
    };
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}
	
	private void PrepareUI() {
		signBt = (Button) findViewById(R.id.sign_bt);
		mTextView = (TextView) findViewById(R.id.sign_tv);
		signBt = (Button) findViewById(R.id.sign_bt);
		mTextView.setText(getResources().getText(R.string.signtv) + "0");
	}


	/*
	 * ����gifview��ͼƬ�Ķ���Ч�� *
	 */
	public void getGifView() {

		ImageView image = (ImageView) findViewById(R.id.sign_image);
		anim = (AnimationDrawable) image.getBackground();
	}

	/*
	 * 1.�ж����ν���ǩ��ҳ���ʱ���Ƿ���ͬһ�죬����ͬһ�죬������ͣ 2.����ͬһ�죬������ͣ,button��ң��ּ��仯
	 * 
	 * *
	 */
	public void judge() {

		long lastday = Tool.GetLONGSharedPreferences(SignActivity.this,
				THE_LAST_TIME_SIGN, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new java.util.Date());
		long nowtime = getStringToDate(date);
//         Log.d("count", lastday+"-------");
		if (lastday != -1) {
			if ((nowtime - lastday) != 0) {
				
				count = 0;
//				Log.d("count", count+"00000");
				
			} else {
				
				count = 1;
//				Log.d("count", count+"11111");
				mTextView.setText(getResources().getText(R.string.signtv)
						+ String.valueOf(initialsigncore));
				signBt.setBackgroundColor(Color.GRAY);

			}
		} else {
//			Log.d("count", "//////////");
			application = (StuApplication) getApplication();
			user = application.getUser();
			initialsigncore = user.getPoints();
			mTextView.setText(getResources().getText(R.string.signtv)
					+ String.valueOf(initialsigncore));
		}

	}

	@SuppressWarnings("unused")
	private void setActionBar() {
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// ���parentΪnull ��ôxml�ļ���FrameLayout���ֲ�����ʧЧ
//		View v = inflator.inflate(R.layout.actionbar_center_title_layout,
//				new LinearLayout(getApplicationContext()), false);
//		TextView titleView = (TextView) v.findViewById(R.id.actionBar_titel);
//		titleView.setText(R.string.day);

		getActionBar().setDisplayShowCustomEnabled(true);
//		getActionBar().setCustomView(v);
		getActionBar().setDisplayShowTitleEnabled(true);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sign_bt:
			Log.d("count", count+"******");
               //judge();
			if (count == 1) {
				
				Toast.makeText(SignActivity.this,
						getResources().getString(R.string.day_toast),
						Toast.LENGTH_LONG).show();

			} else {
               
				anim.start();
				myHandler.sendEmptyMessageDelayed(0x123, 750);
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				String date = sdf.format(new java.util.Date());
//				Tool.PutSharePreferences(SignActivity.this, THE_LAST_TIME_SIGN,
//						getStringToDate(date));

				count = 1;
				checkIn();

			}
			break;
		}
	}

	// ������ת����ʱ���
	public long getStringToDate(String time) {

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = new Date();

		try {
			date1 = sdf1.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date1.getTime();

	}

	/*
	 * 1.ǩ��ҳ�� 2.ǩ���ɹ������ؽ���ǩ������ 3.��ȡ�ѻ���� 4.ǩ���󽫻����ϴ��������� *
	 */
	public void checkIn() {
		mQueue = Volley.newRequestQueue(SignActivity.this);
		int userId = user.getId();
		builder = Uri.parse(Config.URL_CHECK_IN).buildUpon();
		builder.appendQueryParameter("id", String.valueOf(userId));
		request = new StringRequest(Method.GET, builder.toString(),
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						Log.d(SIGN_LAG, response);

						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							
							if ((Integer)json.getObj()==-1) {
								Toast.makeText(SignActivity.this,
										getResources().getString(R.string.day_toast),
										Toast.LENGTH_LONG).show();
								signBt.setBackgroundColor(Color.GRAY);
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								String date = sdf.format(new java.util.Date());
								Tool.PutSharePreferences(SignActivity.this, THE_LAST_TIME_SIGN,
										getStringToDate(date));
							}
							else {
								everysigncore = (Integer) json.getObj();
								Log.d(SIGN_LAG, String.valueOf(everysigncore));
								count = 1;
								initialsigncore = initialsigncore
										+ everysigncore;
								Log.d(SIGN_LAG, String.valueOf(everysigncore));
								signBt.setBackgroundColor(Color.GRAY);
								String showsign = getResources().getString(
										R.string.today_signcore)
										+ String.valueOf(everysigncore);
								mTextView.setText(getResources().getText(
										R.string.signtv)
										+ String.valueOf(initialsigncore));
								Log.d(SIGN_LAG, showsign);
								Toast.makeText(SignActivity.this, showsign,
										Toast.LENGTH_LONG).show();
//								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//								String date = sdf.format(new java.util.Date());
//								Tool.PutSharePreferences(SignActivity.this, THE_LAST_TIME_SIGN,
//										getStringToDate(date));
								updatepoint();
							}
						} else {
							Tool.ToolToast(SignActivity.this, getResources()
									.getString(R.string.today_sign_fail_txt)
									.toString());

						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {

					}

				});
		mQueue.add(request);
		mQueue.start();

	}
	/*
	 * ���»���ֵ
	 * */
	private void updatepoint(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("points",initialsigncore);
		Uri.Builder userUrl = Uri.parse(
				Config.URL_GET_USER_INFORMATION_UPDATE)
				.buildUpon();
		int userId = user.getId();
		userUrl.appendQueryParameter("id",
				String.valueOf(userId));
		userUrl.appendQueryParameter("jsonStr",
				FormatUtils.getJSONString(map));
		Log.d(SIGN_LAG, FormatUtils.getJSONString(map));
		updaterequest = new StringRequest(Method.GET,
				userUrl.toString(),
				new Response.Listener<String>() {
					@Override
					public void onResponse(
							String response) {

						Json json = JSON.parseObject(
								response.toString(),
								Json.class);
						if (json.isSuccess()) {

							user.setPoints(initialsigncore);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String date = sdf.format(new java.util.Date());
							Tool.PutSharePreferences(SignActivity.this, THE_LAST_TIME_SIGN,
									getStringToDate(date));

						} else {
							MainActivity
									.showToast(
											SignActivity.this,
											R.string.publish_job_time_publish_error);

						}

						Log.d(SIGN_LAG, response);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(
							VolleyError error) {

					}

				});
		mQueue.add(updaterequest);
		mQueue.start();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}