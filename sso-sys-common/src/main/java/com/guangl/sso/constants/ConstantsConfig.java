package com.guangl.sso.constants;

/**
 * @ClassName: ConstantsConfig
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-13 12:04
 * @Version: 1.0.0
 * @param: * @param null
 */
public class ConstantsConfig {

    /**
     * log的配置名称
     */
    public final static String LOG_CONFIG_LOCATION_NAME = "logging.fileLocation";
    /**
     * windows系统log日志存放地址
     */
    public final static String WIN_LOG_PATH = "C:\\usr\\local\\data\\logs";
    /**
     * mac系统log日志存放地址
     */
    public final static String MAC_LOG_PATH = "/usr/local/data/logs";
    /**
     * linux系统log日志存放地址
     */
    public final static String LINUX_LOG_PATH = "/data/logs";
    /**
     * USER信息
     */
    public final static String USER_INFO = "user";
    /**
     * nacos.config地址
     */
//    public final static String NACOS_ADDRS = "nacos1.guangl.io:8848,nacos2.guangl.io:8848,nacos3.guangl.io:8848";
    public final static String NACOS_ADDRS = "127.0.0.1:8848";
    /**
     * nacos.config.group
     */
    public final static String NACOS_GROUP = "sso";
    /**
     * nacos.config配置名
     */
    public final static String NACOS_FILE_NAME = "sso-sys.yml";
    /**
     * nacos.config.extend配置名
     */
    public final static String NACOS_EXTEND_FILE_NAME = "sso-sys-extend.yml";
    /**
     * nacos.config热更新
     */
    public final static boolean NACOS_REFRESH = true;
    /**
     * 服务名，在MVC声明时使用
     */
    public final static String APPLICATION_NAME = "/sso-sys";
    /**
     * 前端返回需要相关用户相关页面的渲染权限
     */
    public final static String FRONT_RENDER = "Web-Path";
    /**
     * USER信息
     */
    public final static String USER_ID = "uid";
}
