package com.guangl.sso.configs;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.guangl.sso.constants.ConstantsConfig;
import com.guangl.sso.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;


/**
 * @ClassName: ExtendConfiguration
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-06 17:03
 * @Version: 1.0.0
 * @param: * @param null
 */
@Configuration
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = ConstantsConfig.NACOS_ADDRS))
@NacosPropertySource(dataId = ConstantsConfig.NACOS_EXTEND_FILE_NAME, groupId = ConstantsConfig.NACOS_GROUP, autoRefreshed = ConstantsConfig.NACOS_REFRESH, type = ConfigType.YAML)
public class ExtendConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(ExtendConfiguration.class);

    private static String encryptJWTKey;
    private static String verifySecret;

    @NacosValue(value = "${jwt.config.encrypt.jwt-key}", autoRefreshed = true)
    public void setEncryptJWTKey(String encryptJWTKey) {
        ExtendConfiguration.encryptJWTKey = encryptJWTKey;
    }

    @NacosValue(value = "${jwt.config.verify.secert}", autoRefreshed = true)
    public void setVerifySecret(String verifySecret) {
        ExtendConfiguration.verifySecret = verifySecret;
    }

    public static String getEncryptJWTKey() {
        return encryptJWTKey;
    }

    public static String getVerifySecret() {
        return verifySecret;
    }
}
