package com.qiujing.holder;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.client.codec.Codec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Slf4j
@Component
@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "SpellCheckingInspection"})
public class RedissonHolder {

    @Autowired
    private RedissonClient redissonClient;

    @Value("${qiujing.common.redis-project:FACTORY}")
    private String redisProject;

    /**
     * 获取指定Redis键对应的Redisson-RBucket映射
     *
     * @param cacheKeySuffix 缓存键的后缀部分
     * @param valueCodec     Value类型的Redisson编解码器
     * @param <V>            Redis缓存中Value的类型
     * @return {@link RBucket}<{@link V}> {@link RBucket} 实例
     */
    public <V> RBucket<V> getBucket(String cacheKeySuffix, Codec valueCodec) {
        return redissonClient.getBucket(StrUtil.format("{}:{}", redisProject, cacheKeySuffix), valueCodec);
    }

    /**
     * 获取指定类型的Redisson-RMap映射
     *
     * @param cacheNamespace 逻辑缓存命名空间
     * @param valueCodec     Value类型的Redisson编解码器
     * @param <K>            Redis缓存中Key的类型
     * @param <V>            Redis缓存中Value的类型
     * @return {@link RMap}<{@link K}, {@link V}> 指定Key和Value类型的Redisson分布式Map结构
     */
    public <K, V> RMap<K, V> getTypedMap(String cacheNamespace, Codec valueCodec) {
        return redissonClient.getMap(StrUtil.format("{}:{}", redisProject, cacheNamespace), valueCodec);
    }

    /**
     * 原子刷新缓存：先将数据写入临时 Map，再通过rename实现无缝替换，避免并发读空
     *
     * @param cacheNamespace 逻辑缓存命名空间
     * @param valueCodec     Value类型的Redisson编解码器
     * @param cacheEntries   要刷入缓存的内容
     * @param seconds        缓存有效秒数
     * @param <K>            Redis缓存中Key的类型
     * @param <V>            Redis缓存中Value的类型
     */
    public <K, V> void refreshTypedMap(String cacheNamespace, Codec valueCodec, Map<K, V> cacheEntries, long seconds) {
        this.refreshTypedMap(cacheNamespace, valueCodec, cacheEntries, Duration.ofSeconds(seconds));
    }

    /**
     * 原子刷新缓存：先将数据写入临时 Map，再通过rename实现无缝替换，避免并发读空
     *
     * @param cacheNamespace 逻辑缓存命名空间
     * @param valueCodec     Value类型的Redisson编解码器
     * @param cacheEntries   要刷入缓存的内容
     * @param duration       缓存有效时间(null为永久有效)
     * @param <K>            Redis缓存中Key的类型
     * @param <V>            Redis缓存中Value的类型
     */
    public <K, V> void refreshTypedMap(String cacheNamespace, Codec valueCodec, Map<K, V> cacheEntries, Duration duration) {
        if (MapUtil.isEmpty(cacheEntries)) {
            // 若缓存内容为空，则不执行写入和重命名操作(如果写入的Map为空，调用rename方法会报错)
            this.getTypedMap(cacheNamespace, valueCodec).clear();
            return;
        }
        RMap<K, V> temporaryMapping = this.getTypedMap(StrUtil.format("{}_TMP", cacheNamespace), valueCodec);
        // 确保是干净地写入
        temporaryMapping.clear();
        temporaryMapping.putAll(cacheEntries);
        Opt.ofNullable(duration).ifPresentOrElse(temporaryMapping::expire, temporaryMapping::clearExpire);
        // 使用rename实现原子替换
        temporaryMapping.rename(StrUtil.format("{}:{}", redisProject, cacheNamespace));
    }

    /**
     * 获取Redis中指定命名空间的原子计数器实例
     *
     * @param cacheNamespace 逻辑缓存命名空间
     * @return {@link RAtomicLong} RAtomicLong原子计数器实例
     */
    public RAtomicLong counterOf(String cacheNamespace) {
        return redissonClient.getAtomicLong(StrUtil.format("{}:{}", redisProject, cacheNamespace));
    }

    /**
     * 获取Redisson分布式锁实例，具体加锁与释放策略由调用方决定
     *
     * @param lockName 分布式锁名称
     * @return {@link RLock} Redisson分布式锁
     */
    public RLock getLock(String lockName) {
        return redissonClient.getLock(StrUtil.format("{}:{}", redisProject, lockName));
    }

}