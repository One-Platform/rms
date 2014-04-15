package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QRoletask is a Querydsl query type for Roletask
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QRoletask extends EntityPathBase<RoleTask> {

    private static final long serialVersionUID = 1253816098;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QRoletask roletask = new QRoletask("roletask");

    public final StringPath flag = createString("flag");

    public final StringPath isvalidate = createString("isvalidate");

    public final QRole role;

    public final StringPath roletaskid = createString("roletaskid");

    public final QTaskAuth taskAuth;

    public QRoletask(String variable) {
        this(RoleTask.class, forVariable(variable), INITS);
    }

    public QRoletask(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRoletask(PathMetadata<?> metadata, PathInits inits) {
        this(RoleTask.class, metadata, inits);
    }

    public QRoletask(Class<? extends RoleTask> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
        this.taskAuth = inits.isInitialized("taskAuth") ? new QTaskAuth(forProperty("taskAuth"), inits.get("taskAuth")) : null;
    }

}

