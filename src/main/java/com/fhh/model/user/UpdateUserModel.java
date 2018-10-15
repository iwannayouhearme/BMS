package com.fhh.model.user;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：（更新用户模型）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-14 17:38
 */
@Validated
public class UpdateUserModel {
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 外号
     */
    private String nickName;
    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空！")
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}