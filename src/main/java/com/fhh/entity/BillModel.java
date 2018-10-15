package com.fhh.entity;

import com.fhh.base.BaseEntity;

/**
 * 功能描述：（账单模型类）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 18:35
 */
public class BillModel extends BaseEntity {
    /**
     * 借款人id
     */
    private String borrowerManId;
    /**
     * 借款金额
     */
    private String loanAmount;
    /**
     * 借款类型（0商品，1现金）
     */
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
     * 还款状态（0为未还款，1为已还款）
     */
    private String bstatus;
    /**
     * 还款日期
     */
    private String payDate;
    /**
     * 还款签收人
     */
    private String payOpeManId;
    /**
     * 账单所属年月
     */
    private String yearMonth;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 还款方式（0现金 1支付宝 2微信）
     */
    private String paySource;

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayOpeManId() {
        return payOpeManId;
    }

    public void setPayOpeManId(String payOpeManId) {
        this.payOpeManId = payOpeManId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
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

    public String getCreateManName() {
        return createManName;
    }

    public void setCreateManName(String createManName) {
        this.createManName = createManName;
    }

    public String getBorrowerNikeName() {
        return borrowerNikeName;
    }

    public void setBorrowerNikeName(String borrowerNikeName) {
        this.borrowerNikeName = borrowerNikeName;
    }

    public String getBstatus() {
        return bstatus;
    }

    public void setBstatus(String bstatus) {
        this.bstatus = bstatus;
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

    public String getPaySource() {
        return paySource;
    }

    public void setPaySource(String paySource) {
        this.paySource = paySource;
    }
}