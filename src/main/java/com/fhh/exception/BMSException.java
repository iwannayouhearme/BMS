package com.fhh.exception;

/**
 * 功能描述：（）
 *
 * @author: biubiubiu小浩
 * @date: 2018-10-09 16:03
 */
public class BMSException extends Exception {
    /**
     * 异常信息
     */
    private String message;

    public BMSException(String message) {
        super(message);
        this.message = message;

    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
