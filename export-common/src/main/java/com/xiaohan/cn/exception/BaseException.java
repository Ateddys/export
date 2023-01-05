package com.xiaohan.cn.exception;


/**
 * 自定义基础异常类
 *
 * @author teddy
 * @since 2022/12/25
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1381325479896057076L;
    /**
     * 异常码
     */
    private String code;
    /**
     * 异常信息
     */
    private String message;

    /**
     * 异常信息参数
     */
    private transient Object[] values;

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BaseException(String code, String message) {
        this(code, message, null);
    }

    private BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = code;
    }

    public BaseException(String code, String message, Object[] values, Throwable cause) {
        this(code, message, cause);
        this.values = values == null ? null : values.clone();
    }

    /**
     * 获取 {@link #code}
     *
     * @return {@link #code}
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取 {@link #message}
     *
     * @return {@link #message}
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 获取 {@link #values}
     *
     * @return {@link #values}
     */
    public Object[] getValues() {
        return values;
    }
}
