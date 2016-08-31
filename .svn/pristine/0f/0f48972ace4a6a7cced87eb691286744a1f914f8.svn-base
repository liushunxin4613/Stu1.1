package com.fengyang.customLib;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义的ViewPager 让高度可以根据宽度按比例缩放
 * 
 * @author HeJie
 *
 */
public class FixViewPager extends ViewPager {

	// private static final String TAG = "fixViewPager";
	public FixViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FixViewPager(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (getChildCount() == 0) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

		int height = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(0);
			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
					heightMeasureSpec, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height)
				height = h;
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
				MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

}
