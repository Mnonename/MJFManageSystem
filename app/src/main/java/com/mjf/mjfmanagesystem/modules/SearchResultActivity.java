package com.mjf.mjfmanagesystem.modules;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.adapter.SearchResultAdapter;
import com.mjf.mjfmanagesystem.base.BaseActivity;
import com.mjf.mjfmanagesystem.entity.UserInfo;
import com.mjf.mjfmanagesystem.sqlite.RecodeGpsListSQLHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 查询结果页面
 */
public class SearchResultActivity extends BaseActivity implements SearchResultAdapter.OnRecyclerViewListener{
    public static final String SearchResultKEY = "searchresultkey";
    @InjectView(R.id.iv_back) ImageView ivBack;
    @InjectView(R.id.rl_title) RelativeLayout rlTitle;
    @InjectView(R.id.lv_main) SuperRecyclerView mRecyclerView;
    private UserInfo userInfo;
    private List<UserInfo> userInfoList;
    private SearchResultAdapter mAdapter;
    private int pageSize = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.inject(this);
        userInfo = (UserInfo) getIntent().getSerializableExtra(SearchResultKEY);
        userInfoList = new ArrayList<>();
        userInfoList = mHelper.getUserInfoList(userInfo,pageSize);
        mAdapter = new SearchResultAdapter(mContext, userInfoList);
        mAdapter.setOnRecyclerViewListener(this);
        //创建布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
//        StringBuffer sb = new StringBuffer();
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                sb.append(list.get(i).username + "  " + list.get(i).phone + "  ");
//            }
//        }
        mRecyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                Handler aa =  new Handler();
                aa.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.hideProgress();
                        mRecyclerView.hideMoreProgress();
//                        userInfoList.addAll(userInfoList);
                        pageSize +=1;
                        userInfoList= mHelper.getUserInfoList(userInfo,pageSize);
                        if(userInfoList.size()<10){
                            mRecyclerView.removeMoreListener();
                        }
                        mAdapter.addAll(userInfoList);
                    }
                },2000);
            }
        },1);
    }

    public static void newIntent(Context context, UserInfo userInfo) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(SearchResultKEY, userInfo);
        context.startActivity(intent);
    }
    @OnClick({(R.id.iv_back)})
    public void setIvBack() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            UserInfo userInfoChanged = (UserInfo) data.getSerializableExtra("userInfo");
            mAdapter.remove(modifyPosition);
            mAdapter.add(userInfoChanged,modifyPosition);
            mAdapter.notifyItemChanged(modifyPosition);
//            toast(userInfoChanged.username);
        }
    }
    private int modifyPosition;

    @Override
    public void onItemClick(int position, View v) {
        modifyPosition = position;
        UserInfo userInfo = mAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra(ModifyActivity.SearchResultKEY, userInfo);
        intent.setClass(mContext,ModifyActivity.class);
        startActivityForResult(intent,0);
    }

    @Override
    public boolean onItemLongClick(int position) {
        UserInfo userInfo = mAdapter.getItem(position);
        showDialog(userInfo,position);
        return true;
    }
    private void showDialog(final UserInfo userInfo, final int position) {
        new AlertDialog.Builder(mContext).setTitle("提示")//设置对话框标题
                .setMessage("是否确认删除？")//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        mAdapter.remove(position);
                        RecodeGpsListSQLHelper mHelper = new RecodeGpsListSQLHelper(mContext);
                        mHelper.deleteUser(userInfo.ID);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
            }

        }).show();//在按键响应事件中显示此对话框
    }
}
