package com.xiaohan.cn.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * 属性映射
 *
 * @author teddy
 * @since 2022/12/27
 */
@SuppressWarnings("serial")
@Data
@Accessors(chain = true)
public class PropertyInfo implements Serializable {
    /**
     * 属性名称，正常字段普通字母 扩展字段带 “.” 依赖其他表的字段带 “@”
     */
    private String key;
    /**
     * 是否为扩展字段 Y,N
     */
    private String extend;
    /**
     * 是否为依赖其他表的字段 Y,N
     */
    private String ref;
    /**
     * 表单或表头的国际化key
     */
    private String labelKey;
    /**
     * 前端组件类型 readonly 只读,input 输入框,textarea 文本域, select 单选,mulSelect 多选,switch
     * 开关,radio,checkbox,slider 滑块,time 时间(时分秒),timeRange 时间范围,date 日期,
     * dateRange 日期范围,dateTime,dateTimeRange,cascader 级联单选,mulCascader 级联多选,file 文件,richText 富文本
     */
    private String elementType = "input";
    /**
     * 导出的日期格式(仅在elementType包含date的情况下生效)
     */
    private String pattern;

}
