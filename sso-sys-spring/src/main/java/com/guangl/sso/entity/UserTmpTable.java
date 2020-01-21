package com.guangl.sso.entity;

import com.guangl.sso.utils.CommonUtils;
import com.guangl.sso.utils.Md5Util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tmp_table")
public class UserTmpTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "g_uid")
    private Long gUid;

    @Column(name = "sys_id")
    private Long sysId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "pass_word")
    private String passWord;

    @Column(name = "password_salt")
    private String passwordSalt;

    @Column(name = "created_tm")
    private Date createdTm;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_tm")
    private Date updatedTm;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Column(name = "is_enabled")
    private Integer isEnabled;

    @Column(name = "sys_salt")
    private String sysSalt;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "sex_gender")
    private Integer sexGender;

    @Column(name = "prnt_guid")
    private Long prntGuid;

    @Column(name = "expire_dt")
    private Date expireDt;

    public UserTmpTable(String phoneNum, Long sysId) {
        this.phoneNum = phoneNum.trim();
        this.sysId = sysId;
        this.isDeleted = 1;
        this.isEnabled = 1;
    }

    public UserTmpTable(Long createdBy, Long updatedBy, String sysSalt, Long sysId, String phoneNum) {
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.sysSalt = sysSalt;
        this.sysId = sysId;
        this.phoneNum = phoneNum;
    }

    public UserTmpTable(Long gUid, String passWord, Long updatedBy) {
        this.gUid = gUid;
        this.passwordSalt = Md5Util.encode(CommonUtils.getRandomString(12));
        this.passWord = Md5Util.encode(passWord + this.passwordSalt + this.gUid);
        this.updatedTm = new Date();
        this.updatedBy = updatedBy;
    }

}