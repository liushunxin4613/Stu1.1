package com.fengyang.activity;

import java.sql.Timestamp;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.entity.AppUserAdvice;
import com.fengyang.model.Json;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.tool.Tool;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.VersionUtils;
import com.fengyang.util.ui.ImmersionActivity;

public class FeedBackActivity extends ImmersionActivity {

	private static final String TAG = "FeedBackActivity";
	private EditText edit_feedBack;
	private Button btn_send;

	private StuApplication mStuApplication;
	private RequestQueue mQueue;
	private AppUserAdvice mAdvice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		edit_feedBack = (EditText) findViewById(R.id.edit_feedBack);
		btn_send = (Button) findViewById(R.id.btn_send);

		mQueue = Volley.newRequestQueue(FeedBackActivity.this);
		btn_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				mySend();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void mySend() {
		// 判断输入是否为空
		// 若无输入，显示提示
		if ((edit_feedBack.getText().toString()).equals("")) {
			Toast.makeText(FeedBackActivity.this, R.string.feedback_null,
					Toast.LENGTH_SHORT).show();
		} else {// 有输入
			// 判断网络
			ConnectivityManager cm = (ConnectivityManager) FeedBackActivity.this
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			// 无网络
			if (info == null) {
				Toast.makeText(FeedBackActivity.this, R.string.soft_no_net,
						Toast.LENGTH_SHORT).show();
			}
			// 有网络
			// 把用户的意见送到服务器
			else {
				try {
					// 判断是否登陆，登陆的话要传id号，没有就不能提意见
					mStuApplication = (StuApplication) this.getApplication();
					mAdvice = new AppUserAdvice();
//					vu = new VersionUtils(getApplicationContext());
					
					mAdvice.setOubaMember(mStuApplication.getUser());
					mAdvice.setAppVersionCode(VersionUtils.getVersionCode(getApplicationContext()));
					mAdvice.setAppVersionName(VersionUtils.getVersionName(getApplicationContext()));
					mAdvice.setDeviceModel(Build.MODEL);
					mAdvice.setAndroidRelease(Build.VERSION.RELEASE);
					mAdvice.setAndroidSdk(Build.VERSION.SDK_INT);
					mAdvice.setAdviceContent(edit_feedBack.getText().toString());
					mAdvice.setPublishTime(new Timestamp(new Date().getTime()));
					mAdvice.setIsSolved((short) 0);
					Log.w("versionCode", VersionUtils.getVersionCode(getApplicationContext())+"");
					Log.w("verionName",VersionUtils.getVersionName(getApplicationContext()));
					Uri.Builder builder = Uri.parse(
							Config.URL_ADVICE_SEND).buildUpon();
					builder.appendQueryParameter("jsonStr",
							FormatUtils.getJSONString(mAdvice));
					StringRequest feedbackrequest = new StringRequest(Method.GET,
							builder.toString(), new Response.Listener<String>() {
								@Override
								public void onResponse(String response) {
									Log.d(TAG,"response  " + response);
									Json json = JSON.parseObject(response.toString(),
											Json.class);
									if (json.isSuccess()) {
										Tool.ToolToast(FeedBackActivity.this, getResources().getString(R.string.feedback_success));
										finish();
									} else {
										Tool.ToolToast(FeedBackActivity.this,getResources().getString(R.string.feedback_fail));
									}
								}
							}, new VolleyErrorAdapter(this) {
							});
					mQueue.add(feedbackrequest);
					mQueue.start();

				} catch (Exception e) {
					Toast.makeText(FeedBackActivity.this, getResources().getString(R.string.error_undefined),
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			}
		}

	}
	
}
