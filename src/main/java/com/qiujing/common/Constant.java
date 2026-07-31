package com.qiujing.common;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.LinkedHashMap;

public class Constant {

    /**
     * 接口运行结果：成功
     */
    public final static Integer SUCCESS = 0;

    /**
     * 接口运行结果：失败
     */
    public final static Integer FAIL = 1;

    /**
     * 接口运行结果：登录态失效
     */
    public final static Integer LOGIN_EXPIRED = 2;

    /**
     * 默认报错提示语
     */
    public final static String DEFAULT_ERROR_MESSAGE = "服务器异常，请稍后重试";

    /**
     * 用户令牌
     */
    public final static String TOKEN = "Token";

    /**
     * trace_id
     */
    public final static String TRACE_ID = "trace_id";

    /**
     * 项目名称
     */
    @SuppressWarnings("SpellCheckingInspection")
    public final static String SPRING_APPLICATION_NAME = "qiujing-service-rebuild";

    /**
     * 空字符串
     */
    public final static String EMPTY = "";

    /**
     * 配置开启
     */
    public final static String ENABLED = "1";

    /**
     * Map反序列化类型（统一复用，避免重复创建）
     */
    public final static TypeReference<LinkedHashMap<String, Object>> LINKED_HASH_MAP_TYPE = new TypeReference<LinkedHashMap<String, Object>>() {

    };

    /**
     * 项目root路径
     */
    public final static String ROOT_PATH = System.getProperty("user.dir");

}
