package com.guangl.sso.feignInterface;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: HelloFeign
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-15 19:53
 * @Version: 1.0.0
 * @param: * @param null
 */
@FeignClient("train-plan-mng")
public interface PlanFeign {

}
