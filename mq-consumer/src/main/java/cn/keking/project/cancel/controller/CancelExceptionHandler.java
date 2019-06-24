package cn.keking.project.cancel.controller;

import com.alibaba.fastjson.JSONObject;
import cn.keking.project.cancel.base.util.CommonResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zhangwengang
 * @Date: 2019/6/20
 * @Version: V1.0
 * @Description: 统一异常处理
 */
@ControllerAdvice
@Slf4j
public class CancelExceptionHandler {

    /**
     * 处理所有异常
     *
     * @param e
     * @return error msg
     */
    @ExceptionHandler()
    @ResponseBody
    public JSONObject exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResponseUtil.errorJson(e.getMessage());
    }
}
