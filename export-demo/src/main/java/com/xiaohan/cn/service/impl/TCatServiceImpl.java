package com.xiaohan.cn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohan.cn.converts.TCatConvertMapper;
import com.xiaohan.cn.enums.CatPubTypeEnum;
import com.xiaohan.cn.enums.CatSpeciesEnum;
import com.xiaohan.cn.enums.GenderEnum;
import com.xiaohan.cn.mapper.TCatMapper;
import com.xiaohan.cn.model.TCat;
import com.xiaohan.cn.model.dto.TCatAddDto;
import com.xiaohan.cn.model.dto.TCatDto;
import com.xiaohan.cn.model.dto.TCatUpDataDto;
import com.xiaohan.cn.service.TBaseServiceImpl;
import com.xiaohan.cn.service.TCatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.legendscloud.common.base.ReqPage;
import top.legendscloud.common.enums.CommonEnumCode;
import top.legendscloud.common.exception.BizException;
import top.legendscloud.db.page.PageUtils;

import java.util.List;

/**
 * 猫
 *
 * @author teddy
 * @since 2022/12/30
 */
@Slf4j
@Service
public class TCatServiceImpl extends TBaseServiceImpl<TCat> implements TCatService {

//    @Autowired
//    private PageUtils pageUtils;
//
//
//    private QueryWrapper<TCat> getBuildQueryWrapper(TCat param) {
//        QueryWrapper<TCat> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda()
//                .eq(StringUtils.isNotBlank(param.getCatName()), TCat::getCatName, param.getCatName())
//                .eq(StringUtils.isNotBlank(param.getCatNo()), TCat::getCatNo, param.getCatNo())
//                .eq(CatPubTypeEnum.P_ALL.isAll(param.getPubType()), TCat::getPubType, param.getPubType())
//                .eq(CatSpeciesEnum.S_ALL.isAll(param.getSpecies()), TCat::getSpecies, param.getSpecies())
//                .eq(GenderEnum.G_ALL.isAll(param.getCatSex()), TCat::getSpecies, param.getSpecies())
//        ;
//        return queryWrapper;
//    }
//
//    @Override
//    public List<TCat> exportList(ReqPage<TCatDto> reqPage) {
//        return tCatConvertMapper.tCatDtoToTCats(this.page(reqPage).getRecords());
//    }
//
//    @Override
//    public IPage<TCatDto> page(ReqPage<TCatDto> req) {
//        return page(pageUtils.page(req.getPage(), req.getSize(), req.isSort(), req.getSortName()),
//                getBuildQueryWrapper(tCatConvertMapper.tCatDtoToTCat(req.getData())));
//    }
//
//    @Override
//    public List<TCatDto> listEntity(TCatDto tCatDto) {
//        return tCatConvertMapper.tCatToTCatDtos(this.list(getBuildQueryWrapper(tCatConvertMapper.tCatDtoToTCat(tCatDto))));
//    }
//
//    @Override
//    public TCatDto loadById(Long id) {
//        return tCatConvertMapper.tCatToTCatDto(getById(id));
//    }
//
//    @Override
//    public void add(TCatAddDto tCatAddDto) {
//        if (!save(tCatConvertMapper.tCatAddDtoToTCat(tCatAddDto))) {
//            throw new BizException(CommonEnumCode.FAIL.getCode(), "新增出现问题!");
//        }
//    }
//
//    @Override
//    public void modifyById(TCatUpDataDto tCatUpDataDto) {
//        if (!updateById(tCatConvertMapper.tCatUpDataDtoToTCat(tCatUpDataDto))) {
//            throw new BizException(CommonEnumCode.FAIL.getCode(), String.format("修改异常！ID：%d", tCatUpDataDto.getId()));
//
//        }
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        if (!removeById(id)) {
//            throw new BizException(CommonEnumCode.FAIL.getCode(), String.format("删除异常！ID：%d", id));
//        }
//    }
//
//    @Override
//    public void deleteByIds(List<Long> ids) {
//        if (!removeByIds(ids)) {
//            throw new BizException(CommonEnumCode.FAIL.getCode(), String.format("批量删除异常！IDS：%s", ids.toArray()));
//        }
//    }

}
