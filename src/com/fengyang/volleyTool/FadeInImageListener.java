package com.fengyang.volleyTool;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.fengyang.stu.R;

/**
 * 普通ImageView控件对应的图片监听器， 图片切换效果是fade in
 * 
 * @author HeJie
 *
 */
public class FadeInImageListener implements ImageLoader.ImageListener {

	WeakReference<ImageView> mImageView;
	Context mContext;
	Object tag;
	int defaultImg;
	int errorImg;
	
	/**
	 * 图片监听器的构造方法
	 * 
	 * @param image
	 *            图片实例
	 * @param context
	 */
	public FadeInImageListener(ImageView image, Context context) {
		this(image, context, 0, 0);
	}

	/**
	 * 图片监听器的构造方法
	 * 
	 * @param image
	 *            图片实例
	 * @param context
	 * @param defaultImg
	 *            默认图片
	 * @param errorImg
	 *            加载错误时的图片
	 */
	public FadeInImageListener(ImageView image, Context context,
			int defaultImg, int errorImg) {
		this(image, context, defaultImg, errorImg, null);
	}

	/**
	 * 图片监听器的构造方法
	 * 
	 * @param image
	 *            图片实例
	 * @param context
	 * @param defaultImg
	 *            默认图片
	 * @param errorImg
	 *            加载错误时的图片
	 * @param tag
	 *            图片标志
	 */
	public FadeInImageListener(ImageView image, Context context,
			int defaultImg, int errorImg, Object tag) {
		mImageView = new WeakReference<ImageView>(image);
		mContext = context;
		this.tag = tag;
		if (defaultImg <= 0)
			this.defaultImg = R.drawable.default_img;
		else
			this.defaultImg = defaultImg;
		if (errorImg <= 0)
			this.errorImg = R.drawable.default_img;
		else
			this.errorImg = errorImg;
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		if (mImageView.get() != null) {
			mImageView.get().setImageResource(errorImg);
		}
	}

	@Override
	public void onResponse(ImageContainer response, boolean arg1) {
		if (mImageView.get() != null) {
			ImageView image = mImageView.get();
			if (response.getBitmap() != null) {
				if (tag == null || tag != null && tag.equals(image.getTag())) {
					image.startAnimation(AnimationUtils.loadAnimation(mContext,
							android.R.anim.fade_in));
					image.setImageBitmap(response.getBitmap());
				}
			} else {
				image.setImageResource(defaultImg);
			}
		}
	}
}