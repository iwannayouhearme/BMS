package com.fhh.constant;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-12 08:58
 */
public interface UserConstant {
    /**
     * @author biubiubiu小浩
     * @description 用户类型
     * @date 2018/10/12 8:59
     **/
    static class Type {
        /**
         * 管理员
         */
        public static final String ADMIN = "1";
        /**
         * 普通用户
         */
        public static final String COMMON = "2";
    }

    /**
     * @param
     * @author biubiubiu小浩
     * @description redis中的常量
     * @date 2018/10/14 17:49
     * @return
     **/
    static class RedisConstant {
        /**
         * 用户信息
         */
        public static final String USERINFO = "userInfo";
        /**
         * token
         */
        public static final String TOKEN = "token";
        /**
         * bms token
         */
        public static final String LOGINTOKEN = "bms:token:";
        /**
         * 登录账号
         */
        public static final String LOGINNUMBER = "Login_Number";
    }
}