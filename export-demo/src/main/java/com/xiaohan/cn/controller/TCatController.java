package com.xiaohan.cn.controller;

import com.xiaohan.cn.model.TCat;
import com.xiaohan.cn.service.TCatService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TCatService tCatService;

//    @ApiOperation(value = "分页列表", notes = "分页列表")
//    @PostMapping("/page")
//    public ComResp<IPage<TCat>> page(@Valid @RequestBody ComReq<ReqPage<TCat>> comReq) {
//        return new ComResp.Builder<IPage<TCat>>().fromReq(comReq)
//                .data(tCatService.page(comReq.getData()))
//                .success().build();
//    }
}
