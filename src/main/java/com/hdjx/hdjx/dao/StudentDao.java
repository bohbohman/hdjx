package com.hdjx.hdjx.dao;

import com.hdjx.hdjx.entity.StudentEntity;
import com.hdjx.hdjx.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity> {

}
