package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QTask is a Querydsl query type for Task
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QTask extends EntityPathBase<Task> {

    private static final long serialVersionUID = -1323725524;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QTask task1 = new QTask("task1");

    public final ListPath<BusPower, QBuspower> buspowers = this.<BusPower, QBuspower>createList("buspowers", BusPower.class, QBuspower.class);

    public final StringPath des = createString("des");

    public final ListPath<ExcPower, QExcpower> excpowers = this.<ExcPower, QExcpower>createList("excpowers", ExcPower.class, QExcpower.class);

    public final StringPath flag = createString("flag");

    public final StringPath isasmenu = createString("isasmenu");

    public final StringPath iscofigdatarule = createString("iscofigdatarule");

    public final StringPath isvalidate = createString("isvalidate");

    public final StringPath menuname = createString("menuname");

    public final StringPath menuurl = createString("menuurl");

    public final StringPath name = createString("name");

    public final StringPath sort = createString("sort");

    public final StringPath sysflag = createString("sysflag");

    public final QTask task;

    public final ListPath<TaskAuth, QTaskAuth> taskAuths = this.<TaskAuth, QTaskAuth>createList("taskAuths", TaskAuth.class, QTaskAuth.class);

    public final StringPath taskid = createString("taskid");

    public final ListPath<Task, QTask> tasks = this.<Task, QTask>createList("tasks", Task.class, QTask.class);

    public QTask(String variable) {
        this(Task.class, forVariable(variable), INITS);
    }

    public QTask(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QTask(PathMetadata<?> metadata, PathInits inits) {
        this(Task.class, metadata, inits);
    }

    public QTask(Class<? extends Task> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
    }

}

