package com.fhh.model.bill;

import com.fhh.entity.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Pattern;

/**
 * 功能描述：（更新账单模型）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-14 13:08
 */
@Validated
public class UpdateBillModel {
    /**
     * 借款人id
     */
    private String borrowerManId;
    /**
     * 借款人名称
     */
    private String borrowerMan;
    /**
     * 借款人外号
     */
    private String borrowerNikeName;
    /**
     * 金额
     */
    @Pattern(regexp = "^([1-9]\\d\\d\\d|[1-9]\\d\\d|[1-9]\\d|\\d)$", message = "请输入正确的借款金额！")
    private String loanAmount;
    /**
     * 借款类型
     */
    @Pattern(regexp = "^[0-1]$", message = "请输入正确的借款类型！")
    private String btype;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 账单id
     */
    private String billId;
    /**
     * 用户模型
     */
    private User userModel;

    public User getUserModel() {
        return userModel;
    }

    public void setUserModel(User userModel) {
        this.userModel = userModel;
    }

    public String getBorrowerManId() {
        return borrowerManId;
    }

    public void setBorrowerManId(String borrowerManId) {
        this.borrowerManId = borrowerManId;
    }

    public String getBorrowerMan() {
        return borrowerMan;
    }

    public void setBorrowerMan(String borrowerMan) {
        this.borrowerMan = borrowerMan;
    }

    public String getBorrowerNikeName() {
        return borrowerNikeName;
    }

    public void setBorrowerNikeName(String borrowerNikeName) {
        this.borrowerNikeName = borrowerNikeName;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getBtype() {
        return btype;
    }

    public void setBtype(String btype) {
        this.btype = btype;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }
}