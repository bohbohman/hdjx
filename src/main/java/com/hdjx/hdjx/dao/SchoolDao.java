package com.hdjx.hdjx.dao;

import com.hdjx.hdjx.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDao extends JpaRepository<SchoolEntity, Integer>, JpaSpecificationExecutor<SchoolEntity> {

}
