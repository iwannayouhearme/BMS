package com.fhh.service.impl;

import com.fhh.base.BaseService;
import com.fhh.dao.GoodsTypeDao;
import com.fhh.entity.GoodsTypeModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.service.GoodsTypeService;
import com.fhh.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-15 13:31
 */
@Service public class GoodsTypeServiceImpl extends BaseService implements GoodsTypeService {
    @Autowired private GoodsTypeDao goodsTypeDao;

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
}