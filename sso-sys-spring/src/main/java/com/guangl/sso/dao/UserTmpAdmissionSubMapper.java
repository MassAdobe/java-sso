package com.guangl.sso.dao;

import com.guangl.sso.entity.UserTmpAdmissionSub;
import com.guangl.sso.tk.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserTmpAdmissionSubMapper extends MyMapper<UserTmpAdmissionSub> {
}