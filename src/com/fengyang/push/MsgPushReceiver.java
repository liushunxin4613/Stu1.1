/*
 * @Title TestPushReceiver.java
 * @Copyright Copyright 2010-2015 Careland Software Co,.Ltd All Rights Reserved.
 * @author 何杰
 * @date 2015年3月26日 下午2:36:27
 * @version 1.0
 */
package com.fengyang.push;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.fengyang.activity.PartDetailActivity;
import com.fengyang.activity.SecondDetailActivity;
import com.fengyang.model.PushMsgType;
import com.fengyang.service.InitUserService;
import com.fengyang.stu.MainActivity;
import com.fengyang.util.push.PushUtil;

/**
 * Push消息处理receiver。<br/>
 * 请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值； onMessage用来接收透传消息；
 * onSetTags、onDelTags、onListTags是tag相关操作的回调；<br/>
 * onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调<br/>
 * <br/>
 * 
 * 返回值中的errorCode，解释如下： <br/>
 * 0 - Success <br/>
 * 10001 - Network Problem <br/>
 * 30600 - Internal Server Error <br/>
 * 30601 - Method Not Allowed <br/>
 * 30602 - Request Params Not Valid <br/>
 * 30603 - Authentication Failed <br/>
 * 30604 - Quota Use Up Payment Required <br/>
 * 30605 - Data Required Not Found <br/>
 * 30606 - Request Time Expires Timeout <br/>
 * 30607 - Channel Token Timeout <br/>
 * 30608 - Bind Relation Not Found <br/>
 * 30609 - Bind Number Too Many <br/>
 * 
 * @author 何杰
 */
public class MsgPushReceiver extends FrontiaPushMessageReceiver {

	private static final String TAG = "MsgPushReceiver";

	/**
	 * 当调用PushManager.startWork()之后,会向服务器发送绑定请求，当回应绑定结果时调用
	 */
	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);
		if (errorCode == 0) {
			PushUtil.setBind(context, appid, userId, channelId);
			Intent service = new Intent(context, InitUserService.class);
			service.putExtra(InitUserService.KEY_START_SERVICE_FOR,
					InitUserService.TASK_BIND_PUSH_CHANNEL_ID);
			context.startService(service);
		}
	}

	/**
	 * delTags()的回调函数
	 */
	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onDelTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);
	}

	/**
	 * listTags()的回调函数
	 */
	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
			String requestId) {
		String responseString = "onListTags errorCode=" + errorCode + " tags="
				+ tags;
		Log.d(TAG, responseString);

	}

	/**
	 * 当接受到透传消息时调用
	 */
	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		String messageString = "透传消息 message=\"" + message
				+ "\" customContentString=" + customContentString;
		Log.d(TAG, messageString);

	}

	/**
	 * 当点击Notification消息时调用
	 */
	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString) {
		String notifyString = "通知点击 title=\"" + title + "\" description=\""
				+ description + "\" customContent=" + customContentString;
		Log.d(TAG, notifyString);
		PushMsgType pushMsg = JSON.parseObject(customContentString,
				PushMsgType.class);
		if (pushMsg.showDialog) {
			Intent show =new Intent(MainActivity.ACTION_SHOW_DIALOG);
			show.putExtra(MainActivity.EXTRA_DIALOG_TITLE, title);
			show.putExtra(MainActivity.EXTRA_DIALOG_CONTENT, description);
			context.sendBroadcast(show);
		}
		Intent intent = null;
		switch (pushMsg.type) {
		case PushMsgType.TYPE_UPDATE_USER:
			intent = new Intent(context, InitUserService.class);
			intent.putExtra(InitUserService.KEY_START_SERVICE_FOR,
					InitUserService.TASK_UPDATE_USER);
			context.startService(intent);
			break;
		case PushMsgType.TYPE_OPEN_PART_TIME:
			intent = new Intent(context, PartDetailActivity.class);
			intent.putExtra(PartDetailActivity.KEY_PART_ID, pushMsg.arg1);
			context.startActivity(intent);
			break;
		case PushMsgType.TYPE_OPEN_SECOND_HAND:
			intent = new Intent(context, SecondDetailActivity.class);
			intent.putExtra(SecondDetailActivity.KEY_SECOND_HAND_ID,
					pushMsg.arg1);
			context.startActivity(intent);
			break;
		case PushMsgType.TYPE_OPEN_URL:

			break;

		default:
			break;
		}
	}

	/**
	 * setTags()的回调函数
	 */
	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onSetTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);

	}

	/**
	 * PushManager.stopWork()的回调函数
	 */
	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		String responseString = "onUnbind errorCode=" + errorCode
				+ " requestId = " + requestId;
		Log.d(TAG, responseString);
		// PushUtil.setBind(context, false, null, null, null);
	}

}
