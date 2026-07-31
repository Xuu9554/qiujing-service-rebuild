package com.qiujing.interceptor;

import cn.hutool.core.lang.Opt;
import com.qiujing.exception.LoginInvalidException;
import com.qiujing.holder.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@Order(1)
@Slf4j
@SuppressWarnings("NullableProblems")
public class UserInfoVerifyInterceptor extends BaseInterceptor {

    @Value("${qiujing.common.authentication_enabled:0}")
    @SuppressWarnings("SpellCheckingInspection")
    private String authenticationEnabled;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if ("1".equals(authenticationEnabled)) {
            Opt.ofBlankAble(UserHelper.get()).orElseThrow(LoginInvalidException::new);
        }

        return true;
    }

    /**
     * 在默认交易放行白名单的基础上添加其他的白名单交易路径
     *
     * @param interceptorExcludePathPatterns 默认交易放行白名单
     */
    @Override
    public void extendInterceptorExcludePathPatterns(List<String> interceptorExcludePathPatterns) {
        // 不拦截登录/退出/验证码获取等基础接口
        interceptorExcludePathPatterns.add("/portal/**");
    }

}
