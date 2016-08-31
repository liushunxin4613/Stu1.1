package com.fengyang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fengyang.stu.MainActivity;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

public class ShareActivity extends ImmersionActivity {
	String share;
    private Button shareButton;
    private TextView mTextView;
    private EditText mEditText;
    private String beforStr;
    private String afterStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
	    shareButton = (Button) findViewById(R.id.sharebutton);
		mTextView = (TextView) findViewById(R.id.share_tv);
		mEditText = (EditText) findViewById(R.id.sharecontent);
		beforStr = getString(R.string.share_tv_before)+" ";
		afterStr = " "+getString(R.string.share_tv_after);
		String apkUrl;
		if (MainActivity.apkUrl == null) {
			apkUrl = getString(R.string.app_download_site);
		}else {
			apkUrl = MainActivity.apkUrl;
		}
		mTextView.setText(beforStr+apkUrl+afterStr);
		
		//������
		mTextView.setAutoLinkMask(Linkify.WEB_URLS); 
		
		mTextView.setMovementMethod(LinkMovementMethod.getInstance());
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				  Intent intent=new Intent(Intent.ACTION_SEND);
			      intent.setType("text/plain");
			      intent.putExtra(Intent.EXTRA_SUBJECT, "ssnskk");
			      intent.putExtra(Intent.EXTRA_TEXT,mTextView.getText().toString()+'\n'+'\n'+mEditText.getText().toString());
			      startActivity(Intent.createChooser(intent, getTitle()));
			}
		});
		
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}
	public void setActionBar() {
		
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		LayoutInflater inflator = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// ���parentΪnull ��ôxml�ļ���FrameLayout���ֲ�����ʧЧ
		View v = inflator.inflate(R.layout.actionbar_center_title_layout,
				new LinearLayout(getApplicationContext()), false);
		TextView titleView = (TextView) v.findViewById(R.id.actionBar_titel);
		titleView.setText(R.string.sharebutton);

		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setCustomView(v);
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
 
}