package com.xiaohan.cn.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author by teddy
 * @date 2022/12/29 9:46
 */
@Configuration
public class MybatisPlusConfig {

    /***
     * 分页插件
     * @return  PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
