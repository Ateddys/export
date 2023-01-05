package com.xiaohan.cn.base.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xiaohan.cn.vo.PropertyInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * 配置信息（导入导出）
 *
 * @author teddy
 * @since 2022/12/30
 */
@ApiModel(value = "TSysConfig")
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@TableName("t_sys_config")
public class TSysConfig extends BaseEntity<Long, TSysConfig> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("配置名称名称")
    @TableField(value = "name")
    private String name;
    @ApiModelProperty("配置编码")
    @TableField(value = "code")
    private String code;
    @ApiModelProperty("配置对应的模块")
    @TableField(value = "type")
    private String type;
    @ApiModelProperty("属性映射配置")
    @TableField(value = "property_mapping")
    private String propertyMapping;
    @ApiModelProperty("Excel导入配置")
    @TableField(value = "import_mapping")
    private String importMapping;
    @ApiModelProperty("Excel导出配置")
    @TableField(value = "export_mapping")
    private String exportMapping;

    @TableField(exist = false)
    @ApiModelProperty("属性定义")
    private Map<String, PropertyInfo> propertyMap;
    @TableField(exist = false)
    @ApiModelProperty("导入列")
    private List<String> exportList;
    @TableField(exist = false)
    @ApiModelProperty("导出列")
    private List<String> importList;
}
