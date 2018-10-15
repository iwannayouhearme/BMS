package com.fhh.service;

import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.user.AddUserModel;
import com.fhh.model.user.UpdateUserModel;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：（用户业务层接口）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-09 15:12
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param response
     * @param loginNumber
     * @param password
     * @return
     * @author: biubiubiu小浩
     */
    boolean userLogin(HttpServletResponse response, String loginNumber, String password) throws BMSException;

    /**
     * 新增用户
     *
     * @param model
     * @param user
     * @return
     * @author: biubiubiu小浩
     */
    boolean addUser(AddUserModel model, User user) throws BMSException;

    /**
     * @param user
     * @return
     * @description 获取用户列表
     * @author biubiubiu小浩
     * @date 2018/10/11 8:45
     **/
    List<User> getUserList(User user);
    /**
     * @description 根据用户id删除用户
     * @author biubiubiu小浩
     * @date 2018/10/14 13:59
     * @param user 当前操作用户模型
     * @param delPerId 删除人id
     * @return
     **/
    boolean delUserById(User user,String delPerId) throws BMSException;

    /**
     * @description 更新用户信息
     * @author biubiubiu小浩
     * @date 2018/10/14 17:43
     * @param model 更新用户信息模型
     * @return
     **/
    boolean updateUser(UpdateUserModel model) throws BMSException;
}
