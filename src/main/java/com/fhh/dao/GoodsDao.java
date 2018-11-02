package com.fhh.dao;

import com.fhh.entity.GoodsModel;
import com.fhh.model.goods.AddGoodsModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：（商品数据访问层接口）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-12 16:15
 */
public interface GoodsDao {
    /**
     * 添加商品
     *
     * @param model
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/13 22:13
     **/
    int addGoods(AddGoodsModel model);

    /**
     * 更新商品
     *
     * @param model
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/13 22:15
     **/
    int updateGoodsById(AddGoodsModel model);

    /**
     * 获取商品
     *
     * @param goodsId 商品id  不传查全部
     * @param isdel   删除标记
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/13 22:17
     **/
    List<GoodsModel> getGoods(@Param("goodsId") String goodsId, @Param("isdel") String isdel);

    /**
     * 删除商品
     *
     * @param goodsId 商品id
     * @param delPer  删除人id
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/13 22:18
     **/
    int delGoods(@Param("goodsId") String goodsId, @Param("delPer") String delPer);

    /**
     * 根据商品类别id查询商品
     *
     * @param goodsTypeId 商品类别id
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/15 17:44
     **/
    List<GoodsModel> getGoodsListByGoodsTypeId(@Param("goodsTypeId") String goodsTypeId);

    /**
     * 根据商品id获取商品内容
     *
     * @param goodsId
     * @return
     * @author biubiubiu小浩
     * @date 2018/11/2 15:01
     **/
    GoodsModel getGoodsById(@Param("goodsId") String goodsId);
}