package com.spring.bohbohman.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTeacherEntity is a Querydsl query type for TeacherEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeacherEntity extends EntityPathBase<TeacherEntity> {

    private static final long serialVersionUID = -176678142L;

    public static final QTeacherEntity teacherEntity = new QTeacherEntity("teacherEntity");

    public final DatePath<java.util.Date> createdAt = createDate("createdAt", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> parentId = createNumber("parentId", Integer.class);

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> schoolId = createNumber("schoolId", Integer.class);

    public final StringPath schoolType = createString("schoolType");

    public final StringPath subject = createString("subject");

    public final StringPath type = createString("type");

    public final DatePath<java.util.Date> updatedAt = createDate("updatedAt", java.util.Date.class);

    public QTeacherEntity(String variable) {
        super(TeacherEntity.class, forVariable(variable));
    }

    public QTeacherEntity(Path<? extends TeacherEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeacherEntity(PathMetadata metadata) {
        super(TeacherEntity.class, metadata);
    }

}

