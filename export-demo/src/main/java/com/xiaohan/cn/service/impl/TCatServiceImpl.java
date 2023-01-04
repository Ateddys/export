package com.xiaohan.cn.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohan.cn.enums.CatPubTypeEnum;
import com.xiaohan.cn.enums.CatSpeciesEnum;
import com.xiaohan.cn.enums.GenderEnum;
import com.xiaohan.cn.model.TCat;
import com.xiaohan.cn.service.TBaseServiceImpl;
import com.xiaohan.cn.service.TCatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 猫
 *
 * @author teddy
 * @since 2022/12/30
 */
@Slf4j
@Service
public class TCatServiceImpl extends TBaseServiceImpl<TCat> implements TCatService {

    @Override
    protected QueryWrapper<TCat> buildQueryWrapper(TCat param) {
        QueryWrapper<TCat> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda()
//                .eq(StringUtils.isNotBlank(param.getCatName()), TCat::getCatName, param.getCatName())
//                .eq(StringUtils.isNotBlank(param.getCatNo()), TCat::getCatNo, param.getCatNo())
//                .eq(CatPubTypeEnum.P_ALL.isAll(param.getPubType()), TCat::getPubType, param.getPubType())
//                .eq(CatSpeciesEnum.S_ALL.isAll(param.getSpecies()), TCat::getSpecies, param.getSpecies())
//                .eq(GenderEnum.G_ALL.isAll(param.getCatSex()), TCat::getSpecies, param.getSpecies())
//        ;
        return queryWrapper;
    }

}
