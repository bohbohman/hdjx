package com.hdjx.hdjx.dao;

import com.hdjx.hdjx.entity.SchoolEntity;
import com.hdjx.hdjx.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends JpaRepository<TeacherEntity, Integer>, JpaSpecificationExecutor<TeacherEntity> {

}
