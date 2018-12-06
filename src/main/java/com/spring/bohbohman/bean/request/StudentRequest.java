package com.spring.bohbohman.bean.request;

import javax.persistence.*;
import java.util.Date;

public class StudentRequest {


    private Integer id;

    private Integer schoolId;

    /**
     * 名称
     */
    private String name;

    /**
     * 班级
     */
    private String grade;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 考场号
     */
    private String examRoomNum;

    /**
     * 学科
     */
    private String subject;

    /**
     * 座位号
     */
    private String seatNum;

    /**
     * 学生类别（H 或者 X）
     */
    private String type;

    /**
     * 是否参加统考
     */
    private String isJoin;

    /**
     * 备注
     */
    private String description;

    /**
     * 学校类别
     */
    private String schoolType;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }
}
