package com.fengyang.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.tool.Tool;
import com.fengyang.util.Config;
import com.fengyang.util.FormatUtils;
import com.fengyang.util.MD5;
import com.fengyang.util.ui.ImmersionActivity;

public class ReSetActivity extends ImmersionActivity {
	private EditText psdEt, repsdEt;
	private Button submitBt;
	private RequestQueue mQueue;
	private StringRequest updatePsdRequset;

	public static final String KEY_USER_ID = "user_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_re_set);
		setStatusColor(getResources().getColor(R.color.immersionColor));
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(true);
		mQueue = Volley.newRequestQueue(getApplicationContext());

		psdEt = (EditText) findViewById(R.id.find_pwd_reset);
		repsdEt = (EditText) findViewById(R.id.find_pwd_reset_repeat);
		submitBt = (Button) findViewById(R.id.reset_psd_submit_bt);
		//setActionBar();
		Intent intent = getIntent();
		final int id = intent.getIntExtra(KEY_USER_ID, -1);
		// Tool.ToolToast(getApplicationContext(), id+"");
		submitBt.setOnClickListener(new OnClickListener() {
			// register_pwd_require
			@Override
			public void onClick(View v) {
				if (check()) {
					updatePsd(id, psdEt.getText().toString());
				}

			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

//	private void setActionBar() {
//		getActionBar().setDisplayShowHomeEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//		LayoutInflater inflator = (LayoutInflater) this
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		// 如果parent为null 那么xml文件的FrameLayout布局参数会失效
//		View v = inflator.inflate(R.layout.actionbar_center_title_layout,
//				new LinearLayout(getApplicationContext()), false);
//		TextView titleView = (TextView) v.findViewById(R.id.actionBar_titel);
//		titleView.setText(R.string.title_activity_re_set);
//
//		getActionBar().setDisplayShowCustomEnabled(true);
//		getActionBar().setCustomView(v);
//	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void updatePsd(int id, String psd) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("password", MD5.GetMD5Code(psd));
		Uri.Builder userdetail = Uri.parse(
				Config.URL_GET_USER_INFORMATION_UPDATE).buildUpon();

		userdetail.appendQueryParameter("id", String.valueOf(id));
		userdetail.appendQueryParameter("jsonStr",
				FormatUtils.getJSONString(map));
		updatePsdRequset = new StringRequest(Method.GET, userdetail.toString(),
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							Log.d("response", "rewsponce" + response.toString());
							Tool.ToolToast(getApplicationContext(),
									getString(R.string.reset_password_succeed));
							setResult(RESULT_OK);
							finish();
						} else {
							Tool.ToolToast(getApplicationContext(),
									getString(R.string.reset_password_failure));
						}
					}
				}, new VolleyErrorAdapter(this) {
				});
		mQueue.add(updatePsdRequset);
	}

	public Boolean check() {
		if (psdEt.getText().length() == 0) {
			MainActivity.showToast(ReSetActivity.this,
					R.string.register_pwd_require);
			return false;
		}
		if (FormatUtils.praseStringType(psdEt.getText().toString()) != 4) {
			MainActivity.showToast(ReSetActivity.this,
					R.string.register_pwd_error);

			return false;
		}
		if (repsdEt.getText().length() == 0) {
			MainActivity.showToast(ReSetActivity.this,
					R.string.resetactivity_renew_psd);
			return false;
		}

		if (!psdEt.getText().toString().equals(repsdEt.getText().toString())) {
			MainActivity.showToast(ReSetActivity.this,
					R.string.register_pwd_not_equal);
			return false;
		}

		return true;

	}
}
