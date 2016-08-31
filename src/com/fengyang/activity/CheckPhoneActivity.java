package com.fengyang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.fengyang.fragment.FindByPhoneFragment;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

public class CheckPhoneActivity extends ImmersionActivity {

	public static String keyStr = "";
	public static String phoneStr = "";
	public static final String TAG = "CheckPhoneActivity";
	public static final String KEY_AUTHENTICATION_TYPE = "authentication";
	public static final String KEY_CHECK_PHONE_TYPE = "phone";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_phone);

		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		keyStr = intent.getStringExtra(KEY_AUTHENTICATION_TYPE);
		if (null != intent.getStringExtra(KEY_CHECK_PHONE_TYPE)) {
			phoneStr = intent.getStringExtra(KEY_CHECK_PHONE_TYPE);
		}
		Log.i(TAG, keyStr);

		setStatusColor(getResources().getColor(R.color.immersionColor));

		getSupportFragmentManager().beginTransaction()
				.add(R.id.Fm_check_phone, new FindByPhoneFragment()).commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
