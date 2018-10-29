package com.fhh.service.impl;

import com.alibaba.fastjson.JSON;
import com.fhh.constant.BillConstant;
import com.fhh.constant.UserConstant;
import com.fhh.dao.BillDao;
import com.fhh.dao.UserDao;
import com.fhh.entity.BillModel;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.model.bill.GetBillByUserModel;
import com.fhh.model.bill.UpdateBillModel;
import com.fhh.model.user.AddUserModel;
import com.fhh.model.user.UpdateUserModel;
import com.fhh.service.UserService;
import com.fhh.util.CookiesUtil;
import com.fhh.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-09 15:13
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BillDao billDao;

    @Override
    public boolean userLogin(HttpServletResponse response, String loginNumber, String password) throws BMSException {
        User user = userDao.selectByNumberAndPassword(loginNumber, password);
        if (!ObjectUtils.isEmpty(user)) {
            String token = user.getId();
            //写入cookie token信息，设置过期时间为6000秒
            CookiesUtil.setCookie(response, UserConstant.RedisConstant.TOKEN, token, 5*365*24*60*60);
            stringRedisTemplate.opsForValue().set(UserConstant.RedisConstant.LOGINTOKEN + token, token);
            stringRedisTemplate.opsForValue().set(UserConstant.RedisConstant.USERINFO + user.getId(), JSON.toJSONString(user));
        } else {
            throw new BMSException("账号或密码错误！");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(AddUserModel model, User userModel) throws BMSException {
        model.setCreateManId(userModel.getId());
        //从redis中获取现在已经添加到了的用户编号
        String loginNumber = stringRedisTemplate.opsForValue().get(UserConstant.RedisConstant.LOGINNUMBER);
        //redis中的用户编号+1
        int loginNumberInt = Integer.parseInt(loginNumber) + 1;
        model.setLoginNumber(String.valueOf(loginNumberInt));
        model.setUuId(DataUtil.getUUid());
        if (userDao.addUser(model) < 1) {
            throw new BMSException("添加新用户失败，请稍后重试！");
        }
        //将新的用户编号写入redis中
        stringRedisTemplate.opsForValue().set(UserConstant.RedisConstant.LOGINNUMBER, String.valueOf(loginNumberInt));
        return true;
    }

    @Override
    public List<User> getUserList(User user) {
        //排除当前用户
        List<User> userList = userDao.getUserList();
        if (userList.isEmpty()) {
            return userList;
        }
        List<User> userList1 = new ArrayList<>();
        for (User userMap : userList) {
            if (!user.getId().equals(userMap.getId())) {
                userList1.add(userMap);
            }
        }
        return userList1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delUserById(User user, String delPerId) throws BMSException {
        //判断当前用户是否有为结账的账单，如果存在未结账的账单，则不允许删除
        GetBillByUserModel getBillByUserModel = new GetBillByUserModel();
        getBillByUserModel.setUserId(delPerId);
        getBillByUserModel.setBstatus("0");
        List<BillModel> billByUser = billDao.getBillByUser(getBillByUserModel);
        if (!billByUser.isEmpty()){
            throw new BMSException("该用户存在未付款的账单，不可删除！");
        }
        int delUser = userDao.delUser(user.getId(), delPerId);
        if (delUser < 1) {
            throw new BMSException("删除用户失败，请联系管理员处理！");
        }
        //清除redis中的用户信息
        if (stringRedisTemplate.hasKey(UserConstant.RedisConstant.USERINFO + user.getId())) {
            stringRedisTemplate.delete(UserConstant.RedisConstant.USERINFO + user.getId());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UpdateUserModel model) throws BMSException {
        int updateUser = userDao.updateUser(model);
        if (updateUser < 1) {
            throw new BMSException("更新用户信息失败，请联系管理员处理！");
        }
        // 更新账单表中冗余的用户信息字段
        //查询更新的用户关联的所有账单
        GetBillByUserModel billModel = new GetBillByUserModel();
        billModel.setUserId(model.getUserId());
        List<BillModel> billByUser = billDao.getBillByUser(billModel);
        if (!billByUser.isEmpty()) {
            for (BillModel bill : billByUser) {
                if (bill.getBstatus().equals(BillConstant.Bstatus.UNPAY)) {
                    UpdateBillModel updateBillModel = new UpdateBillModel();
                    updateBillModel.setBillId(bill.getId());
                    updateBillModel.setBorrowerMan(model.getRealName());
                    updateBillModel.setBorrowerNikeName(model.getNickName());
                    updateBillModel.setBorrowerManId(bill.getBorrowerManId());
                    int updateBill = billDao.updateBill(updateBillModel);
                    if (updateBill < 1) {
                        throw new BMSException("更新用户信息失败,请联系管理员处理！");
                    }
                }
            }
        }
        //更新用户信息成功后刷新redis中该用户的信息
        User updateUserInfo = userDao.getUserById(model.getUserId());
        stringRedisTemplate.opsForValue().set(UserConstant.RedisConstant.USERINFO + model.getUserId(), JSON.toJSONString(updateUserInfo));
        return true;
    }
}
