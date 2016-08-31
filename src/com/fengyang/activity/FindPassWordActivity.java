package com.fengyang.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengyang.fragment.FindByEmailFragment;
import com.fengyang.fragment.FindByPhoneFragment;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

public class FindPassWordActivity extends ImmersionActivity {

	public static final int FIND_BY_PHONE = 0;
	public static final int FIND_BY_EMAIL = 1;
	public static final String FIND_TYPE = "findByWhat";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pass_word);

		setStatusColor(getResources().getColor(R.color.immersionColor));

		int type = getIntent().getIntExtra(FIND_TYPE, -1);
		int title = 0;
		if (type == FIND_BY_PHONE) {
			title = R.string.find_psd_by_phone;
			getSupportFragmentManager().beginTransaction()
					.add(R.id.find_pwd_container, new FindByPhoneFragment())
					.commit();
		} else if (type == FIND_BY_EMAIL) {
			title = R.string.find_psd_by_mail;
			getSupportFragmentManager().beginTransaction()
					.add(R.id.find_pwd_container, new FindByEmailFragment())
					.commit();
		}
		setActionBar(title);
	}

	private void setActionBar(int title) {
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 如果parent为null 那么xml文件的FrameLayout布局参数会失效
		View v = inflator.inflate(R.layout.actionbar_center_title_layout,
				new LinearLayout(getApplicationContext()), false);
		TextView titleView = (TextView) v.findViewById(R.id.actionBar_titel);
		titleView.setText(title);
		getActionBar().setDisplayShowCustomEnabled(true);
	    getActionBar().setCustomView(v);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
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
