package com.xiaohan.cn.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础service实现
 *
 * @author teddy
 * @since 2023/1/3
 */
@Service
public abstract class TBaseServiceImpl<T> extends ServiceImpl<BaseMapper<T>, T> implements TBaseService<T> {

}
