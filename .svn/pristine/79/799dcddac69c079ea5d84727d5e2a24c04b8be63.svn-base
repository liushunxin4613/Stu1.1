package com.fengyang.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fengyang.stu.R;
import com.fengyang.util.ui.UIUtils;
import com.fengyang.volleyTool.DiskBitmapCache;
import com.fengyang.volleyTool.FadeInImageListener;

public class DetailImageActivity extends Activity implements
		OnPageChangeListener, OnClickListener {

	SectionsPagerAdapter mPagerAdapter;

	ViewPager mViewPager;

	private TextView indicatorBar;
	/**
	 * 当前位置
	 */
	private int currentPos;

	private int width;
	/**
	 * 图片路径
	 */
	private ArrayList<String> path;

	private RequestQueue mQueue;

	private static ImageLoader imageLoader;

	private DiskBitmapCache diskCache;

	public static final String KEY_IMAGE_POS = "imagePos";

	public static final String KEY_IMAGE_ARRAY = "imageArray";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_image);

		mQueue = Volley.newRequestQueue(getApplicationContext());
		diskCache = new DiskBitmapCache(getCacheDir(), 10);
		imageLoader = new ImageLoader(mQueue, diskCache);
		currentPos = getIntent().getIntExtra(KEY_IMAGE_POS, 0);
		path = getIntent().getStringArrayListExtra(KEY_IMAGE_ARRAY);

		mPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.second_detail_pager);
		indicatorBar = (TextView) findViewById(R.id.second_detail_image_tipBar);

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(currentPos);
		mViewPager.setOffscreenPageLimit(path.size());
		mViewPager.setOnClickListener(this);

		width = UIUtils.getDisplayWidth(getApplicationContext()) / path.size();
		indicatorBar.setWidth(width);
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter implements OnClickListener {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			ImageFragment fragment = ImageFragment.newInstance(path.get(position), position);
			fragment.setOnClickListener(this);
			return fragment;
		}

		@Override
		public int getCount() {
			return path.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		@Override
		public void onClick(View v) {
			finish();
		}
	}

	public static class ImageFragment extends Fragment {

		public static final String KEY_IMAGE_URI = "imageUri";
		public static final String KEY_IMAGE_POS = "imagePos";

		private View rootView;

		public ImageView imageView;

		private String imgUrl;

		private OnClickListener onClickListener;

		public static ImageFragment newInstance(String uri, int pos) {
			ImageFragment fragment = new ImageFragment();
			Bundle args = new Bundle();
			args.putString(KEY_IMAGE_URI, uri);
			args.putInt(KEY_IMAGE_POS, pos);
			fragment.setArguments(args);
			return fragment;
		}

		public ImageFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.fragment_detail_image,
					container, false);

			if (onClickListener != null)
				rootView.setOnClickListener(onClickListener);
			imageView = (ImageView) rootView
					.findViewById(R.id.second_detail_image);
			imgUrl = getArguments().getString(KEY_IMAGE_URI);
			loadImage();
			return rootView;
		}

		private void loadImage() {
			imageLoader.get(imgUrl, new FadeInImageListener(imageView,
					getActivity()));
		}

		public OnClickListener getOnClickListener() {
			return onClickListener;
		}

		public void setOnClickListener(OnClickListener onClickListener) {
			this.onClickListener = onClickListener;
			if (rootView != null)
				rootView.setOnClickListener(onClickListener);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		int x = (int) ((position + positionOffset) * width);
		((View) indicatorBar.getParent()).scrollTo(-x,
				indicatorBar.getScrollY());
	}

	@Override
	public void onPageSelected(int position) {
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {
	}

}
