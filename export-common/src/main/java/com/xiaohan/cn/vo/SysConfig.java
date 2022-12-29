package com.xiaohan.cn.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author teddy
 * @since 2022/12/29
 */
@Data
public class SysConfig {
    @ApiModelProperty("配置名称名称")
    private String name;
    @ApiModelProperty("配置编码")
    private String code;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("配置对应的模块")
    private String type;

    @ApiModelProperty("属性定义")
    private Map<String, PropertyInfo> propertyMap;
    @ApiModelProperty("导入列")
    private List<String> exportList;
    @ApiModelProperty("导出列")
    private List<String> importList;
}
