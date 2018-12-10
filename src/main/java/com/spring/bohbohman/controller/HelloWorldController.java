package com.spring.bohbohman.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.spring.bohbohman.repository.SchoolRepository;
import com.spring.bohbohman.repository.TeacherRepository;
import com.spring.bohbohman.entity.SchoolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

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
    private SchoolRepository schoolRepository;

    @Autowired
    private TeacherRepository teacherRepository;

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
            schoolEntity = schoolRepository.getOne(schoolId);
            operations.set(key, schoolEntity);
        }
        return JSON.toJSON(schoolEntity);
    }

    @RequestMapping(value = "/schools", method = RequestMethod.GET)
    public Page<SchoolEntity> findSchools(@RequestParam(required = false, defaultValue = "0") Integer pageIndex,
                                          @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, new Sort(Sort.Direction.DESC, "id"));
        Page<SchoolEntity> schoolPage = schoolRepository.findAll(new Specification<SchoolEntity>() {
            @Override
            public Predicate toPredicate(Root<SchoolEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = Lists.newArrayList();
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%学%"));

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        return schoolPage;
    }

}
