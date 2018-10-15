package com.fhh.dao;

import com.fhh.entity.BillModel;
import com.fhh.model.bill.PayForBillModel;
import com.fhh.model.bill.UpdateBillModel;
import com.fhh.model.bill.AddBillModel;
import com.fhh.model.bill.GetBillByUserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能描述：（账单数据访问层接口）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 18:38
 */
public interface BillDao {
    /**
     * @param model
     * @return
     * @description 新增账单
     * @author biubiubiu小浩
     * @date 2018/10/11 19:56
     **/
    int addBill(AddBillModel model);

    /**
     * @param model
     * @return
     * @description 获取用户账单
     * @author biubiubiu小浩
     * @date 2018/10/12 11:20
     **/
    List<BillModel> getBillByUser(GetBillByUserModel model);

    /**
     * @param delPer 删除人id
     * @param billId 账单id
     * @return
     * @description 删除账单
     * @author biubiubiu小浩
     * @date 2018/10/14 13:06
     **/
    int delBill(@Param("delPer") String delPer, @Param("billId") String billId);

    /**
     * @param model 更新账单模型
     * @return
     * @description 更新账单
     * @author biubiubiu小浩
     * @date 2018/10/14 13:15
     **/
    int updateBill(UpdateBillModel model);

    /**
     * @param model 还款模型实体类
     * @return
     * @description 还款
     * @author biubiubiu小浩
     * @date 2018/10/14 13:22
     **/
    int payForBill(PayForBillModel model);
}
