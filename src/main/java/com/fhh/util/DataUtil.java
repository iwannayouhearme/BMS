package com.fhh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * 功能描述：（数据操作工具类）
 * @author: biubiubiu小浩
 * @date: 2018-10-09 14:23
 */
public class DataUtil {
    private static Logger LOGGER = LoggerFactory.getLogger(DataUtil.class);
    /**
     * 将json数据转成模型 并返回
     * @param json  json数据(JSObject 字符串)
     * @param obj  模型
     * @return
     */
    public static Object jsonToModel(String json, Object obj) {

        if (json.length() == 0) {
            return null;
        }
        JSONObject jsono = JSON.parseObject(json);
        return DataUtil.jsonToModel(jsono, obj);
    }

    public static Object jsonToModel(JSONObject jsono, Object obj) {
        Field[] fs = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                // 设置些属性是可以访问的
                f.setAccessible(true);

                String name = f.getName();
                if (!jsono.containsKey(name)) {
                    continue;
                }
                Object value = null;
                value = jsono.get(name);

                // 得到此属性的类型
                String type = f.getType().toString();
                if (type.endsWith("class java.lang.String")) {
                    // 给属性设值
                    f.set(obj, value + "");
                } else if (type.endsWith("int") || type.endsWith("Integer")) {
                    if (Integer.parseInt(value.toString()) != 0) {
                        // 给属性设值
                        f.set(obj, Integer.parseInt(value.toString()));
                    }

                } else {
                    LOGGER.info(f.getType() + "\t");
                }
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            LOGGER.error("", e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            LOGGER.error("", e);
        } // 得到此属性的值
        return obj;
    }

    /**
     * @description 生成UUID
     * @author biubiubiu小浩
     * @date 2018/10/14 20:24
     * @param
     * @return
     **/
    public static String getUUid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
