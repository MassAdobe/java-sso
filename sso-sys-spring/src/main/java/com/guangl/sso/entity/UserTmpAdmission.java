package com.guangl.sso.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tmp_admission")
public class UserTmpAdmission {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admission_id")
    private Long admissionId;

    /**
     * 用户ID
     */
    @Column(name = "guid")
    private Long guid;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_tm")
    private Date createdTm;

    @Column(name = "updated_tm")
    private Date updatedTm;

    @Column(name = "is_enabled")
    private Integer isEnabled;

    @Column(name = "is_deleted")
    private Integer isDeleted;

    public UserTmpAdmission(Long guid) {
        this.guid = guid;
        this.isEnabled = 1;
        this.isDeleted = 1;
    }
}