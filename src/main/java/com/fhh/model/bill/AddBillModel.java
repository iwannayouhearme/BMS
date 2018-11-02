package com.fhh.model.bill;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 功能描述：（借款生成账单模型）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 19:37
 */
@Validated
public class AddBillModel {
    /**
     * UUID
     */
    private String uuId;
    /**
     * 借款人id
     */
    @NotBlank(message = "借款人id不能为空！")
    private String borrowerManId;
    /**
     * 借款金额
     */
    private String loanAmount;
    /**
     * 借款类型（0商品，1现金）
     */
    @Pattern(regexp = "^[0-1]$", message = "请输入正确的借款类型！")
    private String btype;
    /**
     * 借款人名
     */
    private String borrowerMan;
    /**
     * 放款人id
     */
    private String createManId;
    /**
     * 放款人名
     */
    private String createManName;
    /**
     * 借款人外号
     */
    private String borrowerNikeName;
    /**
     * 年月
     */
    private String yearMonth;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getCreateManName() {
        return createManName;
    }

    public void setCreateManName(String createManName) {
        this.createManName = createManName;
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getBorrowerManId() {
        return borrowerManId;
    }

    public void setBorrowerManId(String borrowerManId) {
        this.borrowerManId = borrowerManId;
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

    public String getBorrowerMan() {
        return borrowerMan;
    }

    public void setBorrowerMan(String borrowerMan) {
        this.borrowerMan = borrowerMan;
    }

    public String getCreateManId() {
        return createManId;
    }

    public void setCreateManId(String createManId) {
        this.createManId = createManId;
    }

    public String getBorrowerNikeName() {
        return borrowerNikeName;
    }

    public void setBorrowerNikeName(String borrowerNikeName) {
        this.borrowerNikeName = borrowerNikeName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}