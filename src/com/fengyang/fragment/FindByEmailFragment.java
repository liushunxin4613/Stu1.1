package com.fengyang.fragment;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fengyang.activity.ReSetActivity;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.ui.BaseUIHandler;
import com.fengyang.volleyTool.FixedJsonRequest;

public class FindByEmailFragment extends Fragment implements OnClickListener {

	protected static final String TAG = "FindByEmailFragment";

	private static final String TAG_GET_CODE = "getVerifyCode";

	private static final String TAG_VERIFY_CODE = "verifyCode";
	/**
	 * 更新按钮的计时
	 */
	private static final int MSG_UPDATE_COUNT_BUTTON = 1;
	/**
	 * 计时完成，更新按钮及相关控件
	 */
	private static final int MSG_COUNT_FINISHED = 2;

	private EditText emailET;

	private EditText codeET;

	private Button getCodeBtn;

	private Button verifyBtn;

	private RequestQueue mQueue;

	private LoadingDialog dialog;

	private Timer timer;

	private UIHandler handelr;

	static class UIHandler extends BaseUIHandler<FindByEmailFragment> {

		public UIHandler(FindByEmailFragment target) {
			super(target);
		}

		@Override
		public void handleMessage(Message msg) {
			FindByEmailFragment target = get();
			switch (msg.what) {
			case MSG_UPDATE_COUNT_BUTTON:
				target.getCodeBtn.setText(target.getString(
						R.string.find_pwd_get_auth_code_count, msg.arg1));
				break;
			case MSG_COUNT_FINISHED:
				target.getCodeBtn.setText(R.string.find_pwd_get_code_again);
				target.getCodeBtn.setEnabled(true);
				target.getCodeBtn
						.setBackgroundResource(R.drawable.button_red_selector);
				break;

			}
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mQueue = Volley.newRequestQueue(getActivity());
		handelr = new UIHandler(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_find_by_email,
				container, false);

		emailET = (EditText) rootView.findViewById(R.id.find_psd_email);
		codeET = (EditText) rootView.findViewById(R.id.find_psd_checkcode);
		getCodeBtn = (Button) rootView
				.findViewById(R.id.find_psd_by_email_get_code);
		verifyBtn = (Button) rootView.findViewById(R.id.find_psd_email_submit);
		perpareUI();
		return rootView;
	}

	private void perpareUI() {
		getCodeBtn.setOnClickListener(this);
		verifyBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.find_psd_by_email_get_code:
			requestVerifyCode();
			break;
		case R.id.find_psd_email_submit:
			verifyCode();
			break;
		}
	}

	@Override
	public void onDestroy() {
		mQueue.cancelAll(TAG_GET_CODE);
		mQueue.cancelAll(TAG_VERIFY_CODE);
		super.onDestroy();
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
	/**
	 * 发送请求获取验证码
	 */
	private void requestVerifyCode() {
		if (emailET.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.find_pwd_input_email_require);
			return;
		}
		if (FormatUtils.praseStringType(emailET.getText().toString()) != 2) {
			MainActivity.showToast(getActivity(),
					R.string.find_pwd_input_email_error);
			return;
		}
		Uri.Builder builder = Uri.parse(Config.URL_REQUEST_VERIFY_BY_EMAIL)
				.buildUpon();
		builder.appendQueryParameter("key", emailET.getText().toString());
		Log.i(TAG, "uri = " + builder.toString());
		FixedJsonRequest request = new FixedJsonRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						dialog.dismiss();
						MainActivity.showToast(getActivity(), json.getMessage());
						if (json.isSuccess()) {
							startTimer();
						}
					}
				}, new VolleyErrorAdapter(getActivity()) {
					@Override
					protected void onOccurError(VolleyError error) {
						super.onOccurError(error);
						dialog.dismiss();
						// MainActivity.showToast(getActivity(),
						// R.string.find_pwd_auth_send_failure);
					}
				});
		request.setTimeOut(20000, 0);
		request.setTag(TAG_GET_CODE);
		mQueue.add(request);
		mQueue.start();
		dialog = new LoadingDialog(
				getString(R.string.find_pwd_auth_sending_code));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {

			@Override
			public void onBackPressed() {
				dialog.dismiss();
				mQueue.cancelAll(TAG_GET_CODE);
			}
		});
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		dialog.show(ft, TAG);

	}

	/**
	 * 开始计时，
	 */
	private void startTimer() {
		Log.d(TAG, "startTimer");
		timer = new Timer();
		getCodeBtn.setEnabled(false);
		getCodeBtn.setBackgroundColor(Color.GRAY);
		timer.schedule(new TimerTask() {

			int count = Config.VERIFY_EMAIL_CODE_EXPIRATION_TIME / 1000;

			@Override
			public void run() {
				Log.d(TAG, "count = " + count);
				if (count > 0) {
					handelr.obtainMessage(MSG_UPDATE_COUNT_BUTTON, count--, 0)
							.sendToTarget();
				} else {
					timer.cancel();
					handelr.obtainMessage(MSG_COUNT_FINISHED).sendToTarget();

				}

			}
		}, 0, 1000);
	}

	/**
	 * 验证验证码是否正确
	 */
	private void verifyCode() {
		if (codeET.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.authentication_number);
			return;
		}
		Uri.Builder builder = Uri.parse(Config.URL_VERIFY_EMAIL).buildUpon();
		builder.appendQueryParameter("key", emailET.getText().toString());
		builder.appendQueryParameter("code", codeET.getText().toString());
		builder.appendQueryParameter("verifyTime",
				String.valueOf(new Date().getTime()));
		Log.i(TAG, "uri = " + builder.toString());
		FixedJsonRequest request = new FixedJsonRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						dialog.dismiss();
						MainActivity.showToast(getActivity(), json.getMessage());
						if (json.isSuccess()) {
							if (timer != null)
								timer.cancel();
							Integer userId = (Integer) json.getObj();
							Log.d(TAG, "userId = " + userId);
							Intent intent = new Intent(getActivity(),
									ReSetActivity.class);
							intent.putExtra(ReSetActivity.KEY_USER_ID, userId);
							startActivity(intent);
							getActivity().finish();
						} else {

						}
					}
				}, new VolleyErrorAdapter(getActivity()) {
					@Override
					protected void onOccurError(VolleyError error) {
						super.onOccurError(error);
						dialog.dismiss();
					}
				});
		request.setTag(TAG_VERIFY_CODE);
		mQueue.add(request);
		mQueue.start();
		dialog = new LoadingDialog(getString(R.string.find_pwd_auth_verify));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {

			@Override
			public void onBackPressed() {
				dialog.dismiss();
				mQueue.cancelAll(TAG_VERIFY_CODE);
			}
		});
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		dialog.show(ft, TAG);
	}
}
