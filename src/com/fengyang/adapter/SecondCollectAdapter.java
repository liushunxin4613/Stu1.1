package com.fengyang.adapter;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fengyang.customLib.NetworkImageView;
import com.fengyang.model.SecondCollection;
import com.fengyang.service.CollectionService;
import com.fengyang.stu.R;
import com.fengyang.util.Config;
import com.fengyang.volleyTool.DiskBitmapCache;

public class SecondCollectAdapter extends StuBaseAdapter<SecondCollection> {

	private RequestQueue mQueue;

	private ImageLoader imageLoader;

	private DiskBitmapCache diskCache;

	public SecondCollectAdapter(List<SecondCollection> data, Context context) {
		super(data, context);
		mQueue = Volley.newRequestQueue(context);
		diskCache = new DiskBitmapCache(context.getCacheDir(), 10);
		imageLoader = new ImageLoader(mQueue, diskCache);
		
		comparator = new Comparator<SecondCollection>() {

			@Override
			public int compare(SecondCollection lhs, SecondCollection rhs) {
				if (lhs.getCollectDate().getTime() > rhs.getCollectDate().getTime())
					return -1;
				else if (lhs.getCollectDate().getTime() < rhs.getCollectDate().getTime())
					return 1;
				else
					return 0;
			}
		};
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_second_collect_layout,
					parent, false);

			holder = new ViewHolder();
			holder.image = (NetworkImageView) view
					.findViewById(R.id.item_second_collect_img);
			holder.name = (TextView) view
					.findViewById(R.id.item_second_collect_name);
			holder.price = (TextView) view
					.findViewById(R.id.item_second_collect_price);
			holder.info = (TextView) view
					.findViewById(R.id.item_second_collect_info);
			holder.cancelBtn = (CheckBox) view
					.findViewById(R.id.item_second_collect_cancel);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		SecondCollection item = data.get(position);
		String imgUrl = Config.SECOND_PIC_PATH_THUM + "/" + Config.THUM_PERFIX
				+ item.getImagePath();
		holder.image.setImageUrl(imgUrl, imageLoader);

		holder.name.setText(item.getSecondName());
		holder.price.setText(String.valueOf(item.getSecondPrice()));
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm",
				Locale.CHINA);
		holder.info.setText(context.getString(R.string.second_publish_time,
				df.format(item.getPublishTime())));
		holder.cancelBtn.setChecked(true);
		holder.cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// 不需要实现代码，这里只是为了让checkBox能够响应点击
				Intent intent = new Intent(context, CollectionService.class);
				intent.putExtra(CollectionService.KEY_START_SERVICE_FOR,
						CollectionService.TASK_SECOND_DELETE_COLLECT);
				intent.putExtra(CollectionService.KEY_SECOND_HAND_ID,
						(int) getItemId(position));
				context.startService(intent);
				view.setBackgroundResource(R.drawable.ic_loading_circle);
				Animation anim = AnimationUtils.loadAnimation(context,
						R.anim.rotate_refresh);
				view.startAnimation(anim);
				view.setTag(true);
			}
		});
		holder.cancelBtn.setBackgroundResource(R.drawable.button_collect_checked);
		holder.cancelBtn.clearAnimation();
		return view;
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).getSecondHandId();
	}
	
	/**
	 * 移除item
	 * 
	 * @param id
	 *            二手物品的id
	 */
	public void reomveItemById(int id) {
		for (SecondCollection t : data) {
			if (id == t.getSecondHandId()) {
				data.remove(t);
				break;
			}
		}
		notifyDataSetChanged();
	}

	class ViewHolder {
		NetworkImageView image;
		TextView name;
		TextView price;
		TextView info;
		CheckBox cancelBtn;
	}

	@Override
	public boolean isContains(SecondCollection obj) {
		// TODO Auto-generated method stub
		return false;
	}
}
