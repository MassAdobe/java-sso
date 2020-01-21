package com.guangl.sso.feignInterface;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: StudentFeign
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-15 20:22
 * @Version: 1.0.0
 * @param: * @param null
 */
@FeignClient("train-student-mng")
public interface StudentFeign {
}
