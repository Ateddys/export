package com.xiaohan.cn.service.impl;

import com.xiaohan.cn.model.User;
import com.xiaohan.cn.mapper.UserMapper;
import com.xiaohan.cn.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author teddy
 * @since 2022-12-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
