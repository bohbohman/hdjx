package com.spring.bohbohman.controller;

import com.alibaba.fastjson.JSON;
import com.spring.bohbohman.dao.SchoolDao;
import com.spring.bohbohman.entity.SchoolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: xueyb
 * @Date: 18/12/6 18:54
 * @Description:
 */
@RequestMapping(value = "/hello")
@RestController
@Transactional
public class HelloWorldController {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/world", method = RequestMethod.GET)
    public String helloWorld() {
        return "helloWorld";
    }

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public Object getSchool(@RequestParam Integer schoolId) {
        SchoolEntity schoolEntity = new SchoolEntity();
        String key = "school_" + schoolId;
        ValueOperations<String, SchoolEntity> operations = redisTemplate.opsForValue();
        //查询缓存
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            schoolEntity = operations.get(key);
        } else {
            schoolEntity = schoolDao.getOne(schoolId);
            operations.set(key, schoolEntity);
        }
        return JSON.toJSON(schoolEntity);
    }
}
