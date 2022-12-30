package com.xiaohan.cn.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * 接口返回结果封装类
 *
 * @param <T> data属性的类型
 * @author teddy
 * @since 2022/12/29
 */
@ApiModel("接口响应封装类")
public class ApiResponseResult<T> {

    @ApiModelProperty("结果码, 表示接口处理结果状态")
    private String resultCode;

    @ApiModelProperty("结果信息, 描述接口处理结果")
    private String resultMessage;

    @ApiModelProperty("结果数据, 接口处理成功时返回的业务数据")
    private T data;

    private static final Pattern MODEL_ERROR_CODE_PATTERN = Pattern.compile("^[1-9][0-9]{8}$");

    private static ObjectMapper mapper = new ObjectMapper();

    public ApiResponseResult() {
    }

    public ApiResponseResult(T data) {
        this(ApiResultCode.SUCCESS.getResultCode(), ApiResultCode.SUCCESS.getResultMessage(), data);
    }

    public ApiResponseResult(ApiResultCode apiResultCode) {
        this(apiResultCode.getResultCode(), apiResultCode.getResultMessage(), null);
    }

    public ApiResponseResult(ApiResultCode apiResultCode, T data) {
        this(apiResultCode.getResultCode(), apiResultCode.getResultMessage(), data);
    }

    public ApiResponseResult(String resultCode, String resultMessage, T data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.data = data;
    }

    public ApiResponseResult(String resultCode, String resultMessage) {
        this(resultCode, resultMessage, null);
    }

    public static <T> ApiResponseResult<T> buildByApiResultCode(ApiResultCode apiResultCode, Object... messageArgs) {
        return new ApiResponseResult<>(apiResultCode.getResultCode(), messageArgs != null ? String.format(apiResultCode.getResultMessage(), messageArgs) : apiResultCode.getResultMessage(), null);
    }

    public static ApiResponseResult buildByBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasGlobalErrors()) {
            ObjectError globalError = bindingResult.getGlobalError();
            String errorCode = globalError.getCode();
            if (StringUtils.isNotBlank(errorCode) && MODEL_ERROR_CODE_PATTERN.matcher(errorCode).matches()) {
                return new ApiResponseResult<>(errorCode, StringUtils.defaultString(globalError.getDefaultMessage()));
            }
        } else if (bindingResult.hasFieldErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String errorCode = fieldError.getCode();
            if (StringUtils.isNotBlank(errorCode) && MODEL_ERROR_CODE_PATTERN.matcher(errorCode).matches()) {
                return new ApiResponseResult<>(errorCode, StringUtils.defaultString(fieldError.getDefaultMessage()));
            }
            return new ApiResponseResult<>(ApiResultCode.INVALID_PARAM_VALUE.getResultCode(), String.format(ApiResultCode.INVALID_PARAM_VALUE.getResultMessage(), fieldError.getField(), StringUtils.defaultString(fieldError.getDefaultMessage())), null);
        }
        return new ApiResponseResult<>(null);
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

    /**
     * 获取 {@link #data}
     *
     * @return {@link #data}
     */
    public T getData() {
        return data;
    }

    /**
     * 获取 {@link #data}
     *
     * @param clz clz
     *
     * @return {@link #data}
     */
    public T getData(Class<T> clz) {
        return this.data instanceof Map ? fromMap((Map) this.data, clz) : this.data;
    }

    /**
     * 设置 {@link #data}
     *
     * @param data {@link #data}
     */
    public void setData(T data) {
        this.data = data;
    }

    private T fromMap(Map<?, ?> map, Class<T> t) {
        if (map == null) {
            return null;
        } else {
            try {
                String jsonString = mapper.writeValueAsString(map);
                return mapper.readValue(jsonString, t);
            } catch (Exception e) {
                return null;
            }
        }
    }

}
