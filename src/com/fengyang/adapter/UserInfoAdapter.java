package com.fengyang.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengyang.model.UserInfo;
import com.fengyang.stu.R;

public class UserInfoAdapter extends StuBaseAdapter<UserInfo> {

	public UserInfoAdapter(List<UserInfo> data, Context context) {
		super(data, context);
	}


	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_me_info, parent, false);
			holder = new ViewHolder();
			holder.keyTv = (TextView) view.findViewById(R.id.Tv_item_me_info_key);
			holder.valueTv = (TextView) view.findViewById(R.id.Tv_item_me_info_value);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		UserInfo userInfo = data.get(position);
		holder.keyTv.setText(userInfo.getKey());
		holder.valueTv.setText(userInfo.getValue());
		
		return view;
	}


	@Override
	public boolean isContains(UserInfo userInfo) {
		return false;
	}
	
	class ViewHolder {
		TextView keyTv;
		TextView valueTv;
	}

}
