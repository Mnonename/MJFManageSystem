package com.mjf.mjfmanagesystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.entity.ManagerUserInfo;
import com.mjf.mjfmanagesystem.modules.LoginActivity;
import com.mjf.mjfmanagesystem.sqlite.RecodeGpsListSQLHelper;
import com.mjf.mjfmanagesystem.util.CommonUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.iv_back) ImageView ivBack;
    @InjectView(R.id.rl_title) RelativeLayout rlTitle;
    @InjectView(R.id.et_username) TextView etUsername;
    @InjectView(R.id.et_phone) TextView etPhone;
    @InjectView(R.id.et_idcard) TextView etIdcard;
    @InjectView(R.id.et_usercode) TextView etUsercode;
    @InjectView(R.id.btn_exit) Button btnExit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MyFragment() {
        // Required empty public constructor
    }

    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
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
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String usercode = CommonUtil.getString(getActivity(), CommonUtil.USERCODE);
        RecodeGpsListSQLHelper mHelper = new RecodeGpsListSQLHelper(getActivity());
        ManagerUserInfo managerUserInfo = mHelper.getManagerUserInfo(usercode);
        if (managerUserInfo != null) {
            etUsername.setText(managerUserInfo.username);
            etPhone.setText(managerUserInfo.phone);
            etIdcard.setText(managerUserInfo.idcard);
            etUsercode.setText(managerUserInfo.userCode);
        }
    }

    @OnClick(R.id.btn_exit)
    public void setBtnExit() {
        CommonUtil.putString(getActivity(), CommonUtil.USERCODE, "");
        CommonUtil.putString(getActivity(), CommonUtil.PASSWORD, "");
        Intent intent = new Intent();
        intent.setClass(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
