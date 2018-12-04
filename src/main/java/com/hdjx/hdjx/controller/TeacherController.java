package com.hdjx.hdjx.controller;

import com.hdjx.hdjx.dao.SchoolDao;
import com.hdjx.hdjx.dao.StudentDao;
import com.hdjx.hdjx.dao.TeacherDao;
import com.hdjx.hdjx.entity.SchoolEntity;
import com.hdjx.hdjx.entity.TeacherEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/teacher")
@RestController
public class TeacherController {


    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @RequestMapping("/login")
    public String helloBoot(){

        List<TeacherEntity> list = teacherDao.findAll();
        return "Hello Boot-JPA";
    }
}
