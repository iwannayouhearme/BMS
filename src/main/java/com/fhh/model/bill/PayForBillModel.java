package com.fhh.model.bill;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 功能描述：（还款模型）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-14 13:16
 */
@Validated
public class PayForBillModel {
    /**
     * 还款日期
     */
    private String payDate;
    /**
     * 还款签收人id
     */
    private String userId;
    /**
     * 还款方式(0现金 1支付宝 2微信)
     */
    @Pattern(regexp = "^[0-2]$", message = "请选择正确的还款方式！")
    private String paySource;
    /**
     * 还款账单id
     */
    @NotBlank(message = "还款账单编号不能为空！")
    private String billId;

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaySource() {
        return paySource;
    }

    public void setPaySource(String paySource) {
        this.paySource = paySource;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }
}