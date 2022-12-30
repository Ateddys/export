package com.xiaohan.cn.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohan.cn.model.TCat;
import com.xiaohan.cn.model.dto.TCatAddDto;
import com.xiaohan.cn.model.dto.TCatUpDataDto;
import top.legendscloud.common.base.ReqPage;

import java.util.List;

/**
 * 猫
 *
 * @author teddy
 * @since 2022/12/30
 */
public interface TCatService extends IService<TCat> {

    /**
     * 发布广场分页列表
     *
     * @return 分页
     */
    IPage<TCat> page(ReqPage<TCat> req);

    /**
     * 发布广场列表查询
     *
     * @param tCat 参数对象
     * @return 列表
     */
    List<TCat> listEntity(TCat tCat);

    /**
     * 发布广场详情
     *
     * @param id 主键id
     * @return 实体
     */
    TCat loadById(Long id);

    /**
     * 发布广场新增
     *
     * @param tCatAddDto 根据需要进行传值
     */
    void add(TCatAddDto tCatAddDto);

    /**
     * 发布广场修改
     *
     * @param tCatUpDataDto 根据需要进行传值
     */
    void modifyById(TCatUpDataDto tCatUpDataDto);

    /**
     * 发布广场删除(单个条目)
     *
     * @param id 主键
     */
    void deleteById(Long id);

    /**
     * 删除(多个条目)
     *
     * @param ids 主键集合
     */
    void deleteByIds(List<Long> ids);
}
