package com.guangl.sso.enums;

/**
 * @ClassName: ErrorCodeMsg
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-13 11:28
 * @Version: 1.0.0
 * @param: * @param null
 */
public enum ErrorCodeMsg {
    // 系统级别(0,0->999)
    SUCCESS(0, "成功"),
    UNKNOWN_ERROR(999, "未知错误"),
    SERVER_ERROR(998, "服务错误"),
    PARAM_ERROR(997, "参数错误"),
    PAGE_OR_API_ERROR(996, "页面或接口错误"),
    HEADER_USER_ERROR(995, "非过滤接口中头信息不含有用户要素"),
    REDIS_ERROR(994, "缓存错误"),
    REDIS_INCR_ERROR(993, "递增因子必须大于0"),
    USER_BEYOND_EXPIRE_TM_ERROR(992, "用户已经超出了系统给定的使用时间"),
    // 业务错误(xx业务:3000->3999)
    JSON_DECODE_ERROR(2999, "JSON解析错误"),
    LOGIN_PARAMS_ERROR(2998, "登录用户名或者密码错误"),
    BASE64_ENCRYPT_ERROR(2997, "Base64加密错误"),
    TOKEN_UNSUPPORT_ENCODE_ERROR(2996, "JWTToken认证解密出现UnsupportedEncodingException异常"),
    SIGN_UP_PARAMS_ERROR(2995, "注册用户名密码或手机验证码错误"),
    USER_NO_EXIST_ERROR(2994, "用户不存在或此手机号为非注册手机号"),
    USER_PASS_WORD_ERROR(2993, "用户密码不正确"),
    BASE64_DECRYPT_ERROR(2992, "Base64解密错误"),
    SUPER_MANAGER_UNEXIST_ERROR(2991, "超管不存在"),
    PHONE_NUM_NULLABLE_ERROR(2990, "请填入用户的手机号"),
    WRONG_PHONE_NUM_ERROR(2899, "手机号码错误"),
    ILLEGAL_USER_ERROR(2898, "非法用户"),
    USER_ADMISSION_NO_EXIST_ERROR(2897, "用户的Admission不存在"),
    USER_PERMISSION_NO_EXIST_ERROR(2896, "用户的Permission不存在"),
    WRONG_CONFIRM_PWD_ERROR(2895, "用户的密码(CONFIRM_PWD)不正确"),

    // 数据级别
    ;

    private int code;
    private String message;

    ErrorCodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
