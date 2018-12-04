package com.hdjx.hdjx.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "student")
public class StudentEntity {

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
     * 备注
     */
    @Column(name = "desc", nullable = false)
    private String desc;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getExamRoomNum() {
        return examRoomNum;
    }

    public void setExamRoomNum(String examRoomNum) {
        this.examRoomNum = examRoomNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(String isJoin) {
        this.isJoin = isJoin;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
