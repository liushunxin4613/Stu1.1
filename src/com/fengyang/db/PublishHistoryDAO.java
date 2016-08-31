package com.fengyang.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengyang.entity.AppPartTime;
import com.fengyang.entity.AppSecondHand;

/**
 * 用户发布历史的数据库操作类
 * 
 * @author HeJie
 *
 */
public class PublishHistoryDAO {

	private StuDBHelper DBHelper;

	public PublishHistoryDAO(Context context) {
		super();
		DBHelper = new StuDBHelper(context, null);
	}

	/**
	 * 查询用户的兼职发布历史
	 * 
	 * @param userId
	 *            用户id
	 * @return 用户发布历史列表
	 */
	public List<AppPartTime> queryPart(int userId, int page, int pageSize) {
		List<AppPartTime> list = new ArrayList<AppPartTime>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StuDBHelper.TABLE_NAME_PUBLISH_PART, null,
				"user_id=?", new String[] { String.valueOf(userId) }, null,
				null, "publish_time DESC", (page - 1) * pageSize + " , " + pageSize);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AppPartTime partTime = new AppPartTime();
			partTime.setPartTimeId(cursor.getInt(cursor
					.getColumnIndex("part_id")));
			partTime.setPartTimeName(cursor.getString(cursor
					.getColumnIndex("part_name")));
			partTime.setPublishTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("publish_time"))));
			partTime.setPay(cursor.getDouble(cursor.getColumnIndex("pay")));
			partTime.setPayUnit(cursor.getInt(cursor.getColumnIndex("pay_unit")));
			partTime.setPayWay(cursor.getInt(cursor.getColumnIndex("pay_way")));
			partTime.setTimeType(cursor.getShort(cursor
					.getColumnIndex("time_type")));
			list.add(partTime);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 根据id查询兼职发布历史信息
	 * 
	 * @param pId
	 *            兼职id
	 * @return
	 */
	public AppPartTime queryPartById(int pId) {
		AppPartTime partTime = null;
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StuDBHelper.TABLE_NAME_PUBLISH_PART, null,
				"part_id=?", new String[] { String.valueOf(pId) }, null, null,
				null);
		if (cursor.moveToFirst()) {
			partTime = new AppPartTime();
			partTime.setPartTimeId(pId);
			partTime.setPartTimeName(cursor.getString(cursor
					.getColumnIndex("part_name")));
			partTime.setPublishTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("publish_time"))));
			partTime.setPay(cursor.getDouble(cursor.getColumnIndex("pay")));
			partTime.setPayUnit(cursor.getInt(cursor.getColumnIndex("pay_unit")));
			partTime.setPayWay(cursor.getInt(cursor.getColumnIndex("pay_way")));
			partTime.setTimeType(cursor.getShort(cursor
					.getColumnIndex("time_type")));
		}
		cursor.close();
		db.close();
		return partTime;
	}

	/**
	 * 插入兼职发布历史信息
	 * 
	 * @param userId
	 *            用户id
	 * @param partTime
	 *            兼职信息
	 * @return
	 */
	public boolean insertPart(int userId, AppPartTime partTime) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("user_id", userId);
		values.put("part_id", partTime.getPartTimeId());
		values.put("part_name", partTime.getPartTimeName());
		values.put("publish_time", partTime.getPublishTime().getTime());
		values.put("pay", partTime.getPay());
		values.put("pay_unit", partTime.getPayUnit());
		values.put("pay_way", partTime.getPayWay());
		values.put("time_type", partTime.getTimeType());
		long id = db.insert(StuDBHelper.TABLE_NAME_PUBLISH_PART, null, values);
		db.close();
		return id != -1;
	}

	/**
	 * 插入兼职发布历史信息
	 * 
	 * @param userId
	 *            用户id
	 * @param partTimes
	 *            兼职信息
	 * @return
	 */
	public int insertPart(int userId, List<AppPartTime> partTimes) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int count = 0;
		db.beginTransaction();
		try {
			for (AppPartTime partTime : partTimes) {
				ContentValues values = new ContentValues();
				values.put("user_id", userId);
				values.put("part_id", partTime.getPartTimeId());
				values.put("part_name", partTime.getPartTimeName());
				values.put("publish_time", partTime.getPublishTime().getTime());
				values.put("pay", partTime.getPay());
				values.put("pay_unit", partTime.getPayUnit());
				values.put("pay_way", partTime.getPayWay());
				values.put("time_type", partTime.getTimeType());
				long id = db.insert(StuDBHelper.TABLE_NAME_PUBLISH_PART, null,
						values);
				if (id != -1)
					count++;
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();
		return count;
	}

	/**
	 * 删除兼职发布历史
	 * 
	 * @param pId
	 *            兼职id
	 * @return
	 */
	public boolean deletePart(int pId) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_PUBLISH_PART, "part_id=?",
				new String[] { String.valueOf(pId) });
		db.close();
		return rows > 0;
	}

	/**
	 * 删除所有兼职发布历史
	 * 
	 * @return
	 */
	public boolean deletePartAll() {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_PUBLISH_PART, null, null);
		db.close();
		return rows > 0;
	}

	/**
	 * 跟新兼职发布信息
	 * 
	 * @param userId
	 *            用户id
	 * @param partTime
	 *            兼职信息
	 * @return
	 */
	public boolean updatePart(int userId, AppPartTime partTime) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("user_id", userId);
		values.put("part_name", partTime.getPartTimeName());
		values.put("publish_time", partTime.getPublishTime().getTime());
		values.put("pay", partTime.getPay());
		values.put("pay_unit", partTime.getPayUnit());
		values.put("pay_way", partTime.getPayWay());
		values.put("time_type", partTime.getTimeType());
		long id = db.update(StuDBHelper.TABLE_NAME_PUBLISH_PART, values,
				"part_id=?",
				new String[] { String.valueOf(partTime.getPartTimeId()) });
		db.close();
		return id != -1;
	}

	/**
	 * 查询二手发布历史信息
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<AppSecondHand> querySecond(int userId, int page, int pageSize) {
		List<AppSecondHand> list = new ArrayList<AppSecondHand>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StuDBHelper.TABLE_NAME_PUBLISH_SECOND, null,
				"user_id=?", new String[] { String.valueOf(userId) }, null,
				null, "publish_time DESC", (page - 1) * pageSize + " , " + pageSize);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AppSecondHand secondHand = new AppSecondHand();
			secondHand.setSecondHandId(cursor.getInt(cursor
					.getColumnIndex("second_id")));
			secondHand.setSecondHandName(cursor.getString(cursor
					.getColumnIndex("second_name")));
			secondHand.setImagePath(cursor.getString(cursor
					.getColumnIndex("image_path")));
			secondHand.setPublishTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("publish_time"))));
			secondHand.setSecondHandPrice(cursor.getDouble(cursor
					.getColumnIndex("price")));
			list.add(secondHand);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 根据二手物品id查询二手物品信息
	 * 
	 * @param sId
	 *            二手物品id
	 * @return
	 */
	public AppSecondHand querySecondById(int sId) {
		AppSecondHand secondHand = null;
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StuDBHelper.TABLE_NAME_PUBLISH_SECOND, null,
				"second_id=?", new String[] { String.valueOf(sId) }, null,
				null, null);
		if (cursor.moveToFirst()) {
			secondHand = new AppSecondHand();
			secondHand.setSecondHandId(sId);
			secondHand.setSecondHandName(cursor.getString(cursor
					.getColumnIndex("second_name")));
			secondHand.setImagePath(cursor.getString(cursor
					.getColumnIndex("image_path")));
			secondHand.setPublishTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("publish_time"))));
			secondHand.setSecondHandPrice(cursor.getDouble(cursor
					.getColumnIndex("price")));
		}
		cursor.close();
		db.close();
		return secondHand;
	}

	/**
	 * 插入二手物品发布信息
	 * 
	 * @param userId
	 *            用户id
	 * @param secondHand
	 *            二手物品信息
	 * @return
	 */
	public boolean insertSecond(int userId, AppSecondHand secondHand) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("user_id", userId);
		values.put("second_id", secondHand.getSecondHandId());
		values.put("second_name", secondHand.getSecondHandName());
		values.put("image_path", secondHand.getImagePath());
		values.put("publish_time", secondHand.getPublishTime().getTime());
		values.put("price", secondHand.getSecondHandPrice());
		long id = db.insert(StuDBHelper.TABLE_NAME_PUBLISH_SECOND, null,
				values);
		db.close();
		return id != -1;
	}

	/**
	 * 插入二手物品发布信息
	 * 
	 * @param userId
	 *            用户id
	 * @param secondHand
	 *            二手物品信息
	 * @return
	 */
	public int insertSecond(int userId, List<AppSecondHand> secondHands) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int count = 0;
		db.beginTransaction();
		try {
			for (AppSecondHand secondHand : secondHands) {

				ContentValues values = new ContentValues();
				values.put("user_id", userId);
				values.put("second_id", secondHand.getSecondHandId());
				values.put("second_name", secondHand.getSecondHandName());
				values.put("image_path", secondHand.getImagePath());
				values.put("publish_time", secondHand.getPublishTime()
						.getTime());
				values.put("price", secondHand.getSecondHandPrice());
				long id = db.insert(StuDBHelper.TABLE_NAME_PUBLISH_SECOND,
						null, values);
				if (id != -1)
					count++;
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		db.close();
		return count;
	}

	/**
	 * 删除二手物品信息
	 * 
	 * @param sId
	 *            二手物品信息
	 * @return
	 */
	public boolean deleteSecond(int sId) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_PUBLISH_SECOND,
				"second_id=?", new String[] { String.valueOf(sId) });
		db.close();
		return rows > 0;
	}

	/**
	 * 删除所有二手物品信息
	 * 
	 * @return
	 */
	public boolean deleteSecondAll() {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_PUBLISH_SECOND, null, null);
		db.close();
		return rows > 0;
	}

	/**
	 * 更新二手物品发布信息
	 * 
	 * @param userId
	 *            用户id
	 * @param secondHand
	 *            二手物品发布信息
	 * @return
	 */
	public boolean updateSecond(int userId, AppSecondHand secondHand) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("user_id", userId);
		values.put("second_name", secondHand.getSecondHandName());
		values.put("image_path", secondHand.getImagePath());
		values.put("publish_time", secondHand.getPublishTime().getTime());
		values.put("price", secondHand.getSecondHandPrice());
		long id = db.update(StuDBHelper.TABLE_NAME_FAVORITE_SECOND, values,
				"second_id=?",
				new String[] { String.valueOf(secondHand.getSecondHandId()) });
		db.close();
		return id != -1;
	}

}
