package com.fengyang.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengyang.db.StableDBHelper.StableTableDAO;
import com.fengyang.entity.AppSecondClass;

public class SecondClassDAO implements StableTableDAO<AppSecondClass>{

	StableDBHelper DBHelper;

	public SecondClassDAO(Context context) {
		DBHelper = new StableDBHelper(context, null);
	}

	/**
	 * 依据父类型查询所有子类型
	 * 
	 * @param parentId
	 * @return
	 */
	public ArrayList<AppSecondClass> queryByParent(int parentId) {
		ArrayList<AppSecondClass> list = new ArrayList<AppSecondClass>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_SECOND_CLASS, null,
				"class_parent=?", new String[] { String.valueOf(parentId) },
				null, null, "class_id");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(cursor.getColumnIndex("class_id"));
			int level = cursor.getInt(cursor.getColumnIndex("class_level"));
			String classStr = cursor.getString(cursor.getColumnIndex("class"));
			list.add(new AppSecondClass(id, level, parentId, classStr));
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 依据id查询二手物品类别
	 * @param id
	 * @return
	 */
	public AppSecondClass queryById(int id) {
		AppSecondClass secondClass = null;
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_SECOND_CLASS, null,
				"class_id=?", new String[] { String.valueOf(id) }, null, null,
				"class_id");
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			int level = cursor.getInt(cursor.getColumnIndex("class_level"));
			int parent = cursor.getInt(cursor.getColumnIndex("class_parent"));
			String classStr = cursor.getString(cursor.getColumnIndex("class"));
			secondClass = new AppSecondClass(id, level, parent, classStr);
		}
		cursor.close();
		db.close();
		return secondClass;
	}

	@Override
	public int getTotalLevel() {
		return 2;
	}

	@Override
	public List<String> parseShowList(List<AppSecondClass> list) {
		ArrayList<String> strList = new ArrayList<String>();
		for (AppSecondClass appSecondClass : list) {
			strList.add(appSecondClass.getClass_());
		}
		return strList;
	}

	@Override
	public int parseId(AppSecondClass t) {
		return t.getClassId();
	}

	@Override
	public List<AppSecondClass> queryByKey(String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}
}
