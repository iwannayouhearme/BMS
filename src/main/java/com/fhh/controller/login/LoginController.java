package com.fhh.controller.login;

import com.fhh.base.BaseController;
import com.fhh.entity.User;
import com.fhh.exception.BMSException;
import com.fhh.service.UserService;
import com.fhh.util.CookiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author biubiubiu小浩
 * @description 用户操作类
 * @date 2018/10/10 9:29
 **/
@Controller
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @param response
     * @param loginNumber
     * @param password
     * @return
     * @throws BMSException
     * @description 用户登录
     * @author biubiubiu小浩
     * @date 2018/10/10 9:39
     **/
    @PostMapping(value = "/bms/user/login")
    public String userLogin(HttpServletResponse response, String loginNumber,
                            String password) throws BMSException {
        boolean flag = userService.userLogin(response, loginNumber, password);
        return this.poClient(response, flag);
    }

    /**
     * @param request
     * @param response
     * @return
     * @throws BMSException
     * @description 用户登出
     * @author biubiubiu小浩
     * @date 2018/10/10 9:56
     **/
    @PostMapping(value = "/bms/user/loginOut")
    public String userLoginOut(HttpServletRequest request,
                               HttpServletResponse response) throws BMSException {
        //清空cookies
        Boolean delCookies = CookiesUtil.delCookies(request, response);
        if (delCookies) {
            User user = this.getUserSession(request);
            //清除redis
            stringRedisTemplate.delete("bms:token:" + user.getId());
        }
        return this.poClient(response, delCookies);
    }
}
