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

import com.fengyang.entity.AppPartTime;
import com.fengyang.stu.R;

public class PartTimeAdapter extends StuBaseAdapter<AppPartTime> {

	protected static final String TAG = "PartTimeAdapter";
	
	String[] payUnit;
	String[] payWay;
	int[] imgTimeType = { R.drawable.part_time_type_long,
			R.drawable.part_time_type_short };
	int[] imgPublisherType = { R.drawable.part_publisher_type_s,
			R.drawable.part_publisher_type_m, R.drawable.part_publisher_type_o };

	public PartTimeAdapter(List<AppPartTime> data, Context context) {
		super(data, context);
		payUnit = context.getResources().getStringArray(R.array.job_pay_unit);
		payWay = context.getResources().getStringArray(
				R.array.job_pay_type_entries);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_part_time_layout, parent,
					false);

			holder = new ViewHolder();
			holder.name = (TextView) view
					.findViewById(R.id.item_part_time_title);
			holder.price = (TextView) view
					.findViewById(R.id.item_part_time_price);
			holder.method = (TextView) view
					.findViewById(R.id.item_part_time_method);
			holder.timeType = (ImageView) view
					.findViewById(R.id.item_part_time_type);
			holder.publisher = (TextView) view
					.findViewById(R.id.item_part_time_publish);
			holder.info = (TextView) view
					.findViewById(R.id.item_part_time_info);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		AppPartTime item = data.get(position);

		holder.price.setText(context.getString(R.string.part_pay,
				String.valueOf(item.getPay())));
		holder.method.setText(payUnit[item.getPayUnit()]);
		holder.name.setText(item.getPartTimeName());
		holder.timeType.setImageResource(imgTimeType[item.getTimeType()]);
		holder.publisher.setText(item.getPartPublisher());
		holder.publisher.setCompoundDrawablePadding(10);
		holder.publisher.setCompoundDrawablesWithIntrinsicBounds(
				imgPublisherType[item.getPublisherType()], 0, 0, 0);
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm",
				Locale.CHINA);

		holder.info.setText(context.getString(R.string.part_publish_time,
				df.format(item.getPublishTime())));
		return view;
	}

	class ViewHolder {
		TextView price;
		TextView method;
		TextView name;
		ImageView timeType;
		TextView publisher;
		TextView info;
	}
	
	@Override
	public long getItemId(int position) {
		return data.get(position).getPartTimeId();
	}

	@Override
	public boolean isContains(AppPartTime object) {
		for (AppPartTime appPartTime : data) {
			if (appPartTime.getPartTimeId().equals(object.getPartTimeId()))
				return true;
		}
		return false;
	}
	
	/**
	 * ÒÆ³ýitem
	 * 
	 * @param id
	 *            ¼æÖ°µÄid
	 */
	public void removeItemById(int id) {
		for (AppPartTime t : data) {
			if (id == t.getPartTimeId()) {
				data.remove(t);
				break;
			}
		}
		notifyDataSetChanged();
	}
	
}
