package com.spring.bohbohman.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSchoolEntity is a Querydsl query type for SchoolEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSchoolEntity extends EntityPathBase<SchoolEntity> {

    private static final long serialVersionUID = -504345542L;

    public static final QSchoolEntity schoolEntity = new QSchoolEntity("schoolEntity");

    public final StringPath code = createString("code");

    public final DatePath<java.util.Date> createdAt = createDate("createdAt", java.util.Date.class);

    public final StringPath grandNum = createString("grandNum");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath isComplete = createString("isComplete");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath phonetic = createString("phonetic");

    public final StringPath principal = createString("principal");

    public final StringPath tel = createString("tel");

    public final DatePath<java.util.Date> updatedAt = createDate("updatedAt", java.util.Date.class);

    public final StringPath userName = createString("userName");

    public QSchoolEntity(String variable) {
        super(SchoolEntity.class, forVariable(variable));
    }

    public QSchoolEntity(Path<? extends SchoolEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchoolEntity(PathMetadata metadata) {
        super(SchoolEntity.class, metadata);
    }

}

