package com.xiaohan.cn.vo;

import com.xiaohan.cn.constant.BaseSymbol;
import com.xiaohan.cn.constant.ExportContant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.xiaohan.cn.util.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * 导入进程
 * @author cansheng
 * @date 2021-10-20
 */
@Data
public class ImportProgressVo implements Serializable {
    private static final long serialVersionUID = 699479988905229523L;
    @ApiModelProperty("创建时间")
    private Date createdDate;
    @ApiModelProperty("修改时间")
    private Date lastModified;
    @ApiModelProperty(value = "状态 -1 失败 0：进行中 1: 成功")
    private Integer status;

    @ApiModelProperty("内容")
    private LinkedHashMap<String, String> content = new LinkedHashMap<>();

    @ApiModelProperty(value = "配置名称")
    private String name;

    public ImportProgressVo(String name, String userName) {
        this.createdDate = new Date();
        this.status = ExportContant.ImportStatusEnum.IN_PROGRESS.getKey();
        this.content.put(ExportContant.ImportProgressEnum.START_VALIDATE.getKey(), DateUtils.getSystemTime() + BaseSymbol.SPACE
                + ExportContant.ImportProgressEnum.START_VALIDATE.getName() + userName);
        this.name = name;
    }
}
