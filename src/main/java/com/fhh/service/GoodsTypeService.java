package com.fhh.service;

import com.fhh.entity.GoodsTypeModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 功能描述：（商品类别业务逻辑层接口）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-15 13:11
 */
public interface GoodsTypeService {
    /**
     * 添加商品类别
     * @author biubiubiu小浩
     * @date 2018/10/15 13:14
     * @param goodsTypeName 商品类别名称
     * @param user 当前操作用户信息
     * @return
     * @throws
     **/
    boolean addGoodsType(String goodsTypeName, User user) throws BMSException;

    /**
     * 获取商品类别列表
     * @author biubiubiu小浩
     * @date 2018/10/15 13:58
     * @return
     **/
    List<GoodsTypeModel> getGoodsTypeList();

    /**
     * 更新商品类别
     * @author biubiubiu小浩
     * @date 2018/10/15 14:08
     * @param goodsTypeId 商品类别id
     * @param goodsTypeName 商品类别名称
     * @param user
     * @return
     * @throws
     **/
    boolean updateGoodsType(String goodsTypeId, String goodsTypeName, User user) throws BMSException;

    /**
     * 删除商品类别
     * @author biubiubiu小浩
     * @date 2018/10/15 17:19
     * @param goodsTypeId 商品类别id
     * @param user 用户
     * @return
     * @throws 
     **/
    boolean delGoodsType(String goodsTypeId, User user) throws BMSException;

    /**
     * 根据商品类型id获取商品类型详情
     * @author biubiubiu小浩
     * @date 2018/11/7 19:54
     * @param goodsTypeId 商品类型id
     * @return
     **/
    GoodsTypeModel getGoodsTypeById(String goodsTypeId);
}