package com.fhh.base;

import com.alibaba.fastjson.JSON;
import com.fhh.constant.UserConstant;
import com.fhh.dao.UserDao;
import com.fhh.entity.User;
import com.fhh.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-11 20:01
 */
public class BaseService {
    /**
     * 缓存
     */
    @Autowired
    protected StringRedisTemplate stringRedisTemplate;
    @Autowired
    protected UserDao userDao;

    protected boolean isInteger(String str) {
        String compileString = "^[-\\+]?[\\d]*$";
        Pattern pattern = Pattern.compile(compileString);
        return pattern.matcher(str).matches();
    }

    protected boolean isNil(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    protected String isNullJudge(String[] judgeArray) {
        String isNullJudgeString = "";
        for (int i = 0; i < judgeArray.length; i++) {
            if (this.isNil(judgeArray[i])) {
                isNullJudgeString = "缺少必要参数";
                break;
            }
        }
        return isNullJudgeString;
    }

    /**
     * 获取用户信息
     */
    protected User getUserInfo(String userid) {
        String userKey = "userInfo" + userid;
        User goods = new User();
        if (stringRedisTemplate.hasKey(userKey)) {
            // 格式备注 角色id,角色2id--对应权限组合id,权限组合id2
            String objs = stringRedisTemplate.opsForValue().get(userKey);
            if (objs.length() > 0) {
                goods = (User) DataUtil.jsonToModel(objs, new User());
            }
        } else {
            goods = userDao.getUserById(userid);
            if (!ObjectUtils.isEmpty(goods)){
                stringRedisTemplate.opsForValue().set("userInfo"+userid, JSON.toJSONString(goods));
            }
        }
        return goods;
    }

    /**
     * @description 判断当前用户是否有权限
     * @author biubiubiu小浩
     * @date 2018/10/12 8:56
     * @param user
     * @return
     **/
    protected boolean judgeHasPower(User user){
        if (UserConstant.Type.ADMIN.equals(user.getType())){
            return true;
        }
        return false;
    }
}