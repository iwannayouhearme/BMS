package com.fhh.dao;

import com.fhh.entity.GoodsTypeModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：（商品类别数据访问层接口）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-12 16:16
 */
public interface GoodsTypeDao {
    /**
     * @param goodsTypeId
     * @param goodsTypeName
     * @return
     * @description 添加商品类别
     * @author biubiubiu小浩
     * @date 2018/10/12 17:09
     **/
    int addGoodsType(@Param("goodsTypeId") String goodsTypeId, @Param("goodsTypeName") String goodsTypeName);

    /**
     * @return
     * @description 获取所有商品类别
     * @author biubiubiu小浩
     * @date 2018/10/12 17:10
     **/
    List<GoodsTypeModel> getAllGoodsType();

    /**
     * @param isdel
     * @param goodsTypeId
     * @return
     * @description 根据商品类别的id获取商品类别信息
     * @author biubiubiu小浩
     * @date 2018/10/12 17:13
     **/
    GoodsTypeModel getGoodsTypeById(@Param("isdel") String isdel, @Param("goodsTypeId") String goodsTypeId);

    /**
     * @param goodsTypeName 商品类别名称
     * @return
     * @description 更新商品类型名称
     * @author biubiubiu小浩
     * @date 2018/10/12 17:20
     **/
    int updateGoodsTypeById(@Param("goodsTypeName") String goodsTypeName);

    /**
     * @param goodsTypeId 商品类别id
     * @param delPer      删除人id
     * @return
     * @description 删除商品类别
     * @author biubiubiu小浩
     * @date 2018/10/12 17:22
     **/
    int delGoodsType(@Param("goodsTypeId") String goodsTypeId, @Param("depPer") String delPer);
}
