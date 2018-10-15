package com.fhh.model.user;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述：（添加用户实体类）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-10 19:03
 */
@Validated
public class AddUserModel {
    /**
     * UUID
     */
    private String uuId;
    /**
     * 用户编号
     */
    private String loginNumber;
    /**
     * 用户真是姓名
     */
    @NotBlank(message = "请输入真实姓名！")
    private String realName;
    /**
     * 用户外号
     */
    private String nickName;
    /**
     * 创建人id
     */
    private String createManId;

    public String getCreateManId() {
        return createManId;
    }

    public void setCreateManId(String createManId) {
        this.createManId = createManId;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(String loginNumber) {
        this.loginNumber = loginNumber;
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
}