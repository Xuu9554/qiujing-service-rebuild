package com.qiujing.interceptor;

import cn.hutool.core.lang.Opt;
import com.qiujing.common.UserThreadLocal;
import com.qiujing.holder.RedissonHolder;
import org.redisson.api.RBucket;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.qiujing.common.Constant.TOKEN;

@Order(0)
@Component
@SuppressWarnings({"NullableProblems", "SpringJavaAutowiredFieldsWarningInspection"})
public class UserInfoInitInterceptor extends BaseInterceptor {

    @Autowired
    private RedissonHolder redissonHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(TOKEN);
        RBucket<String> bucket = redissonHolder.getBucket("PORTAL:TOKEN:" + token, StringCodec.INSTANCE);
        Opt.ofBlankAble(bucket.get()).ifPresent(UserThreadLocal::set);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 接口执行完毕后清除信息，避免内存溢出
        UserThreadLocal.remove();
    }

}
