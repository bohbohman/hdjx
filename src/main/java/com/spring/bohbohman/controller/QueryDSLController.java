package com.spring.bohbohman.controller;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.spring.bohbohman.bean.ApiResponse;
import com.spring.bohbohman.bean.dto.SchoolTeacherDTO;
import com.spring.bohbohman.entity.QTeacherEntity;
import com.spring.bohbohman.entity.TeacherEntity;
import com.spring.bohbohman.repository.SchoolRepository;
import com.spring.bohbohman.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: xueyb
 * @Date: 18/12/10 15:35
 * @Description:
 */
@RequestMapping(value = "/dsl")
@RestController
@Transactional
public class QueryDSLController extends BaseAbstractController {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private TeacherRepository teacherRepository;


    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    public Page<TeacherEntity> findTeachers(@RequestParam(required = false, defaultValue = "0") Integer pageIndex,
                                            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        QTeacherEntity qTeacher = QTeacherEntity.teacherEntity;
        Predicate predicate = qTeacher.phone.isNotNull()
                .and(qTeacher.parentId.eq(0));
        Sort sort = new Sort(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, sort);
        Page<TeacherEntity> teacherPage = teacherRepository.findAll(predicate, pageRequest);
        return teacherPage;
    }

    @RequestMapping(value = "/school_teacher", method = RequestMethod.GET)
    public ApiResponse<SchoolTeacherDTO> findSchoolTeachers(@RequestParam(required = false, defaultValue = "0") Integer pageIndex,
                                          @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Predicate predicate = QTeacherEntity.teacherEntity.phone.isNotNull();
        PageRequest pageRequest = PageRequest.of(pageIndex, pageSize);
        OrderSpecifier[] orderSpecifiers = {QTeacherEntity.teacherEntity.id.desc()};
        Page<SchoolTeacherDTO> page = schoolRepository.findSchoolLeftJoinTeacher(predicate, pageRequest, orderSpecifiers);

        return convertApiResponse(page, SchoolTeacherDTO.class);
    }
}
