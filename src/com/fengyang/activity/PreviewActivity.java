package com.fengyang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengyang.fragment.PreviewPhotoFragment;
import com.fengyang.fragment.PreviewPhotoFragment.OnloadedBitmap;
import com.fengyang.model.ImageHolder;
import com.fengyang.stu.R;

/**
 * 用户选择图片的预览界面
 * 
 * @author HeJie
 *
 */
public class PreviewActivity extends FragmentActivity implements
		OnClickListener, AnimationListener {

	private FrameLayout topView;
	private LinearLayout bottomView;
	private ImageButton backBtn;
	private TextView indicatorView;
	private ImageButton deleteBtn;

	private boolean isShowOption = true;
	private ArrayList<Uri> deleteUri;

	PreviewPagerAdapter mPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	public static final String KEY_CURRENT_IMAGE = "currentImage";
	public static final String KEY_IMAGE_DATA = "imageUri";
	public static final String KEY_DELETE_DATA = "deleteUri";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		mViewPager = (ViewPager) findViewById(R.id.preview_pager);
		topView = (FrameLayout) findViewById(R.id.preview_top_container);
		bottomView = (LinearLayout) findViewById(R.id.preview_bottom_container);
		backBtn = (ImageButton) findViewById(R.id.preview_backBtn);
		indicatorView = (TextView) findViewById(R.id.preview_indicator);
		deleteBtn = (ImageButton) findViewById(R.id.preview_deleteBtn);

		backBtn.setOnClickListener(this);
		deleteBtn.setOnClickListener(this);

		ArrayList<Uri> data = null;
		if (savedInstanceState == null) {
			data = getIntent().getParcelableArrayListExtra(KEY_IMAGE_DATA);
		} else {
			data = savedInstanceState.getParcelableArrayList(KEY_IMAGE_DATA);
			deleteUri = savedInstanceState
					.getParcelableArrayList(KEY_DELETE_DATA);
		}
		mPagerAdapter = new PreviewPagerAdapter(getSupportFragmentManager(),
				data);
		mViewPager.setAdapter(mPagerAdapter);
		int position = getIntent().getIntExtra(KEY_CURRENT_IMAGE, 0);
		updateIndicatoer(position);
		mViewPager.setCurrentItem(position);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				updateIndicatoer(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		ArrayList<Uri> d = new ArrayList<Uri>();
		for (ImageHolder imageHolder : mPagerAdapter.getData()) {
			d.add(imageHolder.getUri());
			if (imageHolder.getBitmap() != null)
				imageHolder.getBitmap().recycle();
		}
		outState.putParcelableArrayList(KEY_IMAGE_DATA, d);
		outState.putParcelableArrayList(KEY_DELETE_DATA, deleteUri);
	}

	/**
	 * 更新页码
	 * 
	 * @param position
	 */
	private void updateIndicatoer(int position) {
		String indicator = (position + 1) + "/" + mPagerAdapter.getCount();
		indicatorView.setText(indicator);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class PreviewPagerAdapter extends FragmentStatePagerAdapter implements OnClickListener {

		private ArrayList<ImageHolder> data;

		// private ArrayList<Fragment> fragments;

		public PreviewPagerAdapter(FragmentManager fm, ArrayList<Uri> data) {
			super(fm);
			this.data = new ArrayList<ImageHolder>();
			// fragments = new ArrayList<Fragment>();
			for (Uri uri : data) {
				this.data.add(new ImageHolder(uri));
				// fragments.add(null);
			}
		}

		@Override
		public Fragment getItem(int position) {
			PreviewPhotoFragment item = null;
			// if (position < fragments.size()){
			// item = (PreviewPhotoFragment) fragments.get(position);
			// }
			// if (item != null)
			// return item;
			item = PreviewPhotoFragment.newInstance(position,
					data.get(position));
			// while(position >= fragments.size())
			// fragments.add(null);
			// fragments.set(position, item);
			item.setOnClickListener(this);
			item.setOnloadBitmap(new OnloadedBitmap() {

				@Override
				public void loadBitmapFinish(int pos, Bitmap bm) {
					if (pos >= data.size())
						return;
					ImageHolder holder = data.get(pos);
					if (holder.getBitmap() == null)
						holder.setBitmap(bm);
				}
			});
			return item;
		}

		@Override
		public int getItemPosition(Object object) {
			return FragmentStatePagerAdapter.POSITION_NONE;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		public ImageHolder deleteItem(int pos) {
			Log.i("adapter", "delelte pos = " + pos);
			ImageHolder holder = data.remove(pos);
			// fragments.remove(pos);
			notifyDataSetChanged();
			return holder;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		public ArrayList<ImageHolder> getData() {
			return data;
		}

		public void setData(ArrayList<ImageHolder> data) {
			this.data = data;
		}

		@Override
		public void onClick(View v) {
			toggleOptionView();
		}
	}

	/**
	 * 若选项已显示，则隐藏上下选项栏；若选项栏隐藏，则显示
	 */
	private void toggleOptionView() {
		if (!isShowOption) {
			isShowOption = true;
			Animation animUp = AnimationUtils.loadAnimation(this,
					R.anim.anim_slide_up_from_bottom);
			Animation animDown = AnimationUtils.loadAnimation(this,
					R.anim.anim_slide_down_from_top);
			animUp.setAnimationListener(this);
			animDown.setAnimationListener(this);
			topView.startAnimation(animDown);
			bottomView.startAnimation(animUp);
		} else {
			isShowOption = false;
			Animation animUp = AnimationUtils.loadAnimation(this,
					R.anim.anim_slide_to_top);
			Animation animDown = AnimationUtils.loadAnimation(this,
					R.anim.anim_slide_to_bottom);
			animUp.setAnimationListener(this);
			animDown.setAnimationListener(this);
			topView.startAnimation(animUp);
			bottomView.startAnimation(animDown);
		}
	}

	/**
	 * 删除图片
	 */
	private void deleteImage() {
		if (deleteUri == null) {
			deleteUri = new ArrayList<Uri>();
		}
		final int index = mViewPager.getCurrentItem();
		int count = mPagerAdapter.getCount();
		Log.i("delete", "index = " + index);
		Log.i("delete", "count = " + count);
		if (count > 1) {
			Animation anim = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.remove_image);
			anim.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					deleteUri.add(mPagerAdapter.deleteItem(index).getUri());
					updateIndicatoer(mViewPager.getCurrentItem());
				}
			});
			mViewPager.startAnimation(anim);
		} else {
			deleteUri.add(mPagerAdapter.deleteItem(index).getUri());
			backTo();
		}
	}

	/**
	 * 退出activity，返回删除的数据
	 */
	private void backTo() {
		Intent data = new Intent();
		data.putParcelableArrayListExtra(KEY_DELETE_DATA, deleteUri);
		setResult(RESULT_OK, data);
		finish();
	}

	@Override
	public void onBackPressed() {
		backTo();
	}

	@Override
	protected void onStop() {
		for (ImageHolder imageHolder : mPagerAdapter.getData()) {
			if (imageHolder.getBitmap() != null)
				imageHolder.getBitmap().recycle();
		}
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.preview_backBtn:
			backTo();
			break;
		case R.id.preview_deleteBtn:
			deleteImage();
			break;
		default:
			break;
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (isShowOption) {
			topView.setVisibility(View.VISIBLE);
			bottomView.setVisibility(View.VISIBLE);
		} else {
			topView.setVisibility(View.GONE);
			bottomView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

}
