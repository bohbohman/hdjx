package com.spring.bohbohman.bean.request;


import com.spring.bohbohman.bean.AbstractBaseApiBean;
import lombok.Data;

@Data
public class TeacherRequest extends AbstractBaseApiBean {

    private Integer id;

    private Integer parentId;

    private Integer schoolId;

    private String name;

    private String phone;

    private String type;

    private String subject;

    /**
     * 学校类别
     */
    private String schoolType;
}
