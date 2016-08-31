package com.fengyang.fragment;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

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
import com.fengyang.entity.User;
import com.fengyang.model.Json;
import com.fengyang.service.InitUserService;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.MD5;
import com.fengyang.util.ui.UIUtils;

public class RegistFragment extends Fragment implements OnTouchListener,
		OnClickListener {

	protected static final String TAG = "RegistFragment";

	private static final String TAG_REGIST_REQUEST = "registRequest";

	private RequestQueue mQueue;

	private LoadingDialog dialog;

	private LinearLayout containerLayout;

	private EditText userNameET;
	private EditText userPwdET;
	private EditText userRepwdET;
	private Button registBtn;
	private CheckBox readCheckBox;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_regist, container,
				false);
		containerLayout = (LinearLayout) rootView
				.findViewById(R.id.fragment_layout);

		userNameET = (EditText) rootView.findViewById(R.id.register_account_ET);
		userPwdET = (EditText) rootView.findViewById(R.id.register_password_ET);
		userRepwdET = (EditText) rootView
				.findViewById(R.id.register_repassword_ET);
		registBtn = (Button) rootView.findViewById(R.id.register_register_BT);
		readCheckBox = (CheckBox) rootView.findViewById(R.id.register_checkbox);

		registBtn.setOnClickListener(this);
		readCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					registBtn.setClickable(true);
					registBtn
							.setBackgroundResource(R.drawable.button_blue_selector);
				} else {
					registBtn.setClickable(false);
					registBtn.setBackgroundColor(getResources().getColor(
							R.color.base_button_unclickable));
				}

			}
		});

		containerLayout.setOnTouchListener(this);
		return rootView;
	}

	@Override
	public boolean onTouch(View v, MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			if (!UIUtils.isTouchInView(userNameET, x, y)
					&& !UIUtils.isTouchInView(userPwdET, x, y)
					&& !UIUtils.isTouchInView(userRepwdET, x, y)) {
				// 点击位置不在输入框内，隐藏键盘
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			break;
		case MotionEvent.ACTION_UP:
			v.performClick();
			break;
		default:
			break;
		}
		return false;
	}

	private void sendRegist() {
		if (checkData()) {
			User user = new User();
			String userName = userNameET.getText().toString();
			user.setName(userName);
			
			if (FormatUtils.praseStringType(userName) == 1) {//验证手机号
				user.setPhone(userName);
			} else if (FormatUtils.praseStringType(userName) == 2) {//验证邮箱
				user.setEmail(userName);
			} else {
				MainActivity.showToast(getActivity(),
						R.string.register_name_error);
				return;
			}
			String pwd = userPwdET.getText().toString();
			user.setPassword(MD5.GetMD5Code(pwd));
			Uri.Builder builder = Uri.parse(Config.URL_GET_USER_REGIST)
					.buildUpon();
			builder.appendQueryParameter("jsonStr",
					FormatUtils.getJSONString(user));
			JsonObjectRequest request = new JsonObjectRequest(Method.GET,
					builder.toString(), null, new Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							// TODO Auto-generated method stub
							Log.d(TAG, "response : " + response.toString());
							Json json = JSON.parseObject(response.toString(),
									Json.class);
							if (json.isSuccess()) {
								dialog.dismiss();
								User user = JSON.parseObject(json.getObj()
										.toString(), User.class);
								onRegistSecceed(user);
							} else {
								MainActivity.showToast(getActivity(),
										R.string.register_failure);
								dialog.dismiss();
							}
						}

					}, new VolleyErrorAdapter(getActivity()) {
						@Override
						protected void onOccurError(VolleyError error) {
							super.onOccurError(error);
							dialog.dismiss();
						}
					});

			request.setTag(TAG_REGIST_REQUEST);
			mQueue = Volley.newRequestQueue(getActivity());
			mQueue.add(request);
			mQueue.start();
			dialog = new LoadingDialog(getString(R.string.register_is_registing));
			dialog.setOnBackPressedListener(new OnBackPressedLisener() {

				@Override
				public void onBackPressed() {
					dialog.dismiss();
					mQueue.cancelAll(TAG_REGIST_REQUEST);
				}
			});
			FragmentTransaction ft = getChildFragmentManager()
					.beginTransaction();
			dialog.show(ft, TAG);
		}
	}

	/**
	 * 注册成功之后的一些操作
	 * 
	 * @param user
	 */
	private void onRegistSecceed(User user) {
		MainActivity.showToast(getActivity(), R.string.register_Succeed);
		StuApplication application = (StuApplication) getActivity()
				.getApplication();
		application.setUser(user);
		SharedPreferences sharedPre = getActivity().getSharedPreferences(
				Config.SHAREPREFERENCE_USER_INFO, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPre.edit();
		editor.putString(Config.USER_INFO_NAME, userNameET.getText().toString());
		editor.putString(Config.USER_INFO_PASSWORD, userPwdET.getText()
				.toString());
		editor.commit();
		getActivity().sendBroadcast(new Intent(InitUserService.ACTION_LOGINED));
		getActivity().setResult(Activity.RESULT_OK);
		getActivity().finish();
	}

	private boolean checkData() {
		if (userNameET.getText().length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.register_name_require);
			return false;
		}
		String pwd = userPwdET.getText().toString();
		String rePwd = userRepwdET.getText().toString();
		if (pwd.length() == 0) {
			MainActivity
					.showToast(getActivity(), R.string.register_pwd_require);
			return false;
		} else {
			if (FormatUtils.praseStringType(pwd) != 4) {
				MainActivity.showToast(getActivity(),
						R.string.register_pwd_error);
				return false;
			}
		}
		if (rePwd.length() == 0) {
			MainActivity.showToast(getActivity(),
					R.string.register_repwd_require);
			return false;
		} else {
			if (FormatUtils.praseStringType(rePwd) != 4) {
				MainActivity.showToast(getActivity(),
						R.string.register_pwd_error);
				return false;
			}
		}
		if (!pwd.equals(rePwd.toString())) {
			MainActivity.showToast(getActivity(),
					R.string.register_pwd_not_equal);
			return false;
		}
		return true;
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mQueue != null)
			mQueue.stop();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.register_register_BT:
			sendRegist();
			break;

		default:
			break;
		}
	}
}
