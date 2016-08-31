package com.fengyang.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fengyang.customLib.NetworkImageView;
import com.fengyang.entity.AppSecondHand;
import com.fengyang.stu.R;
import com.fengyang.util.Config;
import com.fengyang.volleyTool.DiskBitmapCache;

public class SecondHandAdapter extends StuBaseAdapter<AppSecondHand> {

	private RequestQueue mQueue;

	private ImageLoader imageLoader;

	private DiskBitmapCache diskCache;

	int[] imgPublisherType = { R.drawable.part_publisher_type_s,
			R.drawable.part_publisher_type_m, R.drawable.part_publisher_type_o };

	protected static final String TAG = "SecondHandAdapter";

	public SecondHandAdapter(List<AppSecondHand> data, Context context) {
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
			view = inflater.inflate(R.layout.item_second_hand_layout, parent,
					false);

			holder = new ViewHolder();
			holder.image = (NetworkImageView) view
					.findViewById(R.id.item_secondHand_img);
			holder.publisherType = (ImageView) view
					.findViewById(R.id.item_secondHand_type);
			holder.name = (TextView) view
					.findViewById(R.id.item_secondHand_name);
			holder.price = (TextView) view
					.findViewById(R.id.item_secondHand_price);
			holder.info = (TextView) view
					.findViewById(R.id.item_secondHand_info);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		AppSecondHand item = data.get(position);
		String imgUrl = Config.SECOND_PIC_PATH_THUM + "/" + Config.THUM_PERFIX
				+ item.getImagePath();
		// Log.i(TAG, "SecondHandName= " +item.getSecondHandName()+ " url = " +
		// imgUrl);
		// Œ™imageView…Ë÷√tag ∑¿÷πÕº∆¨¥ÌŒª
		// holder.image.setTag(imgUrl);
		// imageLoader.get(imgUrl, new FadeInImageListener(holder.image,
		// context, imgUrl));
		holder.image.setImageUrl(imgUrl, imageLoader);

		holder.publisherType.setImageResource(imgPublisherType[item
				.getPublisherType()]);
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
		ImageView publisherType;
		TextView name;
		TextView price;
		TextView info;
	}

	@Override
	public boolean isContains(AppSecondHand obj) {
		for (AppSecondHand appSecondHand : data) {
			if (appSecondHand.getSecondHandId().equals(obj.getSecondHandId()))
				return true;
		}
		return false;
	}

}
