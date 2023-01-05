package com.xiaohan.cn.controller;

import com.xiaohan.cn.base.controller.TBaseController;
import com.xiaohan.cn.base.service.TBaseService;
import com.xiaohan.cn.constant.ExportContant;
import com.xiaohan.cn.service.ExcelService;
import com.xiaohan.cn.vo.ImportProgressVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import top.legendscloud.common.base.ComReq;
import top.legendscloud.common.base.ComResp;
import top.legendscloud.common.base.ReqPage;

import javax.servlet.http.HttpServletResponse;

/**
 * 基础controller含有导入导出
 *
 * @author teddy
 * @since 2023/1/5
 */
public class TBaseExcelController<T> extends TBaseController<T> {

    @Autowired
    private ExcelService<T> excelService;

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
