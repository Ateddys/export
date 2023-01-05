package com.xiaohan.cn.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 发布类型
 *
 * @author by teddy
 * @date 2022/12/16 10:00
 */
@Getter
public enum CatPubTypeEnum {

    // 所有类型
    P_ALL(-1, ""),

    // 喂养
    P_WY(1, "喂养"),

    // 领养
    P_LY(2, "领养"),

    // 说说
    P_SS(3, "说说");

    @EnumValue
    private Integer key;

    @JsonValue
    private String name;

    CatPubTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public boolean isAll(CatPubTypeEnum catPubTypeEnum) {
        return catPubTypeEnum != null && !this.key.equals(catPubTypeEnum.key);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
