package com.qiujing.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PortalLoginRequest implements Serializable {

    private static final long serialVersionUID = 4917821216628040870L;

    /**
     * 账号
     */
    private String userCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码-凭证
     */
    private String verifyKey;

    /**
     * 验证码
     */
    private String verifyCode;

}
