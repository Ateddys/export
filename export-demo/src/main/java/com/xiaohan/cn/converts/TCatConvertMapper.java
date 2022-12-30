package com.xiaohan.cn.converts;

import com.xiaohan.cn.model.TCat;
import com.xiaohan.cn.model.dto.TCatAddDto;
import com.xiaohan.cn.model.dto.TCatUpDataDto;
import org.mapstruct.Mapper;

/**
 * 发布广场 数据转换
 *
 * @author by teddy
 * @date 2022/12/16 14:31
 */
@Mapper(componentModel = "spring")
public interface TCatConvertMapper {

    /**
     * 新增dto实体 转 实体
     *
     * @param tCatAddDto 新增dto实体
     * @return 实体
     */
    TCat tCatAddDtoToTCat(TCatAddDto tCatAddDto);

    /**
     * 修改dto实体 转 实体
     *
     * @param tCatUpDataDto 新增dto实体
     * @return 实体
     */
    TCat tCatUpDataDtoToTCat(TCatUpDataDto tCatUpDataDto);
}
