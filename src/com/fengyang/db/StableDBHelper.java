package com.fengyang.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class StableDBHelper extends BaseDBHelper{

	public static final String DB_NAME = "stu_college";
    /**
     * 兼职分类表名
     */
    public static final String TABLE_NAME_PART_CLASS = "part_class";
    /**
     * 二手物品分类表名
     */
    public static final String TABLE_NAME_SECOND_CLASS = "second_class";
    /**
     * 分类的父类为空的id
     */
    public static final int CLASS_PARENT_ROOT = 0;

	public static final String TABLE_NAME_COLLEGE = "t_college";
	
	public static final String TABLE_NAME_AREA = "t_area";

//	private static final String TAG = "CollegeDBHelper";

	public StableDBHelper(Context context, CursorFactory factory) {
		super(context, DB_NAME, factory, 1);
		if (!checkDBFile(TABLE_NAME_COLLEGE)) {
			copyDBFile();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public interface StableTableDAO<T> {
		/**
		 * 通过父类id查询所有子类
		 * @param parentId
		 * @return
		 */
		public List<T> queryByParent(int parentId);
		/**
		 * 通过id查询
		 * @param id
		 * @return
		 */
		public T queryById(int id);
		/**
		 * 通过关键字查询
		 * @param keyWord 关键字
		 * @return
		 */
		public List<T> queryByKey(String keyWord);
		/**
		 * 解析得到父类id
		 * @return
		 */
		public int parseId(T t);
		/**
		 * 得到泛型列表所对应的显示列表
		 * @param list
		 * @return
		 */
		public List<String> parseShowList(List<T> list);
		/**
		 * 获取表的层级数
		 * @return
		 */
		public int getTotalLevel();
	}

}
