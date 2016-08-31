package com.fengyang.stu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.fengyang.activity.CheckPhoneActivity;
import com.fengyang.activity.ChooseListActivity;
import com.fengyang.activity.HistoryActivity;
import com.fengyang.activity.LoginActivity;
import com.fengyang.activity.PublishAuthenticationActivity;
import com.fengyang.activity.PublishPartActivity;
import com.fengyang.activity.PublishSecondActivity;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.db.AreaDAO;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.entity.AppApkVersion;
import com.fengyang.entity.OubaArea;
import com.fengyang.entity.User;
import com.fengyang.fragment.CollectFragment;
import com.fengyang.fragment.FindFragment;
import com.fengyang.fragment.HomeFragment;
import com.fengyang.fragment.MeFragment;
import com.fengyang.fragment.VersionInfoFragment;
import com.fengyang.model.Json;
import com.fengyang.tool.Tool;
import com.fengyang.util.Config;
import com.fengyang.util.VersionUtils;
import com.fengyang.util.push.PushUtil;
import com.fengyang.util.ui.ImmersionActivity;

@SuppressLint("HandlerLeak") public class MainActivity extends ImmersionActivity implements
NavigationDrawerFragment.NavigationDrawerCallbacks {

	private ActionBar actionBar;

	private FrameLayout container;
	/**
	 * �ײ��ĸ�Radio��ť
	 */
	private RadioButton[] bottomBtn;

	private RadioGroup radioGroup;
	/**
	 * ��ǰfragment��λ��
	 */
	private int location = 0;

	/**
	 * ȷ���˳�
	 */
	private boolean confirm = false;
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private Menu menu;

	public BDLocationListener mMyLocationListener;
	public LocationClient mLocationClient;
	public boolean AU_location = false;
	public static String locationAddress = null;
	private static final int MAIN_REQUEST_CHOOSE_AREA = 1;
	//	private static final int REQUEST_CHECK_STU = 2;
	//	private static final int REQUEST_CHECK_SELLER = 3;
	/* ������ */
	//	private static final int DOWNLOAD = 1;
	/* ���ؽ��� */
	//	private static final int DOWNLOAD_FINISH = 2;
	public static final int XML_HASHMAP = 3;
	public static String LOCATION_DISTRICT = "locationAddress";
	private static StuApplication application;
	public static String LOCATION_CITY_ACTION = "city_action";
	public static String LOCATION_CITY = "city";
	public static String LOCATION_DISTRACT = "distract";
	public String city = null;
	public String distract = null;
	/***
	 * �������ص�ַ
	 */
	public static String apkUrl;

	private static int versionCode;

	private String mSavePath;

	private boolean stopUpdate = false;
	private boolean closeUpdate = false;

	private static boolean downUpdate = true;

	private static final int MSG_LENGTH = 2;

	public static final int MSG_DOWN = 3;

	public static final int MSG_PROGRESS = 4;

	public static final int MSG_SEELP = 5;

	public static final int XML_DOWN = 1;

	public static final int NOTIFICATION_ID=2015042101;
	private boolean xj = true;
	private NotificationManager nm;  
	private Notification notification;  
	private float length;
	public static final int THREAD_TIME = 2000;
	public static final float FLOAT_SIZE = 1024;
	/**
	 * ����һλС��ʱ
	 */
	public static final int INT_ONE = 10;
	private boolean msgSend;
	/**
	 * ��ʾ�Ի����action
	 */
	public static final String ACTION_SHOW_DIALOG = "MainActivity.showDialog";

	public static final String ACTION_STOP_MOFY = "com.android.broadcasttest.stopnofy";

	public static final String ACTION_DELETE_MOFY = "com.android.broadcasttest.deletenofy";
	/**
	 * �Ի���ı�������
	 */
	public static final String EXTRA_DIALOG_TITLE = "showDialog.title";
	/**
	 * �Ի������������
	 */
	public static final String EXTRA_DIALOG_CONTENT = "showDialog.content";
	/**
	 *�ֶ���λ�Ĺ㲥
	 */
	public static final String MainActivity_HAND_MOVEMENT = "hand movement";

	//	private URL url;

	/**
	 * �°��ȡ�汾��Ϣ
	 */
	private AppApkVersion mApkVersion;
	private RequestQueue mQueue;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_SHOW_DIALOG.equals(action)) {
				String title = intent.getStringExtra(EXTRA_DIALOG_TITLE);
				String content = intent.getStringExtra(EXTRA_DIALOG_CONTENT);
				ConfirmDialog dialog = new ConfirmDialog(
						ConfirmDialog.CONFIRM_STYLE_CENTER, title, content,
						context.getString(R.string.dialog_sure), null);
				dialog.show(getSupportFragmentManager(), TAG);
			}

			if (ACTION_STOP_MOFY.equals(action)) {
				Log.i(TAG, "����");
				stopUpdate = !stopUpdate;
				Log.i(TAG, "stopUpdate= "+stopUpdate);
			}

			if (ACTION_DELETE_MOFY.equals(action)) {
				Log.i(TAG, "ɾ����֪ͨ");
				stopUpdate = true;
				closeUpdate = true;
				nm.cancel(NOTIFICATION_ID);
			}

			/**
			 * ��֪ͨ���ֶ���λ
			 * */
			if(MainActivity_HAND_MOVEMENT.equals(action)){
				ArrayList<Integer> choosedId = null;
				Log.d(TAG, "MainActivity_HAND_MOVEMENT+MAIN_REQUEST_CHOOSE_AREA");
				choosedId =intent.getIntegerArrayListExtra(ChooseListActivity.KEY_CHOOSED_LIST);
				//				int id = intent.getIntExtra("id", -1);
				Log.d(TAG, choosedId.size()+"choosedId");
				AreaDAO areaDAO = new AreaDAO(getApplicationContext());
				for (int i = 0; i < choosedId.size(); i++) {
					if (i == 0) {
						OubaArea provinceArea = areaDAO.queryById(choosedId.get(i));
						application.setProvice(provinceArea);

					}
					if (i == 1) {
						OubaArea cityArea = areaDAO.queryById(choosedId.get(i));
						application.setCity(cityArea);
					}
					if (i == 2) {
						OubaArea distrct = areaDAO.queryById(choosedId.get(i));
						application.setDistrct(distrct);
						String dis = areaDAO.queryById(choosedId.get(i))
								.getAreaName();
						actionBar.setTitle(dis);
						Tool.PutSharePreferences(getApplicationContext(),
								LOCATION_DISTRICT, dis);

					}
				}		
			}
		}
	};

	private FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(
			getSupportFragmentManager()) {

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {
			case R.id.radio_home:
				return new HomeFragment();
			case R.id.radio_collection:
				return new CollectFragment();
			case R.id.radio_find:
				return new FindFragment();
			case R.id.radio_me:
				return new MeFragment();
			}
			return null;
		}
	};

	/**
	 * ��½������
	 */
	public static final int REQUEST_LOGIN = 0x011;

	private static final String TAG = "MainActivity";

	public static void showToast(Context context, int resId) {
		Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * ����û��Ƿ��½
	 * 
	 * @return �û��Ƿ��½
	 */
	public static boolean checkUserLogin(final FragmentActivity activity) {
		StuApplication application = (StuApplication) activity.getApplication();
		Context context = application.getApplicationContext();
		if (application.isLogin()) {
			return true;
		} else {
			ConfirmDialog dialog = new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					context.getString(R.string.login_title),
					context.getString(R.string.login_content),
					context.getString(R.string.login),
					context.getString(R.string.dialog_cancel));
			dialog.setOnclickListener(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						activity.startActivity(new Intent(activity,
								LoginActivity.class));
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			});
			dialog.show(
					activity.getSupportFragmentManager().beginTransaction(),
					TAG);
			return false;
		}
	}

	/**
	 * 
	 * ����û��Ƿ���֤
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean checkUserVerify(final FragmentActivity activity) {
		StuApplication application = (StuApplication) activity.getApplication();
		Context context = application.getApplicationContext();
		if (application.getUser().getIsVerify() == User.VERIFY_TYPE_PASSED) {
			return true;
		} else if (application.getUser().getIsVerify() == User.VERIFY_TYPE_COMMIT) {
			ConfirmDialog dialog = new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					activity.getString(R.string.dialog_me_is_commit_title),
					activity.getString(R.string.dialog_me_is_commit_content),
					activity.getString(R.string.dialog_sure), null);
			dialog.show(activity.getSupportFragmentManager(), TAG);
			return false;
		} else {
			ConfirmDialog dialog = new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					context.getString(R.string.dialog_verify_title),
					context.getString(R.string.dialog_verify_content),
					context.getString(R.string.dialog_verify_go_verify),
					context.getString(R.string.dialog_cancel));
			dialog.setOnclickListener(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						showAuthenDialog(activity);
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			});
			dialog.show(activity.getSupportFragmentManager(), TAG);
			return false;
		}
	}

	/**
	 * ��ʾѡ����֤���͵ĶԻ���
	 * 
	 * @param activity
	 */
	public static void showAuthenDialog(final FragmentActivity activity) {
		final Intent intent = new Intent(activity, CheckPhoneActivity.class);
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_CENTER,
				activity.getString(R.string.fragment_me_publisher_authentication_BT),
				activity.getString(R.string.fragment_me_auth_content), activity
				.getString(R.string.fragment_me_auth_stu), activity
				.getString(R.string.fragment_me_auth_merchant));

		dialog.setOnclickListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					intent.putExtra(
							PublishAuthenticationActivity.KEY_AUTHENTICATION_TYPE,
							PublishAuthenticationActivity.TYPE_STU);
					activity.startActivity(intent);
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					intent.putExtra(
							PublishAuthenticationActivity.KEY_AUTHENTICATION_TYPE,
							PublishAuthenticationActivity.TYPE_MERCHANT);
					activity.startActivity(intent);
					break;
				}
				Log.i("which", "" + which);
			}
		});
		dialog.show(activity.getSupportFragmentManager().beginTransaction(),
				TAG);
	}

	//	@Override
	//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	//		super.onActivityResult(requestCode, resultCode, data);
	//		Log.d(TAG, "����onActivityResult");
	//		if (resultCode == Activity.RESULT_OK) {
	//			Log.d(TAG, "Activity.RESULT_OK");
	//			ArrayList<Integer> choosedId = null;
	//			switch (requestCode) {
	//			case MAIN_REQUEST_CHOOSE_AREA:
	//				Log.d(TAG, "MAIN_REQUEST_CHOOSE_AREA");
	//				choosedId = data
	//				.getIntegerArrayListExtra(ChooseListActivity.KEY_CHOOSED_LIST);
	//				//StringBuffer sb = new StringBuffer();
	//				AreaDAO areaDAO = new AreaDAO(getApplicationContext());
	//				for (int i = 0; i < choosedId.size(); i++) {
	//					if (i == 0) {
	//						OubaArea provinceArea = areaDAO.queryById(choosedId.get(i));
	//						application.setProvice(provinceArea);
	//						
	//					}
	//					if (i == 1) {
	//						OubaArea cityArea = areaDAO.queryById(choosedId.get(i));
	////						String cityar = areaDAO.queryById(choosedId.get(i))
	////								.getAreaName();
	////						city = cityar;
	//						application.setCity(cityArea);
	//					}
	//					if (i == 2) {
	//						OubaArea distrct = areaDAO.queryById(choosedId.get(i));
	//						application.setDistrct(distrct);
	//						 String dis = areaDAO.queryById(choosedId.get(i))
	//								.getAreaName();
	//						actionBar.setTitle(dis);
	//						Tool.PutSharePreferences(getApplicationContext(),
	//								LOCATION_DISTRICT, dis);
	//						
	////						if (location == 0) {
	////							actionBar.setTitle(dis);
	////						//	distract = dis;
	////							
	////							AU_location = true;
	////						}
	//					}
	//				}
	//				
	//				break;
	//			}
	//		}
	//
	//	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		application = (StuApplication) getApplication();

		versionCode = VersionUtils.getVersionCode(this);

		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.app_logo,
				VersionUtils.getAppName(this), System.currentTimeMillis());
		// ���Ա��������FLAG_AUTO_CANCEL
		// �����Ա��������FLAG_NO_CLEAR
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView = new RemoteViews(getPackageName(), R.layout.progressdialog_fragment);

		if (downUpdate) {
			Log.i(TAG, "������");
			mQueue = new Volley().newRequestQueue(this);
			updateThread();
		}

		actionBar = getActionBar();

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		container = (FrameLayout) findViewById(R.id.container);
		radioGroup = (RadioGroup) findViewById(R.id.BottomGroup);
		bottomBtn = new RadioButton[4];
		bottomBtn[0] = (RadioButton) findViewById(R.id.radio_home);
		bottomBtn[1] = (RadioButton) findViewById(R.id.radio_collection);
		bottomBtn[2] = (RadioButton) findViewById(R.id.radio_find);
		bottomBtn[3] = (RadioButton) findViewById(R.id.radio_me);

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				PushUtil.getMetaValue(MainActivity.this, "api_key"));

		perpareUI();

		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_SHOW_DIALOG);
		filter.addAction(ACTION_STOP_MOFY);
		filter.addAction(ACTION_DELETE_MOFY);
		filter.addAction(MainActivity_HAND_MOVEMENT);
		registerReceiver(receiver, filter);

		mLocationClient = new LocationClient(getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		setLocationOption();
		mLocationClient.start();

		if (mLocationClient != null && mLocationClient.isStarted()) {

			//			int i = mLocationClient.requestLocation();
			// Tool.ToolToast(getApplicationContext(), "***1+++"+i);
		} else {
			Log.d("LocSDK5", "locClient is null or not started");
			// Tool.ToolToast(getApplicationContext(), "888***");
		}

	}

	private Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak") 
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_DOWN:
				// ��װ�ļ�
				Log.i(TAG, "MSG_DOWN");
				// ȡ��֪ͨ
				nm.cancel(NOTIFICATION_ID);
				stopUpdate = true;
				closeUpdate =true;
				installApk();
				xj = true;
				break;
			case MSG_LENGTH:
				// ��ȡ�ļ���С
				length = (Float) msg.obj;
				setNotificationManager(0, length, 0);
				Log.i(TAG, "MSG_LENGTH" + length);
				break;
			case MSG_SEELP:
				// ʱ����ʾ
				Log.i(TAG, "ʱ����ʾ");
				msgSend = true;
				break;
			case MSG_PROGRESS:
				// ���½�����
				Log.i(TAG, "���½�����");
				int progress = msg.arg1;
				float count = (Float) msg.obj;
				notiView(progress, length, count);
				break;
			case XML_DOWN:
				AppApkVersion apkVersion = (AppApkVersion) msg.obj;
				mApkVersion = apkVersion;
				int serviceCode = Integer.valueOf(apkVersion.getVersionCode());
				// �汾�ж�
				if (serviceCode > versionCode) {
					showNoticeDialog();
				}
				break;
			}
		};
	};
	private void setNotificationManager(int progress,float length,int counts){
		notification.contentView.setProgressBar(R.id.pb,100,progress,false);
		notification.contentView.setTextViewText(R.id.progress_message,VersionUtils.getAppName(this));
		notification.contentView.setTextViewText(R.id.progress_current,"������"+progress+"%");
		notification.contentView.setTextViewText(R.id.progress_current_M, counts+"M/"+length+"M");

		//ȫ�����
		Intent deleteIntent = new Intent(ACTION_DELETE_MOFY); 
		notification.deleteIntent = PendingIntent.getBroadcast(this, 0, deleteIntent, 0);
		//����֪ͨ
		Intent stopIntent = new Intent(ACTION_STOP_MOFY); 
		notification.contentIntent = PendingIntent.getBroadcast(this, 0, stopIntent, 0);

	}

	private void notiView(int progress, float length, float counts) {
		notification.contentView.setProgressBar(R.id.pb, 100, progress, false);
		notification.contentView.setTextViewText(R.id.progress_message, "���׼�ְ");
		notification.contentView.setTextViewText(R.id.progress_current, "������"+progress+"%");
		notification.contentView.setTextViewText(R.id.progress_current_M, counts+"M/"+length+"M");
		//���õ�ǰֵΪcount  
		nm.notify(NOTIFICATION_ID, notification);//�����Ǹ���notification,���Ǹ��½����� 

	}

	private void installApk() {
		File apkfile = new File(mSavePath, mApkVersion.getApkName());
		if (!apkfile.exists()) {
			return;
		}
		// ͨ��Intent��װAPK�ļ�
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	public void showNoticeDialog() {
		ConfirmDialog dialog = new ConfirmDialog(
				ConfirmDialog.CONFIRM_STYLE_CENTER,
				getString(R.string.soft_update_title),
				getString(R.string.soft_update_info),
				getString(R.string.soft_update_updatebtn),
				getString(R.string.soft_update_later));
		dialog.setOnclickListener(new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case DialogInterface.BUTTON_POSITIVE:
					showDownloadDialog();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
				}
			}
		});
		dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
	}

	private void showDownloadDialog() {
		// �����ļ�
		stopUpdate = false;
		closeUpdate =false;
		xj = true;
		new Thread(run).start();
		new Thread(runSeelp).start();
	}

	private Runnable run = new Runnable() {
		@Override
		public void run() {
			try {
				Message msg;
				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// ��ô洢����·��
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";
					URL url = new URL(Config.URL_APP_DOWNLOADLASTAPK);
					apkUrl = Config.URL_APP_DOWNLOADLASTAPK;
					// ��������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// ��ȡ�ļ���С
					int length = conn.getContentLength();

					float lengthF = (length / FLOAT_SIZE) / FLOAT_SIZE;
					lengthF = (float) Math.round(lengthF * INT_ONE) / INT_ONE;
					msg = new Message();
					msg.what = MSG_LENGTH;
					msg.obj = lengthF;
					mHandler.sendMessage(msg);

					// ����������
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// �ж��ļ�Ŀ¼�Ƿ����
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, mApkVersion.getApkName());
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// ����
					byte buf[] = new byte[1024];
					// д�뵽�ļ���
					while (!closeUpdate) {
						do {
							int numread = is.read(buf);
							count += numread;

							// ���������λ��
							int progress = (int) (((float) count / length) * 100);

							if (msgSend) {
								msg = new Message();
								msg.what = MSG_PROGRESS;
								msg.arg1 = progress;
								float countF = (count/FLOAT_SIZE)/FLOAT_SIZE;
								countF = (float)(Math.round(countF*INT_ONE))/INT_ONE;
								msg.obj = countF;
								mHandler.sendMessage(msg);
								msgSend = false;
							}

							if ((numread <= 0) && xj ) {
								// �������
								mHandler.sendEmptyMessage(MSG_DOWN);
								xj = false;
								break;
							}
							// д���ļ�
							if (numread >= 0) {
								fos.write(buf, 0, numread);
							}
						} while (!stopUpdate);// ���ȡ����ֹͣ����.
					}

					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	};
	private Runnable runSeelp = new Runnable() {
		@Override
		public void run() {
			try {
				while (!closeUpdate) {
					while (!stopUpdate) {
						@SuppressWarnings("unused")
						Message msg = new Message();
						Thread.sleep(THREAD_TIME);
						mHandler.sendEmptyMessage(MSG_SEELP);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	//	private void updateThread() {
	//		new Thread(new Runnable() {
	//			@Override
	//			public void run() {
	//				try {
	//					url = new URL(Config.APK_FILE_VERSION_PATH);
	//					HttpURLConnection Connection = (HttpURLConnection) url
	//							.openConnection();
	//					Connection.connect();
	//					InputStream is = Connection.getInputStream();
	//					HashMap<String, String> map = ParseXmlService.parseXml(is);
	//					if (null != map) {
	//						Message msg = new Message();
	//						msg.obj = map;
	//						msg.what = VersionInfoFragment.XML_DOWN;
	//						mHandler.sendMessage(msg);
	//					}
	//				} catch (MalformedURLException e) {
	//					e.printStackTrace();
	//				} catch (IOException e) {
	//					e.printStackTrace();
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		}).start();
	//	}
	private void updateThread(){

		Uri.Builder builder = Uri.parse(Config.URL_APP_GETLASTVERSION)
				.buildUpon();
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.i(TAG, "response = " + response);
				Json json = JSON.parseObject(response.toString(),
						Json.class);
				if (json.isSuccess()) {
					AppApkVersion apkVersion = JSON.parseObject(json.getObj().toString(),
							AppApkVersion.class);
					Message msg = new Message();
					msg.obj = apkVersion;
					msg.what = VersionInfoFragment.XML_DOWN;
					mHandler.sendMessage(msg);
				} else {
					isError(json.getMessage());
				}
			}
		}, new VolleyErrorAdapter(this) {
			@Override
			protected void onOccurError(VolleyError error) {
				super.onOccurError(error);
			}
		});
		request.setTag(TAG);
		mQueue.add(request);
		mQueue.start();
	}

	private void isError(String msg) {
		if (msg != null)
			MainActivity.showToast(this, msg);
		else
			MainActivity.showToast(this,
					R.string.publish_version_info_publish_error);

	}

	/**
	 * ��ʼ��ui
	 */
	public void perpareUI() {

		setStatusColor(getResources().getColor(R.color.immersionColor));

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Fragment fragment = (Fragment) pageAdapter.instantiateItem(
						container, checkedId);
				pageAdapter.setPrimaryItem(container, 0, fragment);
				pageAdapter.finishUpdate(container);

				bottomBtn[location].setTextColor(getResources().getColor(
						R.color.text_gray));
				switch (checkedId) {
				case R.id.radio_home:
					location = 0;
					setMenu(false);
					break;
				case R.id.radio_collection:
					location = 1;
					setMenu(false);
					break;
				case R.id.radio_find:
					location = 2;
					setMenu(false);
					break;
				case R.id.radio_me:
					location = 3;
					setMenu(true);
					break;
				}
				setActionBar(location);
				bottomBtn[location].setTextColor(getResources().getColor(
						R.color.base_blue));
			}
		});

		SharedPreferences sharedPre = getSharedPreferences(
				Config.SHAREPREFERENCE_SETTING, MODE_PRIVATE);
		location = sharedPre.getInt(Config.SETTING_HOME_INDEX, 0);
		actionBar.setDisplayShowTitleEnabled(true);
		setActionBar(location);
		bottomBtn[location].setChecked(true);
		bottomBtn[location].setTextColor(getResources().getColor(
				R.color.base_blue));
		Fragment fragment = null;
		switch (location) {
		case 0:
			fragment = (Fragment) pageAdapter.instantiateItem(container,
					R.id.radio_home);
			break;
		case 1:
			fragment = (Fragment) pageAdapter.instantiateItem(container,
					R.id.radio_collection);
			break;
		case 2:
			fragment = (Fragment) pageAdapter.instantiateItem(container,
					R.id.radio_find);
			break;
		case 3:
			fragment = (Fragment) pageAdapter.instantiateItem(container,
					R.id.radio_me);
			break;
		}
		pageAdapter.setPrimaryItem(container, 0, fragment);
		pageAdapter.finishUpdate(container);
	}

	private void setActionBar(int pos) {
		if (actionBar == null)
			return;
		switch (pos) {
		case 0:
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setHomeButtonEnabled(true);
			if (Tool.GetLONGSharedPreferences(getApplicationContext(),
					LOCATION_DISTRICT, null) != null) {
				actionBar.setTitle(Tool.GetLONGSharedPreferences(
						getApplicationContext(), LOCATION_DISTRICT, null));
			} else {
				actionBar.setTitle(R.string.action_located);
			}

			break;
		case 1:
		case 2:
		case 3:
			actionBar.setHomeButtonEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setTitle(R.string.app_name);
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		this.menu = menu;
		if (location == 3) {
			setMenu(true);
		} else {
			setMenu(false);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_publish_setting:
			if (mNavigationDrawerFragment.isDrawerOpen()) {
				mNavigationDrawerFragment.colseDrawer();
			} else {
				mNavigationDrawerFragment.openDrawer();
			}
			return true;
		case R.id.action_publish_part:
			if (checkUserLogin(this) && checkUserVerify(this)) {
				startActivity(new Intent(this, PublishPartActivity.class));
			}
			break;
		case R.id.action_publish_secondHand:
			if (checkUserLogin(this) && checkUserVerify(this)) {
				startActivity(new Intent(this, PublishSecondActivity.class));
			}
			break;
		case R.id.action_publish_history:
			if (checkUserLogin(this) && checkUserVerify(this))
				startActivity(new Intent(getApplicationContext(),
						HistoryActivity.class));
			break;
		case android.R.id.home:
			// startActivity(new Intent(getApplicationContext(),BCLocationActivity.class));
			//			 ConfirmDialog dialog = new ConfirmDialog(
			//					 ConfirmDialog.CONFIRM_STYLE_BOTTOM,
			//					 getString(R.string.dialog_location),
			//					 getString(R.string.dialog_location_au),
			//					 getString(R.string.dialog_location_sure),
			//					 getString(R.string.dialog_location_cancle));
			//			 dialog.setOnclickListener(new DialogInterface.OnClickListener() {
			//
			//				 @Override
			//				 public void onClick(DialogInterface dialog, int which) {
			//					 switch (which) {
			//					 case DialogInterface.BUTTON_POSITIVE:
			//						 chooseArea();
			//						 break;
			//					 case DialogInterface.BUTTON_NEGATIVE:
			//						 AU_location = false;
			//						 mLocationClient.start();
			//						 mLocationClient.requestLocation();
			//						 break;
			//					 }
			//				 }

			//			 });
			//			 dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * ������ز���
	 */
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setIsNeedAddress(true);// ���صĶ�λ���������ַ��Ϣ
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(24 * 60 * 60 * 1000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		mLocationClient.setLocOption(option);
	}

	/**
	 * ʵ��ʵλ�ص�����
	 */

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			/*
			 * StringBuffer sb = new StringBuffer(256); sb.append("��ǰʱ�� : ");
			 * sb.append(location.getTime()); sb.append("\n������ : ");
			 * sb.append(location.getLocType()); sb.append("\nγ�� : ");
			 * sb.append(location.getLatitude()); sb.append("\n���� : ");
			 * sb.append(location.getLongitude()); sb.append("\n�뾶 : ");
			 * sb.append(location.getRadius()); if (location.getLocType() ==
			 * BDLocation.TypeGpsLocation) { sb.append("\n�ٶ� : ");
			 * sb.append(location.getSpeed()); sb.append("\n������ : ");
			 * sb.append(location.getSatelliteNumber()); } else if
			 * (location.getLocType() == BDLocation.TypeNetWorkLocation) {
			 * sb.append("\n��ַ : "); sb.append(location.getAddrStr()); }
			 */
			locationAddress = location.getDistrict();
			city = location.getCity();
			distract = locationAddress;
			Intent intent = new Intent();
			intent.setAction(LOCATION_CITY_ACTION);
			intent.putExtra(LOCATION_CITY, city);
			intent.putExtra(LOCATION_DISTRACT, distract);
			MainActivity.this.sendBroadcast(intent);
			if (MainActivity.this.location == 0 && AU_location == false
					&& locationAddress != null) {
				actionBar.setTitle(locationAddress);
				Tool.PutSharePreferences(getApplicationContext(),
						LOCATION_DISTRICT, locationAddress);
			} else {
				if (Tool.GetLONGSharedPreferences(getApplicationContext(),
						LOCATION_DISTRICT, null) != null
						&& MainActivity.this.location == 0) {
					// Tool.ToolToast(getApplicationContext(),
					// getResources().getString(R.string.main_no_net).toString());
					actionBar.setTitle(Tool.GetLONGSharedPreferences(
							getApplicationContext(), LOCATION_DISTRICT, null));
				}
			}
			Log.d(TAG, "onReceiveLocation " + locationAddress);
		}

	}

	@Override
	protected void onDestroy() {
		SharedPreferences.Editor editor = getSharedPreferences(
				Config.SHAREPREFERENCE_SETTING, MODE_PRIVATE).edit();
		editor.putInt(Config.SETTING_HOME_INDEX, location);
		editor.commit();
		// mLocationClient.stop();
		PushManager.stopWork(this);

		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		if (!confirm) {
			confirm = true;
			Toast.makeText(this, R.string.exit_remind, Toast.LENGTH_SHORT)
			.show();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					confirm = false;
				}
			}, 2000);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		// FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager
		// .beginTransaction()
		// .replace(R.id.container,
		// PlaceholderFragment.newInstance(position + 1)).commit();
	}

	private void setMenu(boolean showSetting) {
		if (menu == null)
			return;
		if (showSetting) {
			menu.findItem(R.id.action_menu_main).setVisible(false);
			// menu.findItem(R.id.action_publish_part).setVisible(false);
			// menu.findItem(R.id.action_publish_history).setVisible(false);
			menu.findItem(R.id.action_publish_setting).setVisible(true);
		} else {
			menu.findItem(R.id.action_menu_main).setVisible(true);
			// menu.findItem(R.id.action_publish_part).setVisible(true);
			// menu.findItem(R.id.action_publish_history).setVisible(true);
			menu.findItem(R.id.action_publish_setting).setVisible(false);
		}

	}

	/**
	 * ѡ�����
	 */
	@SuppressWarnings("unused")
	private void chooseArea() {
		Log.d(TAG, "����chooseArea()");
		Intent intent = new Intent(getApplicationContext(),
				ChooseListActivity.class);
		intent.putExtra(ChooseListActivity.KEY_DAO_CLASS, AreaDAO.class);
		//intent.setAction(MainActivity_HAND_MOVEMENT);
		// intent.setData(data);
		// MainActivity.this.sendBroadcast(intent);
		//startActivity(intent);
		startActivityForResult(intent, MAIN_REQUEST_CHOOSE_AREA);
	}

}