package com.fengyang.volleyTool;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.fengyang.stu.R;

public class UserPhotoImageListener implements ImageListener {

	WeakReference<View> mPhotoView;
	Context mContext;

	public UserPhotoImageListener(View image, Context context) {
		mPhotoView = new WeakReference<View>(image);
		mContext = context;
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		if (mPhotoView.get() != null) {
			mPhotoView.get().setBackgroundResource(R.drawable.default_user);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onResponse(ImageContainer response, boolean arg1) {
		if (mPhotoView.get() != null) {
			View image = mPhotoView.get();
			if (response.getBitmap() != null) {
				image.startAnimation(AnimationUtils.loadAnimation(mContext,
						android.R.anim.fade_in));
				image.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(),
						response.getBitmap()));
			} else {
				image.setBackgroundResource(R.drawable.default_user);
			}
		}
	}

}
