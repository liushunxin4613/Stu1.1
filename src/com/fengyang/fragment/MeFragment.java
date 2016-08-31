package com.fengyang.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fengyang.activity.CheckPhoneActivity;
import com.fengyang.activity.LoginActivity;
import com.fengyang.activity.MeInfoActivity;
import com.fengyang.activity.ModifyUserPhotoActivity;
import com.fengyang.activity.PublishAuthenticationActivity;
import com.fengyang.activity.PublishUserActivity;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.entity.User;
import com.fengyang.service.InitUserService;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.volleyTool.DiskBitmapCache;
import com.fengyang.volleyTool.UserPhotoImageListener;

public class MeFragment extends Fragment implements OnClickListener {

	private StuApplication application;
	/**
	 * �û�ͷ��
	 */
	private RelativeLayout userPhoto;
	/**
	 * ��½��鿴�û���Ϣ��ImageButton
	 */
	private ImageButton userBtn;

	/**
	 * �û�����TextView
	 */
	private TextView userNameTV;
	/**
	 * ��½��鿴�û���Ϣ��TextView
	 */
	private TextView loginTV;

	/**
	 * Request�������
	 */
	private RequestQueue mQueue;

	private ImageLoader imageLoader;

	private View view;
//	private String phoneStr="";
//	private String userStr="";
	public static final String KEY_ME_TYPE = "authentication";
	public static final String USER_PHOTO_PATH = "photoPath";
	private DiskBitmapCache diskCache;
	private static final String TAG = "Fragment";
	public static final String KEY_URI_DATA = "uriData";
	public static final String KEY_PHONE_TYPE = "phone";
	public static final String KEY_CHECK_PHONE_TYPE = "phone";
//	private static final int REQUEST_MODIFY_PHOTOT = 1;
	private RelativeLayout[] items = new RelativeLayout[4];
	private View lineView4;
	private static MeFragment fragment;

	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new MeFragment();
		return fragment;
	}

	private IntentFilter mFilter;
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// ��½�ɹ��������û���Ϣ������
			if (InitUserService.ACTION_LOGINED.equals(action)
					|| InitUserService.ACTION_UPDATE_USER.equals(action)) {
				User user = application.getUser();
				setUserData(true, user);
			}
			if (intent.getAction().equals(InitUserService.ACTION_LOGIN_OUT)) {
				setUserData(false, null);
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (StuApplication) getActivity().getApplication();
		mQueue = Volley.newRequestQueue(getActivity());
		diskCache = new DiskBitmapCache(getActivity().getCacheDir(), 10);
		imageLoader = new ImageLoader(mQueue, diskCache);

		mFilter = new IntentFilter();
		mFilter.addAction(InitUserService.ACTION_LOGINED);
		mFilter.addAction(InitUserService.ACTION_LOGIN_OUT);
		mFilter.addAction(InitUserService.ACTION_UPDATE_USER);
		getActivity().registerReceiver(receiver, mFilter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.fragment_me, container, false);
		userPhoto = (RelativeLayout) rootView.findViewById(R.id.user_IV);
		userBtn = (ImageButton) rootView.findViewById(R.id.user_imgBtn);
		userNameTV = (TextView) rootView.findViewById(R.id.user_name_TV);
		loginTV = (TextView) rootView.findViewById(R.id.user_hello);
		items[0] = (RelativeLayout) rootView
				.findViewById(R.id.me_item_userInfo);
		items[1] = (RelativeLayout) rootView
				.findViewById(R.id.me_item_identificate);
		items[2] = (RelativeLayout) rootView
				.findViewById(R.id.me_item_discount);
		view = rootView.findViewById(R.id.View_me_item_discount);
		items[3] = (RelativeLayout) rootView.findViewById(R.id.me_item_exist);

		lineView4 = rootView.findViewById(R.id.me_item_line_5);

		items[2].setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		if (application.isLogin()) {
			User user = application.getUser();
			setUserData(true, user);
		} else {
			items[3].setVisibility(View.INVISIBLE);
			lineView4.setVisibility(View.INVISIBLE);
		}

		for (RelativeLayout layout : items) {
			layout.setOnClickListener(this);
		}
		
		userBtn.setOnClickListener(this);
		loginTV.setOnClickListener(this);
		return rootView;
	}
	
	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	private void setUserData(boolean isLogin, User user) {
		if (isLogin) {
			userNameTV.setText(user.getName());
			loginTV.setText(R.string.fragment_me_info_TV);
			requestUserPhoto(user.getPhotoPath());
			items[3].setVisibility(View.VISIBLE);
			lineView4.setVisibility(View.VISIBLE);
		} else {
			userPhoto.setBackgroundResource(R.drawable.default_user);
			userNameTV.setText(R.string.fragment_me_default_name);
			loginTV.setText(R.string.fragment_me_welcome_TV);
			items[3].setVisibility(View.INVISIBLE);
			lineView4.setVisibility(View.INVISIBLE);
		}
	}

	private void requestUserPhoto(String photoPath) {
		if (photoPath != null && photoPath.length() > 0) {
			String imgUrl = Config.USER_PHOTO_PATH + "/" + photoPath;
			imageLoader.get(imgUrl, new UserPhotoImageListener(userPhoto,
					getActivity()));
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		final Intent intent;
		switch (id) {
		case R.id.user_imgBtn:
			updateUserImage();
			break;
		case R.id.user_hello:
			// ����½����༭����
			if (application.isLogin()) {
				intent = new Intent(getActivity(), PublishUserActivity.class);
				startActivity(intent);
			} else {// �����½
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivityForResult(intent, MainActivity.REQUEST_LOGIN);
			}
			break;
		case R.id.me_item_userInfo:
			skipIntent(MeInfoActivity.class);
			break;
		case R.id.me_item_identificate:
			skipIntent(PublishAuthenticationActivity.class);
			break;
		case R.id.me_item_exist:
			loginOut();
			break;
		}

	}

	/**
	 * �޸��û�ͷ��
	 */
	private void updateUserImage() {
		if (MainActivity.checkUserLogin(getActivity())) {
			Intent intent=new Intent(getActivity(),ModifyUserPhotoActivity.class);
			startActivity(intent);
		}
	}

	private void loginOut() {
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_CENTER,
				getString(R.string.dialog_exist_title),
				getString(R.string.dialog_exist_content),
				getString(R.string.dialog_sure),
				getString(R.string.dialog_cancel));

		dialog.setOnclickListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					
					application.setUser(null);
					startActivity(new Intent(getActivity(), LoginActivity.class));
					getActivity().sendBroadcast(
							new Intent(InitUserService.ACTION_LOGIN_OUT));
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getChildFragmentManager().beginTransaction(), TAG);
	}

	public void skipIntent(Class<? extends Activity> mClass) {
		final Intent intent;
		if (MainActivity.checkUserLogin(getActivity())) {
			intent = new Intent(getActivity(), mClass);
			if (mClass == PublishAuthenticationActivity.class) {
				if (application.getUser().getIsVerify() == User.VERIFY_TYPE_COMMIT) {
					ConfirmDialog dialog = new ConfirmDialog(
							ConfirmDialog.CONFIRM_STYLE_CENTER,
							getString(R.string.dialog_me_is_commit_title),
							getString(R.string.dialog_me_is_commit_content),
							getString(R.string.dialog_sure), null);
					dialog.show(getChildFragmentManager(), TAG);
				} else if (application.getUser().getIsVerify() == User.VERIFY_TYPE_PASSED) {
					ConfirmDialog dialog = new ConfirmDialog(
							ConfirmDialog.CONFIRM_STYLE_CENTER,
							getString(R.string.dialog_me_is_auth_title),
							getString(R.string.dialog_me_is_auth_content),
							getString(R.string.dialog_sure), null);
					dialog.show(getChildFragmentManager(), TAG);
				} else {
					publishIntent();
				}
			} else {
				startActivityForResult(intent, MainActivity.REQUEST_LOGIN);
			}
		}

	}

	public void publishIntent() {
		final Intent intent = new Intent(getActivity(),CheckPhoneActivity.class);
		if (null != application.getUser().getPhone()) {
			intent.putExtra(KEY_CHECK_PHONE_TYPE, application.getUser().getPhone());
		}
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_CENTER,
				getString(R.string.fragment_me_publisher_authentication_BT),
				getString(R.string.fragment_me_auth_content),
				getString(R.string.fragment_me_auth_stu),
				getString(R.string.fragment_me_auth_merchant));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					intent.putExtra(
							PublishAuthenticationActivity.KEY_AUTHENTICATION_TYPE,
							PublishAuthenticationActivity.TYPE_STU);
					startActivity(intent);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					intent.putExtra(
							PublishAuthenticationActivity.KEY_AUTHENTICATION_TYPE,
							PublishAuthenticationActivity.TYPE_MERCHANT);
					startActivity(intent);
					break;
				}
				Log.i("which", "" + which);
			}
		});
		dialog.show(getChildFragmentManager().beginTransaction(), TAG);
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}

	// private boolean checkSoftStage() {
	// if (Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED)) { // �ж��Ƿ����SD��
	// return true;
	// } else {
	// Toast.makeText(getActivity(), R.string.sdCard_error,
	// Toast.LENGTH_SHORT).show();
	// return false;
	// }
	// }
}