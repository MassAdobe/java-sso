package com.guangl.sso.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: RequestUser
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2019-12-26 11:24
 * @Version: 1.0.0
 * @param: * @param null
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class RequestUser {
    private Long guid;
    private Long sysid;
}
