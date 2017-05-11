package com.mjf.mjfmanagesystem.entity;

import java.io.Serializable;

/**
 * Created by lxt on 2017/5/10.
 * 管理员信息
 */
public class UserInfo implements Serializable {
    public String userCode;//账号
    public String phone;//手机号
    public String username;//姓名
    public String idcard;//身份证
    public String status;//状态
    public String sex;//性别
    public String createTime;//创建时间
    public String isVip;//是不是vip
    public String ID;//ID
    public UserInfo(){

    }

}
