package com.fengyang.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * Created by HeJie on 2015/1/21.
 */
public class StuDBHelper extends BaseDBHelper {

    private static final String TAG = "StuDBHelper";

    /**
     * 数据库名
     */
    public static final String DB_NAME = "user_info";
    /**
     * 用户信息表名
     */
    public static final String TABLE_NAME_USER_INFO = "user";
    /**
     * 用户详情表名
     */
    public static final String TABLE_NAME_USER_DETAIL = "user_detail";
    /**
     * 用户收藏兼职表名
     */
    public static final String TABLE_NAME_FAVORITE_PART = "user_part_favorite";
    /**
     * 用户收藏二手表名
     */
    public static final String TABLE_NAME_FAVORITE_SECOND = "user_second_favorite";
    /**
     * 用户发布兼职历史表名
     */
    public static final String TABLE_NAME_PUBLISH_PART = "publish_part";
    /**
     * 用户发布二手历史表名
     */
    public static final String TABLE_NAME_PUBLISH_SECOND = "publish_second";
    /**
     * 创建用户表的sql语句
     */
    public static final String CREATE_TABLE_USER = "create table " + 
    		TABLE_NAME_USER_INFO 
    			+ "(id INTEGER Primary Key,"
    			+ "c_name VARCHAR(50)," 
    			+ "c_email VARCHAR(100),"
    			+ "c_email_bind INTEGER,"
    			+ "c_phone VARCHAR(11)," 
    			+ "c_phone_bind INTEGER," 
    			+ "c_photoPath VARCHAR(50),"
    			+ "c_state INTEGER," 
    			+ "c_password VARCHAR(32)," 
    			+ "c_paypwd VARCHAR(32)," 
    			+ "c_isVerify INTEGER," 
    			+ "c_points INTEGER," 
    			+ "c_exppoints INTEGER," 
    			+ "c_informAllow INTEGER," 
    			+ "c_isBuy INTEGER," 
    			+ "c_isAllowTalk INTEGER," 
    			+ "c_userType INTEGER," 
    			+ "c_update_time TIMESTAMP )";
    /**
     * 创建用户详情表的sql语句
     */
    public static final String CREATE_TABLE_USER_DETAIL = "create table " + 
            TABLE_NAME_USER_DETAIL 
            	+ "(id INTEGER Primary Key," 
            	+ "c_true_name VARCHAR(20)," 
            	+ "c_sex INTEGER,"
            	+ "c_birthday VARCHAR(14)," 
            	+ "c_qqNO VARCHAR(100)," 
            	+ "c_registTime VARCHAR(20)," 
            	+ "c_brifIntrodction TEXT)";
    /**
     * 创建用户收藏信息表
     */
    public static final String CREATE_TABLE_USER_FAVORITE_PART = "create table " + 
            TABLE_NAME_FAVORITE_PART
            		+ "(id INTEGER Primary Key,"
            		+ "collecter_id INTEGER,"
            		+ "part_id INTEGER,"
            		+ "part_name VARCHAR(100),"
            		+ "part_publisher VARCHAR(50),"
            		+ "part_publisher_type INTEGER,"
            		+ "publish_time VARCHAR(20),"
            		+ "pay DECIMAL(8,2),"
            		+ "pay_unit INTEGER,"
            		+ "pay_way INTEGER,"
            		+ "time_type SMALLINT,"
            		+ "add_time VARCHAR(20),"
            		+ "UNIQUE (collecter_id, part_id)"
            		+ ")";
    /**
     * 创建用户收藏信息表
     */
    public static final String CREATE_TABLE_USER_FAVORITE_SECOND = "create table " + 
            TABLE_NAME_FAVORITE_SECOND
            		+ "(id INTEGER Primary Key,"
                    + "collecter_id INTEGER,"
            		+ "second_id INTEGER,"
            		+ "second_name VARCHAR(100),"
            		+ "image_path VARCHAR(250),"
            		+ "second_publisher_type INTEGER,"
            		+ "publish_time VARCHAR(20),"
            		+ "price DECIMAL(8,2),"
            		+ "add_time VARCHAR(20),"
            		+ "UNIQUE (collecter_id, second_id)"
            		+ ")";
    /**
     * 创建用户发布兼职历史表
     */
    public static final String CREATE_TABLE_PUBLISH_PART = "create table " + 
            TABLE_NAME_PUBLISH_PART 
            		+ "(id INTEGER Primary Key,"
            		+ "user_id INTEGER,"
            		+ "part_id INTEGER UNIQUE,"
            		+ "part_name VARCHAR(100),"
            		+ "publish_time VARCHAR(20),"
            		+ "pay DECIMAL(8,2),"
            		+ "pay_unit INTEGER,"
            		+ "pay_way INTEGER,"
            		+ "time_type INTEGER"
            		+ ")";
    /**
     * 创建用户发布二手物品历史表
     */
    public static final String CREATE_TABLE_PUBLISH_SECOND = "create table " + 
            TABLE_NAME_PUBLISH_SECOND 
            		+ "(id INTEGER Primary Key,"
            		+ "user_id INTEGER,"
            		+ "second_id INTEGER UNIQUE,"
            		+ "second_name VARCHAR(100),"
            		+ "image_path VARCHAR(250),"
            		+ "publish_time VARCHAR(20),"
            		+ "price DECIMAL(8,2)"
            		+ ")";
    
    /**
     * @param context
     * @param factory
     */
    public StuDBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!IsTableExist(db, TABLE_NAME_USER_INFO)) {
            Log.i(TAG, CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_USER);
        }
        if (!IsTableExist(db, TABLE_NAME_USER_DETAIL)) {
            Log.i(TAG, CREATE_TABLE_USER_DETAIL);
            db.execSQL(CREATE_TABLE_USER_DETAIL);
        }
        if (!IsTableExist(db, TABLE_NAME_FAVORITE_PART)){
        	Log.i(TAG, CREATE_TABLE_USER_FAVORITE_PART);
            db.execSQL(CREATE_TABLE_USER_FAVORITE_PART);
        }
        if (!IsTableExist(db, TABLE_NAME_FAVORITE_SECOND)){
        	Log.i(TAG, CREATE_TABLE_USER_FAVORITE_SECOND);
        	db.execSQL(CREATE_TABLE_USER_FAVORITE_SECOND);
        }
        if (!IsTableExist(db, TABLE_NAME_PUBLISH_PART)){
        	Log.i(TAG, CREATE_TABLE_PUBLISH_PART);
        	db.execSQL(CREATE_TABLE_PUBLISH_PART);
        }
        if (!IsTableExist(db, TABLE_NAME_PUBLISH_SECOND)){
        	Log.i(TAG, CREATE_TABLE_PUBLISH_SECOND);
        	db.execSQL(CREATE_TABLE_PUBLISH_SECOND);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
