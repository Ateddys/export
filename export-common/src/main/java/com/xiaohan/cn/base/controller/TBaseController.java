package com.xiaohan.cn.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohan.cn.base.service.TBaseService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.legendscloud.common.base.ComReq;
import top.legendscloud.common.base.ComResp;
import top.legendscloud.common.base.ReqPage;
import top.legendscloud.common.base.dto.BaseDelDTO;
import top.legendscloud.common.base.dto.BaseDelsDTO;

import javax.validation.Valid;
import java.util.List;

/**
 * 基础controller
 *
 * @author teddy
 * @since 2023/1/3
 */
public abstract class TBaseController<T> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected TBaseService tBaseService;

    @ApiOperation(value = "分页列表", notes = "分页列表")
    @PostMapping("/page")
    public ComResp<IPage<T>> page(@Valid @RequestBody ComReq<ReqPage<T>> comReq) {
        return new ComResp.Builder<IPage<T>>().fromReq(comReq)
                .data(tBaseService.page(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "列表查询", httpMethod = "POST")
    @PostMapping("/listEntity")
    public ComResp<List<T>> listEntity(@Valid @RequestBody ComReq<T> comReq) {
        return new ComResp.Builder<List<T>>().fromReq(comReq)
                .data(tBaseService.listEntity(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "根据id获取详情信息", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public ComResp<T> loadById(@PathVariable ComReq<Long> comReq) {
        return new ComResp.Builder<T>().fromReq(comReq)
                .data(tBaseService.loadById(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "新增数据", httpMethod = "POST")
    @PostMapping(value = "/add")
    public ComResp<Boolean> add(@Valid @RequestBody ComReq<T> comReq) {
        return new ComResp.Builder<Boolean>().fromReq(comReq)
                .data(tBaseService.add(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "修改数据", httpMethod = "POST")
    @PutMapping(value = "/modifyById")
    public ComResp<Boolean> modifyById(@Valid @RequestBody ComReq<T> comReq) {
        return new ComResp.Builder<>().fromReq(comReq)
                .data(tBaseService.modifyById(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "删除(单个条目)", httpMethod = "POST")
    @GetMapping(value = "/deleteById")
    public ComResp<Boolean> remove(@Valid @RequestBody ComReq<BaseDelDTO> comReq) {
        return new ComResp.Builder<>().fromReq(comReq)
                .data(tBaseService.deleteById(comReq.getData().getId()))
                .success().build();
    }

    @ApiOperation(value = "删除(多个条目)", httpMethod = "POST")
    @GetMapping(value = "/deleteByIds")
    public ComResp<Boolean> deleteByIds(@Valid @RequestBody ComReq<BaseDelsDTO> comReq) {
        return new ComResp.Builder<>().fromReq(comReq)
                .data(tBaseService.deleteByIds(comReq.getData().getIds()))
                .success().build();
    }
}
