package com.fengyang.activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fengyang.db.StableDBHelper;
import com.fengyang.db.StableDBHelper.StableTableDAO;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

/**
 * 多项，多层次选择的Activity
 * 
 * @author HeJie
 *
 * @param <T>
 */
public class ChooseListActivity<T> extends ImmersionActivity {

	/**
	 * 选择项的id列表
	 */
	private static ArrayList<Integer> choosedId;

	private PlaceholderFragment<T> fragment;

	private static final int REQUEST_CHOOSE_DEEP = 1;

	public static final String KEY_DAO_CLASS = "DAOClass";
	public static final String KEY_PARENT_ID = "parentId";
	public static final String KEY_CURRENT_LEVEL = "currentLevel";
	public static final String KEY_CHOOSED_LIST = "choosedId";
	public static final String TAG = "ChooseListActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_area);
		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		if (savedInstanceState == null) {
			fragment = new PlaceholderFragment<T>();
			fragment.parentId = getIntent().getIntExtra(KEY_PARENT_ID,
					StableDBHelper.CLASS_PARENT_ROOT);
			fragment.currentLevel = getIntent().getIntExtra(KEY_CURRENT_LEVEL,
					1);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
		}

		Class<?> daoClazz = (Class<?>) getIntent().getSerializableExtra(
				KEY_DAO_CLASS);
		try {
			Constructor<?> constructor = daoClazz.getConstructor(Context.class);
			@SuppressWarnings("unchecked")
			StableTableDAO<T> dao = (StableTableDAO<T>) constructor
					.newInstance(this);
			setDao(dao);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.choose_area, menu);
	// return true;
	// }

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

	public StableTableDAO<T> getDao() {
		return fragment.dao;
	}

	public void setDao(StableTableDAO<T> dao) {
		fragment.setUpDao(dao);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 只取requestCode的后十六位，从fragment调用的startActivityForResult，
		// requestCode的前十六位是fragment的 index+1
		requestCode = requestCode & 0x0000ffff;
		if (resultCode == RESULT_OK && requestCode == REQUEST_CHOOSE_DEEP) {
			choosedId = data.getIntegerArrayListExtra(KEY_CHOOSED_LIST);
			choosedId.add(0, fragment.dao.parseId(fragment.data));
			Intent intent = new Intent();
			
			intent.putIntegerArrayListExtra(KEY_CHOOSED_LIST, choosedId);
			setResult(RESULT_OK, intent);
			
			finish();
		}
	}

	public static class PlaceholderFragment<T> extends Fragment implements
			OnItemClickListener {

		private int parentId;

		private int currentLevel;

		private ListView listView;
		private ArrayList<T> list;
		private StableTableDAO<T> dao;
		private T data;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_choose_list,
					container, false);
			listView = (ListView) rootView.findViewById(R.id.choose_item_list);

			if (list != null) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_list_item_1,
						dao.parseShowList(list));
				listView.setAdapter(adapter);
			}
			listView.setOnItemClickListener(this);
			return rootView;
		}

		public void setUpDao(StableTableDAO<T> dao) {
			Log.d(TAG, "parentId = " + parentId);
			this.dao = dao;
			list = (ArrayList<T>) dao.queryByParent(parentId);
			if (listView != null && list.size() > 0) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_list_item_1,
						dao.parseShowList(list));
				listView.setAdapter(adapter);
			}
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			if (list.size() == 0) {
				// 如若查询到的信息为0条
				Intent intent = new Intent();
				choosedId = new ArrayList<Integer>();
				intent.putIntegerArrayListExtra(KEY_CHOOSED_LIST, choosedId);
				intent.setAction(MainActivity.MainActivity_HAND_MOVEMENT);
				Log.d("choose", "choose");
				getActivity().sendBroadcast(intent);
				getActivity().setResult(RESULT_OK, intent);
				getActivity().finish();
			}
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (dao != null) {
				data = list.get(position);
				if (currentLevel < dao.getTotalLevel()) {
					Intent intent = new Intent(getActivity(),
							ChooseListActivity.class);
					intent.putExtra(KEY_DAO_CLASS, dao.getClass());
					intent.putExtra(KEY_PARENT_ID, dao.parseId(data));
					intent.putExtra(KEY_CURRENT_LEVEL, currentLevel + 1);
					startActivityForResult(intent, REQUEST_CHOOSE_DEEP);
				} else {// 到底了
					Intent intent = new Intent();
					choosedId = new ArrayList<Integer>();
					choosedId.add(dao.parseId(data));
					intent.putIntegerArrayListExtra(KEY_CHOOSED_LIST, choosedId);
					getActivity().setResult(RESULT_OK, intent);
					getActivity().finish();
				}
			}
		}
	}
}
