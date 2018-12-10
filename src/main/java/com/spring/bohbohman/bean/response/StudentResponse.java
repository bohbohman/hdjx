package com.spring.bohbohman.bean.response;

import com.spring.bohbohman.bean.AbstractBaseApiBean;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class StudentResponse extends AbstractBaseApiBean {


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

    private Date createdAt;

    private Date updatedAt;
}
