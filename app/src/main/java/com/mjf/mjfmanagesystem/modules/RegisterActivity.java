package com.mjf.mjfmanagesystem.modules;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mjf.mjfmanagesystem.R;
import com.mjf.mjfmanagesystem.base.BaseActivity;
import com.mjf.mjfmanagesystem.entity.ManagerUserInfo;
import com.mjf.mjfmanagesystem.util.CommonUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.iv_back) ImageView ivBack;
    @InjectView(R.id.rl_title) RelativeLayout rlTitle;
    @InjectView(R.id.btn_commit) Button btnCommit;
    @InjectView(R.id.btn_cancel) Button btnCancel;
    @InjectView(R.id.et_usercode) EditText etUsercode;
    @InjectView(R.id.et_password) EditText etPassword;
    @InjectView(R.id.et_password_again) EditText etPasswordAgain;
    @InjectView(R.id.et_username) EditText etUsername;
    @InjectView(R.id.et_idcard) EditText etIdcard;
    @InjectView(R.id.et_phone) EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
    }

    @OnClick({(R.id.iv_back), (R.id.btn_cancel)})
    public void setIvBack() {
        finish();
    }

    @OnClick(R.id.btn_commit)
    public void setBtnCommit(){
        String usercode = etUsercode.getText().toString();
        String password = etPassword.getText().toString();
        String psswordAgain = etPasswordAgain.getText().toString();

        if(CommonUtil.isNUll(usercode)){
            toast("账号不能为空");
            return;
        }
        if(CommonUtil.isNUll(password)){
            toast("密码不能为空");
            return;
        }
        if(CommonUtil.isNUll(psswordAgain)){
            toast("请再次确认密码");
            return;
        }
        if(!password.equals(psswordAgain)){
            toast("两次密码输入不一致");
            return;
        }


        String username = etUsername.getText().toString();
        String idcard = etIdcard.getText().toString();
        String phone = etPhone.getText().toString();
        if(CommonUtil.isNUll(username)){
            toast("用户不能为空");
            return;
        }
        if(CommonUtil.isNUll(phone)){
            toast("手机号不能为空");
            return;
        }else if(!CommonUtil.isphonenum(phone)){
            toast("手机号格式错误");
            return;
        }
        if(CommonUtil.isNUll(idcard)){
            toast("身份证不能为空");
            return;
        }else if(!CommonUtil.isValidChinaCard(idcard)){
            toast("身份证格式错误");
            return;
        }
        ManagerUserInfo userInfo = new ManagerUserInfo();
        userInfo.idcard = idcard;
        userInfo.userCode = usercode;
        userInfo.password = password;
        userInfo.username = username;
        userInfo.phone = phone;
        userInfo.status = "1";
        // 保存在本地数据库
        mHelper.saveSystemUserInfo(userInfo);
        toast("注册成功");
        finish();
    }



}
