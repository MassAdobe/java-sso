package com.guangl.sso.exception;

import com.google.common.base.Strings;
import com.guangl.sso.enums.ErrorCodeMsg;
import lombok.Data;

/**
 * @ClassName: AttemptException
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-13 11:28
 * @Version: 1.0.0
 * @param: * @param null
 */
@Data
public class AttemptException extends RuntimeException {
    private ErrorCodeMsg errorCodeMsg;
    private int code;
    private String message;

    public AttemptException(ErrorCodeMsg errorCodeMsg) {
        this.errorCodeMsg = errorCodeMsg;
        this.code = errorCodeMsg.getCode();
        this.message = errorCodeMsg.getMessage();
    }

    public AttemptException(ErrorCodeMsg errorCodeMsg, String otherErrorInfo) {
        this.errorCodeMsg = errorCodeMsg;
        this.code = errorCodeMsg.getCode();
        this.message = errorCodeMsg.getMessage() + Strings.lenientFormat("【%s】", otherErrorInfo);
    }

}
