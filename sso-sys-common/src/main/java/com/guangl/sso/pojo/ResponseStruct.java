package com.guangl.sso.pojo;

import com.guangl.sso.enums.ErrorCodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: ResponseStruct
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-13 12:10
 * @Version: 1.0.0
 * @param: * @param null
 */
@Data
@AllArgsConstructor
public class ResponseStruct {
    private final static String SUCCESS_DESC = "成功";

    private int code;
    private String msg;
    private Object data;

    public static ResponseStruct success() {
        return new ResponseStruct(ErrorCodeMsg.SUCCESS.getCode(), ErrorCodeMsg.SUCCESS.getMessage(), "");
    }

    public static ResponseStruct success(Object data) {
        return new ResponseStruct(ErrorCodeMsg.SUCCESS.getCode(), ErrorCodeMsg.SUCCESS.getMessage(), data);
    }

    public static ResponseStruct failure(int code, String msg) {
        return new ResponseStruct(code, msg, "");
    }

    public static ResponseStruct failure(int code, String msg, Object data) {
        return new ResponseStruct(code, msg, data);
    }
}
