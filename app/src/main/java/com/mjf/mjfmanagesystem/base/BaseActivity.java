package com.mjf.mjfmanagesystem.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mjf.mjfmanagesystem.sqlite.RecodeGpsListSQLHelper;

import java.util.HashMap;

public class BaseActivity extends FragmentActivity {
    protected Context mContext;
    protected Activity mActivity;
    protected LayoutInflater mInflater;

    protected final int mPageSize = 10;
    protected final int FIRST_PAGE_NO = 1;
    protected int mPageNo = FIRST_PAGE_NO;
    protected int mTotalNo = 0;
    protected boolean mIsLoading = false;
    protected ProgressDialog mDialog;
    protected RecodeGpsListSQLHelper mHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        mActivity = this;
        mInflater = LayoutInflater.from(this);
        createLoading();
        mHelper = new RecodeGpsListSQLHelper(mContext);
    }
    //创建loading加载框
    protected void createLoading() {

        if (null != mActivity) {
            if (mDialog == null && !mActivity.isFinishing()) {
                mDialog = new ProgressDialog(mActivity);
                mDialog.setMessage("加载中...");
                mDialog.setCancelable(true);
                mDialog.setCanceledOnTouchOutside(true);
            }
        }
    }

    //隐藏对话框
    public void hideDialog() {
        try {
            if (mDialog != null  && !mActivity.isFinishing()) {
                mDialog.dismiss();
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    //显示对话框
    public void showDialog(){
        try {
            if (mDialog != null &&  !mActivity.isFinishing()) {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //触摸事件
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        try {
//            //Bugtags.onDispatchTouchEvent(this, ev);
//            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//                View v = getCurrentFocus();
//                if (shouleHideIM(v, ev)) {
//                    CommonUtil.hideIME(mContext, v);
//                    hideIMECallBack();
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return true;
//        }
//    }

    protected void hideIMECallBack() {
    }

    protected boolean shouleHideIM(View v, MotionEvent ev) {
        if (null != v && v instanceof EditText) {

            // 获取当前edittext在屏幕中的位置
            int[] location = {0, 0};
            v.getLocationInWindow(location);// x:location[0] y:location[1]
            int left = location[0], top = location[1];
            int bottom = top + v.getHeight(), right = left + v.getWidth();

            // 判断点击时间是否在输入框区域，true就不隐藏，false代表发生在区域外，需隐藏软键盘
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    protected void toast(String message) {
        Toast.makeText(mContext,message, Toast.LENGTH_LONG).show();
    }



    protected void toast(Object message) {
        toast(String.valueOf(message));
    }

    protected void toast(int resid) {
        toast(getString(resid));
    }

    /**
     * 跳转到指定的Activity 默认界面
     *
     * @param cls
     */
    protected void startNewActivity(Class<?> cls) {
        startNewActivity(cls, false);
    }


    /**
     * 跳转到指定的Activity
     *
     * @param cls
     */
    protected void startNewActivity(Class<?> cls, boolean isFinish) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        if (isFinish) {
            this.finish();
        }
    }

    /**
     * 跳转到指定的Activity
     *
     * @param cls
     */
    protected void startNewActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }

    protected HashMap<String, Object> getParams() {
        return new HashMap<String, Object>();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        //Bugtags.onPause(this);
        super.onPause();
    }



}
