package com.spring.bohbohman.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.spring.bohbohman.bean.dto.SchoolTeacherDTO;
import com.spring.bohbohman.entity.SchoolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<SchoolEntity, Integer>, JpaSpecificationExecutor<SchoolEntity> {

    SchoolEntity findByUserNameAndPassword(String userName, String password);

    SchoolEntity findByUserName(String userName);

    Page<SchoolTeacherDTO> findSchoolLeftJoinTeacher(Predicate predicate, PageRequest pageRequest, OrderSpecifier[] orderSpecifiers);
}
