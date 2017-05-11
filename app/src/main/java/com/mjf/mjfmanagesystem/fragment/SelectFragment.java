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
    public void setBtnSeleet() {
        List<UserInfo> list = new ArrayList<>();
//        RecodeGpsListSQLHelper mHelper = new RecodeGpsListSQLHelper(getActivity());
        UserInfo userInfo = new UserInfo();
        userInfo.phone = "15116992241";
//        list = mHelper.getUserInfoList(userInfo);
//        StringBuffer sb = new StringBuffer();
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                sb.append(list.get(i).username + "  " + list.get(i).phone + "  ");
//            }
//        }
        SearchResultActivity.newIntent(getActivity(),userInfo);
//        tvTest.setText(sb.toString());
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
