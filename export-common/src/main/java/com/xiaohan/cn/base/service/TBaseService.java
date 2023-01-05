package com.xiaohan.cn.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import top.legendscloud.common.base.ReqPage;

import java.util.List;

/**
 * 基础service
 *
 * @author teddy
 * @since 2023/1/3
 */
public interface TBaseService<T> extends IService<T> {

    /**
     * 导出专用接口list
     *
     * @param reqPage 参数
     * @return 数据
     */
    List<T> exportList(ReqPage<T> reqPage);

    /**
     * 获取分页列表
     *
     * @param req 参数
     * @return 分页数据
     */
    IPage<T> page(ReqPage<T> req);

    /**
     * 发布广场列表查询
     *
     * @param t 参数对象
     * @return 列表
     */
    List<T> listEntity(T t);

    /**
     * 发布广场详情
     *
     * @param id 主键id
     * @return 实体
     */
    T loadById(Long id);

    /**
     * 发布广场新增
     *
     * @param tCatAddDto 根据需要进行传值
     */
    Boolean add(T tCatAddDto);

    /**
     * 发布广场修改
     *
     * @param tCatUpDataDto 根据需要进行传值
     */
    Boolean modifyById(T tCatUpDataDto);

    /**
     * 发布广场删除(单个条目)
     *
     * @param id 主键
     */
    Boolean deleteById(Long id);

    /**
     * 删除(多个条目)
     *
     * @param ids 主键集合
     */
    Boolean deleteByIds(List<Long> ids);
}
