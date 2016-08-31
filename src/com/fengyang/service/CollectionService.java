package com.fengyang.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.activity.LoginActivity;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.db.CollectDAO;
import com.fengyang.entity.AppPartCollection;
import com.fengyang.entity.AppSecondCollection;
import com.fengyang.entity.User;
import com.fengyang.fragment.PartCollectFragment;
import com.fengyang.fragment.SecondCollectFragment;
import com.fengyang.model.Json;
import com.fengyang.model.PartCollection;
import com.fengyang.model.SecondCollection;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.volleyTool.FixedJsonRequest;

/**
 * 收藏信息的Service 主要功能有向服务器同步兼职二手信息，添加收藏信息，取消收藏信息
 * 
 * @author HeJie
 *
 */
public class CollectionService extends Service {

	private static final String TAG = "SyncCollectService";
	private static final String TAG_SYNC_PART = "SyncPartCollect";
	private static final String TAG_SYNC_SECOND = "SyncSecondCollect";
	private static final String TAG_PART_UNCOLLECT = "PartUncollect";
	private static final String TAG_SECOND_UNCOLLECT = "SecondUncollect";
	/**
	 * Action 更新兼职数据完成
	 */
	public static final String ACTION_SYNC_PART_COLLECT_FINISH = "SyncPartCollectFinish";
	/**
	 * Action 取消收藏兼职失败
	 */
	public static final String ACTION_PART_DELETE_COLLECT_FAILURE = "PartDeleteCollectFailure";
	/**
	 * Action 更新二手物品数据完成
	 */
	public static final String ACTION_SYNC_SECOND_COLLECT_FINISH = "SyncSecondCollectFinish";
	/**
	 * Action 取消收藏二手物品失败
	 */
	public static final String ACTION_SECOND_DELETE_COLLECT_FAILURE = "SecondDeleteCollectFailure";
	/**
	 * Action 取消网络请求
	 */
	public static final String ACTION_CANCEL_REQUEST = "CancelRequest";

	/**
	 * 启动Service的功能请求类型的Key
	 */
	public static final String KEY_START_SERVICE_FOR = "StartServiceFor";
	/**
	 * 停止请求类型的Key
	 */
	public static final String KEY_STOP_SERVICE_FOR = "StopServiceFor";
	/**
	 * 兼职信息的Key
	 */
	public static final String KEY_PART_TIME_ID = "PartTimeId";
	/**
	 * 二手信息的Key
	 */
	public static final String KEY_SECOND_HAND_ID = "SecondHandId";

	/**
	 * 任务编号 启动Service以同步兼职收藏数据
	 */
	public static final int TASK_SYNC_PART_COLLECT = 0x0001;
	/**
	 * 任务编号 启动Service以同步二手物品收藏数据
	 */
	public static final int TASK_SYNC_SECOND_COLLECT = 0x0002;
	/**
	 * 任务编号 取消收藏兼职信息
	 */
	public static final int TASK_PART_DELETE_COLLECT = 0x0004;
	/**
	 * 任务编号 取消收藏二手物品信息
	 */
	public static final int TASK_SECOND_DELETE_COLLECT = 0x0008;

	/**
	 * 任务完成进度
	 */
	private int finishedTask = 0;

	private RequestQueue mQueue;

	private CollectDAO dao;

	private Integer partPage;
	private Integer partCount;
	private Integer partSyncCount;
	private Integer partRetry;

	private Integer secondPage;
	private Integer secondCount;
	private Integer secondSyncCount;
	private Integer secondRetry;

	private Integer uId;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_CANCEL_REQUEST)) {
				if (mQueue == null)
					return;
				int requestCode = intent.getIntExtra(KEY_STOP_SERVICE_FOR, 0);
				if ((requestCode & TASK_SYNC_PART_COLLECT) == TASK_SYNC_PART_COLLECT) {
					mQueue.cancelAll(TAG_SYNC_PART);
				}
				if ((requestCode & TASK_SYNC_SECOND_COLLECT) == TASK_SYNC_SECOND_COLLECT) {
					mQueue.cancelAll(TAG_SYNC_SECOND);
				}
				if ((requestCode & TASK_PART_DELETE_COLLECT) == TASK_PART_DELETE_COLLECT) {
					mQueue.cancelAll(TAG_PART_UNCOLLECT);
				}
				if ((requestCode & TASK_SYNC_SECOND_COLLECT) == TASK_SYNC_SECOND_COLLECT) {
					mQueue.cancelAll(TAG_SECOND_UNCOLLECT);
				}
			}
		}
	};

	@Override
	public void onCreate() {
		mQueue = Volley.newRequestQueue(getApplicationContext());
		StuApplication application = (StuApplication) getApplication();
		uId = application.getUser().getId();
		if (uId == null) {
			startActivity(new Intent(getApplicationContext(),
					LoginActivity.class));
		}
		dao = new CollectDAO(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_CANCEL_REQUEST);
		registerReceiver(receiver, filter);
		finishedTask = 0;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null) {
			stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}
		int requestCode = intent.getIntExtra(KEY_START_SERVICE_FOR, 0);
		if (requestCode == 0) {
			stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}
		Log.i(TAG, "onStartCommand requestCode = " + requestCode);
		if ((requestCode & TASK_SYNC_PART_COLLECT) == TASK_SYNC_PART_COLLECT) {
			finishedTask |= TASK_SYNC_PART_COLLECT;
			partRetry = 10;
			syncPartCollection();
		}
		if ((requestCode & TASK_SYNC_SECOND_COLLECT) == TASK_SYNC_SECOND_COLLECT) {
			finishedTask |= TASK_SYNC_SECOND_COLLECT;
			secondRetry = 10;
			syncSecondCollection();
		}
		if ((requestCode & TASK_PART_DELETE_COLLECT) == TASK_PART_DELETE_COLLECT) {
			finishedTask |= TASK_PART_DELETE_COLLECT;
			int pId = intent.getIntExtra(KEY_PART_TIME_ID, -1);
			uncollectPart(pId);
		}
		if ((requestCode & TASK_SECOND_DELETE_COLLECT) == TASK_SECOND_DELETE_COLLECT) {
			finishedTask |= TASK_SECOND_DELETE_COLLECT;
			int sId = intent.getIntExtra(KEY_SECOND_HAND_ID, -1);
			uncollectSecond(sId);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 同步兼职收藏数据
	 */
	private void syncPartCollection() {
		partSyncCount = 0;
		Uri.Builder builder = Uri.parse(Config.URL_GET_PART_COLLECT_COUNT)
				.buildUpon();
		builder.appendQueryParameter("id", uId.toString());
		FixedJsonRequest request = new FixedJsonRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							partCount = (Integer) json.getObj();
							partPage = 1;
							dao.deletePartAll(uId);
							getPartPage(partPage);
						} else {
							retrySyncPart();
						}
					}
				}, new VolleyErrorAdapter(this) {
				});
		request.setTag(TAG_SYNC_PART);
		mQueue.add(request);
		mQueue.start();
	}

	/**
	 * 检查重复次数，如果重复次数小于partRetry 延时60s再检查更新
	 */
	private void retrySyncPart() {
		if (--partRetry > 0) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					syncPartCollection();
				}
			}, 60 * 1000);
		}
	}

	private synchronized void getPartPage(Integer page) {
		Uri.Builder builder = Uri.parse(Config.URL_GET_PART_COLLECT_LIST)
				.buildUpon();
		builder.appendQueryParameter("id", uId.toString());
		builder.appendQueryParameter("page", page.toString());
		builder.appendQueryParameter("pageSize", "50");
		FixedJsonRequest request = new FixedJsonRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						Log.d(TAG, "response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							List<PartCollection> data = new ArrayList<PartCollection>();
							@SuppressWarnings("unchecked")
							List<com.alibaba.fastjson.JSONObject> list = JSON
									.parseObject(json.getObj().toString(),
											List.class);
							for (com.alibaba.fastjson.JSONObject jsonObject : list) {
								AppPartCollection part = JSON.parseObject(
										jsonObject.toJSONString(),
										AppPartCollection.class);
								data.add(PartCollection.Parse(part));
							}
							int insertCount = dao.insertPart(data);
							if (insertCount == data.size()) { // 如果本页存储成功，则获取下一页
								partSyncCount += insertCount;
								if (partSyncCount < partCount) {
									getPartPage(++partPage);
								} else {
									sendBroadcast(new Intent(
											ACTION_SYNC_PART_COLLECT_FINISH));
									checkStop(TASK_SYNC_PART_COLLECT);
								}
							} else { // 如果本页存储失败，则继续获取本页信息
								retrySyncPart();
							}
						} else {
							retrySyncPart();
						}
					}
				}, new VolleyErrorAdapter(this) {
				});
		request.setTag(TAG_SYNC_PART);
		mQueue.add(request);
		mQueue.start();
	}

	/**
	 * 同步二手收藏数据
	 */
	private void syncSecondCollection() {
		secondSyncCount = 0;
		Uri.Builder builder = Uri.parse(Config.URL_GET_SECOND_COLLECT_COUNT)
				.buildUpon();
		builder.appendQueryParameter("id", uId.toString());
		FixedJsonRequest request = new FixedJsonRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, "response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							secondCount = (Integer) json.getObj();
							secondPage = 1;
							dao.deleteSecondAll(uId);
							getSecondPage(secondPage);
						} else {
							retrySyncSecond();
						}
					}

				}, new VolleyErrorAdapter(this) {

				});
		request.setTag(TAG_SYNC_SECOND);
		mQueue.add(request);
		mQueue.start();
	}

	/**
	 * 检查重复次数，如果重复次数小于secondRetry 延时60s再检查更新
	 */
	private void retrySyncSecond() {
		if (--secondRetry > 0) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					syncSecondCollection();
				}
			}, 60 * 1000);
		}
	}

	/**
	 * 分页获取二手收藏信息
	 * 
	 * @param page
	 *            页码
	 */
	private synchronized void getSecondPage(Integer page) {
		Uri.Builder builder = Uri.parse(Config.URL_GET_SECOND_COLLECT_LIST)
				.buildUpon();
		builder.appendQueryParameter("id", uId.toString());
		builder.appendQueryParameter("page", page.toString());
		builder.appendQueryParameter("pageSize", "50");
		FixedJsonRequest request = new FixedJsonRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						Log.d(TAG, "response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							List<SecondCollection> data = new ArrayList<SecondCollection>();
							@SuppressWarnings("unchecked")
							List<com.alibaba.fastjson.JSONObject> list = JSON
									.parseObject(json.getObj().toString(),
											List.class);
							for (com.alibaba.fastjson.JSONObject jsonObject : list) {
								AppSecondCollection second = JSON.parseObject(
										jsonObject.toJSONString(),
										AppSecondCollection.class);
								data.add(SecondCollection.Parse(second));
							}
							int insertCount = dao.insertSecond(data);
							if (insertCount == data.size()) { // 如果本页存储成功，则获取下一页
								secondSyncCount += insertCount;
								if (secondSyncCount < secondCount) {
									getPartPage(++secondPage);
								} else {
									sendBroadcast(new Intent(
											ACTION_SYNC_SECOND_COLLECT_FINISH));
									checkStop(TASK_SYNC_SECOND_COLLECT);
								}
							} else { // 如果本页存储失败，则继续获取本页信息
								retrySyncSecond();
							}
						} else {
							retrySyncSecond();
						}
					}
				}, new VolleyErrorAdapter(this) {
				});
		request.setTag(TAG_SYNC_PART);
		mQueue.add(request);
		mQueue.start();
	}

	/**
	 * 取消收藏的兼职
	 * 
	 * @param partTimeId
	 */
	private void uncollectPart(final int partTimeId) {
		Uri.Builder builder = Uri.parse(Config.URL_PART_UNCOLLECT).buildUpon();
		builder.appendQueryParameter("id", String.valueOf(uId));
		builder.appendQueryParameter("pId", String.valueOf(partTimeId));
		Log.i(TAG, "uncollectPart URL = " + builder.toString());
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							CollectDAO dao = new CollectDAO(
									getApplicationContext());
							StuApplication application = (StuApplication) getApplication();
							User user = application.getUser();
							dao.deletePart(user.getId(), partTimeId);
							Intent intent = new Intent(
									PartCollectFragment.ACTION_PART_COLLECT_DELETE);
							intent.putExtra(PartCollectFragment.KEY_UPDATE_PID,
									partTimeId);
							sendBroadcast(intent);
							checkStop(TASK_PART_DELETE_COLLECT);
						} else {
							sendBroadcast(new Intent(
									ACTION_PART_DELETE_COLLECT_FAILURE));
							Toast.makeText(getApplicationContext(),
									R.string.detail_second_uncollect_failure,
									Toast.LENGTH_SHORT).show();
							checkStop(TASK_PART_DELETE_COLLECT);
						}
					}
				}, new VolleyErrorAdapter(getApplicationContext()) {
					@Override
					protected void onOccurError(VolleyError error) {
						super.onOccurError(error);
						sendBroadcast(new Intent(
								ACTION_PART_DELETE_COLLECT_FAILURE));
						checkStop(TASK_PART_DELETE_COLLECT);
					}
				});
		request.setTag(TAG_PART_UNCOLLECT);
		mQueue.add(request);
		mQueue.start();
	}

	/**
	 * 取消二手收藏
	 * 
	 * @param sId
	 *            二手物品的id
	 */
	private void uncollectSecond(final int sId) {
		Uri.Builder builder = Uri.parse(Config.URL_SECOND_UNCOLLECT)
				.buildUpon();
		StuApplication application = (StuApplication) getApplication();
		User user = application.getUser();
		builder.appendQueryParameter("id", String.valueOf(user.getId()));
		builder.appendQueryParameter("sId", String.valueOf(sId));
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							CollectDAO dao = new CollectDAO(
									getApplicationContext());
							StuApplication application = (StuApplication) getApplication();
							User user = application.getUser();
							dao.deleteSecond(user.getId(), sId);
							Intent intent = new Intent(
									SecondCollectFragment.ACTION_SECOND_COLLECT_DELETE);
							intent.putExtra(
									SecondCollectFragment.KEY_UPDATE_SID, sId);
							sendBroadcast(intent);
							checkStop(TASK_SECOND_DELETE_COLLECT);
						} else {
							sendBroadcast(new Intent(
									ACTION_SECOND_DELETE_COLLECT_FAILURE));
							Toast.makeText(getApplicationContext(),
									R.string.detail_second_uncollect_failure,
									Toast.LENGTH_SHORT).show();
							checkStop(TASK_SECOND_DELETE_COLLECT);
						}
					}
				}, new VolleyErrorAdapter(getApplicationContext()) {
					@Override
					protected void onOccurError(VolleyError error) {
						super.onOccurError(error);
						sendBroadcast(new Intent(
								ACTION_SECOND_DELETE_COLLECT_FAILURE));
						checkStop(TASK_SECOND_DELETE_COLLECT);
					}
				});
		request.setTag(TAG_SECOND_UNCOLLECT);
		mQueue.add(request);
		mQueue.start();

	}

	@Override
	public void onDestroy() {
		if (mQueue != null) {
			mQueue.cancelAll(TAG_SYNC_PART);
			mQueue.cancelAll(TAG_SYNC_SECOND);
			mQueue.stop();
		}
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	/**
	 * 检查是否停止Service
	 * 
	 * @param taskCode
	 *            任务编号
	 */
	private void checkStop(int taskCode) {
		finishedTask &= ~taskCode;
		if (finishedTask == 0)
			stopSelf();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
