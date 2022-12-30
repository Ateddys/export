package com.xiaohan.cn.converts;

import com.xiaohan.cn.model.TCat;
import com.xiaohan.cn.model.dto.TCatAddDto;
import com.xiaohan.cn.model.dto.TCatDto;
import com.xiaohan.cn.model.dto.TCatUpDataDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 发布广场 数据转换
 *
 * @author by teddy
 * @date 2022/12/16 14:31
 */
@Mapper(componentModel = "spring")
public interface TCatConvertMapper {

    /**
     * dto 转 实体
     *
     * @param tCatDto dto
     * @return 实体
     */
    TCat tCatDtoToTCat(TCatDto tCatDto);

    /**
     * dtos 转 实体s
     *
     * @param tCatDtos dtos
     * @return 实体s
     */
    List<TCat> tCatDtoToTCats(List<TCatDto> tCatDtos);

    /**
     * 实体 转 dto
     *
     * @param tCat 实体
     * @return dto
     */
    TCatDto tCatToTCatDto(TCat tCat);

    /**
     * 实体s 转 dtos
     *
     * @param tCats 实体s
     * @return dtos
     */
    List<TCatDto> tCatToTCatDtos(List<TCat> tCats);

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
