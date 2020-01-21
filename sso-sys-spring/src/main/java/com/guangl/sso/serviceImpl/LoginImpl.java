package com.guangl.sso.serviceImpl;

import com.google.common.base.Strings;
import com.guangl.sso.dao.*;
import com.guangl.sso.entity.*;
import com.guangl.sso.enums.ErrorCodeMsg;
import com.guangl.sso.exception.AttemptException;
import com.guangl.sso.pojo.SignUser;
import com.guangl.sso.pojo.UserStruct;
import com.guangl.sso.redisService.LoginRedisService;
import com.guangl.sso.service.LoginService;
import com.guangl.sso.utils.CommonUtils;
import com.guangl.sso.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: LoginImpl
 * @Author: MassAdobe
 * @Email: massadobe8@gmail.com
 * @Description: TODO
 * @Date: Created in 2020-01-08 21:01
 * @Version: 1.0.0
 * @param: * @param null
 */
@Service
public class LoginImpl implements LoginService {

    private final static Logger logger = LoggerFactory.getLogger(LoginImpl.class);

    @Autowired
    private LoginRedisService loginRedisService;

    @Autowired
    private UserTmpTableMapper userTmpTableMapper;
    @Autowired
    private UserTmpAdmissionMapper userTmpAdmissionMapper;
    @Autowired
    private UserTmpAdmissionSubMapper userTmpAdmissionSubMapper;
    @Autowired
    private UserTmpPermissionMapper userTmpPermissionMapper;
    @Autowired
    private UserTmpPermissionSubMapper userTmpPermissionSubMapper;

    @Transactional(readOnly = true)
    @Override
    public boolean confirm(String phoneNum, Long mark) throws AttemptException {
        UserTmpTable userTmpTable = this.userTmpTableMapper.selectOne(new UserTmpTable(phoneNum, mark));
        if (null == userTmpTable) {
            logger.error(Strings.lenientFormat("【LOGIN-IMPL-SIGN-UP】：用户：%s %s", phoneNum, ErrorCodeMsg.USER_NO_EXIST_ERROR));
            throw new AttemptException(ErrorCodeMsg.USER_NO_EXIST_ERROR);
        }
        if (CommonUtils.checkExpireDt(userTmpTable.getExpireDt())) {// 查看是否已经超出了ExpireTm
            logger.error(Strings.lenientFormat("【LOGIN-IMPL-SIGN-UP】：用户：%s %s", phoneNum, ErrorCodeMsg.USER_BEYOND_EXPIRE_TM_ERROR));
            throw new AttemptException(ErrorCodeMsg.USER_BEYOND_EXPIRE_TM_ERROR);
        }
        if (Strings.isNullOrEmpty(userTmpTable.getPassWord()))
            return true;
        return false;
    }

    @Override
    public UserTmpTable signedIn(SignUser signUser) throws AttemptException {
        UserTmpTable userTmpTable = getUserTmpTableByPhoneSysId(signUser.getPhoneNum(), signUser.getMark());// 获取用户信息
        if (null == userTmpTable) {
            logger.error(Strings.lenientFormat("【LOGIN-IMPL-SIGN-IN】：用户：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.USER_NO_EXIST_ERROR));
            throw new AttemptException(ErrorCodeMsg.LOGIN_PARAMS_ERROR);
        } else if (null == userTmpTable.getPrntGuid() || 0L == userTmpTable.getPrntGuid() || Strings.isNullOrEmpty(userTmpTable.getPassWord())) {// 非法用户 prnt_guid不是-1就是大于0的值 或者密码为空
            logger.error(Strings.lenientFormat("【LOGIN-IMPL-SIGN-IN】：用户：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.ILLEGAL_USER_ERROR));
            throw new AttemptException(ErrorCodeMsg.ILLEGAL_USER_ERROR);
        } else if (JwtUtils.checkPassword(signUser.getPassword(), userTmpTable.getPassWord(), userTmpTable.getPasswordSalt(), userTmpTable.getGUid())) {// 校验密码是否正确
            logger.error(Strings.lenientFormat("【LOGIN-IMPL-SIGN-IN】：用户：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.USER_PASS_WORD_ERROR));
            throw new AttemptException(ErrorCodeMsg.LOGIN_PARAMS_ERROR);
        }
        admissionAndPermission(userTmpTable, signUser);// 引用方法，获取admission&permission，并更新redis中信息
        return userTmpTable;
    }

    @Transactional(readOnly = true)
    UserTmpTable getUserTmpTableByPhoneSysId(String phoneNum, Long sysId) {
        return this.userTmpTableMapper.selectOne(new UserTmpTable(phoneNum, sysId));// 获取用户信息
    }

    @Override
    public UserTmpTable signUp(SignUser signUser) throws AttemptException {
        UserTmpTable select = getUserTmpTableByPhone(signUser.getPhoneNum(), signUser.getMark());
        if (null == select) {
            logger.error(Strings.lenientFormat("【LOGIN-IMPL-SIGN-UP】：用户：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.USER_NO_EXIST_ERROR));
            throw new AttemptException(ErrorCodeMsg.USER_NO_EXIST_ERROR);
        }
        admissionAndPermission(select, signUser);// 引用方法，获取admission&permission，并更新redis中信息
        Example example = new Example(UserTmpTable.class);
        example.createCriteria().andEqualTo("gUid", select.getGUid());
        this.userTmpTableMapper.updateByExampleSelective(new UserTmpTable(null, signUser.getPassword(), select.getGUid()), example);
        return select;
    }

    /**
     * @ClassName: LoginImpl
     * @Author: MassAdobe
     * @Email: massadobe8@gmail.com
     * @Description: 获取admission&permission，并更新redis中信息
     * @Date: Created in 2020-01-20 14:43
     * @Version: 1.0.0
     * @param: * @param null
     */
    private void admissionAndPermission(UserTmpTable userTmpTable, SignUser signUser) throws AttemptException {
        // Admission
        UserTmpAdmission userTmpAdmission = this.userTmpAdmissionMapper.selectOne(new UserTmpAdmission(userTmpTable.getGUid()));// 获取Admission
        if (null == userTmpAdmission) {
            logger.error(Strings.lenientFormat("【ADMISSION-AND-PERMISSION】：用户：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.USER_ADMISSION_NO_EXIST_ERROR));
            throw new AttemptException(ErrorCodeMsg.USER_ADMISSION_NO_EXIST_ERROR);
        }
        List<String> admissions = new ArrayList<>();
        this.userTmpAdmissionSubMapper.select(new UserTmpAdmissionSub(userTmpAdmission.getAdmissionId())).forEach(entity -> {
            admissions.add(entity.getAdmissionName());
        });
        this.loginRedisService.addOrUpdateUserStruct(userTmpTable.getGUid(), new UserStruct(admissions, userTmpTable.getSysSalt(), userTmpTable.getExpireDt()));// 插入或修改Redis(UserStruct)
        // Permission
        UserTmpPermission userTmpPermission = this.userTmpPermissionMapper.selectOne(new UserTmpPermission(userTmpTable.getGUid()));// 获取Permission
        if (null == userTmpPermission) {
            logger.error(Strings.lenientFormat("【ADMISSION-AND-PERMISSION】：用户：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.USER_PERMISSION_NO_EXIST_ERROR));
            throw new AttemptException(ErrorCodeMsg.USER_PERMISSION_NO_EXIST_ERROR);
        }
        this.loginRedisService.delPassPermissions(userTmpTable.getGUid());// 先删除原先的Permission
        this.userTmpPermissionSubMapper.select(new UserTmpPermissionSub(userTmpPermission.getPermissionId())).forEach(entity -> {
            this.loginRedisService.addPermissions(userTmpTable.getGUid(), String.valueOf(entity.getFunctionId()), entity.getPermissionName());
        });
    }

    @Transactional(readOnly = true)
    UserTmpTable getUserTmpTableByPhone(String phoneNum, Long sysId) throws AttemptException {
        return this.userTmpTableMapper.selectOne(new UserTmpTable(phoneNum, sysId));
    }

    @Override
    public void createSignUp(SignUser signUser, Long guid) throws AttemptException {
        UserTmpTable select = getUserTmpTableById(guid);
        if (null == select) {
            logger.error(Strings.lenientFormat("【LOGIN-IMPL-CREATE-SIGN-UP】：用户：%s %s", signUser.getPhoneNum(), ErrorCodeMsg.SUPER_MANAGER_UNEXIST_ERROR));
            throw new AttemptException(ErrorCodeMsg.SUPER_MANAGER_UNEXIST_ERROR);
        }
        UserTmpTable preInsert = new UserTmpTable(guid, guid, select.getSysSalt(), select.getSysId(), signUser.getPhoneNum());
        if (Strings.isNullOrEmpty(signUser.getUsername())) {
            preInsert.setUserName(CommonUtils.getRandomString(16));
        }
        if (!Strings.isNullOrEmpty(signUser.getRealName())) {
            preInsert.setRealName(signUser.getRealName());
        }
        if (null != signUser.getSexGender()) {
            preInsert.setSexGender(signUser.getSexGender());
        }
        this.userTmpTableMapper.insert(preInsert);
    }

    @Transactional(readOnly = true)
    UserTmpTable getUserTmpTableById(Long guid) {
        return this.userTmpTableMapper.selectByPrimaryKey(guid);
    }
}
