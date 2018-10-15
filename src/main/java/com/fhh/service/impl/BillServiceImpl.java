package com.fhh.service.impl;

import com.alibaba.fastjson.JSON;
import com.fhh.base.BaseService;
import com.fhh.constant.BillConstant;
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
import java.util.HashMap;
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
        if (!this.judgeHasPower(user)) {
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
    public Map<String, Object> getBillByUser(GetBillByUserModel model, User user) throws BMSException {
        Map<String, Object> data = new HashMap<>(16);
        List<BillModel> billModelList = billDao.getBillByUser(model);
        //总借款金额
        int totalMoney = 0;
        //已还款总金额
        int totalPaymoney = 0;
        //未还款总金额
        int totalUnpayMoney = 0;
        if (!billModelList.isEmpty()) {
            for (BillModel billModel : billModelList) {
                //已还款
                if (BillConstant.Bstatus.HASPAY.equals(billModel.getBstatus())) {
                    try {
                        totalPaymoney += Integer.parseInt(billModel.getLoanAmount());
                    } catch (Exception e) {
                        throw new BMSException("借款金额中存在错误的数据，请联系管理员处理!");
                    }
                }else {
                    try {
                        totalUnpayMoney += Integer.parseInt(billModel.getLoanAmount());
                    } catch (Exception e) {
                        throw new BMSException("借款金额中存在错误的数据，请联系管理员处理!");
                    }
                }
            }
        }
        totalMoney = totalPaymoney+totalUnpayMoney;
        data.put("totalMoney",totalMoney);
        data.put("totalPaymoney",totalPaymoney);
        data.put("totalUnpayMoney",totalUnpayMoney);
        data.put("billList", JSON.toJSONString(billModelList));
        Map<String,Object> result = new HashMap<>(16);
        result.put("data",data);
        return result;
    }
}