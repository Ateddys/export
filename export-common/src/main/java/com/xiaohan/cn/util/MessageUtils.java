package com.xiaohan.cn.util;

import com.xiaohan.cn.exception.BaseException;
import com.xiaohan.cn.i18n.I18nUtils;
import com.xiaohan.cn.result.ApiResponseResult;
import com.xiaohan.cn.result.ApiResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import static com.xiaohan.cn.constant.ExportContant.MODEL_ERROR_CODE_PATTERN;

/**
 * 消息工具类
 *
 * @author gcy
 * @since 2018/12/01
 */
@Component
public class MessageUtils {

    private final Environment environment;
    private final I18nUtils i18nUtils;

    private static MessageUtils messageUtils;

    public MessageUtils(Environment environment, I18nUtils i18nUtils) {
        this.environment = environment;
        this.i18nUtils = i18nUtils;
    }

    @PostConstruct
    public void init() {
        messageUtils = this;
    }

    /**
     * 根据messageKey生成返回结果包装类
     *
     * @param messageKey 配置文件中错误信息的key, 也是错误码
     * @param errorArgs  消息中的参数值
     * @return 返回结果包装类
     */
    public static ApiResultCode buildApiResultCode(String messageKey, Object... errorArgs) {
        String errorMessage = getErrorMessage(messageKey, ApiResultCode.UNKNOWN_SYSTEM_ERROR.getResultMessage(), errorArgs);
        return new ApiResultCode(messageKey, errorMessage);
    }

    /**
     * 根据messageKey生成基础异常类
     *
     * @param messageKey 配置文件中错误信息的key
     * @param errorArgs  消息中的参数值
     * @return 基础异常类
     */
    public static BaseException buildException(String messageKey, Object... errorArgs) {
        return new BaseException(messageKey, ApiResultCode.UNKNOWN_SYSTEM_ERROR.getResultMessage(), errorArgs, null);
    }

    /**
     * 根据返回错误码生成基础异常类
     *
     * @param apiResultCode 返回错误码
     * @param errorArgs     消息中的参数值
     * @return 基础异常类
     */
    public static BaseException buildException(ApiResultCode apiResultCode, Object... errorArgs) {
        return new BaseException(apiResultCode.getResultCode(), apiResultCode.getResultMessage(), errorArgs, null);
    }

    /**
     * 根据返回实体生成基础异常类
     *
     * @param apiResponseResult 返回值实体
     * @param errorArgs     消息中的参数值
     * @return 基础异常类
     */
    public static BaseException buildException(ApiResponseResult apiResponseResult, Object... errorArgs) {
        return new BaseException(apiResponseResult.getResultCode(), apiResponseResult.getResultMessage(), errorArgs, null);
    }

    /**
     * 根据messageKey获取配置文件中的信息,如没有，则返回messageKey
     *
     * @param messageKey 配置文件中错误信息的key
     * @return 配置文件中错误信息
     */
    public static String getMessage(String messageKey) {
        String message = messageUtils.environment.getProperty(messageKey);
        return StringUtils.isEmpty(message) ? messageKey : message;
    }

    /**
     * 根据错误信息获取错误码
     *
     * @param message 错误信息
     * @return 错误码
     */
    public static String getErrorCode(String message) {
        //错误信息应该按照code:message编排
        String code = StringUtils.substringBefore(message, ":");
        //如果错误信息无错误码或错误码不符合规则，则默认为未知系统异常错误码
        if (!(StringUtils.isNotBlank(code) && MODEL_ERROR_CODE_PATTERN.matcher(code).matches())) {
            code = ApiResultCode.UNKNOWN_SYSTEM_ERROR.getResultCode();
        }
        return code;
    }

    /**
     * 获取错误消息
     *
     * @param code      错误码
     * @param message   错误信息
     * @param errorArgs 消息中的参数值
     * @return 错误消息
     */
    public static String getErrorMessage(String code, String message, Object... errorArgs) {
        return getErrorMessage(code, message, null, errorArgs);
    }

    /**
     * 根据错误码和已知错误消息获取国际化文本
     *
     * @param code         错误码
     * @param errorMessage 错误消息
     * @param request      HttpServletRequest
     * @param args         国际化文件中的参数值
     * @return 错误消息
     */
    public static String getErrorMessage(String code, String errorMessage, HttpServletRequest request, Object... args) {
        return messageUtils.i18nUtils.getMessage(code, errorMessage, request, args);
    }

    /**
     * 根据错误码和已知错误消息获取国际化文本
     *
     * @param apiResultCode 错误码实体
     * @param errorArgs     消息中的参数值
     * @return 错误消息
     */
    public static String getErrorMessage(ApiResultCode apiResultCode, Object... errorArgs) {
        return StringUtils.isNotBlank(apiResultCode.getResultMessage())?messageUtils.i18nUtils.getMessageWithDfaultMessage(apiResultCode.getResultCode(), apiResultCode.getResultMessage(), errorArgs):messageUtils.i18nUtils.getMessageWithArgs(apiResultCode.getResultCode(), true, errorArgs);
    }

}
