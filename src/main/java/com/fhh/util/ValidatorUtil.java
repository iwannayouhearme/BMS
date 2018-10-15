package com.fhh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-09 20:53
 */
public class ValidatorUtil {
    private ValidatorUtil() {
    }

    /**
     * @description 判断字符串是否为汉字
     * @author biubiubiu小浩
     * @date 2018/10/15 10:49
     * @param str
     * @return
     **/
    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    /**
     * @description 电话号码格式校验(常用电话号码开头：13、15、17、18)
     * @author biubiubiu小浩
     * @date 2018/10/15 10:50
     * @param phone 电话号码
     * @return
     **/
    public static boolean isPhone(String phone) {
        if (phone == null || phone.trim().length() == 0) {
            return false;
        }
        // String regExp = "/^1[3|4|5|7|8][0-9]{9}$/";
//		String regExp = "^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$";
        String regExp = "^1[0-9]{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        // String regExp = "/^1[3|4|5|7|8][0-9]{9}$/";
        String regExp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * @description 校验指定字符串的长度是否在指定的长度区间之中
     * @author biubiubiu小浩
     * @date 2018/10/15 10:50
     * @param
     * @return
     **/
    public static boolean isLimitLength(String str, int start, int end) {
        /*
         * 1. 首先健壮性校验
         */
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        if (start < 0 || end < 0 || end < start) {
            return false;
        }
        int length = str.trim().length();
        if (length >= start && length <= end) {
            return true;
        }
        return false;
    }

    /**
     * @description 校验对象是否为null(以及其指定的关键属性属性是否为空或者为null)
     * @author biubiubiu小浩
     * @date 2018/10/15 10:50
     * @param
     * @return
     **/
    public static boolean isNullOrEmpty(Object obj, String[] str) {
        boolean flag = false;
        // 1. 首先校验对象是否为"null"
        if (obj == null) {
            flag = true;
        }
        // 字符串对象
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0 ? true : false;
        }
        // 2. 判断该对象中指定的关键属性是否为"" 或者为 "null"
        if (str != null) {
            for (String s : str) {
                if (s == null || s.trim().length() <= 0) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
