package com.mjf.mjfmanagesystem.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.adapter.SearchResultAdapter;
import com.mjf.mjfmanagesystem.base.BaseActivity;
import com.mjf.mjfmanagesystem.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 查询结果页面
 */
public class SearchResultActivity extends BaseActivity {
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
}
