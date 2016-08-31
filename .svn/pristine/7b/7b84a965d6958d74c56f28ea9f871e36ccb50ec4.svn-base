package com.fengyang.util.ui;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.view.View;

public class UIUtils {

	/**
	 * 判断点击位置是否在组件内部范围内
	 * 
	 * @param view
	 * @param x
	 *            绝对x坐标
	 * @param y
	 *            绝对y坐标
	 * @return
	 */
	public static boolean isTouchInView(View view, int x, int y) {
		if (view == null) {
			return false;
		}
		int[] location = new int[2];
		view.getLocationInWindow(location);
		int leftOffset = location[0];
		int topOffset = location[1];
		if (x < leftOffset || x > leftOffset + view.getWidth()) {
			return false;
		}

		if (y < topOffset || y > topOffset + view.getHeight()) {
			return false;
		}

		return true;
	}

	/**
	 * 按比例缩放和剪切头像图片，若宽大于高，则以高为标准缩放，剪切宽；反之则以宽为标准缩放，剪切高
	 *
	 * @param bitmap
	 *            要处理的图片
	 * @param imageView
	 *            存放图片的容器
	 * @return
	 */
	public static Bitmap scaleImage(Bitmap bitmap, View imageView) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		if (height > width) {
			Matrix matrix = new Matrix();
			float scale = imageView.getWidth() * 1.0f / bitmap.getWidth();
			matrix.postScale(scale, scale);
			int y = (height - width) / 2;
			return Bitmap.createBitmap(bitmap, 0, y, width, width, matrix,
					false);
		} else {
			Matrix matrix = new Matrix();
			float scale = imageView.getHeight() * 1.0f / bitmap.getHeight();
			matrix.postScale(scale, scale);
			int x = (width - height) / 2;
			return Bitmap.createBitmap(bitmap, x, 0, height, height, matrix,
					false);
		}
	}

	/**
	 * 按比例缩放和剪切头像图片，若宽大于高，则以高为标准缩放，剪切宽；反之则以宽为标准缩放，剪切高
	 *
	 * @param bitmap
	 *            要处理的图片
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap scaleImage(Bitmap bitmap, int w, int h) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		float bitRatio = width * 1.0f / height;
		float viewRatio = w * 1.0f / h;
		if (bitRatio > viewRatio) {
			Matrix matrix = new Matrix();
			float scale = w * 1.0f / bitmap.getWidth();
			matrix.postScale(scale, scale);
			return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
					true);
		} else {
			Matrix matrix = new Matrix();
			float scale = h * 1.0f / bitmap.getHeight();
			matrix.postScale(scale, scale);
			return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
					true);
		}
	}

	/**
	 * 缩放和剪切头像图片，若宽大于高，则以高为标准缩放，剪切宽；反之则以宽为标准缩放，剪切高
	 *
	 * @param bitmap
	 *            要处理的图片
	 * @param len
	 *            正方形容器边长
	 * @return 返回放缩后的正方形bitmap
	 */
	public static Bitmap scaleImage(Bitmap bitmap, int len) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		if (height > width) {
			Matrix matrix = new Matrix();
			float scale = len * 1.0f / bitmap.getWidth();
			matrix.postScale(scale, scale);
			int y = (height - width) / 2;
			return Bitmap.createBitmap(bitmap, 0, y, width, width, matrix,
					false);
		} else {
			Matrix matrix = new Matrix();
			float scale = len * 1.0f / bitmap.getHeight();
			matrix.postScale(scale, scale);
			int x = (width - height) / 2;
			return Bitmap.createBitmap(bitmap, x, 0, height, height, matrix,
					false);
		}
	}

	/**
	 * 根据给出的大小，计算Options的inSampleSize
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both height and width larger than the requested height and
			// width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	/**
	 * 从外存中加载图片，返回根据宽高参数裁剪的Bitmap对象
	 * 
	 * @param context
	 * @param uri
	 *            图片uri
	 * @param width
	 *            目标宽度
	 * @param height
	 *            目标高度
	 * @return
	 */
	public static Bitmap loadBitmap(Context context, Uri uri, int width,
			int height) {
		Bitmap bitmap = null;
		try {
			// 首先只计算轮廓，得到options参数
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(uri), null, options);
			// 计算最大的inSampleSize
			options.inSampleSize = calculateInSampleSize(options, width, height);
			options.inJustDecodeBounds = false;
			// decode实际所需的图片
			bitmap = BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(uri), null, options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static byte[] convertBitmapToBytes(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
			bitmap.copyPixelsToBuffer(buffer);
			return buffer.array();
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			byte[] data = baos.toByteArray();
			return data;
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDisplayHeigth(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
}
