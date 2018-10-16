package com.fhh.entity;

import com.fhh.base.BaseEntity;

/**
 * 功能描述：（商品实体类）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-12 14:48
 */
public class GoodsModel extends BaseEntity{
    /**
     * 商品名
     */
    private String goodsName;
    /**
     * 商品价格
     */
    private String goodsPrice;
    /**
     * 商品类型id
     */
    private String goodsTypeId;
    /**
     * 全拼
     */
    private String fullPinyin;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public String getFullPinyin() {
        return fullPinyin;
    }

    public void setFullPinyin(String fullPinyin) {
        this.fullPinyin = fullPinyin;
    }
}