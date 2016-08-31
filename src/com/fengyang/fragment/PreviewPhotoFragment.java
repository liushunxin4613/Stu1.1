package com.fengyang.fragment;

import java.io.FileNotFoundException;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fengyang.model.ImageHolder;
import com.fengyang.stu.R;
import com.fengyang.util.ui.UIUtils;

/**
 * 预览图片的fragment
 * 
 * @author HeJie
 *
 */
public class PreviewPhotoFragment extends Fragment {

	private View rootView;
	private int position;
	private ImageView previeView;
	private Uri imageUri;
	// private Bitmap bm;

	private int width;
	private int height;

	private OnClickListener onClickListener;

	private MyHandler handler = new MyHandler(this);
	private OnloadedBitmap onloadBitmap;
	public static final String KEY_FRAMENT_POSITION = "position";
	public static final String KEY_PHOTO_URI = "photoUri";
	public static final String KEY_PHOTO_BITMAP = "photoBitmap";
	public static final String KEY_SCREEN_WIDTH = "screenWidth";
	public static final String KEY_SCREEN_HEIGHT = "screenHight";
	public static final String TAG = "previewPhotoFragment";

	/**
	 * 定义回调接口，当加载完bitmap之后方便添加到适配器中，避免多次从外存中IO
	 * 
	 * @author HeJie
	 *
	 */
	public interface OnloadedBitmap {
		/**
		 * 当加载bitmap完成之后调用
		 * 
		 * @param pos
		 *            当前fragment在ViewPager中的位置
		 * @param bm
		 *            加载完成的bitmap
		 */
		void loadBitmapFinish(int pos, Bitmap bm);
	}

	private final Runnable loadBitmap = new Runnable() {

		@Override
		public void run() {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(getActivity().getContentResolver()
						.openInputStream(imageUri), null, options);
				options.inSampleSize = UIUtils.calculateInSampleSize(options,
						width, height);
				options.inJustDecodeBounds = false;
				Bitmap bitmap = BitmapFactory.decodeStream(getActivity()
						.getContentResolver().openInputStream(imageUri), null,
						options);
				if (onloadBitmap != null)
					onloadBitmap.loadBitmapFinish(position, bitmap);
				Message msg = new Message();
				msg.obj = bitmap;
				handler.sendMessage(msg);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	};

	static class MyHandler extends Handler {

		WeakReference<PreviewPhotoFragment> previewFrag;

		public MyHandler(PreviewPhotoFragment previewFrag) {
			this.previewFrag = new WeakReference<PreviewPhotoFragment>(
					previewFrag);
		}

		public void handleMessage(android.os.Message msg) {
			PreviewPhotoFragment fragment = previewFrag.get();
			Bitmap bitmap = UIUtils.scaleImage((Bitmap) msg.obj,
					fragment.width, fragment.height);
			fragment.previeView.setImageBitmap(bitmap);
		};
	}

	public static PreviewPhotoFragment newInstance(int position,
			ImageHolder holder) {
		PreviewPhotoFragment fragment = new PreviewPhotoFragment();
		Bundle args = new Bundle();
		args.putInt(KEY_FRAMENT_POSITION, position);
		args.putParcelable(KEY_PHOTO_URI, holder.getUri());
		args.putParcelable(KEY_PHOTO_BITMAP, holder.getBitmap());
		fragment.setArguments(args);
		return fragment;
	}

	public PreviewPhotoFragment() {
		Log.i(TAG, "PreviewPhotoFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		width = UIUtils.getDisplayWidth(getActivity());
		height = UIUtils.getDisplayHeigth(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_preview_photo, container,
				false);
		previeView = (ImageView) rootView.findViewById(R.id.preview_photo_IV);

		if (onClickListener != null)
			rootView.setOnClickListener(onClickListener);
		Bundle args = getArguments();
		position = args.getInt(KEY_FRAMENT_POSITION);
		imageUri = args.getParcelable(KEY_PHOTO_URI);
		Bitmap bm = args.getParcelable(KEY_PHOTO_BITMAP);
		Log.i(TAG, "onCreateView position = " + position);
		if (bm == null || bm.isRecycled()) {
			new Thread(loadBitmap).start();
			Log.i(TAG, "load image");
		} else {
			Bitmap bitmap = UIUtils.scaleImage(bm, width, height);
			previeView.setImageBitmap(bitmap);
			Log.i(TAG, "set image");
		}
		return rootView;
	}

	public OnloadedBitmap getOnloadBitmap() {
		return onloadBitmap;
	}

	public void setOnloadBitmap(OnloadedBitmap onloadBitmap) {
		this.onloadBitmap = onloadBitmap;
	}

	/**
	 * 给rootView 设置点击事件监听器
	 * 
	 * @param onClickListner
	 */
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
		if (rootView != null)
			rootView.setOnClickListener(onClickListener);
	}

}
