package com.fengyang.stu;

import android.app.Application;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.baidu.frontia.FrontiaApplication;
import com.fengyang.db.UserDetailProvider;
import com.fengyang.db.UserProvider;
import com.fengyang.entity.OubaArea;
import com.fengyang.entity.User;
import com.fengyang.entity.UserDetail;

public class StuApplication extends Application {

	private User user;

	private OubaArea provice;

	private OubaArea city;

	private OubaArea distrct;

	@Override
	public void onCreate() {
		super.onCreate();
		FrontiaApplication.initFrontiaApplication(this);
	}

	public boolean isLogin() {
		return user != null;
	}

	public User getUser() {
		return user;
	}

	/**
	 * 设置用户数据，并更新到本地数据库
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
		if (user == null)
			return;
		Log.d("StuApplication", "userDetail = " + user.getUserDetail());
		Uri uri = ContentUris.withAppendedId(UserProvider.CONTENT_URI,
				user.getId());
		getContentResolver().update(uri, UserProvider.parseUser(user), null,
				null);
		if (user.getUserDetail() != null) {
			saveOrUpdateUserDetail(user.getUserDetail());
		}
	}

	/**
	 * 获取用户详情信息
	 * 
	 * @return
	 */
	public UserDetail getUserDetail() {
		UserDetail userDetail = null;
		if (null == user.getUserDetail()) {
			userDetail = user.getUserDetail();
			Uri uri = ContentUris.withAppendedId(
					UserDetailProvider.CONTENT_URI, user.getId());
			Cursor cursor = getContentResolver().query(uri, null, null, null,
					null);
			if (cursor != null && cursor.getCount() > 0) {
				userDetail = UserDetailProvider.toUserDetail(cursor).get(0);
				user.setUserDetail(userDetail);
			}
		} else {
			userDetail = user.getUserDetail();
		}
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		saveOrUpdateUserDetail(userDetail);
		if (user != null)
			user.setUserDetail(userDetail);
	}

	private void saveOrUpdateUserDetail(UserDetail userDetail) {
		if (userDetail == null)
			return;
		Uri uri = ContentUris.withAppendedId(UserDetailProvider.CONTENT_URI,
				userDetail.getId());
		Log.d("StuApplication", "uri = " + uri.toString());
		Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			Log.d("StuApplication", "update user detail");
			getContentResolver().update(uri,
					UserDetailProvider.parseUserDetail(userDetail), null, null);
		} else {
			Log.d("StuApplication", "insert user detail");
			getContentResolver().insert(uri,
					UserDetailProvider.parseUserDetail(userDetail));
		}
	}

	public OubaArea getProvice() {
		return provice;
	}

	public void setProvice(OubaArea provice) {
		this.provice = provice;
	}

	public OubaArea getCity() {
		return city;
	}

	public void setCity(OubaArea city) {
		this.city = city;
	}

	public OubaArea getDistrct() {
		return distrct;
	}

	public void setDistrct(OubaArea distrct) {
		this.distrct = distrct;
	}

}
