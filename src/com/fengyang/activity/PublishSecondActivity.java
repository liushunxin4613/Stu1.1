package com.fengyang.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.fragment.PublishSecondFragment;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

public class PublishSecondActivity extends ImmersionActivity {

	private static final String TAG = "publishSecondActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_second);

		//setActionBar();

		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.publish_second_container,
							PublishSecondFragment.getInstance()).commit();
		}
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		setStatusColor(getResources().getColor(R.color.immersionColor));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

//	private void setActionBar() {
//		getActionBar().setDisplayShowHomeEnabled(true);
//		getActionBar().setHomeButtonEnabled(true);
//
//		LayoutInflater inflator = (LayoutInflater) this
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		// 如果parent为null 那么xml文件的FrameLayout布局参数会失效
//		View v = inflator.inflate(R.layout.actionbar_center_title_layout,
//				new LinearLayout(getApplicationContext()), false);
//		TextView titleView = (TextView) v.findViewById(R.id.actionBar_titel);
//		titleView.setText(R.string.publish_second_title);
//
//		getActionBar().setDisplayShowCustomEnabled(true);
//		getActionBar().setCustomView(v);
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			goBack();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		goBack();
	}
	
	/**
	 * 回退
	 */
	private void goBack(){
		if (!popFragment()) {
			ConfirmDialog dialog = new ConfirmDialog(
					ConfirmDialog.CONFIRM_STYLE_CENTER,
					getString(R.string.publish_second_exit_title),
					getString(R.string.publish_second_exit_msg),
					getString(R.string.publish_second_exit_yes),
					getString(R.string.dialog_cancel));
			dialog.setOnclickListener(new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						Log.i(TAG, "finish activity");
						finish();
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			});
			dialog.show(getSupportFragmentManager().beginTransaction(), TAG);
		}
	}
	/**
	 * 移除容器中栈顶的Fragment
	 * 
	 * @return true:移除成功 ; false:移除失败，即backStack中没有Fragment
	 * 
	 */
	private boolean popFragment() {
		int count = getSupportFragmentManager().getBackStackEntryCount();
		Log.i(TAG, "BackStackEntryCount = " + count);
		if (count > 0) {
			getSupportFragmentManager().popBackStack();
			return true;
		}
		return false;
	}
}
