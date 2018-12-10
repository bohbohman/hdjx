package com.spring.bohbohman.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student")
@Data
public class StudentEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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
     * 班级
     */
    @Column(name = "grade", nullable = false)
    private String grade;

    /**
     * 身份证
     */
    @Column(name = "id_card", nullable = false)
    private String idCard;

    /**
     * 考场号
     */
    @Column(name = "exam_room_num", nullable = false)
    private String examRoomNum;

    /**
     * 学科
     */
    @Column(name = "subject", nullable = false)
    private String subject;

    /**
     * 座位号
     */
    @Column(name = "seat_num", nullable = false)
    private String seatNum;

    /**
     * 学生类别（H 或者 X）
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 是否参加统考
     */
    @Column(name = "is_join", nullable = false)
    private String isJoin;

    /**
     * 学校类别
     */
    @Column(name = "school_type", nullable = false)
    private String schoolType;

    /**
     * 备注
     */
    @Column(name = "description", nullable = false)
    private String description;

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
