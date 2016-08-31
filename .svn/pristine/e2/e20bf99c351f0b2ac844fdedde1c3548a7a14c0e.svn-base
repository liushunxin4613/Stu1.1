package com.fengyang.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fengyang.db.StableDBHelper.StableTableDAO;
import com.fengyang.entity.OubaArea;

public class AreaDAO implements StableTableDAO<OubaArea> {

	StableDBHelper DBHelper;
	
	public AreaDAO(Context context) {
		DBHelper = new StableDBHelper(context, null);
	}
	
	@Override
	public List<OubaArea> queryByParent(int parentId) {
		ArrayList<OubaArea> list = new ArrayList<OubaArea>();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_AREA, null,
				"c_Area_parent_ID=?", new String[] { String.valueOf(parentId) },
				null, null, "c_Area_sort");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			OubaArea area = new OubaArea();
			area.setAreaId(cursor.getInt(cursor.getColumnIndex("c_Area_ID")));
			area.setAreaName(cursor.getString(cursor.getColumnIndex("c_Area_name")));
			area.setAreaDeep(cursor.getShort(cursor.getColumnIndex("c_Area_deep")));
			area.setAreaSort(cursor.getShort(cursor.getColumnIndex("c_Area_sort")));
			area.setAreaRegion(cursor.getString(cursor.getColumnIndex("c_Area_region")));
			area.setAreaParentId(parentId);
			list.add(area);
			cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}
	
	/**
	 * 查询所有的省份信息
	 * @return
	 */
	public List<OubaArea> queryProvince(){
		return queryByParent(StableDBHelper.CLASS_PARENT_ROOT);
	}

	@Override
	public OubaArea queryById(int id) {
		OubaArea area = new OubaArea();
		SQLiteDatabase db = DBHelper.getReadableDatabase();
		Cursor cursor = db.query(StableDBHelper.TABLE_NAME_AREA, null,
				"c_Area_ID=?", new String[] { String.valueOf(id) },
				null, null, null);
		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			area.setAreaId(id);
			area.setAreaName(cursor.getString(cursor.getColumnIndex("c_Area_name")));
			area.setAreaDeep(cursor.getShort(cursor.getColumnIndex("c_Area_deep")));
			area.setAreaSort(cursor.getShort(cursor.getColumnIndex("c_Area_sort")));
			area.setAreaRegion(cursor.getString(cursor.getColumnIndex("c_Area_region")));
			area.setAreaParentId(cursor.getInt(cursor.getColumnIndex("c_Area_parent_ID")));
		}
		cursor.close();
		db.close();
		return area;
	}

	@Override
	public List<OubaArea> queryByKey(String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int parseId(OubaArea t) {
		return t.getAreaId();
	}

	@Override
	public List<String> parseShowList(List<OubaArea> list) {
		ArrayList<String> listStr = new ArrayList<String>();
		for (OubaArea oubaArea : list) {
			listStr.add(oubaArea.getAreaName());
		}
		return listStr;
	}

	@Override
	public int getTotalLevel() {
		return 3;
	}


}
