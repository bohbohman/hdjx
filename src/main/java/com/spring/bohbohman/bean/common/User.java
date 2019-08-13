package com.spring.bohbohman.bean.common;

import com.spring.bohbohman.bean.AbstractBaseApiBean;
import lombok.Data;

/**
 * @Auther: xueyb
 * @Date: 2019/1/9 11:43
 * @Description:
 */
@Data
public class User extends AbstractBaseApiBean {

    private Integer id;

    private String info;

    private String name;
}
