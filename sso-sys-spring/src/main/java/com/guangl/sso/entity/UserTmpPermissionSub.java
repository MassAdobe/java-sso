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
@Table(name = "user_tmp_permission_sub")
public class UserTmpPermissionSub {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sub_permission_id")
    private Long subPermissionId;

    @Column(name = "permission_name")
    private String permissionName;

    /**
     * 关联功能
     */
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

    @Column(name = "is_deleted")
    private Integer isDeleted;

    @Column(name = "is_enabled")
    private Integer isEnabled;

    @Column(name = "permission_id")
    private Long permissionId;

    public UserTmpPermissionSub(Long permissionId) {
        this.isDeleted = 1;
        this.isEnabled = 1;
        this.permissionId = permissionId;
    }
}