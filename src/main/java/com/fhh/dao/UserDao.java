package com.fhh.dao;

import com.fhh.entity.User;
import com.fhh.model.user.AddUserModel;
import com.fhh.model.user.UpdateUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 功能描述：（用户数据访问层接口类）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-09 15:15
 */
@Mapper public interface UserDao {
    /**
     * 根据登录账号和密码查询用户
     *
     * @param loginNumber
     * @param password
     * @return
     * @author: biubiubiu小浩
     */
    User selectByNumberAndPassword(@Param("loginNumber") String loginNumber, @Param("password") String password);

    /**
     * 添加用户
     *
     * @param model
     * @return
     * @author: biubiubiu小浩
     */
    int addUser(AddUserModel model);

    /**
     * @return
     * @description 获取所有用户列表
     * @author biubiubiu小浩
     * @date 2018/10/11 8:52
     **/
    List<User> getUserList();

    /**
     * @param userId
     * @return
     * @description 根据用户id获取用户信息
     * @author biubiubiu小浩
     * @date 2018/10/11 20:28
     **/
    User getUserById(String userId);

    /**
     * 更新用户信息
     *
     * @param model 更新用户模型
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/14 12:54
     **/
    int updateUser(UpdateUserModel model);

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @param delPer 删除人id
     * @return
     * @author biubiubiu小浩
     * @date 2018/10/14 12:56
     **/
    int delUser(@Param("userId") String userId, @Param("delPer") String delPer);
}
