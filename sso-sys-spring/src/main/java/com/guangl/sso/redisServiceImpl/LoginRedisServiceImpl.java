package com.guangl.sso.redisServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.guangl.sso.constants.CmnConstants;
import com.guangl.sso.enums.ErrorCodeMsg;
import com.guangl.sso.exception.AttemptException;
import com.guangl.sso.pojo.PermissionStruct;
import com.guangl.sso.pojo.UserStruct;
import com.guangl.sso.redisService.LoginRedisService;
import com.guangl.sso.utils.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName: LoginRedisServiceImpl
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-10 16:04
 * @Version: 1.0.0
 * @param: * @param null
 */
@Service
public class LoginRedisServiceImpl implements LoginRedisService {

    private final static Logger logger = LoggerFactory.getLogger(LoginRedisServiceImpl.class);

    @Autowired
    private RedisClient redisClient;

    @Override
    public void addOrUpdateUserStruct(Long guid, UserStruct userStruct) throws AttemptException {
        try {
            this.redisClient.hset(CmnConstants.REDIS_USER_MAIN_KEY, CmnConstants.REDIS_USER_SUB_KEY + guid, userStruct);
        } catch (AttemptException e) {
            logger.error(Strings.lenientFormat("【LOGIN-REDIS-ADD-UPDATE-USERSTRUCT】：用户(guid)：%s 插入修改缓存错误，入缓存内容：%s", String.valueOf(guid), userStruct.toString()));
            throw new AttemptException(ErrorCodeMsg.SERVER_ERROR);
        }
    }

    @Override
    public void delPassPermissions(Long guid) throws AttemptException {
        try {
            if (this.redisClient.hasKey(CmnConstants.REDIS_PERMISSION_MAIN_KEY + guid)) {// 如果存在
                this.redisClient.hdel(CmnConstants.REDIS_PERMISSION_MAIN_KEY + guid);// 删除
            }
        } catch (AttemptException e) {
            logger.error(Strings.lenientFormat("【LOGIN-REDIS-DEL-PASS-PERMISSION】：用户(guid)：%s 删除permissions", String.valueOf(guid)));
            throw new AttemptException(ErrorCodeMsg.SERVER_ERROR);
        }
    }

    @Override
    public void addPermissions(Long guid, String name, String content) throws AttemptException {
        PermissionStruct ps = new PermissionStruct();
        List<String> pre = new ArrayList<>();
        for (String s : content.split(","))
            if (!Strings.isNullOrEmpty(s))
                pre.add(s);
        ps.setPermissions(pre);
        try {
            this.redisClient.hset(CmnConstants.REDIS_PERMISSION_MAIN_KEY + guid, CmnConstants.REDIS_PERMISSION_SUB_KEY + name, ps);
        } catch (AttemptException e) {
            logger.error(Strings.lenientFormat("【LOGIN-REDIS-ADD-PERMISSION】：用户(guid)：%s 新增permissions：%s 失败", String.valueOf(guid), content));
            throw new AttemptException(ErrorCodeMsg.SERVER_ERROR);
        }
    }
}
