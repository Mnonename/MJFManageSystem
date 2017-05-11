package com.mjf.mjfmanagesystem.modules;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.iv_user) ImageView ivUser;
    @InjectView(R.id.iv_delete) ImageView ivDelete;
    @InjectView(R.id.rl_user) RelativeLayout rlUser;
    @InjectView(R.id.iv_password) ImageView ivPassword;
    @InjectView(R.id.iv_delete2) ImageView ivDelete2;
    @InjectView(R.id.et_password) EditText etPassword;
    @InjectView(R.id.rl_password) RelativeLayout rlPassword;
    @InjectView(R.id.rl_center) RelativeLayout rlCenter;
    @InjectView(R.id.iv_login_register) ImageView ivLoginRegister;
    @InjectView(R.id.tv_register) TextView tvRegister;
    @InjectView(R.id.btn_login) Button btnLogin;
    @InjectView(R.id.et_usercode) EditText etUsercode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.tv_register)
    public void setTvRegister() {
        startNewActivity(RegisterActivity.class);
    }

    @OnClick(R.id.btn_login)
    public void setBtnLogin() {
        String userCode = etUsercode.getText().toString();
        String password = etPassword.getText().toString();
//        if(CommonUtil.isNUll(userCode)){
//            toast("账号不能为空");
//            return;
//        }
//        if(CommonUtil.isNUll(password)){
//            toast("密码不能为空");
//            return;
//        }
//        String rightPassword = mHelper.getPassword(userCode);
//        if(password.equals(rightPassword)){
//            startNewActivity(MainActivity.class, true);
//        }else{
//            toast("账号或密码不正确");
//        }
        startNewActivity(MainActivity.class, true);
    }

    @OnClick(R.id.iv_delete2)
    public void setIvDelete2() {
        etPassword.setText("");
    }

    @OnClick(R.id.iv_delete)
    public void setIvDelete() {
        etUsercode.setText("");
    }
}
