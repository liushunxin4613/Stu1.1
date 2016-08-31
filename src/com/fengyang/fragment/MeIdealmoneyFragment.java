package com.fengyang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fengyang.stu.R;

public class MeIdealmoneyFragment extends Fragment  implements OnClickListener{
//	/*
//	 * 签到积分
//	 */
//	private TextView signTv;
//	/*
//	 * 分享积分
//	 */
//	private TextView shareTv;
//	/*
//	 * 商城积分
//	 */
//	private TextView mallTv;
//	/*
//	 * 合计积分
//	 */
//	private TextView totalTv;
	/*
	 * 使用积分
	 */
	private LinearLayout makeLl;
	
	
	private static MeIdealmoneyFragment fragment = null;
	
	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new MeIdealmoneyFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.fragment_me_idealmoney, container, false);
//		signTv=(TextView) rootView.findViewById(R.id.Tv_item_me_idealmoney_sign);
//		shareTv=(TextView) rootView.findViewById(R.id.Tv_item_me_idealmoney_share);
//		mallTv=(TextView) rootView.findViewById(R.id.Tv_item_me_idealmoney_mall);
//		totalTv=(TextView) rootView.findViewById(R.id.Tv_item_me_idealmoney_total);
		makeLl=(LinearLayout) rootView.findViewById(R.id.Ll_sheep);
		makeLl.setOnClickListener(this);
		return rootView;
	}
	
	@Override
	public void onClick(View v) {
		
	}

}
