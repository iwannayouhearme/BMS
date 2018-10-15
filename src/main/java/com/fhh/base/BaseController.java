package com.fhh.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhh.entity.User;
import com.fhh.util.CookiesUtil;
import com.fhh.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-09 16:14
 */
@Controller
public class BaseController {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    private static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
    /**
     * 获取当前登录用户token
     * @return
     */
    protected String getUserToken(HttpServletRequest request) {
        return CookiesUtil.getCookieByName(request, "token");
    }

    /**
     * 获取当前用户登录token 不带前缀
     * @author ：jfy
     * @date ：2018年6月22日 上午11:26:16
     * @param request
     * @return
     */
    protected String getToken(HttpServletRequest request) {
        return CookiesUtil.getCookieByToken(request);
    }

    /**
     * 获取用户登录的信息
     */
    protected User getUserSession(HttpServletRequest request) {
        String goods = request.getSession().getAttribute("user").toString();
        User user = JSONObject.parseObject(goods, User.class);
        if (goods != null) {
            return user;
        }
        return null;
    }

    /**
     * 获取用户缓存信息
     */
    protected User getUserInfo(String userid) {
        if (redisTemplate.hasKey("userInfo" + userid)) {
            String objs = redisTemplate.opsForValue().get("userInfo" + userid).toString();
            if (objs.length() > 0) {
                User goods = (User) DataUtil.jsonToModel(objs, new User());
                return goods;
            }
        }
        return null;
    }


    protected String isOneNullJudge(String[] judgeArray) {
        String isNullJudgeString = null;
        int step = 2;
        for (int i = 0; i < judgeArray.length; i = i + step) {
            if (this.isNil(judgeArray[i])) {
                isNullJudgeString = judgeArray[i + 1];
                break;
            }
        }
        return isNullJudgeString;
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

    protected boolean isNil(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 返回数据给前端 操作类
     *
     * @param response 响应返回
     * @param isStatus 请求状态
     */
    protected String poClient(HttpServletResponse response, Boolean isStatus) {
        String msg = "";
        if (!isStatus) {
            msg = "缺少必要参数!";
            return this.poFailClient(response, msg);
        }
        return this.poSuccessClient(response);
    }

    /**
     * 返回jsonObject给前端
     * @param response
     * @param chatString
     * @return
     */
    protected String poClient(HttpServletResponse response, JSONObject chatString) {
        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("data", chatString);

        return this.poReturnClient(response, json.toJSONString());
    }

    /**
     * 查询列表数据直接返回，不包含数量 JSONArray
     * @param response
     * @param parmJSONArray
     * @return
     */
    protected String poClient(HttpServletResponse response, JSONArray parmJSONArray) {
        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("data", parmJSONArray);
        return this.poReturnClient(response, json.toJSONString());
    }

    /**
     * 查询列表数据直接返回，不包含数量 List<Map<String,String>>
     * @param response
     * @param parmList
     * @return
     */
    protected String poClient(HttpServletResponse response, List<?> parmList) {
        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("data", JSONArray.parse(JSON.toJSONString(parmList)));
        return this.poReturnClient(response, json.toJSONString());
    }

    /**
     * 该方法已过期, 返回数据给前端 (后续修改为失败使用)
     *
     * @param response
     *            响应返回
     * @param isStatus
     *            请求状态
     * @param chatString
     *            响应给前端数据
     */
    protected String poFailClient(HttpServletResponse response, Boolean isStatus,
                                  String chatString) {
        // true 存在问题报错
        if (isStatus) {
            LOGGER.error("该方法不允许传入true,后续进行整体优化！");
        }
        return poFailClient(response, chatString);
    }

    /**
     * 自定义返回data数据
     * @param response
     * @param chatString
     * @return
     */
    public String poClient(HttpServletResponse response, String chatString) {
        JSONObject json = new JSONObject();
        json.put("success", true);
        json.put("data", chatString);
        return poReturnClient(response, json.toJSONString());
    }

    /**
     * 操作失败，返回操作失败
     * @param response
     * @param chatString
     * @return
     */
    protected String poFailClient(HttpServletResponse response, String chatString) {
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("msg", chatString);
        return this.poReturnClient(response, json.toJSONString());
    }

    /**
     * 操作成功直接返回
     * @param response
     * @return
     */
    protected String poSuccessClient(HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("success", true);
        return this.poReturnClient(response, json.toJSONString());
    }

    /**
     * 返回给前端的最终方法
     * @param response
     * @param chatString
     * @return
     */
    private String poReturnClient(HttpServletResponse response, String chatString) {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(chatString);
            out.close();
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        return null;
    }

    protected String poFailClient(HttpServletResponse response, String chatString, String code) {
        JSONObject json = new JSONObject();
        json.put("success", false);
        json.put("msg", chatString);
        if (!this.isNil(code)) {
            json.put("code", code);
        }
        return this.poReturnClient(response, json.toJSONString());
    }
}
