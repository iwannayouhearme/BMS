package com.fhh.entity;

import com.fhh.base.BaseEntity;

import java.io.Serializable;

/**
 * 功能描述：（用户实体类）
 * @author: biubiubiu小浩
 * @date: 2018-10-09 13:14
 */
public class User extends BaseEntity implements Serializable{
    /**
     * 登录号码
     */
    private String loginNumber;
    /**
     * 用户密码
     */
    private String passWord;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 外号
     */
    private String nickName;
    /**
     * 用户类型1、管理员 2、普通用户
     */
    private String type;
    /**
     * 创建人id
     */
    private String createManId;

    public String getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(String loginNumber) {
        this.loginNumber = loginNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateManId() {
        return createManId;
    }

    public void setCreateManId(String createManId) {
        this.createManId = createManId;
    }
}
