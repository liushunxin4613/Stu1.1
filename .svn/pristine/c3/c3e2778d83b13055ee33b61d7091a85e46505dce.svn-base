package com.fengyang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.fengyang.fragment.RegistFragment;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

/**
 * ×¢²á½çÃæ
 * 
 * @author HeJie
 *
 */
public class RegistActivity extends ImmersionActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		setStatusColor(getResources().getColor(R.color.immersionColor));

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.regist_container, new RegistFragment())
					.commit();
		}

		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.regist_pravcy_link:
			startActivity(new Intent(this, PrivacyActivity.class));
			break;
		}
	}

}
