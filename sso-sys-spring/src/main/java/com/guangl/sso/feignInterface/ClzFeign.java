package com.guangl.sso.feignInterface;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: ClzFeign
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-15 20:52
 * @Version: 1.0.0
 * @param: * @param null
 */
@FeignClient("train-class-mng")
public interface ClzFeign {
}
