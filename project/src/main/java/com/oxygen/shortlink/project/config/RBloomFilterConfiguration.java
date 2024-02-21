package com.oxygen.shortlink.project.config;

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
     * 防止短链接创建查询数据库的布隆过滤器
     * @param redissonClient
     * @return
     */
    @Bean
    public RBloomFilter<String> shortUriCreateCachePenetrationBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("shortUriCreateCachePenetrationBloomFilter");
        bloomFilter.tryInit(100000000L, 0.001);
        return bloomFilter;
    }

}
