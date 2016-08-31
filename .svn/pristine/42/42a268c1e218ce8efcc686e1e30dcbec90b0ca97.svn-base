package com.fengyang.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengyang.db.StableDBHelper.StableTableDAO;
import com.fengyang.entity.AppPartClass;

public class PartClassDAO implements StableTableDAO<AppPartClass> {

	StableDBHelper DBHelper;

	public PartClassDAO(Context context) {
		DBHelper = new StableDBHelper(context, null);
	}

	/**
	 * 查询所有的兼职类型
	 * 
	 * @return
	 */
	@Override
	public ArrayList<AppPartClass> queryByParent(int parentId) {
		ArrayList<AppPartClass> list = new ArrayList<AppPartClass>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_PART_CLASS, null,
				"class_parent=?", new String[] { String.valueOf(parentId) },
				null, null, "class_id");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(cursor.getColumnIndex("class_id"));
			int level = cursor.getInt(cursor.getColumnIndex("class_level"));
			int parent = cursor.getInt(cursor.getColumnIndex("class_parent"));
			String classStr = cursor.getString(cursor.getColumnIndex("class"));
			list.add(new AppPartClass(id, level, parent, classStr));
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 依据id查询兼职类型
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public AppPartClass queryById(int id) {
		AppPartClass partClass = null;
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_PART_CLASS, null,
				"class_id=?", new String[] { String.valueOf(id) }, null, null,
				"class_id");
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			int level = cursor.getInt(cursor.getColumnIndex("class_level"));
			int parent = cursor.getInt(cursor.getColumnIndex("class_parent"));
			String classStr = cursor.getString(cursor.getColumnIndex("class"));
			partClass = new AppPartClass(id, level, parent, classStr);
		}
		cursor.close();
		db.close();
		return partClass;
	}

	@Override
	public int getTotalLevel() {
		return 1;
	}

	@Override
	public List<String> parseShowList(List<AppPartClass> list) {
		ArrayList<String> strList = new ArrayList<String>();
		for (AppPartClass appPartClass : list) {
			strList.add(appPartClass.getClass_());
		}
		return strList;
	}

	@Override
	public int parseId(AppPartClass t) {
		return t.getClassId();
	}

	@Override
	public List<AppPartClass> queryByKey(String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}
}
