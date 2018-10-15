package com.fhh.service;

import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.bill.AddBillModel;
import com.fhh.model.bill.GetBillByUserModel;

import java.util.Map;

/**
 * 功能描述：（账单业务逻辑层接口）
 * @author: biubiubiu小浩
 * @date: 2018-10-11 19:35
 */
public interface BillService {
    /**
     * @description 管理员创建账单
     * @author biubiubiu小浩
     * @date 2018/10/11 19:58
     * @param model
     * @param user
     * @return
     **/
    boolean addBillByAdmin(AddBillModel model, User user) throws BMSException;

    /**
     * @description 获取用户的账单
     * @author biubiubiu小浩
     * @date 2018/10/12 10:17
     * @param model
     * @param user
     * @return
     **/
    Map<String,Object> getBillByUser(GetBillByUserModel model, User user);
}
