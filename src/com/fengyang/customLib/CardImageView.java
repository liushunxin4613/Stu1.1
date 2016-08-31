package com.fengyang.customLib;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CardImageView extends ImageView {
	public CardImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CardImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CardImageView(Context context) {
		super(context);
	}

	/**
	 * 计算宽高，以宽未基准等比例缩放图片资源
	 */
	 @Override
	 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 Drawable bg = getDrawable();
		 if (bg != null) {
			 int width = MeasureSpec.getSize(widthMeasureSpec);
			 int height = width * bg.getIntrinsicHeight() / bg.getIntrinsicWidth();
			 setMeasuredDimension(width,height);
		 }
		 else {
			 super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		 }
	 }
}