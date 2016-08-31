package com.fengyang.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.fengyang.stu.R;
import com.fengyang.util.Config;
import com.fengyang.util.ui.ImmersionActivity;

public class PrivacyActivity extends ImmersionActivity {

	private ProgressBar progressBar;
	
	private WebView webViewPrivacy;

	private static final String PRIVACY = "app_privacy.html";

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		
		setContentView(R.layout.activity_privacy);
		progressBar = (ProgressBar) findViewById(R.id.privacy_progress);
		
		webViewPrivacy = (WebView) findViewById(R.id.webView_privacy);
		
		webViewPrivacy.getSettings().setJavaScriptEnabled(true);
		webViewPrivacy.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress < 100){
					if (progressBar.getVisibility() == View.GONE)
						progressBar.setVisibility(View.VISIBLE);
					progressBar.setProgress(newProgress);
				} else {
					progressBar.setVisibility(View.GONE);
				}
			}
		});
		
		webViewPrivacy.loadUrl(Config.SERVER_STATIC_PAGE + PRIVACY );
		
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
}
