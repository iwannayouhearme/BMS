package com.fhh.util;

import com.fhh.exception.BMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-08 21:50
 */
public class CookiesUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(CookiesUtil.class);
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return "bms:token:" + cookie.getValue();
        } else {
            return null;
        }
    }
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>(16);
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    public static String getCookieByName2(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 保存Cookies
     *
     * @param response
     *            servlet请求
     * @param value
     *            保存值
     * @author jxf
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name,
                                                String value, int time) {
        // new一个Cookie对象,键值对为参数
        Cookie cookie = new Cookie(name, value);
        // tomcat下多应用共享
        cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        cookie.setHttpOnly(true);
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("", e);
        }
        cookie.setMaxAge(time);
        // 将Cookie添加到Response中,使之生效
        // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        response.addCookie(cookie);
        return response;
    }

    public static String getCookieByToken(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey("token")) {
            Cookie cookie = (Cookie) cookieMap.get("token");
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public static Boolean delCookies(HttpServletRequest request,HttpServletResponse response) throws BMSException {
        Cookie[] cookies = request.getCookies();
        try {
            for (Cookie s : cookies) {
                Cookie cookie = new Cookie("delCookie",null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }catch(Exception ex){
            throw new BMSException("清除cookie失败！");
        }
        return true;
    }
}
