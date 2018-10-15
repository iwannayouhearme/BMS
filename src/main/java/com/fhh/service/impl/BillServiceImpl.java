package com.fhh.service.impl;

import com.fhh.base.BaseService;
import com.fhh.dao.BillDao;
import com.fhh.entity.BillModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.bill.AddBillModel;
import com.fhh.model.bill.GetBillByUserModel;
import com.fhh.service.BillService;
import com.fhh.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 19:37
 */
@Service
public class BillServiceImpl extends BaseService implements BillService {
    @Autowired
    private BillDao billDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBillByAdmin(AddBillModel model, User user) throws BMSException {
        //首先判断当前用户是否有权限添加账单
        if (!this.judgeHasPower(user)){
            throw new BMSException("无权限用户操作！");
        }
        model.setUuId(DataUtil.getUUid());
        model.setCreateManId(user.getId());
        model.setCreateManName(user.getRealName());
        User borrowerMan = this.getUserInfo(model.getBorrowerManId());
        model.setBorrowerMan(borrowerMan.getRealName());
        model.setBorrowerNikeName(borrowerMan.getNickName());
        model.setYearMonth(new SimpleDateFormat("YYYY-MM").format(new Date()).toString());
        int addBill = billDao.addBill(model);
        if (addBill < 1) {
            throw new BMSException("新建账单失败，请联系管理员处理！");
        }
        return true;
    }

    @Override
    public Map<String, Object> getBillByUser(GetBillByUserModel model, User user) {
        List<BillModel> billModelList = billDao.getBillByUser(model);

        return null;
    }
}