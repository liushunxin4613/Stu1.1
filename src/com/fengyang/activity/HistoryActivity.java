package com.fengyang.activity;

import java.util.Locale;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.fengyang.dialog.LoadingDialog;
import com.fengyang.dialog.LoadingDialog.OnBackPressedLisener;
import com.fengyang.fragment.HistoryPartFrament;
import com.fengyang.fragment.HistorySecondFragment;
import com.fengyang.stu.R;
import com.fengyang.util.OnBackTaskFinishedListener;
import com.fengyang.util.ui.ImmersionActivity;

public class HistoryActivity extends ImmersionActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	private PagerTabStrip tabStrip;

	private LoadingDialog dialog;

	private static final String TAG = "historyActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.history_viewpager);
		tabStrip = (PagerTabStrip) findViewById(R.id.history_pagertab);
		// 设置PagerTabStrip显示底部的线
		tabStrip.setDrawFullUnderline(true);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(1);

		setStatusColor(getResources().getColor(R.color.immersionColor));

		dialog = new LoadingDialog(getString(R.string.dialog_loading));
		dialog.setOnBackPressedListener(new OnBackPressedLisener() {

			@Override
			public void onBackPressed() {
				
				finish();
			}
		});
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		dialog.show(ft, TAG);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter implements
			OnBackTaskFinishedListener {

		HistoryPartFrament PartHistoryFra;

		HistorySecondFragment SecondHistoryFra;

		boolean partFinish;

		boolean secondFinish;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				if (PartHistoryFra == null) {
					PartHistoryFra = new HistoryPartFrament();
					PartHistoryFra.setOnBackTaskFinishedListener(this);
					partFinish = false;
				}
				fragment = PartHistoryFra;
				break;
			case 1:
				if (SecondHistoryFra == null) {
					SecondHistoryFra = new HistorySecondFragment();
					SecondHistoryFra.setOnBackTaskFinishedListener(this);
					secondFinish = false;
				}
				fragment = SecondHistoryFra;
				break;

			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.part_time).toUpperCase(l);
			case 1:
				return getString(R.string.second_hand).toUpperCase(l);
			}
			return null;
		}

		@Override
		public void onBackTaskFinish(Fragment fragment) {
			if (fragment.equals(PartHistoryFra)) {
				partFinish = true;
			} else if (fragment.equals(SecondHistoryFra)) {
				secondFinish = true;
			}
			if (partFinish && secondFinish)
				dialog.dismiss();
		}
		
	}
	
}
