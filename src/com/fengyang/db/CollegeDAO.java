package com.fengyang.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengyang.entity.OubaArea;
import com.fengyang.entity.AppCollege;

public class CollegeDAO {
	StableDBHelper DBHelper;

	public CollegeDAO(Context context) {
		DBHelper = new StableDBHelper(context, null);
	}

	/**
	 * 查询所有的省
	 * 
	 * @return
	 */
	public List<OubaArea> queryProvince() {
		List<OubaArea> list = new ArrayList<OubaArea>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_AREA, null,
				"c_Area_deep=1", null, null, null, "c_Area_sort");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			OubaArea area = new OubaArea();
			area.setAreaId(cursor.getInt(cursor.getColumnIndex("c_Area_ID")));
			area.setAreaName(cursor.getString(cursor
					.getColumnIndex("c_Area_name")));
			area.setAreaDeep(cursor.getShort(cursor
					.getColumnIndex("c_Area_deep")));
			area.setAreaSort(cursor.getShort(cursor
					.getColumnIndex("c_Area_sort")));
			area.setAreaRegion(cursor.getString(cursor
					.getColumnIndex("c_Area_region")));
			area.setAreaParentId(cursor.getInt(cursor
					.getColumnIndex("c_Area_parent_ID")));
			list.add(area);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}

	public OubaArea queryProvinceById(int id) {
		OubaArea province = null;
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_AREA, null,
				"c_Area_ID=?", new String[] { String.valueOf(id) }, null, null,
				"c_Area_sort");
		if (cursor.moveToFirst()) {
			province = new OubaArea();
			province.setAreaId(cursor.getInt(cursor.getColumnIndex("c_Area_ID")));
			province.setAreaName(cursor.getString(cursor
					.getColumnIndex("c_Area_name")));
			province.setAreaDeep(cursor.getShort(cursor
					.getColumnIndex("c_Area_deep")));
			province.setAreaSort(cursor.getShort(cursor
					.getColumnIndex("c_Area_sort")));
			province.setAreaRegion(cursor.getString(cursor
					.getColumnIndex("c_Area_region")));
			province.setAreaParentId(cursor.getInt(cursor
					.getColumnIndex("c_Area_parent_ID")));
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return province;
	}

	/**
	 * 查询省内的所有大学
	 * 
	 * @param province
	 * @return
	 */
	public List<AppCollege> queryCollege(OubaArea province) {
		List<AppCollege> list = new ArrayList<AppCollege>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_COLLEGE, null,
				"c_Province_ID=? and c_University_name<>''",
				new String[] { String.valueOf(province.getAreaId()) }, null,
				null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AppCollege college = new AppCollege();
			college.setCUniversityId(cursor.getInt(cursor
					.getColumnIndex("c_University_ID")));
			college.setCUniversityName(cursor.getString(cursor
					.getColumnIndex("c_University_name")));
			college.setCUniversityPinyin(cursor.getString(cursor
					.getColumnIndex("c_University_pinyin")));
			college.setCProvinceName(cursor.getString(cursor
					.getColumnIndex("c_Province_name")));
			college.setCCreateTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("c_Create_time"))));
			college.setOubaArea(province);
			list.add(college);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}

	public AppCollege QueryCollegeById(int id) {
		AppCollege college = null;
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_COLLEGE, null,
				"c_University_ID=?",
				new String[] { String.valueOf(id) }, null,
				null, null);
		if (cursor.moveToFirst()) {
			college = new AppCollege();
			college.setCUniversityId(cursor.getInt(cursor
					.getColumnIndex("c_University_ID")));
			college.setCUniversityName(cursor.getString(cursor
					.getColumnIndex("c_University_name")));
			college.setCUniversityPinyin(cursor.getString(cursor
					.getColumnIndex("c_University_pinyin")));
			college.setCProvinceName(cursor.getString(cursor
					.getColumnIndex("c_Province_name")));
			college.setCCreateTime(new Timestamp(cursor.getLong(cursor
					.getColumnIndex("c_Create_time"))));
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return college;
	}
}
