package com.hdjx.hdjx.dao;

import com.hdjx.hdjx.entity.StudentEntity;
import com.hdjx.hdjx.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity> {

    List<StudentEntity> findBySchoolId(Integer schoolId);

    List<StudentEntity> findBySchoolIdAndSchoolTypeAndSubject(Integer schoolId, String grade, String ubject);

    List<StudentEntity> findBySubject(String ysy);

    List<StudentEntity> findByIdCardAndSubject(String idcard,String subject);
}
