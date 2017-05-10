package com.mjf.mjfmanagesystem.entity;

import java.io.Serializable;

/**
 * Created by lxt on 2017/5/10.
 * 管理员信息
 */
public class ManagerUserInfo implements Serializable {
    public String userCode;//账号
    public String password;//密码
    public String phone;//手机号
    public String username;//姓名
    public String idcard;//身份证
    public String status;//状态  1 表示正常使用用户
    public String ID;//ID
    public ManagerUserInfo(){

    }

}
