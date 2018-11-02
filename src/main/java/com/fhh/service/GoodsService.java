package com.fhh.service;

import com.fhh.entity.GoodsModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.goods.AddGoodsModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 功能描述：（商品业务逻辑层接口）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-14 18:48
 */
public interface GoodsService {
    /**
     * 添加商品
     *
     * @param model 添加商品模型
     * @param user  当前用户
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/14 18:50
     **/
    boolean addGoods(AddGoodsModel model, User user) throws BMSException;

    /**
     * 获取商品列表
     *
     * @param goodsId 商品id 如果为空，查询全部
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/14 19:01
     **/
    List<GoodsModel> getGoodsList(String goodsId);

    /**
     * 更新商品
     *
     * @param model
     * @param user
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/15 11:06
     **/
    boolean updateGoods(AddGoodsModel model, User user) throws BMSException;

    /**
     * 删除商品
     *
     * @param goodsIds 商品id 多个商品用逗号隔开
     * @param user     当前操作用户
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/15 11:33
     **/
    boolean delGoods(String goodsIds, User user) throws BMSException;

    /**
     * 根据商品类别id获取商品
     *
     * @param goodsTypeId 商品类别id
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/30 12:36
     **/
    List<GoodsModel> getGoodsListByGoodsTypeId(String goodsTypeId);

    /**
     * 根据商品id获取商品信息
     *
     * @param goodsId 商品id
     * @return
     * @author biubiubiu小浩
     * @date 2018/11/2 15:15
     **/
    GoodsModel getGoodsInfoById(String goodsId);
}