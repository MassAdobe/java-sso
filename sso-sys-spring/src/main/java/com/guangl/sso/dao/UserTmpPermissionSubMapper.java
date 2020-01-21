package com.guangl.sso.dao;

import com.guangl.sso.entity.UserTmpPermissionSub;
import com.guangl.sso.tk.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserTmpPermissionSubMapper extends MyMapper<UserTmpPermissionSub> {
}