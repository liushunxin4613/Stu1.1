package com.fengyang.stu;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fengyang.activity.AboutActivity;
import com.fengyang.activity.FeedBackActivity;
import com.fengyang.activity.VersionInfoActivity;
import com.fengyang.util.Config;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

	/**
	 * Per the design guidelines, you should show the drawer on launch until the
	 * user manually expands it. This shared preference tracks this.
	 */
	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private NavigationDrawerCallbacks mCallbacks;

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;

	public NavigationDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Read in the flag indicating whether or not the user has demonstrated
		// awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		SharedPreferences sp = getActivity().getSharedPreferences(
				Config.SHAREPREFERENCE_SETTING, Activity.MODE_PRIVATE);
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		// setHasOptionsMenu(true);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_navigation_drawer,
				container, false);
		mDrawerListView = (ListView) rootView.findViewById(R.id.setting_list);
		mDrawerListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						selectItem(position);
					}
				});

		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		String[] key = { "icon", "tip", "text" };
		HashMap<String, Object> item = new HashMap<String, Object>();
		item.put(key[0], R.drawable.ic_about_us);
		item.put(key[2], getResources().getString(R.string.setting_about_us));
		data.add(item);
		
		HashMap<String, Object> item1 = new HashMap<String, Object>();
		item1.put(key[0], R.drawable.ic_version_info);
		item1.put(key[2], getResources()
				.getString(R.string.setting_version_info));
		data.add(item1);
		
		HashMap<String, Object> item2 = new HashMap<String, Object>();
		item2.put(key[0], R.drawable.ic_send_advice);
		item2.put(key[2],
				getResources().getString(R.string.setting_send_advice));
		data.add(item2);
		
//		HashMap<String, Object> item3 = new HashMap<String, Object>();
//		item3.put(key[0], R.drawable.ic_version_update);
//		item3.put(key[2],
//				getResources().getString(R.string.account_manager));
//		data.add(item3);
		
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.item_list_setting, key,
				new int[] { R.id.item_setting_icon, R.id.item_setting_tips,
						R.id.item_setting_text });
		mDrawerListView.setAdapter(adapter);
		return rootView;
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null
				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 * 
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.color.home_setting_shadow,
				GravityCompat.END);
		// set up the drawer's list view with items and click listener

		// If the user hasn't 'learned' about the drawer, open it to introduce
		// them to the drawer,
		// per the navigation drawer design guidelines.
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		// mDrawerLayout.post(new Runnable() {
		// @Override
		// public void run() {
		// mDrawerToggle.syncState();
		// }
		// });
		mDrawerLayout.setDrawerListener(new DrawerListener() {

			@Override
			public void onDrawerStateChanged(int arg0) {

			}

			@Override
			public void onDrawerSlide(View arg0, float arg1) {

			}

			@SuppressLint("NewApi")
			@Override
			public void onDrawerOpened(View arg0) {
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {
					// The user manually opened the drawer; store this flag to
					// prevent auto-showing
					// the navigation drawer automatically in the future.
					mUserLearnedDrawer = true;
					SharedPreferences sp = getActivity().getSharedPreferences(
							Config.SHAREPREFERENCE_SETTING, Activity.MODE_PRIVATE);
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
							.apply();
					sp.edit().commit();
				}

				// getActivity().invalidateOptionsMenu(); // calls
				// // onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerClosed(View arg0) {
				if (!isAdded()) {
					return;
				}

				// getActivity().invalidateOptionsMenu(); // calls
				// // onPrepareOptionsMenu()
			}
		});
	}

	private void selectItem(int position) {

		Log.i("TAG", "" + position);
		Intent intent;
		switch (position) {
		
		case 0:// 关于我们
			intent = new Intent(getActivity(),AboutActivity.class);
			startActivity(intent);
			break;
		
		case 1:	//版本信息	里面包括版本升级
			intent = new Intent(getActivity(),VersionInfoActivity.class);
			startActivity(intent);
			break;
			
		case 2:// 意见反馈
			if (MainActivity.checkUserLogin(getActivity())) {
				intent = new Intent(getActivity(),FeedBackActivity.class);
				startActivity(intent);
			}
			break;
		
		/*case 3://账号管理  包括退出登录和切换账户
			
			break;*/

		default:
			break;
		}

		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		// mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// if (mDrawerToggle.onOptionsItemSelected(item)) {
		// return true;
		// }

		// if (item.getItemId() == R.id.action_example) {
		// Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT)
		// .show();
		// return true;
		// }

		return super.onOptionsItemSelected(item);
	}

	public void openDrawer() {
		if (mDrawerLayout != null) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}
	}

	public void colseDrawer() {
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
	}

	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(int position);
	}
}
