package com.xiaohan.cn.model.dto;

import com.xiaohan.cn.enums.CatPubTypeEnum;
import com.xiaohan.cn.enums.CatSpeciesEnum;
import com.xiaohan.cn.enums.GenderEnum;
import com.xiaohan.cn.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: teddylife
 * @description: 广场发布表（猫）
 * @author: Mr.Teddy
 * @create: 2022-11-22 18:44
 **/
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "com-wx-entity-TCat-Dto")
@Data
public class TCatDto extends BaseEntity<Long, TCatDto> {

    @ApiModelProperty(value = "编号")
    private String catNo;

    @ApiModelProperty(value = "猫昵称")
    private String catName;

    @ApiModelProperty(value = "猫性别")
    private GenderEnum catSex;

    @ApiModelProperty(value = "猫龄(月)")
    private Integer catAge;

    @ApiModelProperty(value = "体重 kg")
    private Double catWeight;

    @ApiModelProperty(value = "品种")
    private CatSpeciesEnum species;

    @ApiModelProperty(value = "发布类型")
    private CatPubTypeEnum pubType;

    @ApiModelProperty(value = "备注")
    private String remark;
}