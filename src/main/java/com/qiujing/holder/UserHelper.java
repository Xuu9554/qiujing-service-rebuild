package com.qiujing.holder;

import com.qiujing.common.UserThreadLocal;

public class UserHelper {

    /**
     * 获取当前线程中的登录用户信息
     *
     * @return {@link String} 登录用户id
     */
    public static String get() {
        return UserThreadLocal.get();
    }

}
