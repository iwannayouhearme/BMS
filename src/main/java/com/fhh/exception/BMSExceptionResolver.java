package com.fhh.exception;

import com.fhh.base.BaseController;
import com.fhh.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-09 16:05
 */
@ControllerAdvice
public class BMSExceptionResolver extends BaseController implements HandlerExceptionResolver {
    @Autowired
    private MessageSource messageSource;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 这里用于捕获 springmvc 参数校验validate 没有通过的异常
        if (ex instanceof javax.validation.ConstraintViolationException) {
            String msg = generateMessage(ex);
            if (msg != null && msg.length() > 0) {
                this.poFailClient(response, msg);
            } else {
                this.poFailClient(response, "缺少必要参数！");
            }
            // OA 自定义异常信息
        } else if (ex instanceof BMSException) {
            this.poFailClient(response, ex.getMessage());
        } else {
            // 将异常错误信息写入到数据库中

            // 获取是那个端请求发送的异常(web/iso/android)
//            String source = request.getParameter("version");
//            String excepString = exceptionService.addExceptionInfo(ex.getMessage(),
//                    ex.getStackTrace(), source);
            ex.printStackTrace();
            this.poFailClient(response, "系统错误, 请联系管理员进行处理!");
        }
        return null;
    }

    /**
     * 生成参数校验失败的错误信息
     * @param ex
     * @return
     */
    private String generateMessage(Exception ex) {
        String message = "";
        Set<ConstraintViolation<?>> constraintViolations = ((javax.validation.ConstraintViolationException) ex)
                .getConstraintViolations();
        Iterator<ConstraintViolation<?>> it = constraintViolations.iterator();
        while (it.hasNext()) {
            ConstraintViolation cv = it.next();
            String msgTemp = cv.getMessageTemplate();
            // msgTemp 模板中包含中文，说明是自定义的消息，不用进行截取
            String key = msgTemp;
            if (!ValidatorUtil.isChinese(msgTemp)) {
                key = msgTemp.substring(1, msgTemp.length() - 1);
            }
            String msg = "";
            try {
                msg = messageSource.getMessage(key, new Object[] {}, Locale.CHINA);
            } catch (NoSuchMessageException msgEx) {
                msg = key;
            }
            message += msg + ", ";
        }
        return message.substring(0, message.lastIndexOf(","));
    }

}
