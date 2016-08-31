package com.fengyang.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengyang.model.PartCollection;
import com.fengyang.model.SecondCollection;

/**
 * 用户收藏信息数据操作类
 * 
 * @author HeJie
 *
 */
public class CollectDAO {

	private StuDBHelper DBHelper;

	public CollectDAO(Context context) {
		super();
		DBHelper = new StuDBHelper(context, null);
	}

	/**
	 * 查询收藏的兼职
	 * 
	 * @param userId
	 *            用户id
	 * @return 返回收藏的兼职列表
	 */
	public List<PartCollection> queryPart(int userId, int page, int pageSize) {
		List<PartCollection> list = new ArrayList<PartCollection>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StuDBHelper.TABLE_NAME_FAVORITE_PART, null,
				"collecter_id=?", new String[] { String.valueOf(userId) },
				null, null, "add_time DESC", (page - 1) * pageSize + " , " + pageSize);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PartCollection partTime = new PartCollection();
			partTime.setPcId(cursor.getInt(cursor.getColumnIndex("id")));
			partTime.setCollecterId(userId);
			// partTime.setPublisherId(cursor.getInt(cursor
			// .getColumnIndex("publisher_id")));
			partTime.setPartTimeId(cursor.getInt(cursor
					.getColumnIndex("part_id")));
			partTime.setPartName(cursor.getString(cursor
					.getColumnIndex("part_name")));
			partTime.setPublisherName(cursor.getString(cursor
					.getColumnIndex("part_publisher")));
			partTime.setPublisherType(cursor.getInt(cursor
					.getColumnIndex("part_publisher_type")));
			partTime.setPublishTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("publish_time"))));
			partTime.setPay(cursor.getDouble(cursor.getColumnIndex("pay")));
			partTime.setPayUnit(cursor.getInt(cursor.getColumnIndex("pay_unit")));
			partTime.setPayWay(cursor.getInt(cursor.getColumnIndex("pay_way")));
			partTime.setTimeType(cursor.getShort(cursor
					.getColumnIndex("time_type")));
			partTime.setCollectDate(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("add_time"))));
			cursor.moveToNext();
			list.add(partTime);
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 根据用户和兼职id查询
	 * 
	 * @param userId
	 * @param pId
	 * @return
	 */
	public PartCollection queryPartById(int userId, int pId) {
		PartCollection partTime = null;
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StuDBHelper.TABLE_NAME_FAVORITE_PART, null,
				"collecter_id=? and part_id=?",
				new String[] { String.valueOf(userId), String.valueOf(pId) },
				null, null, null);
		if (cursor.moveToFirst()) {
			partTime = new PartCollection();
			partTime.setPcId(cursor.getInt(cursor.getColumnIndex("id")));
			partTime.setCollecterId(userId);
			// partTime.setPublisherId(cursor.getInt(cursor
			// .getColumnIndex("publisher_id")));
			partTime.setPartTimeId(cursor.getInt(cursor
					.getColumnIndex("part_id")));
			partTime.setPartName(cursor.getString(cursor
					.getColumnIndex("part_name")));
			partTime.setPublisherName(cursor.getString(cursor
					.getColumnIndex("part_publisher")));
			partTime.setPublisherType(cursor.getInt(cursor
					.getColumnIndex("part_publisher_type")));
			partTime.setPublishTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("publish_time"))));
			partTime.setPay(cursor.getDouble(cursor.getColumnIndex("pay")));
			partTime.setPayUnit(cursor.getInt(cursor.getColumnIndex("pay_unit")));
			partTime.setPayWay(cursor.getInt(cursor.getColumnIndex("pay_way")));
			partTime.setTimeType(cursor.getShort(cursor
					.getColumnIndex("time_type")));
			partTime.setCollectDate(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("add_time"))));
		}
		cursor.close();
		db.close();
		return partTime;
	}

	/**
	 * 插入用户收藏的兼职信息
	 * 
	 * @param userId
	 *            用户id
	 * @param partTime
	 *            兼职信息
	 * @return 插入是否成功
	 */
	public boolean insertPart(PartCollection partTime) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("collecter_id", partTime.getCollecterId());
		// values.put("publisher_id", partTime.getPublisherId());
		values.put("part_id", partTime.getPartTimeId());
		values.put("part_name", partTime.getPartName());
		values.put("part_publisher", partTime.getPublisherName());
		values.put("part_publisher_type", partTime.getPublisherType());
		values.put("publish_time", partTime.getPublishTime().getTime());
		values.put("pay", partTime.getPay());
		values.put("pay_unit", partTime.getPayUnit());
		values.put("pay_way", partTime.getPayWay());
		values.put("time_type", partTime.getTimeType());
		values.put("add_time", partTime.getCollectDate().getTime());
		long id = db.insert(StuDBHelper.TABLE_NAME_FAVORITE_PART, null, values);
		db.close();
		return id != -1;
	}

	/**
	 * 插入PartCollection集合
	 * 
	 * @param partTimes
	 *            PartCollection集合对象
	 * @return 插入成功的条数
	 */
	public int insertPart(List<PartCollection> partTimes) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int count = 0;
		db.beginTransaction();
		try {
			for (PartCollection partTime : partTimes) {
				ContentValues values = new ContentValues();
				values.put("collecter_id", partTime.getCollecterId());
				// values.put("publisher_id", partTime.getPublisherId());
				values.put("part_id", partTime.getPartTimeId());
				values.put("part_name", partTime.getPartName());
				values.put("part_publisher", partTime.getPublisherName());
				values.put("part_publisher_type", partTime.getPublisherType());
				values.put("publish_time", partTime.getPublishTime().getTime());
				values.put("pay", partTime.getPay());
				values.put("pay_unit", partTime.getPayUnit());
				values.put("pay_way", partTime.getPayWay());
				values.put("time_type", partTime.getTimeType());
				values.put("add_time", partTime.getCollectDate().getTime());
				long id = db.insert(StuDBHelper.TABLE_NAME_FAVORITE_PART, null,
						values);
				if (id != -1)
					count++;
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return count;
	}

	/**
	 * 删除兼职收藏信息
	 * 
	 * @param userId
	 *            用户id
	 * @param pId
	 *            兼职id
	 * @return
	 */
	public boolean deletePart(int userId, int pId) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_FAVORITE_PART,
				"collecter_id=? and part_id=?",
				new String[] { String.valueOf(userId), String.valueOf(pId) });
		db.close();
		return rows > 0;
	}

	/**
	 * 删除兼职收藏信息
	 * 
	 * @param pcId
	 *            收藏兼职的id
	 * @return
	 */
	public boolean deletePart(int pcId) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_FAVORITE_PART, "id=?",
				new String[] { String.valueOf(pcId) });
		db.close();
		return rows > 0;
	}

	/**
	 * 删除用户的所有兼职收藏信息
	 * @param userId
	 * @return
	 */
	public boolean deletePartAll(int userId) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_FAVORITE_PART,
				"collecter_id=?", new String[] { String.valueOf(userId) });
		db.close();
		return rows > 0;
	}

	/**
	 * 更新兼职信息
	 * 
	 * @param userId
	 *            用户id
	 * @param partTime
	 *            兼职信息
	 * @return
	 */
	public boolean updatePart(PartCollection partTime) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		// values.put("publisher_id", partTime.getPublisherId());
		values.put("collecter_id", partTime.getCollecterId());
		values.put("part_id", partTime.getPartTimeId());
		values.put("part_name", partTime.getPartName());
		values.put("part_publisher", partTime.getPublisherName());
		values.put("part_publisher_type", partTime.getPublisherType());
		values.put("publish_time", partTime.getPublishTime().getTime());
		values.put("pay", partTime.getPay());
		values.put("pay_unit", partTime.getPayUnit());
		values.put("pay_way", partTime.getPayWay());
		values.put("time_type", partTime.getTimeType());
		values.put("add_time", partTime.getCollectDate().getTime());
		long id = db.update(StuDBHelper.TABLE_NAME_FAVORITE_PART, values,
				"id=?", new String[] { String.valueOf(partTime.getPcId()) });
		db.close();
		return id != -1;
	}

	/**
	 * 查询收藏的二手物品信息
	 * 
	 * @param userId
	 *            用户id
	 * @return 返回收藏的二手物品列表
	 */
	public List<SecondCollection> querySecond(int userId, int page, int pageSize) {
		List<SecondCollection> list = new ArrayList<SecondCollection>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StuDBHelper.TABLE_NAME_FAVORITE_SECOND, null,
				"collecter_id=?", new String[] { String.valueOf(userId) },
				null, null, "add_time DESC", (page - 1) * pageSize + " , " + pageSize);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			SecondCollection secondHand = new SecondCollection();
			secondHand.setScId(cursor.getInt(cursor.getColumnIndex("id")));
			secondHand.setCollecterId(userId);
			// secondHand.setPublisherId(cursor.getInt(cursor
			// .getColumnIndex("publisher_id")));
			// secondHand
			// .setScId(cursor.getInt(cursor.getColumnIndex("id")));
			secondHand.setSecondHandId(cursor.getInt(cursor
					.getColumnIndex("second_id")));
			secondHand.setSecondName(cursor.getString(cursor
					.getColumnIndex("second_name")));
			secondHand.setImagePath(cursor.getString(cursor
					.getColumnIndex("image_path")));
			secondHand.setPublisherType(cursor.getInt(cursor
					.getColumnIndex("second_publisher_type")));
			secondHand.setPublishTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("publish_time"))));
			secondHand.setSecondPrice(cursor.getDouble(cursor
					.getColumnIndex("price")));
			secondHand.setCollectDate(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("add_time"))));
			cursor.moveToNext();
			list.add(secondHand);
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 根据用户查询指定id的兼职
	 * 
	 * @param userId
	 *            用户id
	 * @param sId
	 *            二手物品id
	 * @return
	 */
	public SecondCollection querySecondById(int userId, int sId) {
		SecondCollection secondHand = null;
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StuDBHelper.TABLE_NAME_FAVORITE_SECOND, null,
				"collecter_id=? and second_id=?",
				new String[] { String.valueOf(userId), String.valueOf(sId) },
				null, null, null);
		if (cursor.moveToFirst()) {
			secondHand = new SecondCollection();
			secondHand.setScId(cursor.getInt(cursor.getColumnIndex("id")));
			secondHand.setCollecterId(userId);
			// secondHand.setPublisherId(cursor.getInt(cursor
			// .getColumnIndex("publisher_id")));
			secondHand.setSecondHandId(sId);
			secondHand.setSecondName(cursor.getString(cursor
					.getColumnIndex("second_name")));
			secondHand.setImagePath(cursor.getString(cursor
					.getColumnIndex("image_path")));
			secondHand.setPublisherType(cursor.getInt(cursor
					.getColumnIndex("second_publisher_type")));
			secondHand.setPublishTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("publish_time"))));
			secondHand.setSecondPrice(cursor.getDouble(cursor
					.getColumnIndex("price")));
			secondHand.setCollectDate(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("add_time"))));
		}
		cursor.close();
		db.close();
		return secondHand;
	}

	/**
	 * 插入二手物品信息
	 * 
	 * @param userId
	 *            用户id
	 * @param secondHand
	 *            二手信息
	 * @return
	 */
	public boolean insertSecond(SecondCollection secondHand) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("collecter_id", secondHand.getCollecterId());
		// values.put("publisher_id", secondHand.getPublisherId());
		values.put("second_id", secondHand.getSecondHandId());
		values.put("second_name", secondHand.getSecondName());
		values.put("image_path", secondHand.getImagePath());
		values.put("second_publisher_type", secondHand.getPublisherType());
		values.put("publish_time", secondHand.getPublishTime().getTime());
		values.put("price", secondHand.getSecondPrice());
		values.put("add_time", secondHand.getCollectDate().getTime());
		long id = db.insert(StuDBHelper.TABLE_NAME_FAVORITE_SECOND, null,
				values);
		db.close();
		return id != -1;
	}

	public int insertSecond(List<SecondCollection> secondHands) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int count = 0;
		db.beginTransaction();
		try {
			for (SecondCollection secondHand : secondHands) {
				ContentValues values = new ContentValues();
				values.put("collecter_id", secondHand.getCollecterId());
				// values.put("publisher_id", secondHand.getPublisherId());
				values.put("second_id", secondHand.getSecondHandId());
				values.put("second_name", secondHand.getSecondName());
				values.put("image_path", secondHand.getImagePath());
				values.put("second_publisher_type",
						secondHand.getPublisherType());
				values.put("publish_time", secondHand.getPublishTime()
						.getTime());
				values.put("price", secondHand.getSecondPrice());
				values.put("add_time", secondHand.getCollectDate().getTime());
				long id = db.insert(StuDBHelper.TABLE_NAME_FAVORITE_SECOND,
						null, values);
				if (id != -1)
					count++;
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
			db.close();
		}
		return count;
	}

	/**
	 * 删除收藏的二手物品信息
	 * 
	 * @param userId
	 *            用户id
	 * @param sId
	 *            二手物品id
	 * @return
	 */
	public boolean deleteSecond(int userId, int sId) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_FAVORITE_SECOND,
				"collecter_id=? and second_id=?",
				new String[] { String.valueOf(userId), String.valueOf(sId) });
		db.close();
		return rows > 0;
	}

	/**
	 * 删除收藏的二手物品信息
	 * 
	 * @param scId
	 *            收藏二手物品的id
	 * @return
	 */
	public boolean deleteSecond(int scId) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_FAVORITE_SECOND, "id=?",
				new String[] { String.valueOf(scId) });
		db.close();
		return rows > 0;
	}
	
	/**
	 * 删除用户收藏的所有二手物品信息
	 * @param userId 用户id
	 * @return
	 */
	public boolean deleteSecondAll(int userId) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		int rows = db.delete(StuDBHelper.TABLE_NAME_FAVORITE_SECOND, "collecter_id=?",
				new String[] { String.valueOf(userId) });
		db.close();
		return rows > 0;
	}

	/**
	 * 更新二手物品信息
	 * 
	 * @param secondHand
	 *            二手物品信息
	 * @return
	 */
	public boolean updateSecond(SecondCollection secondHand) {
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("collecter_id", secondHand.getCollecterId());
		// values.put("publisher_id", secondHand.getPublisherId());
		values.put("second_id", secondHand.getSecondHandId());
		values.put("second_name", secondHand.getSecondName());
		values.put("image_path", secondHand.getImagePath());
		values.put("second_publisher_type", secondHand.getPublisherType());
		values.put("publish_time", secondHand.getPublishTime().getTime());
		values.put("price", secondHand.getSecondPrice());
		values.put("add_time", secondHand.getCollectDate().getTime());
		long id = db.update(StuDBHelper.TABLE_NAME_FAVORITE_SECOND, values,
				"id=?", new String[] { String.valueOf(secondHand.getScId()) });
		db.close();
		return id != -1;
	}
}
