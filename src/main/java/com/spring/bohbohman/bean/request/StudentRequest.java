package com.spring.bohbohman.bean.request;

import com.spring.bohbohman.bean.AbstractBaseApiBean;
import lombok.Data;

@Data
public class StudentRequest extends AbstractBaseApiBean {

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

}
