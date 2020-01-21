package com.guangl.sso.configs;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.guangl.sso.constants.ConstantsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: NacosConfiguration
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-26 11:24
 * @Version: 1.0.0
 * @param: * @param null
 */
@Configuration
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = ConstantsConfig.NACOS_ADDRS))
@NacosPropertySource(dataId = ConstantsConfig.NACOS_FILE_NAME, groupId = ConstantsConfig.NACOS_GROUP, autoRefreshed = ConstantsConfig.NACOS_REFRESH, type = ConfigType.YAML)
public class NacosConfiguration {

    private final static Logger logger = LoggerFactory.getLogger(NacosConfiguration.class);

}
