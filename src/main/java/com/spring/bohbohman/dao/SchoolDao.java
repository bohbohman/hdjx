package com.spring.bohbohman.dao;

import com.spring.bohbohman.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDao extends JpaRepository<SchoolEntity, Integer>, JpaSpecificationExecutor<SchoolEntity> {

    SchoolEntity findByUserNameAndPassword(String userName, String password);

    SchoolEntity findByUserName(String userName);
}
