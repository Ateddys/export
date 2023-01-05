package com.xiaohan.cn.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * mybatis 分页配置、自动添加基础字段
 *
 * @author teddy
 * @date 2022/12/27
 */
@Configuration
public class MybatisPlusConfig {

    /***
     * 分页插件
     * @return PaginationInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "status", Boolean.class, Boolean.TRUE);
                this.strictInsertFill(metaObject, "createName", String.class, "admin");
                this.strictInsertFill(metaObject, "create_time", Date.class, new Date());
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "lastName", String.class, "admin");
                this.strictUpdateFill(metaObject, "last_time", Date.class, new Date());
            }
        };
    }

}
