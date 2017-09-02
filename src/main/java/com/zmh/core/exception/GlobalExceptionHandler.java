package com.zmh.core.exception;

import com.alibaba.fastjson.JSONObject;
import com.zmh.core.ResponseMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * Created by Administrator on 2017/7/26.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JSONObject exceptionHandler(RuntimeException e, HttpServletResponse response) {
        return ResponseMessage.error(e.getMessage());}
}
