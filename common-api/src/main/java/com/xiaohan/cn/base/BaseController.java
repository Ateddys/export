package com.xiaohan.cn.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 基础控制
 *
 * @author by teddy
 * @date 2022/12/28 18:25
 */
@RestController
public class BaseController<T> {

    @Autowired
    private BaseService<T> baseService;

    public List<T> list() {
        return baseService.list();
    }
}
