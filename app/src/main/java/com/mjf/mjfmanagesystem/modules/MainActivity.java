package com.mjf.mjfmanagesystem.modules;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.base.BaseActivity;
import com.mjf.mjfmanagesystem.entity.UserInfo;
import com.mjf.mjfmanagesystem.fragment.AddFragment;
import com.mjf.mjfmanagesystem.fragment.MyFragment;
import com.mjf.mjfmanagesystem.fragment.SelectFragment;
import com.mjf.mjfmanagesystem.sqlite.RecodeGpsListSQLHelper;
import com.mjf.mjfmanagesystem.util.CommonUtil;
import com.mjf.mjfmanagesystem.view.CustomFragmentAdapter;
import com.mjf.mjfmanagesystem.view.NonSwipeableViewPager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jxl.Workbook;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.view_bottom_line) View viewBottomLine;
    @InjectView(R.id.rb_select) RadioButton rbSelect;
    @InjectView(R.id.rb_add) RadioButton rbAdd;
    @InjectView(R.id.rb_modify) RadioButton rbModify;
    @InjectView(R.id.rb_delete) RadioButton rbDelete;
    @InjectView(R.id.rb_me) RadioButton rbMe;
    @InjectView(R.id.rg_main) RadioGroup rgMain;
    @InjectView(R.id.pager) NonSwipeableViewPager mPager;

    int[] values = {R.id.rb_select, R.id.rb_add, R.id.rb_me};
    RadioButton[] radioButtons = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        addNewPermission();
        radioButtons = new RadioButton[]{rbSelect, rbAdd,  rbMe};
        try {

            List<Fragment> listFragment = new ArrayList<>();
            listFragment.add(new SelectFragment());
            listFragment.add(new AddFragment());
//            listFragment.add(new SelectFragment());
//            listFragment.add(new AddFragment());
            listFragment.add(new MyFragment());

            mPager.setOffscreenPageLimit(values.length);
            mPager.setOnPageChangeListener(new MyPagerChangeListener());
            mPager.setAdapter(new CustomFragmentAdapter(getSupportFragmentManager(), listFragment));
            rgMain.setOnCheckedChangeListener(new MyCheckedListener());
            rgMain.check(values[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class MyCheckedListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            for (int i = 0; i < values.length; i++) {
                if (checkedId == values[i]) {

                    //分类的时候，里面的内容也直接定位分类
//                    if (i == 1) {
//                        EventBus.getDefault().post(new NavEvent(NavEvent.FIRST));
//                    }

                    mPager.setCurrentItem(i, false);
                    break;
                }
            }
        }
    }
    /**
     * ViewPager 滑动监听事件
     */
    private class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 滑动时，取消RadioGroup选中监听时间
            rgMain.setOnCheckedChangeListener(null);
            rgMain.check(values[position]);
            // 滑动完成后，重新绑定选中监听
            rgMain.setOnCheckedChangeListener(new MyCheckedListener());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public static final int FILE_SELECT_CODE = 502;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String path = getPath(this, uri);
                    if(path !=null && (path.contains(".xls") || path.contains(".csv"))){
//                        toast(path);
                        getExcelData(path);
                    }else{
                        toast("文件格式错误，请重新选择");
                    }

                }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void getExcelData(String path){
        try {
            InputStream is = new FileInputStream(path);
            Workbook book = Workbook.getWorkbook(is);
            int num = book.getNumberOfSheets();
            // 获得第一个工作表对象
            jxl.Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            int Cols = sheet.getColumns();

            for (int i = 1; i < Rows; i++) {
                UserInfo userInfo = new UserInfo();
                for (int j = 0; j < 6; j++) {
                    // getCell(Col,Row)获得单元格的值
                    if( sheet.getCell(j,i) !=null){
                        if(j==0){
                            userInfo.username = sheet.getCell(j,i).getContents();
                        }else if(j==1){
                            userInfo.sex = sheet.getCell(j,i).getContents();
                        }else if(j==2){
                            userInfo.phone = sheet.getCell(j,i).getContents();
                        }else if(j==3){
                            userInfo.idcard = sheet.getCell(j,i).getContents();
                        }else if(j==4){
                            userInfo.isVip = sheet.getCell(j,i).getContents();
                        }else if(j==5){
                            userInfo.business = sheet.getCell(j,i).getContents();
                        }
                    }
                }
                addUserInfo(userInfo);
            }
            book.close();
            toast("导入成功");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void addUserInfo(UserInfo userInfo){
        RecodeGpsListSQLHelper mHelper = new RecodeGpsListSQLHelper(mContext);
        if (CommonUtil.isNUll(userInfo.phone)) {
            return;
        }
        if (CommonUtil.isNotNUll(mHelper.getUserInfoByPhone(userInfo.phone))) {
            //toast("该手机号已被添加");
            return;
        }
        if(userInfo.phone !=null && userInfo.phone.length()==12){
            userInfo.phone = userInfo.phone.substring(1);
        }
        if(userInfo.idcard !=null && userInfo.idcard.length()==19){
            userInfo.phone = userInfo.phone.substring(1);
        }
        String[] businessList = {"49元飞YOUNG纯流量卡", "58元全球通套餐", "100元乐享4G套餐", "288元至尊VIP专属套装"};
        if("0".equals(userInfo.business)){
            userInfo.business = businessList[0];
        }else if("1".equals(userInfo.business)){
            userInfo.business = businessList[1];
        }else if("2".equals(userInfo.business)){
            userInfo.business = businessList[2];
        }else if("3".equals(userInfo.business)){
            userInfo.business = businessList[3];
        }
        userInfo.createTime = CommonUtil.getCurrentTime();
        boolean success = mHelper.saveUserInfo(userInfo);
    }

    public static String getPath(Context context, Uri uri) {

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection,null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }

        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    private void addNewPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }
}
