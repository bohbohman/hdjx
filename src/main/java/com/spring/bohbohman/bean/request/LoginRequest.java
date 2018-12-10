package com.spring.bohbohman.bean.request;

import com.spring.bohbohman.bean.AbstractBaseApiBean;
import lombok.Data;

@Data
public class LoginRequest extends AbstractBaseApiBean {

    private String userName;

    private String password;
}
