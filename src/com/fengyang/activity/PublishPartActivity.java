package com.fengyang.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.fengyang.dialog.ConfirmDialog;
import com.fengyang.fragment.PublishPartFragment;
import com.fengyang.stu.R;
import com.fengyang.util.ui.ImmersionActivity;

/**
 * 发布兼职的Activity
 * 
 * @author HeJie
 *
 */
public class PublishPartActivity extends ImmersionActivity {

	private static final String TAG = "publishPartActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_part);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.publish_part_container,
							PublishPartFragment.getInstance()).commit();
		}
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		setStatusColor(getResources().getColor(R.color.immersionColor));
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
//		titleView.setText(R.string.publish_job_title);
//
//		getActionBar().setDisplayShowCustomEnabled(true);
//		getActionBar().setCustomView(v);
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_null, menu);
		return true;
	}

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
					getString(R.string.publish_job_time_exit_title),
					getString(R.string.publish_job_time_exit_msg),
					getString(R.string.publish_job_time_exit_yes),
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
