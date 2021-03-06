package com.spring.bohbohman.controller;

import com.spring.bohbohman.bean.request.TeacherRequest;
import com.spring.bohbohman.bean.response.TeacherResponse;
import com.spring.bohbohman.repository.SchoolRepository;
import com.spring.bohbohman.repository.StudentRepository;
import com.spring.bohbohman.repository.TeacherRepository;
import com.spring.bohbohman.entity.TeacherEntity;
import com.spring.bohbohman.util.BeanMapping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@RequestMapping(value = "/teacher")
@RestController
public class TeacherController {


    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * 新增
     *
     * @param reqBean 老师表信息
     * @return 老师表信息
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    Map<String, Object> create(@RequestBody TeacherRequest reqBean){

        Map<String, Object> result = new LinkedHashMap<>();
        TeacherEntity teacherEntity = BeanMapping.map(reqBean,TeacherEntity.class);
        teacherEntity.setId(null);
        teacherEntity = teacherRepository.save(teacherEntity);
        TeacherResponse teacherResponse = BeanMapping.map(teacherEntity,TeacherResponse.class);

        result.put("status", true);
        result.put("msg", "新增老师成功");
        return result;
    }

    /**
     * 更新
     *
     * @param reqBean 老师表信息
     * @return 老师表信息
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    Map<String, Object> update(@RequestBody TeacherRequest reqBean){
        Map<String, Object> result = new LinkedHashMap<>();
        Optional<TeacherEntity> optionalTeacherEntity = teacherRepository.findById(reqBean.getId());
        if (null == optionalTeacherEntity) {
            result.put("status", false);
            result.put("msg", "老师不存在！");
            return result;
        }
        TeacherEntity teacherEntity = optionalTeacherEntity.get();
        teacherEntity.setSubject(reqBean.getSubject());
        teacherEntity.setName(reqBean.getName());
        teacherEntity.setType(reqBean.getType());
        teacherEntity.setPhone(reqBean.getPhone());
        teacherEntity.setSchoolType(reqBean.getSchoolType());
        teacherEntity = teacherRepository.save(teacherEntity);
        TeacherResponse teacherResponse = BeanMapping.map(teacherEntity,TeacherResponse.class);
        result.put("status", true);
        result.put("msg", "修改老师成功");
        return result;
    }

    /**
     * 删除
     *
     * @param id ID
     * @return 空
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    Map<String, Object> delete(@PathVariable Integer id){
        Map<String, Object> result = new LinkedHashMap<>();
        TeacherEntity teacherEntity = teacherRepository.getOne(id);
        if (null == teacherEntity) {
            result.put("status", false);
            result.put("msg", "老师不存在！");
            return result;
        }
        teacherRepository.deleteById(id);
        result.put("status", true);
        result.put("msg", "删除老师成功");
        return result;
    }

    /**
     * 详情
     *
     * @param id ID
     * @return 详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    TeacherResponse get(@PathVariable Integer id){
        TeacherEntity teacherEntity = teacherRepository.getOne(id);
        TeacherResponse teacherResponse= BeanMapping.map(teacherEntity,TeacherResponse.class);
        return teacherResponse;
    }

    /**
     *
     * @param schoolId 学校id
     * @param name 老师姓名
     * @param subject 科目
     * @param schoolType 初中或者高中
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    List<TeacherResponse> findAll(@RequestParam(required = false) Integer schoolId,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String subject,
                                  @RequestParam(required = false) String schoolType){
        Specification specification = new Specification<TeacherEntity>() {
            @Override
            public Predicate toPredicate(Root<TeacherEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicate = new ArrayList<>();
                if(null != schoolId){
                    predicate.add(criteriaBuilder.equal(root.get("schoolId"), schoolId));
                }
                if(StringUtils.isNotBlank(schoolType)){
                    predicate.add(criteriaBuilder.equal(root.get("schoolType"), schoolType));
                }
                if(StringUtils.isNotBlank(subject)){
                    predicate.add(criteriaBuilder.equal(root.get("subject"), subject));
                }
                if(StringUtils.isNotBlank(name)){
                    predicate.add(criteriaBuilder.like(root.get("name"), "%"+name+"%"));
                }
                return criteriaBuilder.and(predicate.toArray(new Predicate[predicate.size()]));
            }
        };
        List<TeacherEntity> teacherEntityList = teacherRepository.findAll(specification);
        List<TeacherResponse> teacherResponseList = BeanMapping.mapList(teacherEntityList,TeacherResponse.class);
        return teacherResponseList;
    }
}
