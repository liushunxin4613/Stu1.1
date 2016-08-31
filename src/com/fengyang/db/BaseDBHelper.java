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
 * �̳�SQLiteOpenHelper�Ļ�����
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
	 * �жϱ��Ƿ����
	 * 
	 * @param db
	 *            һ��SQLiteDatabaseʵ��
	 * @param tableName
	 *            Ҫ��ѯ�ı���
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
	 * �ж����ݿ��ļ��Ƿ����
	 * 
	 * @param table_name
	 *            ���ݿ��еı���
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
	 * ��assetĿ¼����DB�ļ������ݿ�Ŀ¼��
	 * 
	 * @param DBName
	 *            ���ݿ���
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
