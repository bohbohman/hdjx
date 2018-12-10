package com.spring.bohbohman.bean.dto;

import com.spring.bohbohman.bean.AbstractBaseApiBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: xueyb
 * @Date: 18/12/10 17:11
 * @Description:
 */
@Data
@NoArgsConstructor(force=true)//自动生成无参构造方法
@AllArgsConstructor//自动生成包含所有参数的构造方法
public class SchoolTeacherDTO extends AbstractBaseApiBean {

    private Integer schoolId;

    private String schoolName;

    private Integer teacherId;

    private String teacherName;
}
