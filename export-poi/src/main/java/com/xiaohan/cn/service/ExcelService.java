package com.xiaohan.cn.service;

import com.xiaohan.cn.result.vo.ImportProgressVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 导入导出接口
 *
 * @author teddy
 * @since 2022/12/30
 */
public interface ExcelService<T> {

    /**
     * 获取对应配置页面的row元素名
     * @param name 配置name
     * @return row元素集
     */
    String[] getHeaderRowNames(String name);

    /**
     * 导入excel文件
     * @param name 配置name
     * @param file 文件
     */
    void importExcel(String name, MultipartFile file);

    /**
     * 主数据 导出
     *
     * @param response res
     * @param name 配置name
     * @param datas 数据源  List<***DTO>
     */
    void exportData(HttpServletResponse response, String name, List<T> datas);

    /**
     * 获取导入结果
     *
     * @param name 配置name
     */
    ImportProgressVo result(String name);
}
