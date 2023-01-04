package com.xiaohan.cn.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaohan.cn.constant.ExportContant;
import com.xiaohan.cn.service.ExcelService;
import com.xiaohan.cn.service.TBaseService;
import com.xiaohan.cn.vo.ImportProgressVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.legendscloud.common.base.ComReq;
import top.legendscloud.common.base.ComResp;
import top.legendscloud.common.base.ReqPage;
import top.legendscloud.common.base.dto.BaseDelDTO;
import top.legendscloud.common.base.dto.BaseDelsDTO;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 基础controller
 *
 * @author teddy
 * @since 2023/1/3
 */
public class TBaseController<T> {

    @Autowired
    private TBaseService tBaseService;

    @Autowired
    private ExcelService<T> excelService;

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

    @ApiOperation("导入")
    @PostMapping("/importExcelData")
    public void importExcelData(@RequestBody ComReq<MultipartFile> comReq) {
        excelService.importExcelData(ExportContant.T_CAT, comReq.getData());
    }

    @ApiOperation("导出")
    @PostMapping("/exportExcelData")
    public void exportExcelData(@RequestBody ComReq<ReqPage<Void>> comReq, HttpServletResponse response) {
        excelService.exportExcelData(
                response,
                ExportContant.T_CAT,
                tBaseService.exportList(comReq.getData())
        );
    }

    @ApiOperation("导入异步结果")
    @PostMapping("/excelResult")
    public ComResp<ImportProgressVo> excelResult(@RequestBody ComReq<String> comReq) {
        return new ComResp.Builder<ImportProgressVo>().fromReq(comReq)
                .data(excelService.excelResult(ExportContant.T_CAT))
                .success().build();
    }
}
