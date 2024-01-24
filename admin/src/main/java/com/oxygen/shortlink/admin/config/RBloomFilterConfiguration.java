package com.oxygen.shortlink.admin.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiJinLong
 * @description 布隆过滤器配置类
 * @create 2024-01-24 17:11
 * @date 1.0
 */
@Configuration
public class RBloomFilterConfiguration {

    /**
     * 防止用户注册查询数据库的布隆过滤器
     * @param redissonClient
     * @return
     */
    @Bean
    public RBloomFilter<String> userRegisterCachePenetrationBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("userRegisterCachePenetrationBloomFilter");
        bloomFilter.tryInit(100000000L, 0.001);
        return bloomFilter;
    }

}
