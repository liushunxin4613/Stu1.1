package com.fengyang.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengyang.entity.AppPartTime;
import com.fengyang.stu.R;

public class PartHistoryAdapter extends StuBaseAdapter<AppPartTime> {

	String[] payUnit;
	String[] payWay;
	int[] imgTimeType = { R.drawable.part_time_type_long,
			R.drawable.part_time_type_short };

	public PartHistoryAdapter(List<AppPartTime> data, Context context) {
		super(data, context);
		payUnit = context.getResources().getStringArray(R.array.job_pay_unit);
		payWay = context.getResources().getStringArray(
				R.array.job_pay_type_entries);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
	/*	int deletePartId = 3;
		if (position == deletePartId) {
			data.remove(position);//选择行的位置  
			PartHistoryAdapter.this.notifyDataSetChanged();  
			//listView.invalidate();  
		}
    */
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_part_histrory, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) view
					.findViewById(R.id.item_part_history_name);
			holder.price = (TextView) view
					.findViewById(R.id.item_part_history_price);
			holder.method = (TextView) view
					.findViewById(R.id.item_part_history_method);
			holder.timeType = (ImageView) view
					.findViewById(R.id.item_part_time_type);
			holder.info = (TextView) view
					.findViewById(R.id.item_part_history_info);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		AppPartTime item = data.get(position);
		
		holder.name.setText(item.getPartTimeName());
		holder.timeType.setImageResource(imgTimeType[item.getTimeType()]);
		holder.price.setText(String.valueOf(item.getPay()));
		Log.i("adapter", "PayUnit = " + item.getPayUnit());
		holder.method.setText(payUnit[item.getPayUnit()]);
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm",
				Locale.CHINA);
		holder.info.setText(context.getString(R.string.part_publish_time,
				df.format(item.getPublishTime())));
		return view;
	}
	
	@Override
	public long getItemId(int position) {
		return data.get(position).getPartTimeId();
	}

	class ViewHolder {
		TextView price;
		TextView method;
		TextView name;
		ImageView timeType;
		TextView info;
	}

	@Override
	public boolean isContains(AppPartTime obj) {
		for (AppPartTime appPartTime : data) {
			if (appPartTime.getPartTimeId() == obj.getPartTimeId())
				return true;
		}
		return false;
	}
	/**
	 * 移除item
	 * 
	 * @param id
	 *            兼职的id
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
	/**
	 * 更新item
	 * 
	 * @param id
	 *            兼职的id
	 */
	public void updateItemById(int id) {
		for (int i = 0; i < data.size(); i++) {
			AppPartTime t = data.get(i);
			if (id == t.getPartTimeId()) {
				data.set(i, t);
//				Log.d("id", "id="+id);
//				Log.d("id", "id="+t.getPartTimeId());
				return;
			}
			
		}
		notifyDataSetChanged();
	}
}
