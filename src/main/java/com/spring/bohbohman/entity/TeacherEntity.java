package com.spring.bohbohman.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "teacher")
@Data
public class TeacherEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 组长id
     */
    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    /**
     * 学校id
     */
    @Column(name = "school_id", nullable = false)
    private Integer schoolId;

    /**
     * 名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 手机号
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * 类型 组长或者组员 LEADER/MEMBER
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 学科
     */
    @Column(name = "subject", nullable = false)
    private String subject;

    /**
     * 学校类别
     */
    @Column(name = "school_type", nullable = false)
    private String schoolType;

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
