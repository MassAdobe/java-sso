package com.guangl.sso.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @ClassName: SignUser
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: 登录，自行注册，超管生成相关用户公用传参
 * @Date: Created in 2020-01-09 17:58
 * @Version: 1.0.0
 * @param: * @param null
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUser {

    private String username;// 用户名
    private String password;// 密码
    private String confirmPwd;// 确认密码
    private String phoneNum;// 手机号
    private String realName;// 真实姓名
    private Long sysId;// 系统编码
    private Long mark;// 系统编码

    @Max(value = 3, message = "性别错误")
    @Min(value = 1, message = "性别错误")
    private Integer sexGender;// 性别

}
