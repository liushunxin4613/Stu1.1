package com.fengyang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengyang.model.HomeGridItem;
import com.fengyang.stu.R;
import com.fengyang.util.ui.UIUtils;

public class HomeGridViewAdapter extends StuBaseAdapter<HomeGridItem> {

	public int ROW_NUMBER = 1;

	public HomeGridViewAdapter(ArrayList<HomeGridItem> datalist,
			Context context) {
		super(datalist, context);
		this.ROW_NUMBER = (datalist.size() + 1) / 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_home_layout, parent, false);
			
			AbsListView.LayoutParams param = new AbsListView.LayoutParams(
					LayoutParams.MATCH_PARENT,
					UIUtils.dip2px(context, 140));
			
			view.setLayoutParams(param);

			holder = new ViewHolder();
			holder.image = (ImageView) view.findViewById(R.id.homepage_image);
			holder.text = (TextView) view.findViewById(R.id.homepage_text);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		HomeGridItem item = data.get(position);
		holder.image
				.setImageResource(item.getImage_resouce());

		holder.text.setText(item.getText());
		return view;
	}

	class ViewHolder {
		ImageView image;
		TextView text;
	}

	@Override
	public boolean isContains(HomeGridItem obj) {
		return false;
	}

}
