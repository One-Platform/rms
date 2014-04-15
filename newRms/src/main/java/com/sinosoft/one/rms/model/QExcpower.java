package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QExcpower is a Querydsl query type for Excpower
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QExcpower extends EntityPathBase<ExcPower> {

    private static final long serialVersionUID = -777915108;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QExcpower excpower = new QExcpower("excpower");

    public final StringPath excpowerid = createString("excpowerid");

    public final StringPath isvalidate = createString("isvalidate");

    public final QTask task;

    public final QUserpower userpower;

    public QExcpower(String variable) {
        this(ExcPower.class, forVariable(variable), INITS);
    }

    public QExcpower(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QExcpower(PathMetadata<?> metadata, PathInits inits) {
        this(ExcPower.class, metadata, inits);
    }

    public QExcpower(Class<? extends ExcPower> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
        this.userpower = inits.isInitialized("userpower") ? new QUserpower(forProperty("userpower")) : null;
    }

}

