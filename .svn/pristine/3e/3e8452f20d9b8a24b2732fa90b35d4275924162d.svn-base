package com.fengyang.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fengyang.activity.SecondDetailHistoryActivity;
import com.fengyang.adapter.SecondHistoryAdapter;
import com.fengyang.adapter.VolleyErrorAdapter;
import com.fengyang.customLib.DynamicListView;
import com.fengyang.customLib.DynamicListView.LoadMoreListener;
import com.fengyang.customLib.DynamicListView.RefreshListener;
import com.fengyang.db.PublishHistoryDAO;
import com.fengyang.entity.AppSecondHand;
import com.fengyang.model.Json;
import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.stu.StuApplication;
import com.fengyang.util.Config;
import com.fengyang.util.FixedThread;
import com.fengyang.util.OnBackTaskFinishedListener;

public class HistorySecondFragment extends Fragment implements RefreshListener,
		LoadMoreListener, OnItemClickListener {

	private DynamicListView listView;

	private SecondHistoryAdapter adapter;

	private int page = 1;

	private Integer count = 0;

	/**
	 * 已经同步的数据总数
	 */
	private int syncedCount = 0;
	/**
	 * 已经同步的页数
	 */
	private int syncedPage = 1;

	private int uId;

	private PublishHistoryDAO dao;

	private RequestQueue mQueue;

	private MyHandler handler = new MyHandler(this);

	private OnBackTaskFinishedListener onBackTaskFinishedListener;

	private FixedThread thread;

	private static final int MSG_UPDATE_ADAPTER = 1;
	private static final int MSG_LOAD_MORE = 2;
	private static final int MSG_DO_REFRESH = 3;
	private static final String TAG = "historySecondFragment";

	private static final String TAG_GET_COUNT = "getCount";

	private static final String TAG_SYNC = "SyncData";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_history_second,
				container, false);
		listView = (DynamicListView) rootView
				.findViewById(R.id.history_listView_second);

		listView.setOnRefreshListener(this);
		listView.setOnMoreListener(this);
		listView.setOnItemClickListener(this);

		mQueue = Volley.newRequestQueue(getActivity());
		StuApplication application = (StuApplication) getActivity()
				.getApplication();
		uId = application.getUser().getId();
		loadData();

		IntentFilter inf = new IntentFilter();
		inf.addAction(SecondDetailHistoryActivity.SendDetailHISTORY_DELETEACTION);
		getActivity().registerReceiver(receiver, inf);
		return rootView;
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(android.content.Context context, Intent intent) {
			if (SecondDetailHistoryActivity.SendDetailHISTORY_DELETEACTION
					.equals(intent.getAction())) {
				int id = intent
						.getIntExtra(
								SecondDetailHistoryActivity.SendDetailHISTORY_DELETESENDHANDID,
								-1);
				adapter.removeItemById(id);

			}

		};

	};

	private void loadData() {
		thread = new FixedThread(new Runnable() {

			@Override
			public void run() {
				adapter = new SecondHistoryAdapter(getLocalData(),
						getActivity());
				if (!thread.isStop() && !thread.isInterrupted())
					handler.sendEmptyMessage(MSG_UPDATE_ADAPTER);
			}
		});
		thread.start();

	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i(TAG, "onStop");
		mQueue.cancelAll(TAG_GET_COUNT);
		mQueue.cancelAll(TAG_SYNC);
		if (thread != null && !thread.isInterrupted()) {
			Log.i(TAG, "interrupt");
			thread.interrupt();
		}
	}

	private List<AppSecondHand> getLocalData() {
		dao = new PublishHistoryDAO(getActivity());
		page = 1;
		List<AppSecondHand> data = dao.querySecond(uId, page,
				Config.LIST_PAGE_SIZE);
		if (data.size() == 0) {
			handler.sendEmptyMessage(MSG_DO_REFRESH);
			syncHistory();
		}
		if (data.size() < Config.LIST_PAGE_SIZE) {
			listView.setOnMoreListener(null);
		}

		return data;
	}

	/**
	 * 通过网络同步二手物品的发布历史数据
	 */
	private void syncHistory() {
		Uri.Builder builder = Uri.parse(Config.URL_GET_SECOND_COUNT_BY_USER)
				.buildUpon();
		builder.appendQueryParameter("id", String.valueOf(uId));
		builder.appendQueryParameter("isEnable", "true");
		JsonObjectRequest request = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							count = (Integer) json.getObj();
							if (count > 0) {
								syncedCount = 0;
								syncedPage = 1;
								dao.deleteSecondAll();
								listView.setOnMoreListener(HistorySecondFragment.this);
								checkSync();
							} else {
								listView.doneRefresh();
							}
						} else {
							listView.doneRefresh();
							MainActivity.showToast(getActivity(),
									R.string.error_get_data);
						}
					}
				}, new VolleyErrorAdapter(getActivity()) {
					@Override
					protected void onOccurError(VolleyError error) {
						listView.doneRefresh();
						super.onOccurError(error);
					}
				});
		request.setTag(TAG_GET_COUNT);
		mQueue.add(request);
		mQueue.start();
	}

	private void checkSync() {
		if (count > syncedCount) {
			getPage(syncedPage++);
		} else {
			listView.doneRefresh();
			page = 1;
			loadData();
		}
	}

	/**
	 * 分页获取数据
	 * 
	 * @param page
	 */
	private void getPage(int page) {
		Uri.Builder builder = Uri.parse(Config.URL_GET_SECOND_LIST_BY_USER)
				.buildUpon();
		builder.appendQueryParameter("id", String.valueOf(uId));
		builder.appendQueryParameter("sort", "publishTime");
		builder.appendQueryParameter("order", "DESC");
		builder.appendQueryParameter("page", String.valueOf(page));
		builder.appendQueryParameter("pageSize", "50");
		builder.appendQueryParameter("isEnable", "true");
		JsonObjectRequest requestData = new JsonObjectRequest(Method.GET,
				builder.toString(), null, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, " response = " + response);
						Json json = JSON.parseObject(response.toString(),
								Json.class);
						if (json.isSuccess()) {
							List<AppSecondHand> data = new ArrayList<AppSecondHand>();
							@SuppressWarnings("unchecked")
							List<com.alibaba.fastjson.JSONObject> list = JSON
									.parseObject(json.getObj().toString(),
											List.class);
							for (com.alibaba.fastjson.JSONObject jsonObject : list) {
								data.add(JSON.parseObject(
										jsonObject.toJSONString(),
										AppSecondHand.class));
							}
							int insertCount = dao.insertSecond(uId, data);
							syncedCount += insertCount;
							if (insertCount == data.size())
								checkSync();
							else
								syncHistory();
						} else {
							listView.doneRefresh();
							MainActivity.showToast(getActivity(),
									R.string.error_get_data);
						}
					}
				}, new VolleyErrorAdapter(getActivity()) {

					@Override
					protected void onOccurError(VolleyError error) {
						listView.doneRefresh();
						super.onOccurError(error);
					}
				});
		requestData.setTag(TAG_SYNC);
		mQueue.add(requestData);
		mQueue.start();
	}

	static class MyHandler extends Handler {

		WeakReference<HistorySecondFragment> PartRef;

		public MyHandler(HistorySecondFragment partFrament) {
			this.PartRef = new WeakReference<HistorySecondFragment>(partFrament);
		}

		@Override
		public void handleMessage(Message msg) {
			HistorySecondFragment fragment = PartRef.get();
			switch (msg.what) {
			case MSG_UPDATE_ADAPTER:
				fragment.listView.setAdapter(fragment.adapter);
				if (fragment.onBackTaskFinishedListener != null)
					fragment.onBackTaskFinishedListener
							.onBackTaskFinish(fragment);
				break;
			case MSG_LOAD_MORE:

				break;
			case MSG_DO_REFRESH:
				fragment.listView.doRefresh();
				break;
			}
		}
	}

	public OnBackTaskFinishedListener getOnBackTaskFinishedListener() {
		return onBackTaskFinishedListener;
	}

	public void setOnBackTaskFinishedListener(
			OnBackTaskFinishedListener onBackTaskFinishedListener) {
		this.onBackTaskFinishedListener = onBackTaskFinishedListener;
	}

	@Override
	public boolean onRefresh(DynamicListView dynamicListView) {
		syncHistory();
		return false;
	}

	@Override
	public boolean onLoadMore(DynamicListView dynamicListView) {
		List<AppSecondHand> data = dao.querySecond(uId, ++page,
				Config.LIST_PAGE_SIZE);
		adapter.addItems(data);
		if (data.size() < Config.LIST_PAGE_SIZE)
			listView.setOnMoreListener(null);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(),
				SecondDetailHistoryActivity.class);
		intent.putExtra(SecondDetailHistoryActivity.KEY_SECOND_HAND_ID,
				(int) id);
		// Tool.ToolToast(getActivity(),id+"****");
		startActivity(intent);
	}

	@Override
	public void onCancelRefresh(DynamicListView dynamicListView) {
		if (mQueue != null) {
			mQueue.cancelAll(TAG_GET_COUNT);
			mQueue.cancelAll(TAG_SYNC);
		}
	}

	@Override
	public void onCancelLoadMore(DynamicListView dynamicListView) {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unregisterReceiver(receiver);
	}
}
