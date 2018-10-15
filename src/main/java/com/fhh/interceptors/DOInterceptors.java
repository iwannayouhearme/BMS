package com.fhh.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.carrotsearch.sizeof.RamUsageEstimator;
import com.fhh.entity.User;
import com.fhh.util.CookiesUtil;
import com.fhh.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述：（拦截器）
 * @date 2018-10-08 21:16
 * @author biubiubiu小浩
 */
public class DOInterceptors implements HandlerInterceptor {
    @Autowired
    protected RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ipA = (String) httpRequest.getHeader("X-Real-IP");
        if (ipA == null) {
            ipA = request.getRemoteAddr();
        }

        String url = httpRequest.getRequestURI();
        // 以下开始判断 用户是否合法
        String token = CookiesUtil.getCookieByName(httpRequest, "token");
        // 用户token 是否过期 或者不存在
        if (token == null) {
            return this.poClient(response, "您当前尚未登录哦，请先登录！");
        }
        String method = request.getMethod().toUpperCase();
        if ("post".equals(method)) {
            String bodySize = RamUsageEstimator.sizeOf(request.getParameterMap()) + "";
            String key = this.getRepeatRequestKey(token, url, bodySize);
            if (isRepeatRequest(key)) {
                return this.poClient(response, "请勿快速点击,请3秒后再操作！");
            }
            request.getSession().setAttribute("REPEATKEY", key);
        }
        if (stringRedisTemplate.hasKey(token)) {
            String goods = (stringRedisTemplate.opsForValue().get("userInfo" +stringRedisTemplate.opsForValue().get(token)));
            User user=JSONObject.parseObject(goods,User.class);
            redisTemplate.expire(token, 6000,TimeUnit.SECONDS);
            request.getSession().setAttribute("user", goods);

        } else {
//            token = CookiesUtil.getCookieByName2(httpRequest, "token");
//            if (!redisTemplate.hasKey(token)) {
//                return this.poClient(response, "110");
//            }
//            JSONObject jsona = JSON.parseObject(redisTemplate.opsForValue().get(token).toString());
//
//            String objs = redisTemplate.opsForValue().get("userInfo" + jsona.get("id")).toString();
//            User goods = (User) DataUtil.jsonToModel(objs, new User());
//            redisTemplate.expire(token, 1296000,TimeUnit.SECONDS);
//            request.getSession().setAttribute("user", goods);
            return this.poClient(response,"您当前尚未登录哦，请先登录！");
        }
        return true;
    }

    /**
     *
     * 根据 toekn 请求url 请求包大小  生成唯一key
     * @author ：biubiubiu小浩
     * @param url
     * @param bodySize
     * @return
     */
    public String getRepeatRequestKey(String token, String url, String bodySize) {
        String[] requesturl = url.split("/");
        url = requesturl[requesturl.length - 1];
        return "REPEATREQUEST:" + token + ":" + url + ":" + bodySize;
    }

    /**
     * 是否重复请求
     * @return
     */
    public boolean isRepeatRequest(String key) {
        // 思路：
        // 1、如果能获取到key 表示正在处理，直接返回处理结果
        // 2、如果key为空 则写入当前key到reids中
        if (redisTemplate.hasKey(key)) {
            // 锁定3秒
            redisTemplate.expire(key,3, TimeUnit.SECONDS);
        } else {
            return true;
        }
        return false;
    }

    public boolean poClient(HttpServletResponse response, String msg) throws IOException {
        // 登录超时
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().append("{\"success\":false,\"msg\":\"" + msg + "\"}");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String method = request.getMethod().toUpperCase();
        if ("POST".equals(method)) {
            String key = (String) request.getSession().getAttribute("REPEATKEY");
            redisTemplate.delete(key);
        }
    }

}
