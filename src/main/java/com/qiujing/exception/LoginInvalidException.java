package com.qiujing.exception;

public class LoginInvalidException extends RuntimeException {

    private static final long serialVersionUID = -413272634761111466L;

    /**
     * 构造LoginInvalidException
     */
    public LoginInvalidException() {
        super("登录态已过期, 请重新登录");
    }

}
