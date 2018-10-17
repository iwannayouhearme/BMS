package com.fhh.model.goods;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-13 22:06
 */
@Validated
public class AddGoodsModel {
    /**
     * 商品类UUID
     */
    private String goodsId;
    /**
     * 商品名称
     */
    @NotBlank(message = "请输入商品名称！")
    @Length(max = 20,message = "请输入20字以内的商品名称！")
    private String goodsName;
    /**
     * 商品价格
     */
    @Pattern(regexp = "^([1-9]\\d\\d\\d|[1-9]\\d\\d|[1-9]\\d|[1-9])$", message = "请输入1-9999的价格！")
    private String goodsPrice;
    @NotBlank(message = "请选择商品的类型！")
    private String goodsTypeId;
    private String fullPinyin;

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