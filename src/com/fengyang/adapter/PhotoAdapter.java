package com.fengyang.adapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.fengyang.stu.R;
import com.fengyang.util.ui.UIUtils;

public class PhotoAdapter extends BaseAdapter {

	protected ArrayList<Uri> data;
	protected Context context;
	private GridView gridView;

	private MyHandler handler;

	private boolean isOne;

	public PhotoAdapter(GridView gridView, ArrayList<Uri> data) {
		if (data == null)
			this.data = new ArrayList<Uri>();
		else
			this.data = data;
		this.gridView = gridView;
		this.context = gridView.getContext();
		handler = new MyHandler(this);
	}

	public PhotoAdapter(GridView gridView, ArrayList<Uri> data, boolean isOne) {
		if (data == null)
			this.data = new ArrayList<Uri>();
		else
			this.data = data;
		this.gridView = gridView;
		this.context = gridView.getContext();
		handler = new MyHandler(this);

		this.isOne = isOne;
	}

	@Override
	public int getCount() {
		if (isOne) {
			return 1;
		} else {
			return data.size() + 1;
		}
	}

	@Override
	public Object getItem(int position) {
		if (position == data.size())
			return null;
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_photo_thum_layout, parent,
					false);

			holder = new ViewHolder();
			holder.image = (ImageView) view
					.findViewById(R.id.item_photo_container);
			holder.photoBtn = (ImageView) view
					.findViewById(R.id.item_photo_btn);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if (position == data.size()) {
			holder.image.setImageBitmap(null);
			holder.photoBtn.setBackgroundResource(R.drawable.ic_take_photo);
		} else {
			// holder.image.setImageResource(R.drawable.default_img);
			new LoadImageThread(data.get(position), position).start();
			holder.photoBtn
					.setBackgroundResource(R.drawable.photo_frame_selector);
		}

		return view;
	}

	public boolean addItem(Uri uri) {
		if (uri == null || data.contains(uri))
			return false;
		data.add(uri);
		notifyDataSetChanged();
		return true;
	}

	public boolean deleteItem(Uri uri) {
		boolean success = data.remove(uri);
		notifyDataSetChanged();
		return success;
	}

	public void deleteItem(ArrayList<Uri> deleteUri) {
		for (Uri uri : deleteUri) {
			data.remove(uri);
		}
		notifyDataSetChanged();
	}

	public ArrayList<Uri> getData() {
		return data;
	}

	public void setData(ArrayList<Uri> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	/**
	 * 自定义线程加载图片
	 * 
	 * @author HeJie
	 *
	 */
	class LoadImageThread extends Thread {
		/**
		 * 图片的Uri
		 */
		private Uri uri;
		/**
		 * 目标item的位置
		 */
		private int pos;

		public LoadImageThread(Uri uri, int pos) {
			super();
			this.uri = uri;
			this.pos = pos;
		}

		@Override
		public void run() {
			if (uri == null)
				return;
			// 计算出每个item的大致长度，屏幕宽度-（paddingLeft + paddingRight）
			int len = (UIUtils.getDisplayWidth(context) - UIUtils.dip2px(
					context, 32)) / 3;
			Bitmap bitmap = UIUtils.loadBitmap(context, uri, len, len);
			Message msg = new Message();
			msg.arg1 = pos;
			msg.obj = bitmap;
			handler.sendMessage(msg);
		}
	}

	static class MyHandler extends Handler {

		WeakReference<PhotoAdapter> adapterRef;

		public MyHandler(PhotoAdapter adapter) {
			this.adapterRef = new WeakReference<PhotoAdapter>(adapter);
		}

		public void handleMessage(android.os.Message msg) {
			int pos = msg.arg1;
			PhotoAdapter adapter = adapterRef.get();
			Bitmap bitmap = (Bitmap) msg.obj;
			ImageView img = (ImageView) adapter.gridView.getChildAt(pos)
					.findViewById(R.id.item_photo_container);
			img.setImageBitmap(bitmap);
		};
	}

	class ViewHolder {
		ImageView image;
		ImageView photoBtn;
	}

}
