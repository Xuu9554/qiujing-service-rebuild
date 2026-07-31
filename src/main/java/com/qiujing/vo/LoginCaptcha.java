package com.qiujing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(staticName = "of")
public class LoginCaptcha implements Serializable {

    private static final long serialVersionUID = 148082058786432436L;

    /**
     * 验证码-凭证
     */
    private String verifyKey;

    /**
     * 验证码图片(base64)
     */
    private String captcha;

}
