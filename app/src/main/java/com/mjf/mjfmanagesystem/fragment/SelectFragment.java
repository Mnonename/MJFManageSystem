package com.mjf.mjfmanagesystem.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.entity.UserInfo;
import com.mjf.mjfmanagesystem.modules.SearchResultActivity;
import com.mjf.mjfmanagesystem.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class SelectFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
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
    @InjectView(R.id.btn_seleet) Button btnSeleet;

    private String mParam1;
    private String mParam2;

    private String mSex = "";
    private String mIsVip = "";

    public SelectFragment() {
        // Required empty public constructor
    }

    public static SelectFragment newInstance(String param1, String param2) {
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
    }

    @OnClick(R.id.btn_seleet)
    public void setBtnSelect() {
        List<UserInfo> list = new ArrayList<>();
        UserInfo userInfo = new UserInfo();
        String username =  etUsername.getText().toString();
        String phone = etPhone.getText().toString();
        String idcard = etIdcard.getText().toString();
        String business = etBusiness.getText().toString();
        if(CommonUtil.isNotNUll(username)){
            userInfo.username =username;
        }
        if(CommonUtil.isNotNUll(phone)){
            userInfo.phone =phone;
        }
        if(CommonUtil.isNotNUll(idcard)){
            userInfo.idcard =idcard;
        }
        if(CommonUtil.isNotNUll(business)){
            userInfo.business =business;
        }
        if(CommonUtil.isNotNUll(mSex)){
            userInfo.sex = mSex;
        }
        if(CommonUtil.isNotNUll(mIsVip)){
            userInfo.isVip = mIsVip;
        }


        SearchResultActivity.newIntent(getActivity(),userInfo);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.iv_sex_male)
    public void setIvSexMale() {
        ivSexMale.setImageResource(R.mipmap.radio_checked);
        ivSexFemale.setImageResource(R.mipmap.radio);
        mSex = "1";
    }

    @OnClick(R.id.iv_is_vip)
    public void setIvIsVip() {
        ivIsVip.setImageResource(R.mipmap.radio_checked);
        ivIsNotVip.setImageResource(R.mipmap.radio);
        mIsVip = "1";
    }

    @OnClick(R.id.iv_is_not_vip)
    public void setIvIsNotVip() {
        ivIsVip.setImageResource(R.mipmap.radio);
        ivIsNotVip.setImageResource(R.mipmap.radio_checked);
        mIsVip = "0";
    }

    @OnClick(R.id.iv_sex_female)
    public void setIvSexFemale() {
        ivSexMale.setImageResource(R.mipmap.radio);
        ivSexFemale.setImageResource(R.mipmap.radio_checked);
        mSex = "0";
    }
}
