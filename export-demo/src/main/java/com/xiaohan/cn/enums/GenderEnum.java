package com.xiaohan.cn.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 性别枚举
 *
 * @author teddy
 * @since 2022/11/22 16:02
 */
@Getter
public enum GenderEnum {

    // 所有类
    G_ALL(-1, ""),

    // 男
    G_MAN(1, "男宝"),

    // 女
    G_WOMAN(2, "女宝");

    @EnumValue
    private Integer key;

    @JsonValue
    private String name;

    GenderEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public boolean isAll(GenderEnum genderEnum) {
        return genderEnum != null && !this.key.equals(genderEnum.key);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
