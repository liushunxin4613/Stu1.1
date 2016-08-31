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

import com.fengyang.entity.UserDetail;
import com.fengyang.util.FormatUtils;

public class UserDetailProvider extends ContentProvider {
	private StuDBHelper stuHelper;

	/**
	 * 名字与配置文件保持一致，一般与类名相同确保唯一性
	 */
	public static final String AUTHORITY = "com.fengyang.db.UserDetailProvider";
	/**
	 * UserProvider的Content Uri
	 */
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + StuDBHelper.TABLE_NAME_USER_DETAIL);
	/**
	 * 多列数据的mineType
	 */
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fengyang.db.UserDetailProvider";
	/**
	 * 单列数据的mineType
	 */
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.fengyang.db.UserDetailProvider";

	/**
	 * 多列数据的Uri匹配的返回码，查询Uri
	 */
	public static final int ITEM = 1;
	/**
	 * 单列数据的Uri匹配的返回码，查询对应ID的Uri
	 */
	public static final int ITEM_ID = 2;

	private static final UriMatcher URI_MATCHER;

	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTHORITY, StuDBHelper.TABLE_NAME_USER_DETAIL, ITEM);
		URI_MATCHER.addURI(AUTHORITY,
				StuDBHelper.TABLE_NAME_USER_DETAIL + "/#", ITEM_ID);
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
			rows = db.delete(StuDBHelper.TABLE_NAME_USER_DETAIL, selection,
					selectionArgs);
			break;
		case ITEM_ID:
			String id = uri.getPathSegments().get(1);
			rows = db.delete(StuDBHelper.TABLE_NAME_USER_DETAIL, "id="
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
			rowid = db.insert(StuDBHelper.TABLE_NAME_USER_DETAIL, null, values);
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
			cursor = db.query(StuDBHelper.TABLE_NAME_USER_DETAIL, projection,
					selection, selectionArgs, null, null, sortOrder);
			break;
		case ITEM_ID:
			String id = uri.getPathSegments().get(1);
			cursor = db.query(StuDBHelper.TABLE_NAME_USER_DETAIL, projection,
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
//		db.close();
		return cursor;
	}
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = stuHelper.getWritableDatabase();
		int rows = 0;
		switch (URI_MATCHER.match(uri)) {
		case ITEM:
			rows = db.update(StuDBHelper.TABLE_NAME_USER_DETAIL, values,
					selection, selectionArgs);
			break;
		case ITEM_ID:
			String id = uri.getPathSegments().get(1);
			rows = db.update(StuDBHelper.TABLE_NAME_USER_DETAIL, values, "id="
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
	 * 将userDetail的数据转存到ContentValues的对象中
	 * 
	 * @param userDetail
	 * @return
	 */
	public static ContentValues parseUserDetail(UserDetail userDetail) {
		ContentValues values = new ContentValues();
		values.put("id", userDetail.getId());
		values.put("c_true_name", userDetail.getTrueName());
		values.put("c_sex", userDetail.getSex());
		values.put("c_birthday",
				FormatUtils.dateFormat(userDetail.getBirthday()));
		values.put("c_qqNO", userDetail.getQqNO());
		values.put("c_registTime", userDetail.getRegistTime());
		values.put("c_brifIntrodction", userDetail.getBrifIntrodction());
		return values;
	}

	/**
	 * 将游标数据转换成userDetail列表返回
	 * 
	 * @param cursor
	 * @return
	 */
	public static List<UserDetail> toUserDetail(Cursor cursor) {
		if (cursor != null && cursor.getCount() < 1)
			return null;
		List<UserDetail> userDetails = new ArrayList<UserDetail>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			UserDetail userDetail = new UserDetail();
			userDetail.setId(cursor.getInt(cursor.getColumnIndex("id")));
			userDetail.setTrueName(cursor.getString(cursor
					.getColumnIndex("c_true_name")));
			userDetail.setSex(cursor.getInt(cursor.getColumnIndex("c_sex")));
			userDetail.setBirthday(FormatUtils.parseDate(cursor
					.getString(cursor.getColumnIndex("c_birthday"))));
			userDetail
					.setQqNO(cursor.getString(cursor.getColumnIndex("c_qqNO")));
			userDetail.setRegistTime(cursor.getString(cursor
					.getColumnIndex("c_registTime")));
			userDetail.setBrifIntrodction(cursor.getString(cursor
					.getColumnIndex("c_brifIntrodction")));
			userDetails.add(userDetail);
			cursor.moveToNext();
		}
		return userDetails;
	}
}
