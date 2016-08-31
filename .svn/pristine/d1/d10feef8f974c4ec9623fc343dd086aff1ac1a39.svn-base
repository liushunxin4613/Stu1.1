package com.fengyang.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.fengyang.entity.User;

/**
 * 用户信息的ContentProvider
 * 
 * @author HeJie
 *
 */
public class UserProvider extends ContentProvider {

	private StuDBHelper stuHelper;

	/**
	 * 名字与配置文件保持一致，一般与类名相同确保唯一性
	 */
	public static final String AUTHORITY = "com.fengyang.db.UserProvider";
	/**
	 * UserProvider的Content Uri
	 */
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + StuDBHelper.TABLE_NAME_USER_INFO);
	/**
	 * 多列数据的mineType
	 */
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fengyang.db.UserProvider";
	/**
	 * 单列数据的mineType
	 */
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.fengyang.db.UserProvider";

	/**
	 * Uri匹配的返回码，查询Uri
	 */
	public static final int ITEM = 1;
	/**
	 * Uri匹配的返回码，查询对应ID的Uri
	 */
	public static final int ITEM_ID = 2;

	private static final UriMatcher URI_MATCHER;

	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTHORITY, StuDBHelper.TABLE_NAME_USER_INFO, ITEM);
		URI_MATCHER.addURI(AUTHORITY, StuDBHelper.TABLE_NAME_USER_INFO + "/#",
				ITEM_ID);
	}

	@Override
	public boolean onCreate() {
		stuHelper = new StuDBHelper(getContext(), null);
		return false;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = stuHelper.getWritableDatabase();
		int rows = 0;
		switch (URI_MATCHER.match(uri)) {
		case ITEM:
			rows = db.delete(StuDBHelper.TABLE_NAME_USER_INFO, selection,
					selectionArgs);
			break;
		case ITEM_ID:
			String id = uri.getPathSegments().get(1);
			rows = db.delete(StuDBHelper.TABLE_NAME_USER_INFO, "id="
					+ id
					+ (!TextUtils.isEmpty(selection) ? "AND(" + selection + ')'
							: ""), selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		// TODO
		db.close();
		return rows;
	}

	@Override
	public String getType(Uri uri) {
		String type = null;
		switch (URI_MATCHER.match(uri)) {
		case ITEM:
			type = CONTENT_TYPE;
			break;
		case ITEM_ID:
			type = CONTENT_ITEM_TYPE;
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		return type;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = stuHelper.getWritableDatabase();
		long rowid = 0;
		switch (URI_MATCHER.match(uri)) {
		case ITEM:
		case ITEM_ID:
			rowid = db.insert(StuDBHelper.TABLE_NAME_USER_INFO, null, values);
			// TODO
			db.close();
			if (rowid > 0) {
				Uri ResultUri = ContentUris.withAppendedId(CONTENT_URI, rowid);
				getContext().getContentResolver().notifyChange(ResultUri, null);
				return ResultUri;
			} else {
				return null;
			}
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = stuHelper.getReadableDatabase();
		Cursor cursor = null;
		switch (URI_MATCHER.match(uri)) {
		case ITEM:
			cursor = db.query(StuDBHelper.TABLE_NAME_USER_INFO, projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		case ITEM_ID:
			String id = uri.getPathSegments().get(1);
			cursor = db.query(StuDBHelper.TABLE_NAME_USER_INFO, projection,
					"id="
							+ id
							+ (!TextUtils.isEmpty(selection) ? "AND("
									+ selection + ')' : ""), selectionArgs,
					null, null, sortOrder);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		// TODO
		// db.close();
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = stuHelper.getWritableDatabase();
		int rows = 0;
		switch (URI_MATCHER.match(uri)) {
		case ITEM:
			rows = db.update(StuDBHelper.TABLE_NAME_USER_INFO, values,
					selection, selectionArgs);
			break;
		case ITEM_ID:
			String id = uri.getPathSegments().get(1);
			rows = db.update(StuDBHelper.TABLE_NAME_USER_INFO, values, "id="
					+ id
					+ (!TextUtils.isEmpty(selection) ? "AND(" + selection + ')'
							: ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		// TODO
		db.close();
		return rows;
	}

	/**
	 * 将user的数据转存到ContentValues的对象中
	 * 
	 * @param user
	 * @return
	 */
	public static ContentValues parseUser(User user) {
		ContentValues values = new ContentValues();
		values.put("id", user.getId());
		values.put("c_name", user.getName());
		values.put("c_email", user.getEmail());
		values.put("c_email_bind", user.getIsEmailBind());
		values.put("c_phone", user.getPhone());
		values.put("c_phone_bind", user.getIsPhoneBind());
		values.put("c_photoPath", user.getPhotoPath());
		values.put("c_state", user.getState() ? 1 : 0);
		values.put("c_password", user.getPassword());
		values.put("c_paypwd", user.getPaypwd());
		values.put("c_isVerify", user.getIsVerify());
		values.put("c_points", user.getPoints());
		values.put("c_exppoints", user.getExppoints());
		values.put("c_informAllow", user.getInformAllow() ? 1 : 0);
		values.put("c_isBuy", user.getIsBuy() ? 1 : 0);
		values.put("c_isAllowTalk", user.getIsAllowTalk() ? 1 : 0);
		values.put("c_userType", user.getUserType());
		return values;
	}

	/**
	 * 将游标数据转换成user列表返回
	 * 
	 * @param cursor
	 * @return
	 */
	public static List<User> toUser(Cursor cursor) {
		if (cursor != null && cursor.getCount() < 1)
			return null;
		List<User> users = new ArrayList<User>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = new User();
			user.setId(cursor.getInt(cursor.getColumnIndex("id")));
			user.setName(cursor.getString(cursor.getColumnIndex("c_name")));
			user.setEmail(cursor.getString(cursor.getColumnIndex("c_email")));
			user.setIsEmailBind(cursor.getInt(cursor
					.getColumnIndex("c_email_bind")) != 0);
			user.setPhone(cursor.getString(cursor.getColumnIndex("c_phone")));
			user.setIsPhoneBind(cursor.getInt(cursor
					.getColumnIndex("c_phone_bind")) != 0);
			user.setPhotoPath(cursor.getString(cursor.getColumnIndex("c_photoPath")));
			user.setState(cursor.getInt(cursor.getColumnIndex("c_state")) != 0);
			user.setPassword(cursor.getString(cursor
					.getColumnIndex("c_password")));
			user.setPaypwd(cursor.getString(cursor.getColumnIndex("c_paypwd")));
			user.setIsVerify(cursor.getShort(cursor.getColumnIndex("c_isVerify")));
			user.setPoints(cursor.getInt(cursor.getColumnIndex("c_points")));
			user.setExppoints(cursor.getInt(cursor
					.getColumnIndex("c_exppoints")));
			user.setInformAllow(cursor.getInt(cursor
					.getColumnIndex("c_informAllow")) != 0);
			user.setIsBuy(cursor.getInt(cursor.getColumnIndex("c_isBuy")) != 0);
			user.setIsAllowTalk(cursor.getInt(cursor
					.getColumnIndex("c_isAllowTalk")) != 0);
			user.setUserType(cursor.getInt(cursor.getColumnIndex("c_userType")));
			users.add(user);
			cursor.moveToNext();
		}
		if (!cursor.isClosed())
			cursor.close();
		return users;
	}

}
