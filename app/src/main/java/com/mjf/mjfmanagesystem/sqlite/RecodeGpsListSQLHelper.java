package com.mjf.mjfmanagesystem.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mjf.mjfmanagesystem.entity.ManagerUserInfo;

public class RecodeGpsListSQLHelper extends SQLiteOpenHelper{

	 private static final String TB_SYSTEM_USER_INFO = "system_user_info"; //表名称---  系统管理员表
	private static final String TB_USER_INFO = "user_info"; //表名称-- 用户表
     private static final int version = 1; //数据库版本 
	public RecodeGpsListSQLHelper(Context context) {
		super(context, "manager_system.db", null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL( "CREATE TABLE IF NOT EXISTS "+
						TB_SYSTEM_USER_INFO+ "("+
                "ID"+ " integer primary key,"+
                "userCode"+ " varchar,"+
                "password"+ " varchar,"+
                "phone"+ " varchar,"+
                "username"+ " varchar,"+
                "idcard"+ " varchar,"+
                "status"+ " varchar"+
                ")"
                );
		db.execSQL( "CREATE TABLE IF NOT EXISTS "+
				TB_USER_INFO+ "("+
				"ID"+ " integer primary key,"+
				"userCode"+ " varchar,"+
				"password"+ " varchar,"+
				"phone"+ " varchar,"+
				"username"+ " varchar,"+
				"idcard"+ " varchar,"+
				"status"+ " varchar"+
				")"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 	db.execSQL( "DROP TABLE IF EXISTS " + TB_SYSTEM_USER_INFO );
	        onCreate(db);
	        Log. e("Database" ,"onUpgrade" );
		
	}
	
	 public boolean saveUserInfo(ManagerUserInfo userInfo) {
	        ContentValues cv=new ContentValues();  
	        cv.put("userCode", userInfo.userCode);
	        cv.put("password", userInfo.password);
	        cv.put("phone", userInfo.phone);
	        cv.put("username", userInfo.username);
	        cv.put("idcard", userInfo.idcard);
	        cv.put("status", userInfo.status);

	        long insert = getWritableDatabase().insert(TB_SYSTEM_USER_INFO, "userCode", cv);
	        if(insert==-1){
	        	return false;
	        }
	        return true;
	    } 
	 
	 public String getPassword(String userCode){
		 
		    SQLiteDatabase db = getReadableDatabase();  
		      String password = "";
		    Cursor cursor = db.rawQuery("select password from system_user_info where userCode=? ", new String[]{userCode});
		    while (cursor.moveToNext()) {
		    	 password = cursor.getString(0); //获取第一列的值,第一列的索引从0开始
		    }
		    cursor.close();
		      
		    db.close();  
		 
		 return password;
	 }
	 public int deleteUser(String userCode){
		 SQLiteDatabase db =getWritableDatabase();
		int num = db.delete(TB_SYSTEM_USER_INFO, new String("userCode" + " =? "),
				new String[] { userCode });
		 db.close();  
		 
		 return num;
	 }

}
