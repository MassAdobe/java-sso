package com.guangl.sso.exception;

import com.google.common.base.Strings;
import com.guangl.sso.enums.ErrorCodeMsg;
import com.guangl.sso.pojo.ResponseStruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: AttemptControllerAdvice
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-13 12:09
 * @Version: 1.0.0
 * @param: * @param null
 */
@RestControllerAdvice
public class AttemptControllerAdvice {

    private final static Logger logger = LoggerFactory.getLogger(AttemptControllerAdvice.class);

    /**
     * 全局异常捕捉处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResponseStruct errorHandler(Exception ex) throws Exception {
        logger.error("全局：" + ex.toString());
        if (Strings.isNullOrEmpty(ex.getMessage()))
            return ResponseStruct.failure(ErrorCodeMsg.UNKNOWN_ERROR.getCode(), ErrorCodeMsg.UNKNOWN_ERROR.getMessage());
        return ResponseStruct.failure(ErrorCodeMsg.UNKNOWN_ERROR.getCode(), ErrorCodeMsg.UNKNOWN_ERROR.getMessage() + "【" + ex.getMessage() + "】");
    }

    /**
     * 拦截捕捉自定义异常 AttemptException.class
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = AttemptException.class)
    public ResponseStruct myErrorHandler(AttemptException ex) throws Exception {
        logger.error("Attempt异常：" + ex.getErrorCodeMsg().getMessage() + "，", ex.toString());
        return ResponseStruct.failure(ex.getErrorCodeMsg().getCode(), ex.getMessage());
    }
}
