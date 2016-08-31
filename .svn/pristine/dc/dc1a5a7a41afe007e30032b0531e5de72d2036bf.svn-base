package com.fengyang.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.fengyang.stu.R;
import com.fengyang.util.Config;
import com.fengyang.util.ui.ImmersionActivity;

public class MallActivity extends ImmersionActivity {

	private PlaceholderFragment fragment;

	protected static final String TAG = "MallActivity";
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mall);
		
		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		
		if (savedInstanceState == null) {
			fragment= new PlaceholderFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mall, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.action_reload:
			fragment.initProgressBar();
			fragment.webView.reload();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		if (fragment.webView.canGoBack()){
			fragment.webView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		ProgressBar progressBar;
		WebView webView;
		WebSettings setting;
		
		public PlaceholderFragment() {
		}

		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_mall, container,
					false);
			webView = (WebView) rootView.findViewById(R.id.mall_webView);
			progressBar = (ProgressBar) rootView.findViewById(R.id.mall_progress);
			
			webView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
			webView.setWebChromeClient(new WebChromeClient(){
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
			setting = webView.getSettings();
			setting.setJavaScriptEnabled(true);
			webView.loadUrl(Config.SERVER_WAP_MALL);
			return rootView;
		}
		
		public void initProgressBar(){
			progressBar.setProgress(0);
			progressBar.setVisibility(View.VISIBLE);
		}
	}
}
