package com.hdjx.hdjx.controller;

import com.hdjx.hdjx.dao.SchoolDao;
import com.hdjx.hdjx.entity.SchoolEntity;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SchoolController {


    @Autowired
    private SchoolDao schoolDao;

    @RequestMapping("login")
    public String helloBoot(){

        List<SchoolEntity> list = schoolDao.findAll();
        return "Hello Boot-JPA";
    }
}
