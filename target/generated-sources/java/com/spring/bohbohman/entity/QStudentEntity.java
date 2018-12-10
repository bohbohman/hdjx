package com.spring.bohbohman.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudentEntity is a Querydsl query type for StudentEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudentEntity extends EntityPathBase<StudentEntity> {

    private static final long serialVersionUID = 1684990619L;

    public static final QStudentEntity studentEntity = new QStudentEntity("studentEntity");

    public final DatePath<java.util.Date> createdAt = createDate("createdAt", java.util.Date.class);

    public final StringPath description = createString("description");

    public final StringPath examRoomNum = createString("examRoomNum");

    public final StringPath grade = createString("grade");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath idCard = createString("idCard");

    public final StringPath isJoin = createString("isJoin");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> schoolId = createNumber("schoolId", Integer.class);

    public final StringPath schoolType = createString("schoolType");

    public final StringPath seatNum = createString("seatNum");

    public final StringPath subject = createString("subject");

    public final StringPath type = createString("type");

    public final DatePath<java.util.Date> updatedAt = createDate("updatedAt", java.util.Date.class);

    public QStudentEntity(String variable) {
        super(StudentEntity.class, forVariable(variable));
    }

    public QStudentEntity(Path<? extends StudentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudentEntity(PathMetadata metadata) {
        super(StudentEntity.class, metadata);
    }

}

