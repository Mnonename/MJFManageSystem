package com.mjf.mjfmanagesystem.modules;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.base.BaseActivity;
import com.mjf.mjfmanagesystem.entity.UserInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {
    public static final String SearchResultKEY = "DetailActivity";
    @InjectView(R.id.iv_back) ImageView ivBack;
    @InjectView(R.id.rl_title) RelativeLayout rlTitle;
    @InjectView(R.id.et_username) EditText etUsername;
    @InjectView(R.id.iv_sex_male) ImageView ivSexMale;
    @InjectView(R.id.iv_sex_female) ImageView ivSexFemale;
    @InjectView(R.id.et_phone) EditText etPhone;
    @InjectView(R.id.et_idcard) EditText etIdcard;
    @InjectView(R.id.iv_is_vip) ImageView ivIsVip;
    @InjectView(R.id.iv_is_not_vip) ImageView ivIsNotVip;
    @InjectView(R.id.et_business) TextView etBusiness;
    @InjectView(R.id.btn_add) Button btnAdd;
    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.inject(this);
        userInfo = (UserInfo) getIntent().getSerializableExtra(SearchResultKEY);
        etUsername.setText(userInfo.username);
        if("1".equals(userInfo.sex)){
            ivSexMale.setImageResource(R.mipmap.radio_checked);
            ivSexFemale.setImageResource(R.mipmap.radio);
        }else{
            ivSexFemale.setImageResource(R.mipmap.radio_checked);
            ivSexMale.setImageResource(R.mipmap.radio);
        }
        etPhone.setText(userInfo.phone);
        etIdcard.setText(userInfo.idcard);
        if("1".equals(userInfo.isVip)){
            ivIsVip.setImageResource(R.mipmap.radio_checked);
            ivIsNotVip.setImageResource(R.mipmap.radio);
        }else{
            ivIsNotVip.setImageResource(R.mipmap.radio_checked);
            ivIsVip.setImageResource(R.mipmap.radio);
        }
        etBusiness.setText(userInfo.business);
    }
    public static void newIntent(Context context, UserInfo userInfo) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(SearchResultKEY, userInfo);
        context.startActivity(intent);
    }
    @OnClick({(R.id.iv_back)})
    public void setIvBack() {
        finish();
    }
}
