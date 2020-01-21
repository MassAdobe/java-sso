package com.guangl.sso;

import com.guangl.sso.config.OSinfo;
import com.guangl.sso.constants.ConstantsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Properties;


/**
 * @ClassName: AttemptApplication
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-13 15:09
 * @Version: 1.0.0
 * @param: * @param null
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SsoSysApplication {
    public static void main(String[] args) {
        Properties properties = new Properties();
        if (OSinfo.isWindows()) {
            properties.setProperty(ConstantsConfig.LOG_CONFIG_LOCATION_NAME, ConstantsConfig.WIN_LOG_PATH);
        } else if (OSinfo.isMacOSX() || OSinfo.isMacOS()) {
            properties.setProperty(ConstantsConfig.LOG_CONFIG_LOCATION_NAME, ConstantsConfig.MAC_LOG_PATH);
        } else {
            properties.setProperty(ConstantsConfig.LOG_CONFIG_LOCATION_NAME, ConstantsConfig.LINUX_LOG_PATH);
        }
        SpringApplication app = new SpringApplication(SsoSysApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);
    }
}
