package com.fengyang.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fengyang.customLib.NetworkImageView;
import com.fengyang.entity.AppSecondHand;
import com.fengyang.stu.R;
import com.fengyang.util.Config;
import com.fengyang.volleyTool.DiskBitmapCache;

public class SecondHistoryAdapter extends StuBaseAdapter<AppSecondHand> {

	private RequestQueue mQueue;

	private ImageLoader imageLoader;

	private DiskBitmapCache diskCache;

	protected static final String TAG = "SecondHistoryAdapter";

	public SecondHistoryAdapter(List<AppSecondHand> data, Context context) {
		super(data, context);
		mQueue = Volley.newRequestQueue(context);
		diskCache = new DiskBitmapCache(context.getCacheDir(), 10);
		imageLoader = new ImageLoader(mQueue, diskCache);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater
					.inflate(R.layout.item_second_history, parent, false);

			holder = new ViewHolder();
			holder.image = (NetworkImageView) view
					.findViewById(R.id.item_second_history_img);
			holder.name = (TextView) view
					.findViewById(R.id.item_second_history_name);
			holder.price = (TextView) view
					.findViewById(R.id.item_second_history_price);
			holder.info = (TextView) view
					.findViewById(R.id.item_second_history_info);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		AppSecondHand item = data.get(position);
		String imgUrl = Config.SECOND_PIC_PATH_THUM + "/" + Config.THUM_PERFIX
				+ item.getImagePath();
//		Log.i(TAG, "SecondHandName= " + item.getSecondHandName() + " url = "
//				+ imgUrl);
		// Œ™imageView…Ë÷√tag ∑¿÷πÕº∆¨¥ÌŒª
//		holder.image.setTag(imgUrl);
//		imageLoader.get(imgUrl, new FadeInImageListener(holder.image, context,
//				imgUrl));
		holder.image.setImageUrl(imgUrl, imageLoader);

		holder.name.setText(item.getSecondHandName());
		holder.price.setText(context.getString(R.string.second_pay,
				String.valueOf(item.getSecondHandPrice())));
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm",
				Locale.CHINA);
		holder.info.setText(context.getString(R.string.second_publish_time,
				df.format(item.getPublishTime())));
		return view;
	}
	
	@Override
	public long getItemId(int position) {
		return data.get(position).getSecondHandId();
	}

	class ViewHolder {
		NetworkImageView image;
		TextView name;
		TextView price;
		TextView info;
	}

	@Override
	public boolean isContains(AppSecondHand obj) {
		for (AppSecondHand appSecondHand : data) {
			if (appSecondHand.getSecondHandId() == obj.getSecondHandId())
				return true;
		}
		return false;
	}
	public void removeItemById(int id){
		for (AppSecondHand s : data) {
			if (id == s.getSecondHandId()) {
				data.remove(s);
				break;
				
			}
			
		}
		notifyDataSetChanged();
	}

}
