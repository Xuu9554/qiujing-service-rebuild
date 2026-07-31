package com.qiujing.config;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(StrUtil.format("redis://{}:{}", host, port))
                .setPassword(password)
                .setDatabase(0)
                // max-active
                .setConnectionPoolSize(10)
                // min-idle
                .setConnectionMinimumIdleSize(5)
                // 设置空闲连接超时时间，而不是最大等待时间
                .setIdleConnectionTimeout(2000)
                // 设置连接超时时间，可能类似于 max-wait
                .setConnectTimeout(2000)
                // 设置命令重试次数
                .setRetryAttempts(3)
                // 设置命令重试发送时间间隔
                .setRetryInterval(1500);
        return Redisson.create(config);
    }

}

