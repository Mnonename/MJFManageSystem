package com.mjf.mjfmanagesystem.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mjf.mjfmanagesystem.entity.ManagerUserInfo;
import com.mjf.mjfmanagesystem.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

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
				"sex"+ " varchar,"+
				"phone"+ " varchar,"+
				"username"+ " varchar,"+
				"idcard"+ " varchar,"+
				"createTime"+ " varchar,"+
				"isVip"+ " varchar,"+
				"business"+ " varchar"+
				")"
		);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 	db.execSQL( "DROP TABLE IF EXISTS " + TB_SYSTEM_USER_INFO );
	        onCreate(db);
	        Log. e("Database" ,"onUpgrade" );
	}
	//对管理人员进行操作
	 public boolean saveSystemUserInfo(ManagerUserInfo userInfo) {
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
	public ManagerUserInfo getManagerUserInfo(String userCode){
		SQLiteDatabase db = getReadableDatabase();
		String password = "";
		ManagerUserInfo managerUserInfo = new ManagerUserInfo();
		Cursor cursor = db.rawQuery("select * from system_user_info where userCode=? ", new String[]{userCode});
		while (cursor.moveToNext()) {
			managerUserInfo.ID = cursor.getString(0); //获取第一列的值,第一列的索引从0开始
			managerUserInfo.userCode = cursor.getString(1);
			managerUserInfo.password = cursor.getString(2);
			managerUserInfo.phone = cursor.getString(3);
			managerUserInfo.username = cursor.getString(4);
			managerUserInfo.idcard = cursor.getString(5);
			managerUserInfo.status = cursor.getString(6);
		}
		cursor.close();
		db.close();
		return managerUserInfo;
	}
	 public int deleteSystemUser(String userCode){
		 SQLiteDatabase db =getWritableDatabase();
		int num = db.delete(TB_SYSTEM_USER_INFO, new String("userCode" + " =? "),
				new String[] { userCode });
		 db.close();
		 return num;
	 }
	//对人员进行操作
	public boolean saveUserInfo(UserInfo userInfo) {
		ContentValues cv=new ContentValues();
		cv.put("userCode", userInfo.userCode);
		cv.put("phone", userInfo.phone);
		cv.put("username", userInfo.username);
		cv.put("idcard", userInfo.idcard);
		cv.put("sex", userInfo.sex);
		cv.put("createTime", userInfo.createTime);
		cv.put("isVip", userInfo.isVip);
		cv.put("business", userInfo.business);
		long insert = getWritableDatabase().insert(TB_USER_INFO, "phone", cv);
		if(insert==-1){
			return false;
		}
		return true;
	}
	public List<UserInfo> getUserInfoList(UserInfo serachUserInfo,int pageSize){
		SQLiteDatabase db = getReadableDatabase();
		String mUsername = serachUserInfo.username;
		String mSex = serachUserInfo.sex;
		String mPhone = serachUserInfo.phone;
		String mIdcard =serachUserInfo.idcard;
		String mVip = serachUserInfo.isVip;
		String mBusiness = serachUserInfo.business;
		StringBuffer sql = new StringBuffer();
		sql.append("select * from user_info where 1=1 ");
		List<String> mList = new ArrayList<>();
		if(mUsername !=null && mUsername.length()>0){
			sql.append(" and username like ? ");
			mList.add("%"+ mUsername +"%");
		}
		if(mSex !=null && mSex.length()>0){
			sql.append(" and sex = ? ");
			mList.add(mSex);
		}
		if(mPhone !=null && mPhone.length()>0){
			sql.append(" and phone like ? ");
			mList.add("%"+mPhone+"%");
		}
		if(mIdcard !=null && mIdcard.length()>0){
			sql.append(" and idcard like ? ");
			mList.add("%"+mIdcard+"%");
		}
		if(mVip !=null && mVip.length()>0){
			sql.append(" and isVip = ? ");
			mList.add(mVip);
		}
		if(mBusiness !=null && mBusiness.length()>0){
			sql.append(" and business = ? ");
			mList.add(mBusiness);
		}
		sql.append("Limit 10 Offset " +(pageSize-1)*10);
		String[] strings = null;
		if(mList.size()>0){
			strings = new String[mList.size()];
			for (int i = 0; i < mList.size(); i++) {
				strings[i] = mList.get(i);
			}
		}


		Cursor cursor = db.rawQuery(sql.toString(), strings);
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		while (cursor.moveToNext()) {
			String ID = cursor.getString(0); //获取第一列的值,第一列的索引从0开始
			String userCode = cursor.getString(1); //获取第一列的值,第一列的索引从0开始
			String sex = cursor.getString(2);//获取第二列的值
			String phone = cursor.getString(3);//获取第三列的值
			String username = cursor.getString(4);//获取第三列的值
			String idcard = cursor.getString(5);//获取第三列的值
			String createTime = cursor.getString(6);//获取第三列的值
			String isVip = cursor.getString(7);//获取第三列的值
			String business = cursor.getString(8);//获取第三列的值
			UserInfo userInfo = new UserInfo();
			userInfo.ID = ID;
			userInfo.userCode = userCode;
			userInfo.sex = sex;
			userInfo.phone = phone;
			userInfo.username = username;
			userInfo.idcard = idcard;
			userInfo.createTime =createTime;
			userInfo.isVip = isVip;
			userInfo.business = business;
			userInfoList.add(userInfo);
		}
		cursor.close();
		db.close();
		return userInfoList;
	}

}
