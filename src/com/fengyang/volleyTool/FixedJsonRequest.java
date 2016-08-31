package com.fengyang.volleyTool;

import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * 修改过的JsonObjectRequest 添加设置超时的功能
 * 
 * @author HeJie
 *
 */
public class FixedJsonRequest extends JsonObjectRequest {

	/**
	 * 默认的超时时长为 15s
	 */
	private int timeOut = 15000;

	public FixedJsonRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		init();
	}

	public FixedJsonRequest(String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(url, jsonRequest, listener, errorListener);
		init();
	}

	protected void init() {
		setRetryPolicy(new DefaultRetryPolicy(timeOut,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	/**
	 * 获取超时时间
	 * 
	 * @return
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * 设置超时时间
	 * 
	 * @param timeOut
	 *            超时时长，毫秒
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
		setRetryPolicy(new DefaultRetryPolicy(timeOut,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	/**
	 * 设置超时时间
	 * 
	 * @param timeOut
	 *            超时时长，毫秒
	 * @param retries
	 *            重试次数
	 */
	public void setTimeOut(int timeOut, int retries) {
		this.timeOut = timeOut;
		setRetryPolicy(new DefaultRetryPolicy(timeOut, retries,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}
}
