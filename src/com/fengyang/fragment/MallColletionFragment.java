package com.fengyang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.fengyang.customLib.DynamicListView;
import com.fengyang.customLib.DynamicListView.LoadMoreListener;
import com.fengyang.customLib.DynamicListView.RefreshListener;
import com.fengyang.stu.R;

public class MallColletionFragment extends Fragment implements RefreshListener,
		LoadMoreListener {

	private DynamicListView listView;

	private static MallColletionFragment fragment = null;

	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new MallColletionFragment();
		return fragment;
	}

	private MallColletionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collect_mall,
				container, false);
		listView = (DynamicListView) rootView
				.findViewById(R.id.collect_mall_lv);

		listView.setDoMoreWhenBottom(false); // 滚动到低端的时候不自己加载更多
		listView.setOnRefreshListener(this);
		listView.setOnMoreListener(this);

		setData();
		return rootView;
	}

	public void setData() {
		listView.setAdapter(new ArrayAdapter<String>(getActivity()
				.getActionBar().getThemedContext(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, new String[] {
						getString(R.string.setting_about_us),
						getString(R.string.setting_send_advice),
						getString(R.string.setting_version_info),
						getString(R.string.setting_version_update) }));
	}

	@Override
	public boolean onRefresh(DynamicListView dynamicListView) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCancelRefresh(DynamicListView dynamicListView) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onLoadMore(DynamicListView dynamicListView) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCancelLoadMore(DynamicListView dynamicListView) {
		// TODO Auto-generated method stub

	}
}
