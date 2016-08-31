package com.fengyang.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.fengyang.stu.R;

/**
 * Volley����������������ʵ��ErrorListener�ӿ�
 * 
 * @author HeJie
 * @since 2015.1.27
 *
 */
public abstract class VolleyErrorAdapter implements ErrorListener {

	private Context context;
	
	private static final String TAG = "VolleyErrorAdapter";

	public VolleyErrorAdapter(Context context) {
		this.context = context;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		onOccurError(error);
		if (error instanceof NetworkError) {
			onNetworkError(error);
		} else if (error instanceof ServerError) {
			onServerError(error);
		} else if (error instanceof AuthFailureError) {
			onAuthFailureError(error);
		} else if (error instanceof ParseError) {
			onParseError(error);
		} else if (error instanceof NoConnectionError) {
			onNoConnectionError(error);
		} else if (error instanceof TimeoutError) {
			onTimeoutError(error);
		}
	}

	/**
	 * ����������ʱ
	 * @param error
	 */
	protected void onOccurError(VolleyError error){
		Log.i(TAG, "on occur Error");
	}
	
	/**
	 * �������
	 * 
	 * @param error
	 */
	protected void onNetworkError(VolleyError error) {
		Toast.makeText(context, R.string.error_netWork_error,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * �ͻ��˴���
	 * 
	 * @param error
	 */
	protected void onClientError(VolleyError error) {
		Toast.makeText(context, R.string.error_clinet_error, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * ����������
	 * 
	 * @param error
	 */
	protected void onServerError(VolleyError error) {
		Toast.makeText(context, R.string.error_server_error, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * ��֤����
	 * 
	 * @param error
	 */
	protected void onAuthFailureError(VolleyError error) {
		Toast.makeText(context, R.string.error_authentication_error,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * ��������
	 * 
	 * @param error
	 */
	protected void onParseError(VolleyError error) {
		Toast.makeText(context, R.string.error_parse_error, Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * ����������
	 * 
	 * @param error
	 */
	protected void onNoConnectionError(VolleyError error) {
		Toast.makeText(context, R.string.error_no_connnetion_error,
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * ��ʱ����
	 * 
	 * @param error
	 */
	protected void onTimeoutError(VolleyError error) {
		Toast.makeText(context, R.string.error_time_out, Toast.LENGTH_SHORT)
				.show();
	}

}
