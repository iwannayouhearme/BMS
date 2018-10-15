package com.fhh.controller.bill;

import com.fhh.base.BaseController;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.bill.AddBillModel;
import com.fhh.model.bill.GetBillByUserModel;
import com.fhh.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 功能描述：（账单服务控制层）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 19:34
 */
@Controller
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
                                HttpServletResponse response, @Valid GetBillByUserModel model, BindingResult result) {
        User user = this.getUserSession(request);
        billService.getBillByUser(model, user);
        return null;
    }
}