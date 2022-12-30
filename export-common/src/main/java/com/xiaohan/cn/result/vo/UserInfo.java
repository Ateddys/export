package com.xiaohan.cn.result.vo;

import lombok.Data;

/**
 * @author teddy
 * @since 2022/12/29
 */
@Data
public class UserInfo {
    private static final long serialVersionUID = -7880266006078225771L;

    private Long id;

    private String username;

    private String fullName;

    private String token;

    private Long tenantId;

    private String tenantCode;

    private Long orgId;

    private String json;
}
