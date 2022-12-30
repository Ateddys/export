package com.xiaohan.cn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohan.cn.model.TCat;
import com.xiaohan.cn.model.dto.TCatAddDto;
import com.xiaohan.cn.model.dto.TCatUpDataDto;
import com.xiaohan.cn.service.TCatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.legendscloud.common.base.ComReq;
import top.legendscloud.common.base.ComResp;
import top.legendscloud.common.base.ReqPage;
import top.legendscloud.common.base.dto.BaseDelDTO;
import top.legendscloud.common.base.dto.BaseDelsDTO;

import javax.validation.Valid;
import java.util.List;

/**
 * 发布广场
 *
 * @author teddy
 * @since 2022/11/22 15:29
 */
@RestController
@RequestMapping("/cat")
@Api(value = "TCatController | 发布广场")
public class TCatController {

    private final TCatService tCatService;

    public TCatController(TCatService tCatService) {
        this.tCatService = tCatService;
    }

    @ApiOperation(value = "分页列表", notes = "分页列表")
    @PostMapping("/page")
    public ComResp<IPage<TCat>> page(@Valid @RequestBody ComReq<ReqPage<TCat>> comReq) {
        return new ComResp.Builder<IPage<TCat>>().fromReq(comReq)
                .data(tCatService.page(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "发布广场列表查询", notes = "发布广场列表查询")
    @PostMapping("/listEntity")
    public ComResp<List<TCat>> listEntity(@Valid @RequestBody ComReq<TCat> comReq) {
        return new ComResp.Builder<List<TCat>>().fromReq(comReq)
                .data(tCatService.listEntity(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "发布广场详情", httpMethod = "GET")
    @GetMapping(value = "/{id}")
    public ComResp<TCat> loadById(@PathVariable ComReq<Long> comReq) {
        return new ComResp.Builder<TCat>().fromReq(comReq)
                .data(tCatService.loadById(comReq.getData()))
                .success().build();
    }

    @ApiOperation(value = "发布广场新增", httpMethod = "POST")
    @PostMapping(value = "/add")
    public ComResp<Boolean> add(@Valid @RequestBody ComReq<TCatAddDto> comReq) {
        tCatService.add(comReq.getData());
        return new ComResp.Builder<Boolean>().fromReq(comReq).success().build();
    }

    @ApiOperation(value = "发布广场修改", httpMethod = "POST")
    @PostMapping(value = "/modifyById")
    public ComResp<Boolean> modifyById(@Valid @RequestBody ComReq<TCatUpDataDto> comReq) {
        tCatService.modifyById(comReq.getData());
        return new ComResp.Builder<>().fromReq(comReq).success().build();
    }

    @ApiOperation(value = "发布广场删除(单个条目)", httpMethod = "POST")
    @PostMapping(value = "/deleteById")
    public ComResp<Boolean> remove(@Valid @RequestBody ComReq<BaseDelDTO> comReq) {
        tCatService.deleteById(comReq.getData().getId());
        return new ComResp.Builder<>().fromReq(comReq).success().build();
    }

    @ApiOperation(value = "发布广场删除(多个条目)", httpMethod = "POST")
    @PostMapping(value = "/deleteByIds")
    public ComResp<Boolean> deleteByIds(@Valid @RequestBody ComReq<BaseDelsDTO> comReq) {
        tCatService.deleteByIds(comReq.getData().getIds());
        return new ComResp.Builder<>().fromReq(comReq).success().build();
    }

}
