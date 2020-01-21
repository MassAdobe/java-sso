package com.guangl.sso.exception;

import com.guangl.sso.constants.CmnConstants;
import com.guangl.sso.enums.ErrorCodeMsg;
import com.guangl.sso.pojo.ResponseStruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ExceptionUtils
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-19 17:29
 * @Version: 1.0.0
 * @param: * @param null
 */
public class ExceptionUtils {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    public static ResponseStruct ReturnError(ErrorCodeMsg errorCodeMsg, String... strings) {
        List<String> strs = Arrays.asList(strings);
        logger.error("返回错误：" + errorCodeMsg.getMessage() + "，" + strs.stream().map(str -> str).collect(Collectors.joining("，")));
        return ResponseStruct.failure(errorCodeMsg.getCode(), errorCodeMsg.getMessage(), CmnConstants.EMPTY);
    }

}
