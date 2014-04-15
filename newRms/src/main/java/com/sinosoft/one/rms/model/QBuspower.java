package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QBuspower is a Querydsl query type for Buspower
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QBuspower extends EntityPathBase<BusPower> {

    private static final long serialVersionUID = 379143852;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QBuspower buspower = new QBuspower("buspower");

    public final StringPath buspowerid = createString("buspowerid");

    public final QDatarule datarule;

    public final StringPath dataruleparam = createString("dataruleparam");

    public final StringPath isvalidate = createString("isvalidate");

    public final QTask task;

    public final QUserpower userpower;

    public QBuspower(String variable) {
        this(BusPower.class, forVariable(variable), INITS);
    }

    public QBuspower(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBuspower(PathMetadata<?> metadata, PathInits inits) {
        this(BusPower.class, metadata, inits);
    }

    public QBuspower(Class<? extends BusPower> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.datarule = inits.isInitialized("datarule") ? new QDatarule(forProperty("datarule")) : null;
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
        this.userpower = inits.isInitialized("userpower") ? new QUserpower(forProperty("userpower")) : null;
    }

}

