package com.fengyang.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.fengyang.activity.ShareActivity;
import com.fengyang.activity.ShaveActivity;
import com.fengyang.activity.SignActivity;
import com.fengyang.entity.User;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.tool.Tool;

public class FindFragment extends Fragment {

	// 每日签到
	private LinearLayout dayLayout;
	// 附近打折
	private LinearLayout nearLayout;
	// 抽奖
	private LinearLayout shaveLayout;
	// 软件分享有礼
	private LinearLayout shareLayout;
    
//	private StuApplication application;
    public StringRequest request;
    public RequestQueue mQueue;
    public User user;
    public Uri.Builder builder;
    
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_find, container,
				false);
		dayLayout = (LinearLayout) rootView.findViewById(R.id.liearn_day);
		nearLayout = (LinearLayout) rootView.findViewById(R.id.liearn_near);
		shaveLayout = (LinearLayout) rootView.findViewById(R.id.liearn_shave);
		shareLayout = (LinearLayout) rootView.findViewById(R.id.liearn_share);
		dayLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(MainActivity.checkUserLogin(getActivity())){
					
				startActivity(new Intent(getActivity(), SignActivity.class));
			}}
		});
		nearLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Tool.ToolToast(getActivity().getApplicationContext(), getResources().getText(R.string.developing_function).toString());
			}
		});
		shaveLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(MainActivity.checkUserLogin(getActivity())){
					startActivity(new Intent(getActivity(), ShaveActivity.class));
				}}
				
			
		});
		shareLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), ShareActivity.class));
			}
		});
		return rootView;
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

}
