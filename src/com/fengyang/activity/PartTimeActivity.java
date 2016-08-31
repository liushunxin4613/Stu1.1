package com.fengyang.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView.OnQueryTextListener;

import com.fengyang.customLib.DiySearchView;
import com.fengyang.fragment.PartTimeFragment;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

public class PartTimeActivity extends ImmersionActivity {

	private DiySearchView searchView;

	private PartTimeFragment fragment;

	private static final String TAG = "partTimeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_part_time);

		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		setStatusColor(getResources().getColor(R.color.immersionColor));

		if (savedInstanceState == null) {
			fragment = PartTimeFragment.getInstance();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.part_time_container, fragment).commit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.part_time, menu);
		searchView = (DiySearchView) menu.findItem(R.id.action_part_search)
				.getActionView();
		searchView.setSubmitButtonEnabled(true);
		searchView.setQueryHint(getResources().getString(R.string.hint_search));
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				String queryStr = searchView.getQuery().toString();
				Log.i(TAG, "queryStr = " + queryStr);
				if (fragment != null)
					fragment.searchData(queryStr);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});

		searchView.setResouce(R.drawable.search_bg,
				R.drawable.search_submit_bg,
				getResources().getColor(R.color.white), getResources()
						.getColor(R.color.search_hint_gray),
				R.drawable.ic_search_btn, R.drawable.ic_go_btn,
				R.drawable.ic_close_btn);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_part_search:
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
