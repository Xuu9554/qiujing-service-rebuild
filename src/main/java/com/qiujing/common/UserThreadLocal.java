package com.qiujing.common;

public class UserThreadLocal {

    private static final ThreadLocal<String> AUTHORIZATION = new ThreadLocal<>();

    /**
     * 设置当前线程中的登录用户信息
     *
     * @param userCode 登录用户账号
     */
    public static void set(String userCode) {
        AUTHORIZATION.set(userCode);
    }

    /**
     * 获取当前线程中的登录用户信息
     *
     * @return {@link String} 登录用户id
     */
    public static String get() {
        return AUTHORIZATION.get();
    }

    /**
     * 清除当前线程中的登录用户信息
     */
    public static void remove() {
        AUTHORIZATION.remove();
    }

}
