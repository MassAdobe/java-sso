package com.guangl.sso.dao;

import com.guangl.sso.entity.UserTmpTable;
import com.guangl.sso.tk.mybatis.MyMapper;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserTmpTableMapper extends MyMapper<UserTmpTable> {
}