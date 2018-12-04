package com.hdjx.hdjx.controller;

import com.hdjx.hdjx.dao.SchoolDao;
import com.hdjx.hdjx.dao.StudentDao;
import com.hdjx.hdjx.dao.TeacherDao;
import com.hdjx.hdjx.entity.SchoolEntity;
import com.hdjx.hdjx.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/student")
@RestController
public class StudentController {


    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @RequestMapping("/login")
    public String helloBoot(){

        List<StudentEntity> list = studentDao.findAll();
        return "Hello Boot-JPA";
    }
}
