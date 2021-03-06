package com.guangl.sso.constants;

/**
 * @ClassName: JwtConstant
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-13 14:17
 * @Version: 1.0.0
 * @param: * @param null
 */
public class JwtConstant {
    /**
     * Token中校验元素secret
     */
    public final static String TOKEN_VERIFY_KEY = "v_scrt";
    /**
     * Token中的用户KEY
     */
    public final static String TOKEN_USER_KEY = "g_uid";
    /**
     * Token中的OSS系统ID
     */
    public final static String TOKEN_OSS_KEY = "sys_id";
    /**
     * Token中的Login时间
     */
    public final static String TOKEN_LOGIN_TM_KEY = "lgn_tm";
}
