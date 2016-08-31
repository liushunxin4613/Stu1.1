package com.fengyang.stu;

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

import com.fengyang.util.ui.ImmersionActivity;

public class WebViewActivity extends ImmersionActivity {

	private PlaceholderFragment fragment;

	protected static final String TAG = "WebViewActivity";

	public static final String KEY_WEB_TITLE = "WebTitle";

	public static final String KEY_WEB_URL = "WebURL";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);

		setStatusColor(getResources().getColor(R.color.immersionColor));
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		if (savedInstanceState == null) {
			String url = getIntent().getStringExtra(KEY_WEB_URL);
			fragment = new PlaceholderFragment(url);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.web_view, menu);
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
		if (fragment.webView.canGoBack()) {
			fragment.webView.goBack();
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private ProgressBar progressBar;
		private WebView webView;
		private WebSettings setting;

		private String title;

		private String url;

		public PlaceholderFragment(String url) {
			this.url = url;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_web_view,
					container, false);
			webView = (WebView) rootView.findViewById(R.id.web_webView);
			progressBar = (ProgressBar) rootView
					.findViewById(R.id.web_progress);

			title = getActivity().getIntent().getStringExtra(KEY_WEB_TITLE);
			setTitle(title);

			webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}
			});
			webView.setWebChromeClient(new WebChromeClient() {
				@Override
				public void onProgressChanged(WebView view, int newProgress) {
					if (newProgress < 100) {
						if (progressBar.getVisibility() == View.GONE)
							progressBar.setVisibility(View.VISIBLE);
						progressBar.setProgress(newProgress);
					} else {
						progressBar.setVisibility(View.GONE);
					}
				}

				@Override
				public void onReceivedTitle(WebView view, String title) {
					super.onReceivedTitle(view, title);
					setTitle(title);
				}
			});
			setting = webView.getSettings();
			// setting.
			setting.setJavaScriptEnabled(true);
			webView.loadUrl(url);
			return rootView;
		}

		/**
		 * ÉèÖÃActivity±êÌâ
		 * 
		 * @param title
		 */
		private void setTitle(String title) {
			if (this.title == null) {
				getActivity().getActionBar().setTitle(title);
			} else {
				getActivity().getActionBar().setTitle(this.title);
			}
		}

		public void initProgressBar() {
			progressBar.setProgress(0);
			progressBar.setVisibility(View.VISIBLE);
		}
	}
}
