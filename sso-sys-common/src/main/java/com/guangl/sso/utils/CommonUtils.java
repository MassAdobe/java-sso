package com.guangl.sso.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CommonUtils
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-13 14:44
 * @Version: 1.0.0
 * @param: * @param null
 */
public class CommonUtils {

    private final static String RANDOM_SEED = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final static String MOBILE_REGEX = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    private final static String DATE_YYYY_MM_DD = "yyyy-MM-dd";
    private final static String DEFAULT_EXPIRE_DT = "1970-01-01";

    /**
     * @Method: getRandomString
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 生成 a->b A->B 0->9 的入参位数随机字符串
     * @Date: Created in 2019-12-13 14:44
     * @Version: 1.0.0
     * @param: * @param null
     */
    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(RANDOM_SEED.charAt(number));
        }
        return sb.toString();
    }

    /**
     * @ClassName: CommonUtils
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 校验手机号
     * @Date: Created in 2020-01-09 13:48
     * @Version: 1.0.0
     * @param: * @param null
     */
    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile(MOBILE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * @ClassName: CommonUtils
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 校验时间是否过期以及时间是否为'1970-01-01'，如果过期返回true，如果没有过期或者为'1970-01-01'则返回false
     * @Date: Created in 2020-01-14 16:16
     * @Version: 1.0.0
     * @param: * @param null
     */
    public static boolean checkExpireDt(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_YYYY_MM_DD);
        if (new Date().before(date) || sdf.format(date).equals(DEFAULT_EXPIRE_DT))
            return false;
        return true;
    }
}
