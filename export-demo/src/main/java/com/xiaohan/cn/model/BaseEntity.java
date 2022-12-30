package com.xiaohan.cn.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类
 *
 * @author by teddy
 * @date 2022/12/5 15:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseEntity<L, T extends Model<T>> extends Model<T> {

    private static final long serialVersionUID = 1L;

    //    @JsonIgnore
    @ApiModelProperty("自增主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private L id;

    @ApiModelProperty("创建人")
    @TableField(value = "create_name", fill = FieldFill.INSERT)
    private String createName;

    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("最后操作人")
    @TableField(value = "last_name", fill = FieldFill.INSERT_UPDATE)
    private String lastName;

    @ApiModelProperty("最后操作时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "last_time", fill = FieldFill.INSERT_UPDATE)
    private Date lastTime;

    @TableLogic
    @TableField(value = "status", fill = FieldFill.INSERT)
    @ApiModelProperty(value="是否删除  1删除 0未删除")
    private Boolean status;
}
