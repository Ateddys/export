package com.xiaohan.cn.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by teddy
 * @date 2022/12/28 18:40
 */
@Data
public class User implements Serializable {

    private Long id;

    private String name;
}
