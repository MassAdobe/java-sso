package com.guangl.sso.controller;

import com.google.common.base.Strings;
import com.guangl.sso.constants.ConstantsConfig;
import com.guangl.sso.constants.HttpConstant;
import com.guangl.sso.entity.UserTmpTable;
import com.guangl.sso.enums.ErrorCodeMsg;
import com.guangl.sso.exception.AttemptException;
import com.guangl.sso.pojo.RequestUser;
import com.guangl.sso.pojo.ResponseStruct;
import com.guangl.sso.pojo.SignUser;
import com.guangl.sso.service.LoginService;
import com.guangl.sso.utils.CommonUtils;
import com.guangl.sso.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: LoginController
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-08 21:29
 * @Version: 1.0.0
 * @param: * @param null
 */
@RestController
@RequestMapping(value = ConstantsConfig.APPLICATION_NAME + "/login")
public class LoginController extends BasicContoller {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    /**
     * @ClassName: LoginController
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 校验手机号码，当用户的手机号码是第一次登录，则返回未激活；如果是第一次后登录，返回已激活；如果是第一次登录，则使用手机验证码，渲染修改密码按钮；如果是第一次后登录，则使用用户密码，渲染登录按钮。（已写单元测试）
     * @Date: Created in 2020-01-09 17:43
     * @Version: 1.0.0
     * @param: * @param null
     */
    @PostMapping("/confirmMobile")
    public ResponseStruct confirm(@RequestBody SignUser signUser) {
        if (!CommonUtils.isMobile(signUser.getPhoneNum())) {// 校验手机号码是否正常
            logger.error(Strings.lenientFormat("【LOGIN-CONTROLLER-CONFIRM】：手机号：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.WRONG_PHONE_NUM_ERROR.getMessage()));
            throw new AttemptException(ErrorCodeMsg.WRONG_PHONE_NUM_ERROR);
        }
        if (null == signUser.getMark()) {
            logger.error(Strings.lenientFormat("【LOGIN-CONTROLLER-CONFIRM】：手机号：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.PARAM_ERROR.getMessage()));
            throw new AttemptException(ErrorCodeMsg.PARAM_ERROR);
        }
        if (this.loginService.confirm(signUser.getPhoneNum(), signUser.getMark()))
            return ResponseStruct.success(new Ans("未激活"));
        return ResponseStruct.success(new Ans("已激活"));
    }

    /**
     * @ClassName: LoginController
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 校验手机号码使用的内部类
     * @Date: Created in 2020-01-09 17:43
     * @Version: 1.0.0
     * @param: * @param null
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class Ans {
        private String ans;// 回答
    }

    /**
     * @ClassName: LoginController
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 用密码进行登录，登录即为登录，是既校验手机号码之后返回为"已激活后"所调用的方法，使用用户名(即手机号码)和密码进行登录。
     * @Date: Created in 2020-01-08 21:29
     * @Version: 1.0.0
     * @param: * @param null
     */
    @SuppressWarnings("all")
    @PostMapping("/signIn")
    public ResponseStruct signIn(@RequestBody SignUser signUser, HttpServletRequest request, HttpServletResponse response) {
        if (Strings.isNullOrEmpty(signUser.getPhoneNum()) || Strings.isNullOrEmpty(signUser.getPassword()) || null == signUser.getMark()) {
            logger.error(Strings.lenientFormat("【LOGIN-CONTROLLER-SIGN-IN】：%s", ErrorCodeMsg.LOGIN_PARAMS_ERROR.getMessage()));
        } else if (!CommonUtils.isMobile(signUser.getPhoneNum())) {
            logger.error(Strings.lenientFormat("【LOGIN-CONTROLLER-SIGN-IN】：手机号：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.WRONG_PHONE_NUM_ERROR.getMessage()));
            throw new AttemptException(ErrorCodeMsg.LOGIN_PARAMS_ERROR);
        }
        UserTmpTable userTmpTable = this.loginService.signedIn(signUser);
        response.addHeader(HttpConstant.ACCESS_TOKEN_KEY, JwtUtils.sign(userTmpTable.getGUid(), userTmpTable.getSysId()));// access-token
        response.addHeader(HttpConstant.SALT_KEY, userTmpTable.getSysSalt());// salt
        response.addHeader(HttpConstant.DELTA_TM_KEY, String.valueOf(System.currentTimeMillis() - Long.valueOf(request.getHeader(HttpConstant.HEADER_TIMESTAMP_KEY))));// 偏移时间
        if (!Strings.isNullOrEmpty(request.getHeader(ConstantsConfig.FRONT_RENDER))) {
            response.addHeader(ConstantsConfig.USER_ID, String.valueOf(userTmpTable.getGUid()));
            response.addHeader(ConstantsConfig.FRONT_RENDER, request.getHeader(ConstantsConfig.FRONT_RENDER));
        }
        return ResponseStruct.success();
    }

    /**
     * @ClassName: LoginController
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 当超管新增管理员用户后，用户登录时获取验证码后修改其密码，自行注册（修改密码）;并且注册既登录。
     * @Date: Created in 2020-01-08 21:29
     * @Version: 1.0.0
     * @param: * @param null
     */
    @SuppressWarnings("all")
    @PostMapping("/signUp")
    public ResponseStruct signUp(@RequestBody SignUser signUser, HttpServletRequest request, HttpServletResponse response) {
        if (Strings.isNullOrEmpty(signUser.getPhoneNum()) || Strings.isNullOrEmpty(signUser.getConfirmPwd()) || Strings.isNullOrEmpty(signUser.getPassword()) || (0L == signUser.getMark() || null == signUser.getMark())) {
            logger.error(Strings.lenientFormat("【LOGIN-CONTROLLER-SIGN-UP】：%s", ErrorCodeMsg.SIGN_UP_PARAMS_ERROR.getMessage()));
            throw new AttemptException(ErrorCodeMsg.SIGN_UP_PARAMS_ERROR);
        } else if (!CommonUtils.isMobile(signUser.getPhoneNum())) {
            logger.error(Strings.lenientFormat("【LOGIN-CONTROLLER-SIGN-UP】：手机号：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.WRONG_PHONE_NUM_ERROR.getMessage()));
            throw new AttemptException(ErrorCodeMsg.LOGIN_PARAMS_ERROR);
        } else if (!signUser.getPassword().equals(signUser.getConfirmPwd())) {
            logger.error(Strings.lenientFormat("【LOGIN-CONTROLLER-SIGN-UP】：手机号：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.WRONG_CONFIRM_PWD_ERROR.getMessage()));
            throw new AttemptException(ErrorCodeMsg.LOGIN_PARAMS_ERROR);
        }
        UserTmpTable userTmpTable = this.loginService.signUp(signUser);
        response.addHeader(HttpConstant.ACCESS_TOKEN_KEY, JwtUtils.sign(userTmpTable.getGUid(), userTmpTable.getSysId()));// access-token
        response.addHeader(HttpConstant.SALT_KEY, userTmpTable.getSysSalt());// salt
        response.addHeader(HttpConstant.DELTA_TM_KEY, String.valueOf(System.currentTimeMillis() - Long.valueOf(request.getHeader(HttpConstant.HEADER_TIMESTAMP_KEY))));// 偏移时间
        if (!Strings.isNullOrEmpty(request.getHeader(ConstantsConfig.FRONT_RENDER))) {
            response.addHeader(ConstantsConfig.USER_ID, String.valueOf(userTmpTable.getGUid()));
            response.addHeader(ConstantsConfig.FRONT_RENDER, request.getHeader(ConstantsConfig.FRONT_RENDER));
        }
        return ResponseStruct.success();
    }

    /**
     * @ClassName: LoginController
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 超管生成相关用户
     * @Date: Created in 2020-01-09 11:21
     * @Version: 1.0.0
     * @param: * @param null
     */
    @PostMapping("/createSignUp")
    public ResponseStruct createSignUp(@RequestBody SignUser signUser, @ModelAttribute RequestUser requestUser) {
        if (Strings.isNullOrEmpty(signUser.getPhoneNum())) {
            logger.error(Strings.lenientFormat("【LOGIN-CONTROLLER-CREATE-SIGN-UP】：%s", ErrorCodeMsg.PHONE_NUM_NULLABLE_ERROR.getMessage()));
            throw new AttemptException(ErrorCodeMsg.PHONE_NUM_NULLABLE_ERROR);
        }
        this.loginService.createSignUp(signUser, requestUser.getGuid());
        return ResponseStruct.success();
    }

}

