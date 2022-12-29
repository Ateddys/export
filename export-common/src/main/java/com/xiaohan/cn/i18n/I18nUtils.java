package com.xiaohan.cn.i18n;

import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 国际化信息工具，根据key获取国际化文本
 *
 * @author teddy
 * @since 2022/12/20
 */
@Component
public class I18nUtils {

    private final MessageSource messageSource;

    /**
     * @param messageSource 消息源
     */
    public I18nUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 根据key获取国际化文本
     *
     * @param key key
     * @return 国际化文本
     */
    public String getMessage(String key) {
        return this.getMessage(key, null, null);
    }

    /**
     * 根据key获取国际化文本
     *
     * @param key  key
     * @param handleArgs  是否对参数进行
     * @param args 国际化文本参数
     * @return 国际化文本
     */
    public String getMessageWithArgs(String key, boolean handleArgs, Object... args) {
        return this.getMessage(key, handleArgs, null, null, args);
    }

    /**
     * 根据key获取国际化文本
     *
     * @param key  key
     * @param defaultMessage 默认文本
     * @param args 国际化文本参数
     * @return 国际化文本
     */
    public String getMessageWithDfaultMessage(String key, @NonNull String defaultMessage, Object... args) {
        return this.getMessageWithDfaultMessage(key, true, defaultMessage, args);
    }

    /**
     * 根据key获取国际化文本
     *
     * @param key  key
     * @param handleArgs  是否对参数进行国际化处理
     * @param defaultMessage 默认文本
     * @param args 国际化文本参数
     * @return 国际化文本
     */
    public String getMessageWithDfaultMessage(String key, boolean handleArgs, @NonNull String defaultMessage, Object... args) {
        return this.getMessage(key, handleArgs, defaultMessage, null, args);
    }

    /**
     * 根据key获取国际化文本
     *
     * @param key     key
     * @param request HttpServletRequest
     * @param args    国际化文本参数
     * @return 国际化文本
     */
    public String getMessageWithRequest(String key, @NonNull HttpServletRequest request, Object... args) {
        return this.getMessageWithRequest(key, true, request, args);
    }

    /**
     * 根据key获取国际化文本
     *
     * @param key     key
     * @param handleArgs  是否对参数进行国际化处理
     * @param request HttpServletRequest
     * @param args    国际化文本参数
     * @return 国际化文本
     */
    public String getMessageWithRequest(String key, boolean handleArgs, @NonNull HttpServletRequest request, Object... args) {
        return this.getMessage(key, handleArgs, null, request, args);
    }

    /**
     * 根据key获取国际化文本
     *
     * @param key            key
     * @param defaultMessage 默认文本
     * @param request        HttpServletRequest
     * @param args           国际化文本参数
     * @return 国际化文本
     */
    public String getMessage(String key, String defaultMessage, HttpServletRequest request, Object... args) {
        return getMessage(key, true, defaultMessage, request, args);
    }

    /**
     * 根据key获取国际化文本
     *
     * @param key            key
     * @param handleArgs     是否对参数进行国际化处理
     * @param defaultMessage 默认文本
     * @param request        HttpServletRequest
     * @param args           国际化文本参数
     * @return 国际化文本
     */
    public String getMessage(String key, boolean handleArgs, String defaultMessage, HttpServletRequest request, Object... args) {
        Locale locale;
        if (null == request) {
            locale = Locale.getDefault();
        } else {
            locale = request.getLocale();
        }
        return this.getMessageByLocale(key, handleArgs, defaultMessage, locale, args);
    }

    /**
     * 根据key获取本地语言文本
     *
     * @param key    key
     * @param locale 本地语言
     * @param args   国际化文本参数
     * @return 国际化文本
     */
    public String getMessageByLocale(String key, @NonNull Locale locale, Object... args) {
        return this.getMessageByLocale(key,  true, locale, args);
    }

    /**
     * 根据key获取本地语言文本
     *
     * @param key    key
     * @param handleArgs  是否对参数进行国际化处理
     * @param locale 本地语言
     * @param args   国际化文本参数
     * @return 国际化文本
     */
    public String getMessageByLocale(String key, boolean handleArgs, @NonNull Locale locale, Object... args) {
        return this.getMessageByLocale(key,  handleArgs, null, locale, args);
    }

    /**
     * 根据key获取本地语言文本
     *
     * @param key    key
     * @param defaultMessage 默认文本
     * @param locale 本地语言
     * @param args   国际化文本参数
     * @return 国际化文本
     */
    public String getMessageByLocale(String key, String defaultMessage, @NonNull Locale locale, Object... args) {
        return this.getMessageByLocale(key,  true, defaultMessage, locale, args);
    }

    /**
     * @param key            key
     * @param handleArgs     是否对参数进行国际化处理
     * @param defaultMessage 默认文本
     * @param locale         本地语言
     * @param args           国际化文本参数
     * @return 国际化文本
     */
    public String getMessageByLocale(String key, boolean handleArgs, String defaultMessage, @NonNull Locale locale, Object... args) {
        if(handleArgs){
            args = getArgsMessage(args, locale);
        }
        return null == defaultMessage ? this.messageSource.getMessage(key, args, locale) : this.messageSource.getMessage(key, args, defaultMessage, locale);
    }

    /**
     * 对参数进行国际化处理
     *
     * @param args   文本参数
     * @param locale 本地语言
     * @return 国际化文本参数
     */
    private Object[] getArgsMessage(Object[] args, Locale locale) {
        if (null != args && args.length > 0) {
            Object[] result = new Object[args.length];
            for (int i=0;i<args.length;i++) {
                Object arg = args[i];
                if (arg instanceof String) {
                    result[i] = this.messageSource.getMessage((String)arg, null, (String)arg, locale);
                } else {
                    result[i] = args[i];
                }
            }
            return result;
        } else {
            return args;
        }
    }
}
