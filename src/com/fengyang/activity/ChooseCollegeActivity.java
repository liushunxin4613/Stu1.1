package com.fengyang.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fengyang.db.CollegeDAO;
import com.fengyang.entity.OubaArea;
import com.fengyang.entity.AppCollege;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

/**
 * 选择大学的界面
 * 
 * @author HeJie
 *
 */
public class ChooseCollegeActivity extends ImmersionActivity {

	private static OubaArea province;

	public static final String SELECTED_PROVINCE_ID = "SelectedProvince";
	public static final String SELECTED_COLLEGE_ID = "SelectedCollege";
	public static final String TAG = "ChooseCollegeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_college);
		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new ProvinceFragment()).commit();
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.choose_college, menu);
//		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			goBack();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// @Override
	// public void onBackPressed() {
	// goBack();
	// }

	/**
	 * 回退
	 */
	private void goBack() {
		int count = getSupportFragmentManager().getBackStackEntryCount();
		if (count > 0) {
			FragmentManager fm = getSupportFragmentManager();
			fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			fm.executePendingTransactions();
		} else {
			finish();
		}
	}

	public static class ProvinceFragment extends Fragment {

		private CollegeDAO dao;
		private ListView listView;
		private List<OubaArea> list;

		public ProvinceFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_choose_college,
					container, false);
			listView = (ListView) rootView.findViewById(R.id.choose_list);
			dao = new CollegeDAO(getActivity());
			list = dao.queryProvince();
			ArrayList<String> str = new ArrayList<String>();
			if (list != null) {
				for (OubaArea oubaArea : list) {
					str.add(oubaArea.getAreaName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_list_item_1, str);
				listView.setAdapter(adapter);
			}
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					province = list.get(position);
					getActivity()
							.getSupportFragmentManager()
							.beginTransaction()
							.addToBackStack(null)
							.setCustomAnimations(android.R.anim.slide_in_left,
									android.R.anim.slide_out_right,
									android.R.anim.slide_in_left,
									android.R.anim.slide_out_right)
							.add(R.id.container, new CollegeFragment())
							.commit();
				}
			});
			return rootView;
		}
	}

	public static class CollegeFragment extends Fragment {

		private CollegeDAO dao;
		private ListView listView;
		private List<AppCollege> list;

		public CollegeFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_choose_college,
					container, false);
			listView = (ListView) rootView.findViewById(R.id.choose_list);

			dao = new CollegeDAO(getActivity());
			list = dao.queryCollege(province);
			ArrayList<String> str = new ArrayList<String>();
			if (list != null) {
				for (AppCollege college : list) {
					str.add(college.getCUniversityName());
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_list_item_1, str);
				listView.setAdapter(adapter);
			}
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent data = new Intent();
					data.putExtra(SELECTED_PROVINCE_ID, province.getAreaId());
					data.putExtra(SELECTED_COLLEGE_ID, list.get(position)
							.getCUniversityId());
					getActivity().setResult(RESULT_OK, data);
					getActivity().finish();
				}
			});
			return rootView;
		}
	}
}
