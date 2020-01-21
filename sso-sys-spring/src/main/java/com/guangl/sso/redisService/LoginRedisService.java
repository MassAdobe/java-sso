package com.guangl.sso.redisService;

import com.guangl.sso.pojo.UserStruct;

/**
 * @ClassName: LoginRedisService
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: /login下的所有redis操作接口类
 * @Date: Created in 2020-01-10 16:01
 * @Version: 1.0.0
 * @param: * @param null
 */
public interface LoginRedisService {

    /**
     * @ClassName: LoginRedisService
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 增加或者修改登录后的结构体
     * @Date: Created in 2020-01-10 16:03
     * @Version: 1.0.0
     * @param: * @param null
     */
    void addOrUpdateUserStruct(Long guid, UserStruct userStruct);

    /**
     * @ClassName: LoginRedisService
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 删除原先的Permissions
     * @Date: Created in 2020-01-13 15:29
     * @Version: 1.0.0
     * @param: * @param null
     */
    void delPassPermissions(Long guid);

    /**
     * @ClassName: LoginRedisService
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 插入新的Permissions
     * @Date: Created in 2020-01-13 15:31
     * @Version: 1.0.0
     * @param: * @param null
     */
    void addPermissions(Long guid, String name, String content);

}
