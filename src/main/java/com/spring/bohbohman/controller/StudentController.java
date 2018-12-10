package com.spring.bohbohman.controller;

import com.spring.bohbohman.bean.request.StudentRequest;
import com.spring.bohbohman.bean.response.StudentResponse;
import com.spring.bohbohman.repository.SchoolRepository;
import com.spring.bohbohman.repository.StudentRepository;
import com.spring.bohbohman.repository.TeacherRepository;
import com.spring.bohbohman.entity.StudentEntity;
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
import java.util.regex.Pattern;


@RequestMapping(value = "/student")
@RestController
public class StudentController {


    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * 新增学生
     *
     * @param reqBean 学生表信息
     * @return 学生表信息
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    Map<String, Object> create(@RequestBody StudentRequest reqBean){

        String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
        Map<String, Object> result = new LinkedHashMap<>();
        //身份证是否合格
        if (!Pattern.matches(REGEX_ID_CARD, reqBean.getIdCard())) {
            result.put("status", false);
            result.put("msg", "身份证不合格");
            return result;
        }
        //是否参加成绩统计和学生类别
        List<String> isJoin = new ArrayList<>();
        isJoin.add("是");
        isJoin.add("否");
        if (null != reqBean.getIsJoin() && !isJoin.contains(reqBean.getIsJoin())) {
            result.put("status", false);
            result.put("msg", "是否参加成绩统计只能是'是'和'否'");
            return result;
        }
        List<String> type = new ArrayList<>();
        type.add("H");
        type.add("X");
        if (null != reqBean.getType() && !type.contains(reqBean.getType())) {
            result.put("status", false);
            result.put("msg", "学生类别只能是'H'或'X'");
            return result;
        }
        List<StudentEntity> studentEntities = studentRepository.findByIdCardAndSubject(reqBean.getIdCard(),reqBean.getSubject());
        if(studentEntities!=null && studentEntities.size()>0){
            result.put("status", false);
            result.put("msg", "身份证已存在");
            return result;
        }

        StudentEntity studentEntity = BeanMapping.map(reqBean,StudentEntity.class);
        studentEntity.setId(null);
        studentEntity = studentRepository.save(studentEntity);
        StudentResponse studentResponse = BeanMapping.map(studentEntity,StudentResponse.class);
        result.put("status", true);
        result.put("msg", "新增学生成功");
        return result;
    }

    /**
     * 更新学生
     *
     * @param reqBean 学生表信息
     * @return 学生表信息
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    Map<String, Object> update(@RequestBody StudentRequest reqBean){

        Map<String, Object> result = new LinkedHashMap<>();
        Optional<StudentEntity> optionalStudentEntity = studentRepository.findById(reqBean.getId());
        String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
        if (null == optionalStudentEntity) {
            result.put("status", false);
            result.put("msg", "学生不存在！");
            return result;
        }
        //身份证是否合格
        if (!Pattern.matches(REGEX_ID_CARD, reqBean.getIdCard())) {
            result.put("status", false);
            result.put("msg", "身份证不合格");
            return result;
        }
        //是否参加成绩统计和学生类别
        List<String> isJoin = new ArrayList<>();
        isJoin.add("是");
        isJoin.add("否");
        if (null != reqBean.getIsJoin() && !isJoin.contains(reqBean.getIsJoin())) {
            result.put("status", false);
            result.put("msg", "是否参加成绩统计只能是'是'和'否'");
            return result;
        }
        List<String> type = new ArrayList<>();
        type.add("H");
        type.add("X");
        if (null != reqBean.getType() && !type.contains(reqBean.getType())) {
            result.put("status", false);
            result.put("msg", "学生类别只能是'H'或'X'");
            return result;
        }
        List<StudentEntity> studentEntities = studentRepository.findByIdCardAndSubject(reqBean.getIdCard(),reqBean.getSubject());
        if(studentEntities!=null && studentEntities.size()>0){
            if(studentEntities.get(0).getId().intValue() != reqBean.getId()){
                result.put("status", false);
                result.put("msg", "身份证已存在");
                return result;
            }
        }
        StudentEntity studentEntity = optionalStudentEntity.get();
        studentEntity.setDescription(reqBean.getDescription());
        studentEntity.setExamRoomNum(reqBean.getExamRoomNum());
        studentEntity.setGrade(reqBean.getGrade());
        studentEntity.setIdCard(reqBean.getIdCard());
        studentEntity.setIsJoin(reqBean.getIsJoin());
        studentEntity.setName(reqBean.getName());
        studentEntity.setSeatNum(reqBean.getSeatNum());
        studentEntity.setSubject(reqBean.getSubject());
        studentEntity.setType(reqBean.getType());
        studentEntity.setSchoolType(reqBean.getSchoolType());
        studentEntity = studentRepository.save(studentEntity);;
        StudentResponse studentResponse = BeanMapping.map(studentEntity,StudentResponse.class);
        result.put("status", true);
        result.put("msg", "更新学生成功");
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
        StudentEntity studentEntity = studentRepository.getOne(id);
        if (null == studentEntity) {
            result.put("status", false);
            result.put("msg", "学生不存在！");
            return result;
        }
        studentRepository.deleteById(id);

        result.put("status", true);
        result.put("msg", "删除学生成功");
        return result;
    }

    /**
     * 详情
     *
     * @param id ID
     * @return 详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    StudentResponse get(@PathVariable Integer id){
        StudentEntity studentEntity = studentRepository.getOne(id);
        StudentResponse studentResponse = BeanMapping.map(studentEntity,StudentResponse.class);
        return studentResponse;
    }

    /**
     * 查询列表
     * @param schoolId 学校id
     * @param name 老师姓名
     * @param subject 科目
     * @param schoolType 初中或者高中
     * @return 结果集合
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    List<StudentResponse> findAll(@RequestParam(required = false) Integer schoolId,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String subject,
                                  @RequestParam(required = false) String schoolType){
        Specification specification = new Specification<StudentEntity>() {
            @Override
            public Predicate toPredicate(Root<StudentEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != schoolId){
                    predicates.add(criteriaBuilder.equal(root.get("schoolId"), schoolId));
                }
                if(StringUtils.isNotBlank(subject)){
                    predicates.add(criteriaBuilder.equal(root.get("subject"), subject));
                }
                if(StringUtils.isNotBlank(schoolType)){
                    predicates.add(criteriaBuilder.equal(root.get("schoolType"), schoolType));
                }
                if(StringUtils.isNotBlank(name)){
                    predicates.add(criteriaBuilder.like(root.get("name"), "%"+name+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<StudentEntity> studentEntities = studentRepository.findAll(specification);
        List<StudentResponse> studentResponse = BeanMapping.mapList(studentEntities,StudentResponse.class);
        return studentResponse;
    }
}
