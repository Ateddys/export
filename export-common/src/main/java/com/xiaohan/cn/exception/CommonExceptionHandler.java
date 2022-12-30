package com.xiaohan.cn.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.legendscloud.common.base.ComResp;
import top.legendscloud.common.enums.CommonEnumCode;
import top.legendscloud.common.exception.BizException;

/**
 * @program: teddylife
 * @description: 全局异常
 * @author: Teddy
 * @create: 2020-10-26 10:14
 **/
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ComResp<Boolean> exceptionHandler(Exception e) {
        log.error("未知的错误：{}", e.getMessage());
        return new ComResp.Builder<Boolean>()
                .error(CommonEnumCode.UNKNOWN_ERR)
                .build();
    }

    /**
     * 服务器异常 压力
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ComResp<Boolean> exceptionHandler(IllegalArgumentException e) {
        log.error("服务器异常-压力：{}", e.getMessage());
        return new ComResp.Builder<Boolean>()
                .error(CommonEnumCode.SYS_ERR)
                .build();
    }

    /**
     * 请求方式出了问题
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ComResp<Boolean> exceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("请求方式出了问题：{}", e.getMessage());
        return new ComResp.Builder<Boolean>()
                .error(CommonEnumCode.UNKNOWN_ERR)
                .build();
    }

    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ComResp<Boolean> exceptionHandler(NullPointerException e) {
        log.error("处理空指针的异常！", e);
        return new ComResp.Builder<Boolean>()
                .error(CommonEnumCode.MESSAGE_FORMAT_ERR)
                .build();
    }

    /**
     * 处理自定义的业务异常
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ComResp<Boolean> bizExceptionHandler(BizException e) {
        log.error("处理自定义的业务异常！原因是：{}", e.getMessage());
        return new ComResp.Builder<Boolean>()
                .fail()
                .msg(e.getMsg())
                .build();
    }

    /**
     * 处理自定义的业务异常2
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ComResp<Boolean> bizExceptionHandler(BaseException e) {
        log.error("处理自定义的业务异常！原因是：{}", e.getMessage());
        return new ComResp.Builder<Boolean>()
                .fail()
                .msg(e.getMessage())
                .build();
    }
}
