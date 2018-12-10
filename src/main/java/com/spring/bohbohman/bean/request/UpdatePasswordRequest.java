package com.spring.bohbohman.bean.request;

import com.spring.bohbohman.bean.AbstractBaseApiBean;
import lombok.Data;

@Data
public class UpdatePasswordRequest extends AbstractBaseApiBean {

    private Integer id;

    private String password;
}
