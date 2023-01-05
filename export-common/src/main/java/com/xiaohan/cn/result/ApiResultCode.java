package com.xiaohan.cn.result;

import java.text.NumberFormat;

/**
 * 结果码, 表示接口处理结果状态
 *
 * @author teddy
 * @since 2022/12/26
 */
public class ApiResultCode {

    public static final ApiResultCode SUCCESS = buildApiResultCode(getBaseResultCode(), 0, "成功");
    public static final ApiResultCode UNKNOWN_SYSTEM_ERROR = buildApiResultCode(getBaseResultCode(), 99, "未知系统异常");

    public static final ApiResultCode INVALID_PARAM_TYPE = buildApiResultCode(getBaseResultCode(), 10, "无效的提交参数类型, 字段:{0}");
    public static final ApiResultCode INVALID_PARAM_VALUE = buildApiResultCode(getBaseResultCode(), 11, "无效的提交参数值, 字段:{0}, 验证错误信息:{1}");
    public static final ApiResultCode PARAM_IS_NULL = buildApiResultCode(getBaseResultCode(), 12, "参数值不能为空,字段:{0}");
    public static final ApiResultCode REQUIRE_PARAM_IS_ERROR = buildApiResultCode(getBaseResultCode(), 13, "请求参数格式错误");
    public static final ApiResultCode REQUIRE_PARAM_IS_NOT_PRESENT = buildApiResultCode(getBaseResultCode(), 14, "必要的请求参数不存在,类型:{0},字段:{1}");
    public static final ApiResultCode REFERENCE_DATA_ERROR = buildApiResultCode(getBaseResultCode(), 15, "修改数据错误,外键值不正确");
    public static final ApiResultCode DELETE_FAIL_REFERENCE = buildApiResultCode(getBaseResultCode(), 16, "数据已被引用，不可删除");
    public static final ApiResultCode IMPORT_EXCEL_FAIL = buildApiResultCode(getBaseResultCode(), 17, "导入excel文件失败");
    public static final ApiResultCode EXPORT_EXCEL_FAIL = buildApiResultCode(getBaseResultCode(), 18, "导出excel文件失败");
    public static final ApiResultCode GET_DATA_NOT_EXIST = buildApiResultCode(getBaseResultCode(), 19, "查询{0}数据不存在");
    public static final ApiResultCode PERMISSION_DENIED = buildApiResultCode(getBaseResultCode(), 20, "对不起,您没有权限访问该功能");
    public static final ApiResultCode TOKEN_INVALID = buildApiResultCode(getBaseResultCode(), 21, "您的登录状态已失效,请重新登录");
    public static final ApiResultCode DELETE_DATA_FAIL = buildApiResultCode(getBaseResultCode(), 22, "删除数据失败");
    public static final ApiResultCode UPDATE_DATA_FAIL = buildApiResultCode(getBaseResultCode(), 23, "更新数据失败");
    public static final ApiResultCode INSERT_DATA_FAIL = buildApiResultCode(getBaseResultCode(), 24, "新建数据失败");
    public static final ApiResultCode INVALID_PARAM_ERROR = buildApiResultCode(getBaseResultCode(), 25, "参数校验错误");
    public static final ApiResultCode FILE_NOT_EXIST = buildApiResultCode(getBaseResultCode(), 26, "没有导入的文件");
    public static final ApiResultCode SORT_STRING_ERROR = buildApiResultCode(getBaseResultCode(), 27, "排序字段{0}错误");
    public static final ApiResultCode BAD_SQL_GRAMMAR = buildApiResultCode(getBaseResultCode(), 28, "sql语法错误");
    public static final ApiResultCode API_VERSION_ERROR = buildApiResultCode(getBaseResultCode(), 29, "api版本号填写错误");
    public static final ApiResultCode API_VERSION_NOT_EXIST = buildApiResultCode(getBaseResultCode(), 30, "api版本不存在");
    public static final ApiResultCode PARAM_NOT_ALLOW = buildApiResultCode(getBaseResultCode(), 31, "不接受提交的参数字段: {0}");
    public static final ApiResultCode USER_HAS_LOGGED = buildApiResultCode(getBaseResultCode(), 32, "账号从其他设备登录,需要重新登录");
    public static final ApiResultCode QUERY_DATA_FAIL = buildApiResultCode(getBaseResultCode(), 33, "查询数据失败:{0}");
    public static final ApiResultCode EDIT_PROTECTED_DATA_FAIL = buildApiResultCode(getBaseResultCode(), 34, "无法编辑系统数据");
    public static final ApiResultCode EXCEL_FAIL_MESSAGE = buildApiResultCode(getBaseResultCode(), 35, "导入导出失败，原因：{0}");

    /**
     * 返回码
     */
    private String resultCode;
    /**
     * 返回信息
     */
    private String resultMessage;
    /**
     * 模块基础码
     */
    private static final int BASE_RESULT_CODE = 0;

    public ApiResultCode() {
    }

    public ApiResultCode(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    private static int getBaseResultCode() {
        return BASE_RESULT_CODE;
    }

    private static NumberFormat getNumberFormat() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumIntegerDigits(5);
        return numberFormat;
    }

    /**
     * @param baseResultCode 模块错误码前两位, 同一微服务内所有错误码前两位是一样的, 不同微服务错误码前两位不要重复
     * @param subResultCode  子错误码, 同一微服务内不要重复
     * @param resultMessage  错误描述
     * @return ApiResultCode对象
     */
    public static ApiResultCode buildApiResultCode(int baseResultCode, int subResultCode, String resultMessage) {
        return new ApiResultCode(getNumberFormat().format(baseResultCode * 10000L + subResultCode), resultMessage);
    }

    /**
     * @param baseResultCode 模块错误码前两位, 同一微服务内所有错误码前两位是一样的, 不同微服务错误码前两位不要重复
     * @param subResultCode  子错误码, 同一微服务内不要重复
     * @param resultMessage  错误描述
     * @return ApiResultCode对象
     */
    public static ApiResultCode buildApiResultCode(int baseResultCode, String subResultCode, String resultMessage) {
        return new ApiResultCode(getNumberFormat().format(baseResultCode * 10000L + Integer.valueOf(subResultCode)), resultMessage);
    }

    /**
     * 获取 {@link #resultCode}
     *
     * @return {@link #resultCode}
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * 设置 {@link #resultCode}
     *
     * @param resultCode {@link #resultCode}
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 获取 {@link #resultMessage}
     *
     * @return {@link #resultMessage}
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * 设置 {@link #resultMessage}
     *
     * @param resultMessage {@link #resultMessage}
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
