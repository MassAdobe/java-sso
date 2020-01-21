package com.guangl.sso.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.base.Strings;
import com.guangl.sso.configs.ExtendConfiguration;
import com.guangl.sso.constants.JwtConstant;
import com.guangl.sso.enums.ErrorCodeMsg;
import com.guangl.sso.exception.AttemptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @ClassName: JwtUtils
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-08 20:48
 * @Version: 1.0.0
 * @param: * @param null
 */
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 生成签名
     */
    public static String sign(Long gUid, Long sysId) {
        try {
            // 帐号加JWT私钥加密
            String secret = ExtendConfiguration.getVerifySecret() + Base64Util.decodeThrowsException(ExtendConfiguration.getEncryptJWTKey());
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create().withClaim(JwtConstant.TOKEN_VERIFY_KEY, ExtendConfiguration.getVerifySecret()).withClaim(JwtConstant.TOKEN_USER_KEY, gUid).withClaim(JwtConstant.TOKEN_OSS_KEY, sysId).withClaim(JwtConstant.TOKEN_LOGIN_TM_KEY, new Date()).sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            logger.error(Strings.lenientFormat("【JWT-UTILS-SIGN】：生成JWTTOKEN失败！%s", e.getMessage()));
            throw new AttemptException(ErrorCodeMsg.TOKEN_UNSUPPORT_ENCODE_ERROR);
        }
    }

    /**
     * @ClassName: JwtUtils
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 校验该用户密码是否正确
     * @Date: Created in 2020-01-08 21:12
     * @Version: 1.0.0
     * @param: * @param null
     */
    public static boolean checkPassword(String signInPwd, String dbPwd, String salt, Long guid) {
        String encode = Md5Util.encode(signInPwd + salt + guid);
        if (dbPwd.equals(encode))
            return false;
        return true;
    }

    /**
     * @ClassName: JwtUtils
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 生成密码等
     * @Date: Created in 2020-01-10 16:20
     * @Version: 1.0.0
     * @param: * @param null
     */
    public static void main(String[] args) throws Exception {
        String salt = CommonUtils.getRandomString(12);
        System.out.println("salt:" + salt);
        String password = CommonUtils.getRandomString(12);
        System.out.println("password:" + password);
        Long guid = 2L;
        String encode = Md5Util.encode(password + salt + guid);
        System.out.println("pwd-salt:" + encode);
    }

}
