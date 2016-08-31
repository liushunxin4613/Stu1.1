package com.fengyang.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 继承SQLiteOpenHelper的基础类
 * 
 * @author HeJie
 *
 */
public abstract class BaseDBHelper extends SQLiteOpenHelper {

	private static final String TAG = "StuDBHelper";

	public Context context;

	public BaseDBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		this.context = context;
	}

	public BaseDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	/**
	 * 判断表是否存在
	 * 
	 * @param db
	 *            一个SQLiteDatabase实例
	 * @param tableName
	 *            要查询的表名
	 * @return
	 */
	protected boolean IsTableExist(SQLiteDatabase db, String tableName) {
		String sql = "select Count(*) from sqlite_master where type=\"table\" and name=\""
				+ tableName + "\"";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			int count = cursor.getInt(0);
			Log.i(TAG, "check table count = " + count);
			if (count > 0)
				return true;
		}
		if (cursor != null)
			cursor.close();
		return false;
	}

	/**
	 * 判断数据库文件是否存在
	 * 
	 * @param table_name
	 *            数据库中的表名
	 * @return
	 */
	protected boolean checkDBFile(String table_name) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "select Count(*) from sqlite_master where type=\"table\" and name=\""
				+ table_name + "\"";
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			int count = cursor.getInt(0);
			Log.i(TAG, "check table count = " + count);
			if (count > 0)
				return true;
		}
		if (cursor != null)
			cursor.close();
		return false;
	}

	/**
	 * 从asset目录拷贝DB文件到数据库目录下
	 * 
	 * @param DBName
	 *            数据库名
	 */
	protected void copyDBFile() {
		Log.i(TAG, "copy DB File " + getDatabaseName());
		InputStream dbIs = null;
		FileOutputStream desOs = null;
		try {
			dbIs = context.getAssets().open(getDatabaseName() + ".db");
			File desDir = context.getDatabasePath(getDatabaseName());
			desOs = new FileOutputStream(desDir);
			if (!desDir.exists()) {
				desDir.mkdirs();
			}
			byte[] buffer = new byte[8192];
			int count;
			while ((count = dbIs.read(buffer)) > 0) {
				desOs.write(buffer, 0, count);
				desOs.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dbIs != null)
					dbIs.close();
				if (desOs != null)
					desOs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
