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
import android.widget.Toast;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.entity.UserInfo;
import com.mjf.mjfmanagesystem.sqlite.RecodeGpsListSQLHelper;
import com.mjf.mjfmanagesystem.util.CommonUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    @InjectView(R.id.btn_add) Button btnAdd;

    private String mParam1;
    private String mParam2;

    private int sex = 1;// 1 "男";    0 女
    private int isVip = 0;//1 是      0 "否";

    public AddFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.btn_add)
    public void setBtnAdd(){
        String username = etUsername.getText().toString();
        String phone = etPhone.getText().toString();
        String idcard = etIdcard.getText().toString();

        UserInfo userInfo = new UserInfo();
        userInfo.username = username;
        userInfo.phone = phone;
        userInfo.idcard = idcard;
        userInfo.sex =sex+"";
        userInfo.isVip = isVip+"";
        userInfo.createTime = CommonUtil.getCurrentTime();
        RecodeGpsListSQLHelper mHelper = new RecodeGpsListSQLHelper(getActivity());
        boolean success = mHelper.saveUserInfo(userInfo);
        if(success){
            Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(),"保存失败",Toast.LENGTH_LONG).show();
        }
    }
    @OnClick(R.id.iv_sex_male)
    public void setIvSexMale(){
        ivSexMale.setImageResource(R.mipmap.radio_checked);
        ivSexFemale.setImageResource(R.mipmap.radio);
        sex  =1;
    }

    @OnClick(R.id.iv_is_vip)
    public void setIvIsVip(){
        ivIsVip.setImageResource(R.mipmap.radio_checked);
        ivIsNotVip.setImageResource(R.mipmap.radio);
        isVip  =1;
    }
    @OnClick(R.id.iv_is_not_vip)
    public void setIvIsNotVip(){
        ivIsVip.setImageResource(R.mipmap.radio);
        ivIsNotVip.setImageResource(R.mipmap.radio_checked);
        isVip  =0;
    }

    @OnClick(R.id.iv_sex_female)
    public void setIvSexFemale(){
        ivSexMale.setImageResource(R.mipmap.radio);
        ivSexFemale.setImageResource(R.mipmap.radio_checked);
        sex  =0;
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
}
