package com.xiaohan.cn.exception;

/**
 * 参数校验工具类
 *
 * @author teddy
 * @since 2022/12/20
 */
public class BusinessPreconditions {

    /**
     * 参数校验，默认抛出00099未知系统异常
     *
     * @param expression   校验表达式
     * @param errorMessage 错误信息
     */
    public static void checkArgument(boolean expression, String errorMessage) {
        checkArgument(expression, "00099", errorMessage);
    }

    /**
     * 参数校验
     *
     * @param expression   校验表达式
     * @param errorCode    错误码
     * @param errorMessage 错误信息
     */
    public static void checkArgument(boolean expression, String errorCode, String errorMessage) {
        checkArgument(expression, new BaseException(errorCode, errorMessage));
    }

    /**
     * 参数校验
     *
     * @param expression 校验表达式
     * @param exception  异常
     */
    public static void checkArgument(boolean expression, BaseException exception) {
        if (!expression) {
            throw exception;
        }
    }
}
