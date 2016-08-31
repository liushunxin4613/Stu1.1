package com.fengyang.adapter;

import java.text.SimpleDateFormat;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.fengyang.model.PartCollection;
import com.fengyang.service.CollectionService;
import com.fengyang.stu.R;

public class PartCollectAdapter extends StuBaseAdapter<PartCollection> {

	String[] payUnit;
	String[] payWay;

	public PartCollectAdapter(List<PartCollection> list, Context context) {
		super(list, context);
		payUnit = context.getResources().getStringArray(R.array.job_pay_unit);
		payWay = context.getResources().getStringArray(
				R.array.job_pay_type_entries);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_part_collect_layout, parent,
					false);

			holder = new ViewHolder();
			holder.price = (TextView) view
					.findViewById(R.id.item_part_collect_price);
			holder.method = (TextView) view
					.findViewById(R.id.item_part_collect_method);
			holder.name = (TextView) view
					.findViewById(R.id.item_part_collect_name);
			holder.publisher = (TextView) view
					.findViewById(R.id.item_part_collect_publish);
			holder.info = (TextView) view
					.findViewById(R.id.item_part_collect_info);
			holder.cancelBtn = (ImageButton) view
					.findViewById(R.id.item_part_collect_cancel);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		PartCollection item = data.get(position);
		holder.name.setText(item.getPartName());
		holder.price.setText(String.valueOf(item.getPay()));
		holder.method.setText(payUnit[item.getPayUnit()]);
		holder.publisher.setText(item.getPublisherName());
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm",
				Locale.CHINA);
		holder.info.setText(context.getString(R.string.part_publish_time,
				df.format(item.getPublishTime())));
		holder.cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// 不需要实现代码，这里只是为了让checkBox能够响应点击

				Intent intent = new Intent(context, CollectionService.class);
				intent.putExtra(CollectionService.KEY_START_SERVICE_FOR,
						CollectionService.TASK_PART_DELETE_COLLECT);
				intent.putExtra(CollectionService.KEY_PART_TIME_ID,
						(int) getItemId(position));
				context.startService(intent);
				view.setBackgroundResource(R.drawable.ic_loading_circle);
				Animation anim = AnimationUtils.loadAnimation(context,
						R.anim.rotate_refresh);
				view.startAnimation(anim);
				view.setTag(true);
			}
		});
		holder.cancelBtn
				.setBackgroundResource(R.drawable.button_collect_checked);
		holder.cancelBtn.clearAnimation();
		return view;
	}

	/**
	 * 通过兼职id移除item
	 * 
	 * @param id
	 *            兼职的id
	 */
	public void reomveItemById(int id) {
		for (PartCollection partCollection : data) {
			if (id == partCollection.getPartTimeId()) {
				data.remove(partCollection);
				break;
			}
		}
		notifyDataSetChanged();
	}

	class ViewHolder {
		TextView price;
		TextView method;
		TextView name;
		TextView publisher;
		TextView info;
		ImageButton cancelBtn;
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).getPartTimeId();
	}

	@Override
	public boolean isContains(PartCollection obj) {
		return false;
	}
}
