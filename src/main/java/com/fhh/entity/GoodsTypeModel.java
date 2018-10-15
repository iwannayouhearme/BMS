package com.fhh.entity;

import com.fhh.base.BaseEntity;

/**
 * 功能描述：（商品类别实体类）
 * @author: biubiubiu小浩
 * @date: 2018-10-12 13:51
 */
public class GoodsTypeModel extends BaseEntity{
    /**
     * 商品类别名称
     */
    private String goodsTypeName;

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }
}