package com.xiaohan.cn.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 猫品种
 *
 * @author by teddy
 * @date 2022/12/16 10:00
 */
@Getter
public enum CatSpeciesEnum {

    /**
     * 所有类型
     */
    S_ALL(-1, ""),

    // 英短蓝猫
    S_YDLM(1, "英短蓝猫"),
    // 布偶猫
    S_BOM(2, "布偶猫"),
    // 英短猫
    S_YDM(3, "英短猫"),
    // 豹猫
    S_BM(4, "豹猫"),
    // 德文猫
    S_DWM(5, "德文猫"),
    // 东短
    S_DD(6, "东短"),
    // 高地
    S_GD(7, "高地"),
    // 加菲
    S_JF(8, "加菲"),
    // 金吉拉
    S_JJL(9, "金吉拉"),
    // 金渐层
    S_JJC(10, "金渐层"),
    // 橘猫
    S_JM(11, "橘猫"),
    // 曼基康
    S_MJK(12, "曼基康"),
    // 美短
    S_MD(13, "美短"),
    // 美国卷耳猫
    S_MGJEM(14, "美国卷耳猫"),
    // 缅甸猫
    S_MDM(15, "缅甸猫"),
    // 缅因猫
    S_MYM(16, "缅因猫"),
    // 拿破仑
    S_NPL(17, "拿破仑"),
    // 田园猫
    S_TYM(18, "田园猫"),
    // 无毛猫
    S_WMM(19, "无毛猫"),
    // 暹罗
    S_TL(20, "暹罗"),
    // 狮子猫
    S_SZM(21, "狮子猫"),
    // 英短银渐层
    S_YDYJC(22, "英短银渐层"),
    // 玳瑁猫
    S_DMM(23, "玳瑁猫"),
    // 三花猫
    S_SHM(24, "三花猫"),
    // 奶牛猫
    S_MNM(25, "奶牛猫"),
    // 英短蓝白
    S_YDLB(26, "英短蓝白");

    @EnumValue
    private Integer key;

    @JsonValue
    private String name;

    CatSpeciesEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public boolean isAll(CatSpeciesEnum catSpeciesEnum) {
        return catSpeciesEnum != null && !this.key.equals(catSpeciesEnum.key);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
