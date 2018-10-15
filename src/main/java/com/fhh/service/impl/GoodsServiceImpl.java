package com.fhh.service.impl;

import com.fhh.base.BaseService;
import com.fhh.dao.GoodsDao;
import com.fhh.entity.GoodsModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.goods.AddGoodsModel;
import com.fhh.service.GoodsService;
import com.fhh.util.ChineseUtil;
import com.fhh.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能描述：（商品业务逻辑实现层）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-14 18:51
 */
@Service
public class GoodsServiceImpl extends BaseService implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addGoods(AddGoodsModel model, User user) throws BMSException {
        if (!this.judgeHasPower(user)) {
            throw new BMSException("无权限用户操作！");
        }
        model.setGoodsId(DataUtil.getUUid());
        model.setFullPinyin(ChineseUtil.getPingYin(model.getGoodsName()));
        int addGoods = goodsDao.addGoods(model);
        if (addGoods < 1) {
            throw new BMSException("添加商品失败，请联系管理员处理！");
        }
        return true;
    }

    @Override
    public List<GoodsModel> getGoodsList(String goodsId) {
        List<GoodsModel> goodsList = goodsDao.getGoods(goodsId, "0");
        return goodsList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGoods(AddGoodsModel model, User user) throws BMSException {
        boolean flag = judgeHasPower(user);
        if (!flag) {
            throw new BMSException("无权限用户操作！");
        }
        int updateGoodsById = goodsDao.updateGoodsById(model);
        if (updateGoodsById < 1) {
            throw new BMSException("更新商品失败，请联系管理员处理！");
        }
        return true;
    }

    @Override
    public boolean delGoods(String goodsIds, User user) throws BMSException {
        boolean flag = judgeHasPower(user);
        if (!flag) {
            throw new BMSException("无权限用户操作！");
        }
        String[] goodsIdsArray = goodsIds.split(",");
        for (int i = 0; i < goodsIdsArray.length; i++) {
            String goodsId = goodsIdsArray[i];
            int delGoods = goodsDao.delGoods(goodsId, user.getId());
            if (delGoods < 1) {
                throw new BMSException("删除商品失败，请联系管理员处理！");
            }
        }
        return false;
    }
}