package com.fhh.service;

import com.alibaba.fastjson.JSONObject;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.bill.AddBillModel;
import com.fhh.model.bill.GetBillByUserModel;
import com.fhh.model.bill.UpdateBillModel;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 功能描述：（账单业务逻辑层接口）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 19:35
 */
public interface BillService {
    /**
     * 管理员创建账单
     *
     * @param model
     * @param user
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/11 19:58
     **/
    boolean addBillByAdmin(AddBillModel model, User user) throws BMSException;

    /**
     * 获取用户的账单
     *
     * @param model
     * @param user
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/12 10:17
     **/
    Map<String, Object> getBillByUser(GetBillByUserModel model, User user) throws BMSException;

    /**
     * 获取首页数据
     *
     * @param model
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/16 10:17
     **/
    JSONObject getIndexPage(GetBillByUserModel model) throws BMSException;

    /**
     * 删除账单
     *
     * @param user    当前操作用户
     * @param billIds 账单id 多个账单id用逗号隔开
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/16 17:07
     **/
    boolean delBill(User user, String billIds) throws BMSException;

    /**
     * 付款
     *
     * @param user
     * @param billIds
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/16 21:38
     **/
    boolean payForBill(User user, String billIds) throws BMSException;

    /**
     * 根据账单id获取账单详情
     *
     * @param billId 账单id
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/25 15:59
     **/
    JSONObject getBillById(String billId) throws BMSException;

    /**
     * 更新账单
     *
     * @param updateBillModel 更新账单模型
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/11/5 13:59
     **/
    boolean updateBill(@Valid UpdateBillModel updateBillModel) throws BMSException;
}
