package com.fhh.controller.bill;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fhh.base.BaseController;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.bill.AddBillModel;
import com.fhh.model.bill.GetBillByUserModel;
import com.fhh.model.bill.UpdateBillModel;
import com.fhh.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 功能描述：（账单服务控制层）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 19:34
 */
@Controller
@Validated
public class BillController extends BaseController {
    @Autowired
    private BillService billService;

    /**
     * @param model
     * @param result
     * @return
     * @description 管理员新建账单
     * @author biubiubiu小浩
     * @date 2018/10/12 8:43
     **/
    @PostMapping(value = "/bms/bill/addBillByAdmin")
    public String addBillByAdmin(HttpServletRequest request,
                                 HttpServletResponse response, @Valid AddBillModel model, BindingResult result) throws BMSException {
        if (result.hasErrors()) {
            return this.poClient(response, result.getFieldError().getDefaultMessage());
        }
        User user = this.getUserSession(request);
        boolean flag = billService.addBillByAdmin(model, user);
        return this.poClient(response, flag);
    }

    /**
     * @param
     * @return
     * @description 获取用户账单
     * @author biubiubiu小浩
     * @date 2018/10/12 8:44
     **/
    @GetMapping(value = "/bms/bill/getBillByUser")
    public String getBillByUser(HttpServletRequest request,
                                HttpServletResponse response, @Valid GetBillByUserModel model, BindingResult result) throws BMSException {
        User user = this.getUserSession(request);
        if (this.isNil(model.getUserId())) {
            throw new BMSException("缺少用户信息参数！");
        }
        Map<String, Object> billByUser = billService.getBillByUser(model, user);
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(billByUser));
        return this.poClient(response, jsonObject);
    }

    /**
     * 获取首页数据接口
     *
     * @param
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/16 9:56
     **/
    @GetMapping(value = "/bms/bill/getIndexPage")
    public String getIndexPage(HttpServletRequest request, HttpServletResponse response, @Valid GetBillByUserModel model, BindingResult result) throws BMSException {
        if (result.hasErrors()) {
            return this.poClient(response, result.getFieldError().getDefaultMessage());
        }
        JSONObject indexBillData = billService.getIndexPage(model);
        return this.poClient(response, indexBillData);
    }

    /**
     * 删除账单
     *
     * @param request
     * @param response
     * @param billIds  账单id，多个账单id用逗号隔开
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/16 17:04
     **/
    @PostMapping(value = "/bms/bill/delBill")
    public String delBill(HttpServletRequest request, HttpServletResponse response, @NotBlank(message = "账单id不能为空！") String billIds) throws BMSException {
        User user = this.getUserSession(request);
        boolean flag = billService.delBill(user, billIds);
        return this.poClient(response, flag);
    }

    /**
     * 还款
     *
     * @param request
     * @param response
     * @param billIds  账单id，多个id用逗号隔开
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/10/16 21:36
     **/
    @PostMapping(value = "/bms/bill/payForBill")
    public String payForBill(HttpServletRequest request, HttpServletResponse response, @NotBlank(message = "账单编号不能为空！") String billIds) throws BMSException {
        User user = this.getUserSession(request);
        boolean flag = billService.payForBill(user, billIds);
        return this.poClient(response, flag);
    }

    /**
     * 根据账单id获取账单详情
     *
     * @param billId 账单id
     * @return
     * @throws
     * @author biubiubiu小浩
     * @date 2018/10/25 16:07
     **/
    @GetMapping(value = "/bms/bill/getBillById")
    public String getBillById(HttpServletRequest request, HttpServletResponse response, @NotBlank(message = "账单编号不能为空！") String billId) throws BMSException {
        JSONObject billById = billService.getBillById(billId);
        return this.poClient(response, billById);
    }

    /**
     * 更新账单
     *
     * @param updateBillModel 更新账单模型
     * @return
     * @throws BMSException
     * @author biubiubiu小浩
     * @date 2018/11/5 13:56
     **/
    @PostMapping(value = "/bms/bill/updateBill")
    public String updateBill(HttpServletRequest request, HttpServletResponse response, @Valid UpdateBillModel updateBillModel, BindingResult result) throws BMSException {
        if (result.hasErrors()) {
            return this.poClient(response, result.getFieldError().getDefaultMessage());
        }
        User userModel = this.getUserSession(request);
        updateBillModel.setUserModel(userModel);
        boolean updateBill = billService.updateBill(updateBillModel);
        return this.poClient(response, updateBill);
    }
}