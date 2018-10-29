package com.fhh.service;

import com.alibaba.fastjson.JSONObject;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.bill.AddBillModel;
import com.fhh.model.bill.GetBillByUserModel;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 功能描述：（账单业务逻辑层接口）
 * @author: biubiubiu小浩
 * @date: 2018-10-11 19:35
 */
public interface BillService {
    /**
     * 管理员创建账单
     * @author biubiubiu小浩
     * @date 2018/10/11 19:58
     * @param model
     * @param user
     * @return
     * @throws BMSException
     **/
    boolean addBillByAdmin(AddBillModel model, User user) throws BMSException;

    /**
     * 获取用户的账单
     * @author biubiubiu小浩
     * @date 2018/10/12 10:17
     * @param model
     * @param user
     * @return
     * @throws BMSException
     **/
    Map<String,Object> getBillByUser(GetBillByUserModel model, User user) throws BMSException;

    /**
     * 获取首页数据
     * @author biubiubiu小浩
     * @date 2018/10/16 10:17
     * @param model
     * @return
     * @throws BMSException
     **/
    JSONObject getIndexPage(GetBillByUserModel model) throws BMSException;
    
    /**
     * 删除账单
     * @author biubiubiu小浩
     * @date 2018/10/16 17:07
     * @param user 当前操作用户
     * @param billIds 账单id 多个账单id用逗号隔开
     * @return
     * @throws BMSException
     **/
    boolean delBill(User user,String billIds) throws BMSException;

    /**
     * 付款
     * @author biubiubiu小浩
     * @date 2018/10/16 21:38
     * @param user
     * @param billIds
     * @return
     * @throws
     **/
    boolean payForBill(User user, String billIds) throws BMSException;
    /**
     * 根据账单id获取账单详情
     * @author biubiubiu小浩
     * @date 2018/10/25 15:59
     * @param billId 账单id
     * @return
     * @throws BMSException
     **/
    JSONObject getBillById(String billId) throws BMSException;
}
