package com.spring.bohbohman.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spring.bohbohman.bean.dto.SchoolTeacherDTO;
import com.spring.bohbohman.entity.QSchoolEntity;
import com.spring.bohbohman.entity.QTeacherEntity;
import com.spring.bohbohman.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.PostConstruct;

/**
 * @Auther: xueyb
 * @Date: 18/12/10 16:08
 * @Description:
 */
public class SchoolRepositoryImpl extends BaseRepository {

    private JPAQueryFactory jpaQueryFactory;

    @PostConstruct
    public void init() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public Page<SchoolTeacherDTO> findSchoolLeftJoinTeacher(Predicate predicate, PageRequest pageRequest, OrderSpecifier[] orderSpecifiers) {
        QSchoolEntity qSchoolEntity = QSchoolEntity.schoolEntity;
        QTeacherEntity qTeacherEntity = QTeacherEntity.teacherEntity;
        QueryResults<SchoolTeacherDTO> qr = jpaQueryFactory.select(Projections.constructor(SchoolTeacherDTO.class,
                    qSchoolEntity.id,
                    qSchoolEntity.name,
                    qTeacherEntity.id,
                    qTeacherEntity.name))
                .from(qSchoolEntity)
                .leftJoin(qTeacherEntity)
                .on(qTeacherEntity.schoolId.intValue().eq(qSchoolEntity.id.intValue()))
                .where(predicate)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .orderBy(orderSpecifiers)
                .fetchResults();
        return new PageImpl<>(qr.getResults(), pageRequest, qr.getTotal());
    }
}
