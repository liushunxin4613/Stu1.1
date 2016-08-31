package com.fengyang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

/**
 * 
 * @author 蒋红 关于我们界面
 */

public class AboutActivity extends ImmersionActivity implements
		OnItemClickListener, OnItemLongClickListener {

	private ListView list_us;
	private ArrayList<Map<String, Object>> dates;
	private Map<String, Object> map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		
		
		list_us = (ListView) findViewById(R.id.list_us);
		getDates();

		SimpleAdapter adapter = new SimpleAdapter(this, dates,
				R.layout.item_about, new String[] { "img", "text" }, new int[] {
						R.id.image_about, R.id.text_about });

		list_us.setAdapter(adapter);

		list_us.setOnItemClickListener(this);
		list_us.setOnItemLongClickListener(this);

	}

	public void getDates() {

		dates = new ArrayList<Map<String, Object>>();

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.ic_take_phone);
		map.put("text", getString(R.string.about_tel));
		dates.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.ic_qq);
		map.put("text", getString(R.string.about_qq));
		dates.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.ic_sina_weibo);
		map.put("text", getString(R.string.about_weibo));
		dates.add(map);

		map = new HashMap<String, Object>();
		map.put("img", R.drawable.ic_weixin);
		map.put("text", getString(R.string.about_weixin));
		dates.add(map);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 点击listview条目触发的事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		// 拨打客服电话
		case 0:
			Intent intent = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:07198024654"));
			startActivity(intent);
			break;
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		switch (arg2) {
		// 复制QQ
		case 1:
			clipboardManager.setPrimaryClip(ClipData.newPlainText(
					ClipDescription.MIMETYPE_TEXT_PLAIN, "307405193"));
			Toast.makeText(getApplicationContext(), R.string.about_clipboard,
					Toast.LENGTH_SHORT).show();
			break;
		// 复制新浪微博
		case 2:
			clipboardManager
					.setPrimaryClip(ClipData.newPlainText(
							ClipDescription.MIMETYPE_TEXT_PLAIN,
							"13972478340@139.com"));
			Toast.makeText(getApplicationContext(), R.string.about_clipboard,
					Toast.LENGTH_SHORT).show();
			break;
		// 复制微信
		case 3:
			clipboardManager.setPrimaryClip(ClipData.newPlainText(
					ClipDescription.MIMETYPE_TEXT_PLAIN, "jianyeweish"));
			Toast.makeText(getApplicationContext(), R.string.about_clipboard,
					Toast.LENGTH_SHORT).show();
			break;

		}
		return false;
	}

}
