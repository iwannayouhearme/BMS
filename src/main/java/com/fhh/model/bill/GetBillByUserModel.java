package com.fhh.model.bill;

import org.springframework.validation.annotation.Validated;

/**
 * 功能描述：（获取用户账单）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-12 08:46
 */
@Validated
public class GetBillByUserModel {
    /**
     * 账单所属年月
     */
    private String yearMonth;
    /**
     * 还款状态
     */
    private String bstatus;
    /**
     * 用户id
     */
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getBstatus() {
        return bstatus;
    }

    public void setBstatus(String bstatus) {
        this.bstatus = bstatus;
    }
}