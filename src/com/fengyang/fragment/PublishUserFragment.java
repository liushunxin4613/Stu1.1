package com.fengyang.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.dialog.DatePickerDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.dialog.SingleChoiceDialog;
import com.fengyang.dialog.SingleChoiceDialog.OnItemSelectedListener;
import com.fengyang.entity.User;
import com.fengyang.entity.UserDetail;
import com.fengyang.model.Json;
import com.fengyang.service.InitUserService;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;

public class PublishUserFragment extends Fragment implements OnClickListener {

	private static PublishUserFragment fragment;
	private static final String TAG = "publishUserFragment";

	private EditText nicknameEt;
	private EditText phoneEt;
	private EditText truenameEt;
	private EditText emailEt;
	private EditText qqEt;
	private EditText brifEt;
	private String[] sexStr;
//	private String[] verifyStr;
	private LinearLayout sexLy;
	private TextView sexTv;

	private LinearLayout birthdayLy;
	private TextView birthdayTv;

	private Button submitBt;

	private Integer SEX = null;

	private Date birthday;
	private SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd",
			Locale.CHINA);

	private DatePickerDialog datePickerDialog;
	private SingleChoiceDialog sexDialog;

	private StuApplication application;
	private User user;
	private UserDetail userDetail;
	private RequestQueue mQueue;
	private LoadingDialog dialog;

	private BroadcastReceiver receiver;
	private IntentFilter intentFilter;

	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new PublishUserFragment();
		return fragment;
	}

	private OnDateSetListener onDateSetListener = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datepicker, int year, int month,
				int day) {
			birthday = timeToDate(year, month + 1, day);
			if (null != birthday) {
				birthdayTv.setText(ss.format(birthday));
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (StuApplication) getActivity().getApplication();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_publish_user,
				container, false);
		setViewListener(rootView);
		if (application.isLogin()) {
			init();
			isVerifyType();
			fillUser();
		}
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().registerReceiver(receiver, intentFilter);
	}

	@Override
	public void onStop() {
		super.onStop();
		getActivity().unregisterReceiver(receiver);
	}

	private void init() {
		mQueue = Volley.newRequestQueue(getActivity());
		user = application.getUser();
		// 启动service请求获取用户详情数据
		Intent intent = new Intent(getActivity(), InitUserService.class);
		intent.putExtra(InitUserService.KEY_START_SERVICE_FOR,
				InitUserService.TASK_REQUEST_USER_DETAIL);
		getActivity().startService(intent);

		dialog = new LoadingDialog(getString(R.string.dialog_loading));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {
			@Override
			public void onBackPressed() {
				dialog.dismiss();
				mQueue.cancelAll(TAG);
			}
		});
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		dialog.show(ft, TAG);
		intentFilter = new IntentFilter();
		intentFilter.addAction(InitUserService.ACTION_GET_USER_DETAIL);
		intentFilter.addAction(InitUserService.ACTION_GET_USER_DETAIL_FAILURE);
		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (InitUserService.ACTION_GET_USER_DETAIL.equals(action)) {
					userDetail = application.getUserDetail();
					fillUserDetail();
					dialog.dismiss();
				} else if (InitUserService.ACTION_GET_USER_DETAIL_FAILURE
						.equals(action)) {
					dialog.dismiss();
					MainActivity.showToast(getActivity(),
							R.string.error_get_data_error);
					getActivity().finish();
				}
			}
		};
	}

	/**
	 * 填充用户数据
	 */
	private void fillUser() {

		if (null == user.getName()) {
			if (!"".equals(nicknameEt.getText().toString())) {
				user.setName(nicknameEt.getText().toString());
			}
		} else {
			if (!"".equals(user.getName())) {
				nicknameEt.setHint(user.getName());
			}
		}

		if (null == user.getPhone()) {
			if (!"".equals(phoneEt.getText().toString())) {
				user.setPhone(phoneEt.getText().toString());
			}
		} else {
			if (!"".equals(user.getPhone())) {
				phoneEt.setHint(user.getPhone());
				//需验证才能修改手机号
//				phoneEt.setEnabled(false);
			}
		}

		if (null == user.getEmail()) {
			if (!"".equals(emailEt.getText().toString())) {
				user.setEmail(emailEt.getText().toString());
			}
		} else {
			if (!"".equals(user.getEmail())) {
				emailEt.setHint(user.getEmail());
			}
		}

	}

	/**
	 * 填充用户详情数据
	 */
	private void fillUserDetail() {
		
		sexStr=getResources().getStringArray(R.array.sex_choice_array);
		if (null == userDetail.getSex()) {
			if (SEX != null) {
				userDetail.setSex(SEX);
			}
		} else {
			SEX = userDetail.getSex();
			sexTv.setHint(sexStr[SEX]);
		}

		if (null == userDetail.getBirthday()) {
			if (birthday != null) {
				userDetail.setBirthday(birthday);
			}
		} else {
			Log.i("userDetail.getBirthday()", userDetail.getBirthday() + "");
			if (!"".equals(userDetail.getBirthday())) {
				birthdayTv.setHint(ss.format(userDetail.getBirthday()));
			}
		}

		if (null == userDetail.getTrueName()) {
			if (!"".equals(truenameEt.getText().toString())) {
				userDetail.setTrueName(truenameEt.getText().toString());
			}
		} else {
			if (!"".equals(userDetail.getTrueName())) {
				truenameEt.setHint(userDetail.getTrueName());
			}
		}

		if (null == userDetail.getQqNO()) {
			if (!"".equals(qqEt.getText().toString())) {
				userDetail.setQqNO(qqEt.getText().toString());
			}
		} else {
			if (!"".equals(userDetail.getQqNO())) {
				qqEt.setHint(userDetail.getQqNO());
			}
		}
		
		if (null == userDetail.getBrifIntrodction()) {
			if (!"".equals(brifEt.getText().toString())) {
				userDetail.setBrifIntrodction(brifEt.getText().toString());
			}
		} else {
			if (!"".equals(userDetail.getBrifIntrodction())) {
				brifEt.setHint(userDetail.getBrifIntrodction());
			}
		}
	}
	private void isVerifyType(){
		if ((application.getUser().getIsVerify() == User.VERIFY_TYPE_COMMIT)
				|| (application.getUser().getIsVerify() == User.VERIFY_TYPE_PASSED)) {
			sexLy.setEnabled(false);
			truenameEt.setEnabled(false);
		}
	}
	private void setViewListener(View rootView) {

		nicknameEt = (EditText) rootView
				.findViewById(R.id.Et_publish_user_nickname);
		phoneEt = (EditText) rootView.findViewById(R.id.Et_publish_user_phone);
		truenameEt = (EditText) rootView
				.findViewById(R.id.Et_publish_user_truename);
		emailEt = (EditText) rootView.findViewById(R.id.Et_publish_user_email);
		qqEt = (EditText) rootView.findViewById(R.id.Et_publish_user_qq);
		brifEt = (EditText) rootView.findViewById(R.id.Et_publish_user_brif);

		sexLy = (LinearLayout) rootView.findViewById(R.id.Re_publish_user_sex);
		sexLy.setOnClickListener(this);

		birthdayLy = (LinearLayout) rootView
				.findViewById(R.id.Re_publish_user_birthday);
		birthdayLy.setOnClickListener(this);

		sexTv = (TextView) rootView.findViewById(R.id.Tv_publish_user_sex);
		birthdayTv = (TextView) rootView
				.findViewById(R.id.Tv_publish_user_birthday);

		submitBt = (Button) rootView.findViewById(R.id.Bt_publish_user_submit);
		submitBt.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Re_publish_user_sex:
			sexData();
			break;
		case R.id.Re_publish_user_birthday:
			birthdayData();
			break;
		case R.id.Bt_publish_user_submit:
			submitData();
			break;
		}

	}

	/**
	 * 弹出设置生日的对话框
	 */
	private void birthdayData() {
		Calendar c = Calendar.getInstance();
		if (userDetail != null && userDetail.getBirthday() != null)
			c.setTime(userDetail.getBirthday());
		else {
			if (birthday == null) {
				c.set(1990, 0, 1);
				birthday = c.getTime();
			} else
				c.setTime(birthday);
		}
		datePickerDialog = new DatePickerDialog(onDateSetListener,
				c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH));
		datePickerDialog
		.setTitle(getString(R.string.Et_publish_user_birthdays));
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		datePickerDialog.show(ft, TAG);
	}

	private void sexData() {
		int sex = userDetail.getSex() == null ? 0 : userDetail.getSex();
		sexDialog = new SingleChoiceDialog(getActivity(),
				getString(R.string.dialog_choose_sex), sex,
				R.array.sex_choice_array);
		sexDialog.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int pos) {
				userDetail.setSex(pos);
				sexTv.setText(sexStr[pos]);
				SEX = pos;
			}
		});
		sexDialog.show(getChildFragmentManager(), TAG);
	}

	private void submitData() {
		dialog = new LoadingDialog(
				getString(R.string.Bt_publish_user_submitting));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {
			@Override
			public void onBackPressed() {
				dialog.dismiss();
				mQueue.cancelAll(TAG);
			}
		});
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		dialog.show(ft, TAG);
		Log.i("userDetail",
				userDetail.getBirthday() + "  id= " + userDetail.getId());
		andSetUser();
		updateUserDetail();
	}

	private void andSetUser() {
		if (!"".equals(nicknameEt.getText().toString())) {
			user.setName(nicknameEt.getText().toString());
		}

		if (SEX != null) {
			userDetail.setSex(SEX);
		}

		if (birthday != null) {
			userDetail.setBirthday(birthday);
		}

		if (!"".equals(phoneEt.getText().toString())) {
			user.setPhone(phoneEt.getText().toString());
		}

		if (!"".equals(truenameEt.getText().toString())) {
			userDetail.setTrueName(truenameEt.getText().toString());
		}

		if (!"".equals(emailEt.getText().toString())) {
			user.setEmail(emailEt.getText().toString());
		}

		if (!"".equals(qqEt.getText().toString())) {
			userDetail.setQqNO(qqEt.getText().toString());
		}
		
		if (!"".equals(brifEt.getText().toString())) {
			userDetail.setBrifIntrodction(brifEt.getText().toString());
		}
	}

	/**
	 * 更新成功，显示提示信息，并将新数据提交到Application中更新
	 */
	private void isSuccess() {
		user.setUserDetail(userDetail);
		application.setUser(user);
		dialog.dismiss();
		MainActivity.showToast(getActivity(),
				R.string.Bt_publish_user_submit_success);
		getActivity().finish();
	}

	private void isError() {
		dialog.dismiss();
		MainActivity.showToast(getActivity(),
				R.string.Bt_publish_user_submit_failure);

	}

	private Date timeToDate(int year, int month, int day) {
		String time = year + "-" + month + "-" + day;
		Date date = new Date();
		try {
			date = ss.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private void updateUser() {
		Uri.Builder builder = Uri.parse(Config.URL_GET_USER_UPDATE).buildUpon();
		builder.appendQueryParameter("jsonStr", FormatUtils.getJSONString(user,
				null, new String[] { "userDetail" }));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Json json = JSON.parseObject(response.toString(),
						Json.class);
				if (json.isSuccess()) {
					isSuccess();
				} else {
					isError();
				}
			}
		}, new VolleyErrorAdapter(getActivity()) {
			@Override
			protected void onOccurError(VolleyError error) {
				super.onOccurError(error);
				isError();
			}
		});
		request.setTag(TAG);
		mQueue.add(request);
		mQueue.start();
	}

	private void updateUserDetail() {
		Uri.Builder builder = Uri.parse(Config.URL_GET_USER_UPDATE_DETAIL)
				.buildUpon();
		builder.appendQueryParameter("jsonStr",
				FormatUtils.getJSONString(userDetail));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.i(TAG, "response = " + response);
				Json json = JSON.parseObject(response.toString(),
						Json.class);
				if (json.isSuccess()) {
					updateUser();
				} else {
					isError();
				}
			}
		}, new VolleyErrorAdapter(getActivity()) {
			@Override
			protected void onOccurError(VolleyError error) {
				super.onOccurError(error);
				isError();
			}
		});
		request.setTag(TAG);
		mQueue.add(request);
		mQueue.start();
	}

}
