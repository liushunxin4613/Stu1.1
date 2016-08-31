package com.fengyang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengyang.adapter.CollectPagerAdapter;
import com.fengyang.stu.R;

public class CollectFragment extends Fragment {

	private FragmentPagerAdapter mPagerAdapter;

	private ViewPager mViewPager;
	private PagerTabStrip tabStrip;
	
	private static final String TAG = "CollectFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_collection,
				container, false);
		mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
		tabStrip = (PagerTabStrip) rootView.findViewById(R.id.pagertab);
		// 设置PagerTabStrip显示底部的线
		tabStrip.setDrawFullUnderline(true);
		// tabStrip.setTextSpacing(200);
		perpareUI();
		Log.i(TAG, "onCreateView");
		return rootView;
	}

	private void perpareUI() {
		// fragment嵌套fragment
		FragmentManager fm = getChildFragmentManager();
		mPagerAdapter = new CollectPagerAdapter(getActivity(), fm);
		mViewPager.setAdapter(mPagerAdapter);
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
	
}
