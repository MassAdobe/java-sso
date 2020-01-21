package com.guangl.sso.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.guangl.sso.constants.ConstantsConfig;
import com.guangl.sso.enums.ErrorCodeMsg;
import com.guangl.sso.exception.AttemptException;
import com.guangl.sso.pojo.RequestUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BasicContoller
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-06 20:48
 * @Version: 1.0.0
 * @param: * @param null
 */
@RestController
@RequestMapping(value = ConstantsConfig.APPLICATION_NAME)
public class BasicContoller {

    private final static Logger logger = LoggerFactory.getLogger(BasicContoller.class);

    @NacosValue(value = "${config.filter.throughPath}", autoRefreshed = true)
    private List<String> throughList = new ArrayList<>();

    @ModelAttribute
    public RequestUser gainSysInfo(HttpServletRequest request, HttpServletResponse response) {
        RequestUser requestUser = null;
        if (!throughList.contains(request.getRequestURI())) {
            if (Strings.isNullOrEmpty(request.getHeader(ConstantsConfig.USER_INFO))) {
                logger.error(Strings.lenientFormat("【BASIC-CONTROLLER-GAIN-SYSTEM-INFO】：ERROR：{%s}", ErrorCodeMsg.HEADER_USER_ERROR.getMessage()));
                throw new AttemptException(ErrorCodeMsg.HEADER_USER_ERROR);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    requestUser = (objectMapper.readValue(request.getHeader(ConstantsConfig.USER_INFO), RequestUser.class));
                    if (null == requestUser.getSysid() || null == requestUser.getGuid()) {
                        logger.error(Strings.lenientFormat("【BASIC-CONTROLLER-GAIN-SYSTEM-INFO】：ERROR：{%s}", ErrorCodeMsg.HEADER_USER_ERROR.getMessage()));
                        throw new AttemptException(ErrorCodeMsg.HEADER_USER_ERROR);
                    }
                } catch (IOException e) {
                    logger.error(Strings.lenientFormat("【BASIC-CONTROLLER-GAIN-SYSTEM-INFO】：ERROR：{%s}", e.getMessage()));
                    throw new AttemptException(ErrorCodeMsg.JSON_DECODE_ERROR);
                }
            }
            if (!Strings.isNullOrEmpty(request.getHeader(ConstantsConfig.FRONT_RENDER)))
                response.addHeader(ConstantsConfig.USER_ID, String.valueOf(requestUser.getGuid()));
            return requestUser;
        }
        return null;
    }

}
