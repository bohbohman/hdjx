package com.spring.bohbohman.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "school")
@Data
public class SchoolEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 编号
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 编号
     */
    @Column(name = "phonetic", nullable = false)
    private String phonetic;

    /**
     * 登录用户名称
     */
    @Column(name = "user_name", nullable = false)
    private String userName;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 是否完成
     */
    @Column(name = "is_complete", nullable = false)
    private String isComplete;

    /**
     * 班级数
     */
    @Column(name = "grand_num", nullable = false)
    private String grandNum;

    /**
     * 负责人
     */
    @Column(name = "principal", nullable = false)
    private String principal;

    /**
     * 联系电话
     */
    @Column(name = "tel", nullable = false)
    private String tel;

    /**
     * 手机号
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = new Date();
    }
}
