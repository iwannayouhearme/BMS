package com.fhh.constant;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-14 13:25
 */
public interface BillConstant {
    /**
     * @author biubiubiu小浩
     * @description 借款类型
     * @date 2018/10/14 13:27
     **/
    static class Btype {
        /**
         * 商品
         */
        public static final String GOODS = "0";
        /**
         * 现金
         */
        public static final String CASH = "1";
    }

    /**
     * @author biubiubiu小浩
     * @description 还款状态
     * @date 2018/10/14 13:28
     **/
    static class Bstatus {
        /**
         * 未付款
         */
        public static final String UNPAY = "0";
        /**
         * 已付款
         */
        public static final String HASPAY = "1";
    }

    /**
     * @author biubiubiu小浩
     * @description 还款类型
     * @date 2018/10/14 13:30
     **/
    static class PaySource {
        /**
         * 已付款
         */
        public static final String CASH = "0";
        /**
         * 支付宝
         */
        public static final String ALPAY = "1";
        /**
         * 微信
         */
        public static final String WECHAT = "2";
        /**
         * 未结账
         */
        public static final String UNPAY = "3";
    }
}
