package com.fengyang.fragment;

import static cn.smssdk.framework.utils.R.getStringRes;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.activity.CheckPhoneActivity;
import com.fengyang.activity.PublishAuthenticationActivity;
import com.fengyang.activity.ReSetActivity;
import com.fengyang.entity.User;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.tool.Tool;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;

public class FindByPhoneFragment extends Fragment {
	public static String APPKEY = "67a35ee70b22";
	public static String APPSECRET = "8ef469979cf82831efed26e1db20fab5";
	EditText phoneEt, codeEt;
	Button phoneBt, codeBt;
	private RequestQueue mQueue;
	private StringRequest getIdRequest;
	private User user;
	private static String TAG = "FindByPhoneFragment";
	public static final String KEY_PHONE_TYPE = "phone";
	public int id;
	private int recLen ;
	public String phString;
	private String phoneStr;
	private StuApplication application;

	private static final int REQUEST_MODIFY_PWD = 0x11;
	Timer timer;
	TimerTask task;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_find_by_phone,
				container, false);
		phoneEt = (EditText) rootView.findViewById(R.id.find_psd_phone_tel);
		phoneBt = (Button) rootView
				.findViewById(R.id.find_psd_by_message_get_code);
		codeEt = (EditText) rootView
				.findViewById(R.id.find_psd_phone_checkcode);
		codeBt = (Button) rootView.findViewById(R.id.find_psd_message_submit);

		application = (StuApplication) getActivity().getApplication();
//		if (application.isLogin()) {
//			User user = application.getUser();
//			if (null != user.getPhone()) {
//				phoneStr = user.getPhone();
//			}
//		}

		SMSSDK.initSDK(getActivity(), APPKEY, APPSECRET);
		EventHandler eh = new EventHandler() {

			@Override
			public void afterEvent(int event, int result, Object data) {

				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}

		};

		SMSSDK.registerEventHandler(eh);
		phoneBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (phoneEt.getText().toString().length() != 0) {
					phoneStr = phoneEt.getText().toString();
				}
				if ((!TextUtils.isEmpty(phoneStr))) {
					if ((FormatUtils.praseStringType(phoneStr) == 1)) {
						Log.d(TAG, "手机号没问题");
						
						getID(phoneStr);
					}
				} else {
					MainActivity.showToast(getActivity(),
							R.string.find_pwd_input_phone_require);
				}
			}
		});
		codeBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(codeEt.getText().toString())) {
					SMSSDK.submitVerificationCode("86", phString, codeEt
							.getText().toString());

				} else {
					MainActivity.showToast(getActivity(),
							R.string.find_pwd_input_code_require);
				}
			}
		});
		return rootView;
	}
    
	
	final Handler timehandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x123:
				phoneBt.setText(recLen
						+ getResources().getString(R.string.xx_second_reget));
				phoneBt.setClickable(false);
				phoneBt.setBackgroundColor(Color.GRAY);
				if (recLen < 0) {
					timer.cancel();
					task.cancel();
					phoneBt.setClickable(true);
					phoneBt.setText(R.string.find_pwd_get_auth_code);
					phoneBt.setBackgroundResource(R.drawable.button_red_selector);

				}
			}
		}
	};
	

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event=" + event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				// 短信注册成功后，返回MainActivity,然后提示新好友
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					// 提交验证码成功
					MainActivity.showToast(getActivity(),
							R.string.find_pwd_auth_success);
					// 当用户输入的电话与验证的电话不一致时，予以自动修正
					// 修正 StuApplication
					// TODO
					// 未修正本地数据库
					// 未修正服务器数据库
					if (null != application.getUser()) {
						User mUser = application.getUser();
						if (null != phoneStr) {
							if (!phString.equals(application.getUser()
									.getPhone())) {
								mUser.setPhone(phString);
								application.setUser(mUser);
							}
						} else {
							mUser.setPhone(phString);
							application.setUser(mUser);
						}
					}

					if (getActivity().getString(R.string.find_pwd_title)
							.equals(getActivity().getTitle().toString())) {
						Intent intent = new Intent(getActivity(),
								ReSetActivity.class);
						intent.putExtra(ReSetActivity.KEY_USER_ID, id);
						startActivity(intent);
						getActivity().finish();// 验证完成就结束当前页面
					} else {
						Log.i(TAG + " Title ", getActivity().getTitle()
								.toString());
						Log.i(TAG, "提交验证码成功");

						Intent intent = new Intent(getActivity(),
								PublishAuthenticationActivity.class);
						intent.putExtra(
								CheckPhoneActivity.KEY_AUTHENTICATION_TYPE,
								CheckPhoneActivity.keyStr);
						startActivity(intent);
						getActivity().finish();// 验证完成就结束当前页面
					}

				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
					MainActivity.showToast(getActivity(),
							R.string.find_pwd_auth_send_success);
				}
			} else {
				((Throwable) data).printStackTrace();
				int resId = getStringRes(getActivity(), "smssdk_network_error");
				MainActivity.showToast(getActivity(),
						R.string.find_pwd_auth_send_failure);
				if (resId > 0) {
					MainActivity.showToast(getActivity(), resId);
				}
			}

		}

	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_MODIFY_PWD) {
			if (resultCode == Activity.RESULT_OK) {
				getActivity().setResult(Activity.RESULT_OK);
			}
			getActivity().finish();
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer!=null) {
			
			timer.cancel();
		}
		SMSSDK.unregisterAllEventHandler();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getActivity().getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			getActivity().finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void getID(final String phone) {
		Log.d(TAG, "getID");
		mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
		Uri.Builder uri = Uri.parse(Config.URL_GET_USER_BY_PHONE).buildUpon();
		uri.appendQueryParameter("key", phone);
		Log.d(TAG, uri.toString());
		getIdRequest = new StringRequest(Method.GET, uri.toString(),
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d(TAG, response.toString());
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.getObj() != null) {

							SMSSDK.getVerificationCode("86", phone);
							user = JSON.parseObject(json.getObj().toString(),
									User.class);
							id = user.getId();
							phString = phone;
							recLen = 60;
							timer = new Timer();
							  task = new TimerTask() {
								@Override
								public void run() {
									
									recLen--;
									Log.d(TAG, recLen+"");
									Message message = new Message();
									message.what = 0x123;
									
									timehandler.sendMessage(message);
								}
							};
							timer.schedule(task, 1000, 1000);

						} else {

							Tool.ToolToast(getActivity(), getResources()
									.getString(R.string.user_is_not));
						}

					}
				},

				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {

					};
				});

		mQueue.add(getIdRequest);
	}

}
