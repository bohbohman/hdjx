package com.hdjx.hdjx.dao;

import com.hdjx.hdjx.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolDao extends JpaRepository<SchoolEntity, Integer>, JpaSpecificationExecutor<SchoolEntity> {

    SchoolEntity findByUserNameAndPassword(String userName, String password);

    SchoolEntity findByUserName(String userName);
}
