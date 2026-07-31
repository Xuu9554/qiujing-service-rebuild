package com.qiujing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor(staticName = "of")
public class PortalLoginResult implements Serializable {

    private static final long serialVersionUID = -9154155331731409140L;

    /**
     * 登录凭证
     */
    private String token;

    /**
     * 用户姓名
     */
    private String userName;

}
