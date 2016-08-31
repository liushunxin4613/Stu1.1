package com.fengyang.model;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * ��װͼƬ�����Ϣ����
 * @author HeJie
 *
 */
public class ImageHolder{

	Uri uri;

	Bitmap bitmap;

	public ImageHolder() {
	}

	public ImageHolder(Uri uri) {
		super();
		this.uri = uri;
	}

	public Uri getUri() {
		return uri;
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
