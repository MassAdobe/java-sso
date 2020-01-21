package com.guangl.sso.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

public class Md5Util {

    private final static Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public static String encode(String str) {
        try {
            str = DigestUtils.md5DigestAsHex(str.getBytes());
        } catch (Exception e) {
            logger.error("ENCODE ERROR:{}", e);

        }
        return str;
    }

}
