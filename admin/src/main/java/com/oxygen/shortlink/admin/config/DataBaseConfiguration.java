package com.oxygen.shortlink.admin.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiJinLong
 * @description 数据库持久层配置类
 * @create 2024-02-21 20:06
 * @date 1.0
 */
@Configuration(value = "dataBaseConfigurationByAdmin")
public class DataBaseConfiguration {

    /**
     * 分页插件
     */
    @Bean
    @ConditionalOnBean
    public MybatisPlusInterceptor mybatisPlusInterceptorByAdmin() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
