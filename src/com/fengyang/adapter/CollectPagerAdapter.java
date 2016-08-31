package com.fengyang.adapter;

import java.util.Locale;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fengyang.fragment.PartCollectFragment;
import com.fengyang.fragment.SecondCollectFragment;
import com.fengyang.stu.R;

public class CollectPagerAdapter extends FragmentPagerAdapter {
	
	Context context;
	FragmentManager fm;
	Fragment partCollectFra;
	Fragment secondCollectFra;

	public CollectPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
		this.fm = fm;
	}

	@Override
	public Fragment getItem(int pos) {
		switch (pos) {
//		case 0:
//			return MallColletionFragment.getInstance();
		case 0:
			if (partCollectFra == null)
				partCollectFra = new PartCollectFragment();
			return partCollectFra;
		case 1:
			if (secondCollectFra == null)
				secondCollectFra = new SecondCollectFragment();
			return secondCollectFra;
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return 2;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
//		case 0:
//			return context.getString(R.string.mall).toUpperCase(l);
		case 0:
			return context.getString(R.string.part_time).toUpperCase(l);
		case 1:
			return context.getString(R.string.second_hand).toUpperCase(l);
		}
		return null;
	}
}
