package com.fhh.service.impl;

import com.fhh.base.BaseService;
import com.fhh.dao.GoodsDao;
import com.fhh.dao.GoodsTypeDao;
import com.fhh.entity.GoodsModel;
import com.fhh.entity.GoodsTypeModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.service.GoodsTypeService;
import com.fhh.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-15 13:31
 */
@Service
public class GoodsTypeServiceImpl extends BaseService implements GoodsTypeService {
    @Autowired
    private GoodsTypeDao goodsTypeDao;
    @Autowired
    private GoodsDao goodsDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGoodsType(String goodsTypeName, User user) throws BMSException {
        boolean flag = judgeHasPower(user);
        if (!flag) {
            throw new BMSException("无权限用户操作！");
        }
        int addGoodsType = goodsTypeDao.addGoodsType(DataUtil.getUUid(), goodsTypeName);
        if (addGoodsType < 1) {
            throw new BMSException("添加商品类别失败，请联系管理员处理！");
        }
        return true;
    }

    @Override
    public List<GoodsTypeModel> getGoodsTypeList() {
        List<GoodsTypeModel> goodsTypeList = goodsTypeDao.getAllGoodsType();
        return goodsTypeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGoodsType(String goodsTypeId, String goodsTypeName, User user) throws BMSException {
        boolean flag = judgeHasPower(user);
        if (!flag) {
            throw new BMSException("无权限用户操作！");
        }
        int goodsTypeById = goodsTypeDao.updateGoodsTypeById(goodsTypeName, goodsTypeId);
        if (goodsTypeById < 1) {
            throw new BMSException("更新商品失败，请联系管理员处理！");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delGoodsType(String goodsTypeId, User user) throws BMSException {
        boolean flag = judgeHasPower(user);
        if (!flag) {
            throw new BMSException("无权限用户操作！");
        }
        //判断前端传入的值是否正确
        GoodsTypeModel goodsTypeById = goodsTypeDao.getGoodsTypeById("0", goodsTypeId);
        if (ObjectUtils.isEmpty(goodsTypeById)){
            throw new BMSException("存在错误参数！");
        }
        //根据商品类别id查询该类别下有没有关联商品
        List<GoodsModel> goodsList = goodsDao.getGoodsListByGoodsTypeId(goodsTypeId);
        if (!goodsList.isEmpty()){
            throw new BMSException("该商品类别下存在商品，请先确保商品不需要再做删除操作!");
        }
        int delGoodsType = goodsTypeDao.delGoodsType(goodsTypeId, user.getId());
        if (delGoodsType<1){
            throw new BMSException("删除商品类别失败，请联系管理员处理！");
        }
        return true;
    }
}