package com.xiaohan.cn.controller;

import com.xiaohan.cn.model.TCat;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发布广场
 *
 * @author teddy
 * @since 2022/11/22 15:29
 */
@RestController
@RequestMapping("/cat")
@Api(value = "TCatController | 发布广场")
public class TCatController extends TBaseController<TCat> {

}
