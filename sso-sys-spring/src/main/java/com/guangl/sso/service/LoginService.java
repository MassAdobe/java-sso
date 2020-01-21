package com.guangl.sso.service;

import com.guangl.sso.entity.UserTmpTable;
import com.guangl.sso.pojo.SignUser;

/**
 * @ClassName: LoginService
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-08 21:01
 * @Version: 1.0.0
 * @param: * @param null
 */
public interface LoginService {

    /**
     * @ClassName: LoginService
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 校验手机号码，当用户的手机号码是第一次登录，则返回未激活；如果是第一次后登录，返回已激活；如果是第一次登录，则使用手机验证码，渲染修改密码按钮；如果是第一次后登录，则使用用户密码，渲染登录按钮。（已写单元测试）
     * @Date: Created in 2020-01-09 13:53
     * @Version: 1.0.0
     * @param: * @param null
     */
    boolean confirm(String phoneNum, Long mark);

    /**
     * @ClassName: LoginService
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 用密码进行登录，登录即为登录，是既校验手机号码之后返回为"已激活后"所调用的方法，使用用户名(即手机号码)和密码进行登录。
     * @Date: Created in 2020-01-08 21:04
     * @Version: 1.0.0
     * @param: * @param null
     */
    UserTmpTable signedIn(SignUser signUser);

    /**
     * @ClassName: LoginService
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 当超管新增管理员用户后，用户登录时获取验证码后修改其密码，自行注册（修改密码）;并且注册既登录。
     * @Date: Created in 2020-01-08 21:04
     * @Version: 1.0.0
     * @param: * @param null
     */
    UserTmpTable signUp(SignUser signUser);

    /**
     * @ClassName: LoginService
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 超管生成相关用户
     * @Date: Created in 2020-01-09 11:26
     * @Version: 1.0.0
     * @param: * @param null
     */
    void createSignUp(SignUser signUser, Long guid);

}
