package com.guangl.sso.feignController;

import com.guangl.sso.constants.ConstantsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: PlanController
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-15 20:10
 * @Version: 1.0.0
 * @param: * @param null
 */
@RestController
@RequestMapping(ConstantsConfig.APPLICATION_NAME + "/provider")
public class SsoController {

    private final static Logger logger = LoggerFactory.getLogger(SsoController.class);

}
