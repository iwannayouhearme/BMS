package com.fhh.controller.user;

import com.fhh.base.BaseController;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.user.AddUserModel;
import com.fhh.model.user.UpdateUserModel;
import com.fhh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 功能描述：（用户信息控制层）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-09 11:03
 */
@Controller
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * @param model
     * @return
     * @description 添加新用户
     * @author biubiubiu小浩
     * @date 2018/10/11 8:42
     **/
    @PostMapping(value = "/bms/user/addUser")
    public String addUser(HttpServletRequest request,
                          HttpServletResponse response, @Valid AddUserModel model, BindingResult result) throws BMSException {
        if (result.hasErrors()) {
            return this.poClient(response, result.getFieldError().getDefaultMessage());
        }
        User user = this.getUserSession(request);
        boolean addUser = userService.addUser(model, user);
        return this.poClient(response, addUser);
    }

    /**
     * @param
     * @return
     * @description 获取所有用户列表
     * @author biubiubiu小浩
     * @date 2018/10/11 9:35
     **/
    @GetMapping(value = "/bms/user/getUserList")
    public String getUserList(HttpServletRequest request,
                              HttpServletResponse response) {
        User user = this.getUserSession(request);
        List<User> userList = userService.getUserList(user);
        return this.poClient(response, userList);
    }

    /**
     * @param delPerId 需要删除的用户id
     * @return
     * @description 删除用户
     * @author biubiubiu小浩
     * @date 2018/10/14 13:57
     **/
    @PostMapping(value = "/bms/user/delUser")
    public String delUser(HttpServletRequest request,
                          HttpServletResponse response, @NotBlank(message = "缺少必要参数") String delPerId) throws BMSException {
        User user = this.getUserSession(request);
        boolean flag = userService.delUserById(user, delPerId);
        return this.poClient(response, flag);
    }

    /**
     * @param model 更新用户模型
     * @return
     * @description 更新用户信息
     * @author biubiubiu小浩
     * @date 2018/10/14 17:41
     **/
    @PostMapping(value = "/bms/user/updateUser")
    public String updateUser(HttpServletRequest request,
                             HttpServletResponse response, @Valid UpdateUserModel model, BindingResult result) throws BMSException {
        if (result.hasErrors()) {
            return this.poClient(response, result.getFieldError().getDefaultMessage());
        }
        boolean flag = userService.updateUser(model);
        return this.poClient(response, flag);
    }
}
