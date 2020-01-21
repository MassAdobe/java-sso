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
@Table(name = "user_tmp_admission_sub")
public class UserTmpAdmissionSub {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_admission_id")
    private Long subAdmissionId;

    @Column(name = "function_id")
    private Long functionId;

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

    @Column(name = "admission_name")
    private String admissionName;

    @Column(name = "admission_id")
    private Long admissionId;

    public UserTmpAdmissionSub(Long admissionId) {
        this.isEnabled = 1;
        this.isDeleted = 1;
        this.admissionId = admissionId;
    }
}