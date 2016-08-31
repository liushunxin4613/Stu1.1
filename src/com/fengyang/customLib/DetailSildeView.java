package com.fengyang.customLib;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.fengyang.stu.R;

public class DetailSildeView extends FrameLayout implements
		OnPageChangeListener, OnClickListener {

	private Context context;

	private ViewPager viewPager;

	private PageTransformer transformer;

	private ImageView[] dotTips;

	private ImageView[] mImageViews;
	/**
	 * 是否开启线程
	 */
	private boolean openThread;

	private boolean isTouching;

	/**
	 * 点击页面的事件监听器
	 */
	private OnItemClickListener onItemClickListener;

	public interface OnItemClickListener {
		public void onItemClick(ViewPager pager, View view, int position);
	}

	public ImageView[] getDotTips() {
		return dotTips;
	}

	public void setDotTips(ImageView[] dotTips) {
		this.dotTips = dotTips;
	}

	public ImageView[] getmImageViews() {
		return mImageViews;
	}

	public void setmImageViews(ImageView[] mImageViews) {
		this.mImageViews = mImageViews;
	}

	public DetailSildeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public DetailSildeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DetailSildeView(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
	}

	/**
	 * 界面初始化
	 * 
	 * @param len
	 *            图片数量
	 * @param defaultRes
	 *            默认图片的资源id
	 * @param millis
	 *            自动播放的间隔时长 若小于0表示不自动播放
	 */
	public void setUp(int len, int defaultRes, final int millis) {
		viewPager = (ViewPager) findViewById(R.id.image_slide_pager);
		// viewPager.setPageMargin(50);
		viewPager.setOffscreenPageLimit(3);
		if (transformer != null)
			viewPager.setPageTransformer(true, transformer);
		dotTips = new ImageView[len];
		mImageViews = new CardImageView[len];
		viewPager.getParent().requestDisallowInterceptTouchEvent(true);

		LinearLayout dotGroup = (LinearLayout) findViewById(R.id.view_dot);
		for (int i = 0; i < len; i++) {
			ImageView imageView = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(params);
			dotTips[i] = imageView;
			if (i == 0)
				imageView.setBackgroundResource(R.drawable.dot_focused);
			else
				imageView.setBackgroundResource(R.drawable.dot_unfocused);

			dotGroup.addView(imageView);
		}

		// 将图片装载到数组中
		for (int i = 0; i < len; i++) {
			CardImageView imageView = new CardImageView(context);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(params);
			imageView.setImageResource(defaultRes);
			imageView.setOnClickListener(this);
			mImageViews[i] = imageView;
		}

		/**
		 * 监控用户是否在触摸，若是isTouching标记为true 以停止自动播放
		 */
		viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (openThread) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						isTouching = true;
						break;
					case MotionEvent.ACTION_MOVE:
						if (!isTouching) {
							isTouching = true;
						}
						break;
					case MotionEvent.ACTION_UP:
						isTouching = false;
						break;
					default:
						break;
					}
					v.performClick();
					viewPager.getParent().requestDisallowInterceptTouchEvent(
							true);
				}
				return false;
			}
		});
		// 设置Adapter
		viewPager.setAdapter(new SlidePagerAdapter(mImageViews));
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(this);

		// 若图片个数大于1 开启线程循环播放
		if (len > 1 && millis > 0) {
			openThread = true;
			new Thread(new Runnable() {
				// 开启线程。循环播放图片
				@Override
				public void run() {
					while (openThread) {
						try {
							Thread.sleep(millis);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						while (isTouching) {  //如果用户正在滑动页面，则休眠一会儿
							try {
								Thread.sleep(millis);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						handler.sendEmptyMessage(1);
					}
				}
			}).start();
		}
	}

	public ImageView getImageView(int index) {

		return mImageViews[index];
	}

	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	//
	// int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
	// int measureHeight = (width * 3) / 4;
	// System.out.println("width = " + width);
	// System.out.println("measureWidth = " + measureWidth);
	// System.out.println("measureHeight = " + measureHeight);
	//
	// setMeasuredDimension(measureWidth, measureHeight);
	// }

	/**
	 * 设置切换动画
	 * 
	 * @return
	 */
	public PageTransformer getTransformer() {
		return transformer;
	}

	public void setTransformer(PageTransformer transformer) {
		this.transformer = transformer;
		if (viewPager != null)
			viewPager.setPageTransformer(true, transformer);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);
	}

	private void setImageBackground(int selectItems) {
		for (int i = 0; i < dotTips.length; i++) {
			if (i == selectItems) {
				dotTips[i].setBackgroundResource(R.drawable.dot_focused);
			} else {
				dotTips[i].setBackgroundResource(R.drawable.dot_unfocused);
			}
		}
	}

	Handler handler = new MyHandler(this);

	static class MyHandler extends Handler {

		private WeakReference<DetailSildeView> slideView;

		public MyHandler(DetailSildeView view) {
			this.slideView = new WeakReference<DetailSildeView>(view);
		}

		public void handleMessage(Message msg) {
			DetailSildeView view = this.slideView.get();
			// 接收消息，播放下一个图片
			if (msg.what == 1) {
				view.viewPager.setCurrentItem(
						view.viewPager.getCurrentItem() + 1, true);
			}
		}
	}

	protected void onDetachedFromWindow() {
		// view被杀死时 停止线程
		openThread = false;
		super.onDetachedFromWindow();
	};

	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	class SlidePagerAdapter extends PagerAdapter {

		private ImageView[] mImageViews;

		public SlidePagerAdapter(ImageView[] mImageViews) {
			this.mImageViews = mImageViews;
		}

		@Override
		public int getCount() {
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(mImageViews[position]);
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(mImageViews[position], 0);
			return mImageViews[position];
		}
	}

	@Override
	public void onClick(View v) {
		if (onItemClickListener == null)
			return;
		for (int i = 0; i < mImageViews.length; i++) {
			if (mImageViews[i].equals(v))
				onItemClickListener.onItemClick(viewPager, v, i);
		}
	}

}
