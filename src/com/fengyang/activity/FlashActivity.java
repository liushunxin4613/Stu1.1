package com.fengyang.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.fengyang.service.InitUserService;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.tool.Tool;
import com.fengyang.util.ui.KitKatUtils;

/**
 * 闪屏界面
 * 
 * @author HeJie
 */
public class FlashActivity extends Activity {

	protected static final String TAG = "FlashActivity";
	/**
	 * 该Activity最小的延时时间
	 */
	private static int DEFAULT_DELAY = 2000;
	/**
	 * 时间标志，是否已经过了默认的延时时间
	 */
	private static final String IS_FIRST = "is_first";
	private boolean isTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		KitKatUtils.setStatusBar(this, true);
		setContentView(R.layout.activity_flash);

		IntentFilter filter = new IntentFilter();
		filter.addAction(InitUserService.ACTION_LOGINED);
		filter.addAction(InitUserService.ACTION_LOGIN_FAILURE);
		filter.addAction(InitUserService.ACTION_LOGIN_ERROR);
		registerReceiver(receiver, filter);
		
		isTime = false;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				isTime = true;
			}
		}, DEFAULT_DELAY);
		if (!Tool
				.GetSharedPreferences(getApplicationContext(), IS_FIRST, false)) {
			finishActivity(WelcomeActivity.class);
			Tool.PutSharedPreferences(getApplicationContext(), IS_FIRST, true);
		} else {
			finishActivity(MainActivity.class);
			Intent intent = new Intent(this, InitUserService.class);
			intent.putExtra(InitUserService.KEY_START_SERVICE_FOR,
					InitUserService.TASK_LOGIN);
			startService(intent);
		}

		
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "action = " + intent.getAction());
			if (intent.getAction().equals(InitUserService.ACTION_LOGINED)) {
				finishActivity(MainActivity.class);
			} else if (intent.getAction().equals(
					InitUserService.ACTION_LOGIN_FAILURE)) {
				finishActivity(LoginActivity.class);
			} else if (intent.getAction().equals(
					InitUserService.ACTION_LOGIN_ERROR)) {
				finishActivity(LoginActivity.class);
			}
		}
	};

	/**
	 * 结束Activity 并转跳到指定的Activity
	 * 
	 * @param clazzTo
	 *            转跳的目标Activity
	 */
	public void finishActivity(final Class<?> clazzTo) {
		if (!isTime) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					finishActivity(clazzTo);
				}
			}, 500);
		} else {
			startActivity(new Intent(this, clazzTo));
			super.finish();
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	};

}
