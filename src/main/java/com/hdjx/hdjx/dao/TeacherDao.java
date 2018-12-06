package com.hdjx.hdjx.dao;

import com.hdjx.hdjx.entity.SchoolEntity;
import com.hdjx.hdjx.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherDao extends JpaRepository<TeacherEntity, Integer>, JpaSpecificationExecutor<TeacherEntity> {

    List<TeacherEntity> findBySchoolId(Integer schoolId);

    List<TeacherEntity> findBySchoolIdAndSubjectAndSchoolType(Integer schoolId,String subject,String schoolType);

    List<TeacherEntity> findBySchoolIdAndSubject(Integer schoolId,String subject);

    List<TeacherEntity> findBySchoolIdAndSchoolType(Integer schoolId,String schoolType);

    List<TeacherEntity> findBySchoolType(String grade);
}
