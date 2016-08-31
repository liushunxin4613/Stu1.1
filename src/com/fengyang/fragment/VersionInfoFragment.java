package com.fengyang.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.dialog.LoadingDialog;
import com.fengyang.model.Json;
import com.fengyang.entity.AppApkVersion;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.util.Config;
import com.fengyang.util.VersionUtils;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class VersionInfoFragment extends Fragment implements View.OnClickListener{
	/* ���ر���·�� */
	private String mSavePath;
	/* �Ƿ�ȡ������ */
	private boolean stopUpdate = false;
	private boolean closeUpdate = false;
	private TextView versionTv;
	private TextView infoTv;
	private RelativeLayout updateRe;
	private boolean isUpdate;
	private static int versionCode;
	//	private URL url;
	private LoadingDialog dialog;
	private RequestQueue mQueue;
	//private 

	private static final int MSG_LENGTH = 2;

	public static final int MSG_DOWN = 3;

	public static final int MSG_PROGRESS = 4;

	public static final int MSG_SEELP = 5;

	public static final int XML_DOWN = 1;

	public static final int NOTIFICATION_ID=2015042101;

	private boolean xj = true;

	public static final String TAG = "VersionInfoFragment";
	private static VersionInfoFragment fragment;

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
	 * �°��ȡ�汾��Ϣ
	 */
	private AppApkVersion mApkVersion;

	public static Fragment getInstance() {
		if (fragment == null)
			fragment = new VersionInfoFragment();
		return fragment;
	}
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (MainActivity.ACTION_STOP_MOFY.equals(action)) {
				Log.i(TAG, "����");
				stopUpdate = !stopUpdate;
				Log.i(TAG, "stopUpdate= "+stopUpdate);
			}

			if (MainActivity.ACTION_DELETE_MOFY.equals(action)) {
				Log.i(TAG, "ɾ����֪ͨ");
				stopUpdate = true;
				closeUpdate = true;
				nm.cancel(NOTIFICATION_ID);
			}

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_version_info,
				container, false);

		ListenThis(rootView);
		init();

		return rootView;
	}

	private void ListenThis(View rootView) {
		versionTv = (TextView)rootView.findViewById(R.id.version_code);
		infoTv = (TextView) rootView.findViewById(R.id.version_info);
		updateRe = (RelativeLayout) rootView.findViewById(R.id.update);

		versionCode = VersionUtils.getVersionCode(getActivity());

		versionTv.setText(getString(R.string.soft_update_version,
				VersionUtils.getVersionName(getActivity())));

		updateRe.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.update:
			// �����������
			checkUpdate(isUpdate);
			break;
		}

	}

	private void setNotificationManager(int progress,float length,int counts){
		notification.contentView.setProgressBar(R.id.pb,100,progress,false);
		notification.contentView.setTextViewText(R.id.progress_message,VersionUtils.getAppName(getActivity()));
		notification.contentView.setTextViewText(R.id.progress_current,"������"+progress+"%");
		notification.contentView.setTextViewText(R.id.progress_current_M, counts+"M/"+length+"M");

		//���֪ͨ 
		//ȫ�����
		Intent deleteIntent = new Intent(MainActivity.ACTION_DELETE_MOFY); 
		notification.deleteIntent = PendingIntent.getBroadcast(getActivity(), 0, deleteIntent, 0);
		//����֪ͨ
		Intent stopIntent = new Intent(MainActivity.ACTION_STOP_MOFY); 
		notification.contentIntent = PendingIntent.getBroadcast(getActivity(), 0, stopIntent, 0);
	}

	private void notiView(int progress,float length,float counts){
		notification.contentView.setProgressBar(R.id.pb,100,progress,false);  
		notification.contentView.setTextViewText(R.id.progress_message, "���׼�ְ");
		notification.contentView.setTextViewText(R.id.progress_current, "������"+progress+"%");
		notification.contentView.setTextViewText(R.id.progress_current_M, counts+"M/"+length+"M");
		//���õ�ǰֵΪcount  
		nm.notify(NOTIFICATION_ID, notification);//�����Ǹ���notification,���Ǹ��½�����  
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	private void init(){

		mQueue = new Volley().newRequestQueue(getActivity());
		updateThread();

		IntentFilter filter = new IntentFilter();
		filter.addAction(MainActivity.ACTION_STOP_MOFY);
		filter.addAction(MainActivity.ACTION_DELETE_MOFY);
		getActivity().registerReceiver(receiver, filter);

		nm=(NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);  
		notification=new Notification(R.drawable.app_logo,VersionUtils.getAppName(getActivity()),System.currentTimeMillis());
		//���Ա��������FLAG_AUTO_CANCEL
		//�����Ա��������FLAG_NO_CLEAR
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.contentView = new RemoteViews(getActivity().getPackageName(), R.layout.progressdialog_fragment);
	}


	private Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak") 
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_DOWN:
				//��װ�ļ�
				Log.i(TAG,"MSG_DOWN");
				//ȡ��֪ͨ
				nm.cancel(NOTIFICATION_ID);
				stopUpdate = true;
				closeUpdate =true;
				installApk();
				break;
			case MSG_LENGTH:
				//��ȡ�ļ���С
				length = (Float) msg.obj;
				setNotificationManager(0, length, 0);
				Log.i(TAG,"MSG_LENGTH"+length);
				break;
			case MSG_SEELP:
				//ʱ����ʾ
				Log.i(TAG, "ʱ����ʾ");
				msgSend = true;
				break;
			case MSG_PROGRESS:
				//���½�����
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
					infoTv.setText(getResources()
							.getString(R.string.soft_have_update));
					isUpdate = true;
				}else {
					infoTv.setText(getResources().getString(R.string.soft_update_no));
				}
				break;
			}
		};
	};

	private void updateThread(){

		dialog = new LoadingDialog(getString(R.string.tv_version_info_publishing));
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		dialog.show(ft, TAG);

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
					isSuccess();
					Message msg = new Message();
					msg.obj = apkVersion;
					msg.what = VersionInfoFragment.XML_DOWN;
					mHandler.sendMessage(msg);
				} else {
					isError(json.getMessage());
				}
			}
		}, new VolleyErrorAdapter(getActivity()) {
			@Override
			protected void onOccurError(VolleyError error) {
				super.onOccurError(error);
				if (dialog != null)
					dialog.dismiss();
			}
		});
		request.setTag(TAG);
		mQueue.add(request);
		mQueue.start();
	}
	private void isError(String msg) {
		if (dialog != null)
			dialog.dismiss();
		if (msg != null)
			MainActivity.showToast(getActivity(), msg);
		else
			MainActivity.showToast(getActivity(),
					R.string.publish_version_info_publish_error);

	}
	private void isSuccess() {

		if (dialog != null)
			dialog.dismiss();
		//		MainActivity.showToast(getActivity(), R.string.publish_version_info_published);

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
		dialog.show(getActivity().getSupportFragmentManager().beginTransaction(),
				TAG);
	}

	/**
	 * �����������
	 */
	private void checkUpdate(boolean isUpdate) {
		if (isUpdate) {
			// ��ʾ��ʾ�Ի���
			showNoticeDialog();
		} else {
			Toast.makeText(getActivity(), R.string.soft_update_no, Toast.LENGTH_LONG)
			.show();
		}
	}

	/**
	 * ��ʾ�������ضԻ���
	 */
	private void showDownloadDialog() {
		//�����ļ�
		stopUpdate = false;
		closeUpdate =false;
		xj = true;
		new Thread(run).start();
		new Thread(runSeelp).start();
	}

	/**
	 * ��װAPK�ļ�
	 */
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

	/**
	 * �����ļ��߳�
	 */
	private Runnable run = new Runnable() {
		@Override
		public void run() {
			try {
				Message msg ;
				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// ��ô洢����·��
					String sdpath = Environment.getExternalStorageDirectory()
							+ "/";
					mSavePath = sdpath + "download";

					URL url = new URL(Config.URL_APP_DOWNLOADLASTAPK);

					// ��������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// ��ȡ�ļ���С
					int length = conn.getContentLength();

					float lengthF = (length/1024)/1024;
					lengthF = (float)Math.round(lengthF*INT_ONE)/INT_ONE;
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

							if ((numread <= 0)&& xj) {
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

	//	/*��ȡ������Ϣ
	//	 * **/
	//	public void getAppVersionInformation(){
	//       //appVersion = new AppApkVersion();
	//       StringRequest appversionRequest  = new StringRequest(Method.GET, Config.APK_FILE_VERSION_PATH, 
	//    		   new Response.Listener<String>() {
	//
	//		@Override
	//		public void onResponse(String response) {
	//			Log.d(TAG, " response = " + response);
	//			Json json = JSON.parseObject(response.toString(),
	//					Json.class);
	//			//dialog.dismiss();
	//			if (json.isSuccess()) {
	//				appVersion = JSON.parseObject(json.getObj()
	//						.toString(), AppApkVersion.class);
	//				appVersion.getVersionCode();
	//			} else {
	//				MainActivity.showToast(getApplicationContext(),
	//						R.string.error_get_data);
	//			}
	//		}
	//		}
	////			Json json = JSON.parseObject(response.toString(),
	////					Json.class);
	////			Log.i(TAG, "response = " + response);
	////			//Json json = JSON.parseObject(response.toString(),
	////					//Json.class);
	//////			private Integer versionId;
	//////			private Integer versionCode;
	//////			private String versionName;
	//////			private String apkName;
	//////			private Timestamp uploadTime;
	////			if (json.isSuccess()) {
	////				 try { 
	////			            JSONObject jsonObj = new JSONObject(response.toString()).getJSONObject("obj"); 
	////			            appVersion.setApkName(jsonObj.getString("apkName")); 
	////			            appVersion.setVersionCode(Integer.valueOf(jsonObj.getString("versionCode")));
	////			            appVersion.setVersionId(Integer.valueOf(jsonObj.getString("versionId")));
	////			            appVersion.setVersionName(jsonObj.getString("versionName"));
	////			            appVersion.setUploadTime(Timestamp.valueOf(jsonObj.getString("uploadTime")));
	////			        } catch (JSONException e) { 
	////			            System.out.println("Json parse error"); 
	////			            e.printStackTrace(); 
	////			        } 
	////			} else {
	////				Log.d(TAG, "json error");
	////			}
	////		}
	//	}, new Response.ErrorListener() {
	//
	//		@Override
	//		public void onErrorResponse(VolleyError arg0) {
	//			// TODO Auto-generated method stub
	//			
	//		}
	//	});
	//		
	//		mQueue.add(appversionRequest);
	//	}
	//	
}