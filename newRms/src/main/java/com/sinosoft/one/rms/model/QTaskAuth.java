package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QTaskAuth is a Querydsl query type for TaskAuth
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QTaskAuth extends EntityPathBase<TaskAuth> {

    private static final long serialVersionUID = 1108764916;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QTaskAuth taskAuth = new QTaskAuth("taskAuth");

    public final StringPath comcode = createString("comcode");

    public final StringPath flag = createString("flag");

    public final StringPath operateuser = createString("operateuser");

    public final ListPath<RoleTask, QRoletask> roletasks = this.<RoleTask, QRoletask>createList("roletasks", RoleTask.class, QRoletask.class);

    public final QTask task;

    public final StringPath taskauthid = createString("taskauthid");

    public QTaskAuth(String variable) {
        this(TaskAuth.class, forVariable(variable), INITS);
    }

    public QTaskAuth(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTaskAuth(PathMetadata<?> metadata, PathInits inits) {
        this(TaskAuth.class, metadata, inits);
    }

    public QTaskAuth(Class<? extends TaskAuth> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
    }

}

