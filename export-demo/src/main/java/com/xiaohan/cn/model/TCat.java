package com.xiaohan.cn.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohan.cn.base.model.BaseEntity;
import com.xiaohan.cn.enums.CatPubTypeEnum;
import com.xiaohan.cn.enums.CatSpeciesEnum;
import com.xiaohan.cn.enums.GenderEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * （猫）
 *
 * @author teddy
 * @since 2022/12/30
 */
@ApiModel(value = "TCat")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_cat")
public class TCat extends BaseEntity<Long, TCat> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableField(value = "cat_no")
    private String catNo;

    @ApiModelProperty(value = "猫昵称")
    @TableField(value = "cat_name")
    private String catName;

    @ApiModelProperty(value = "猫性别")
    @TableField(value = "cat_sex")
    private GenderEnum catSex;

    @ApiModelProperty(value = "猫龄")
    @TableField(value = "cat_age")
    private Integer catAge;

    @ApiModelProperty(value = "体重")
    @TableField(value = "cat_weight")
    private Double catWeight;

    @ApiModelProperty(value = "品种")
    @TableField(value = "species")
    private CatSpeciesEnum species;

    @ApiModelProperty(value = "发布类型")
    @TableField(value = "pub_type")
    private CatPubTypeEnum pubType;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;
}