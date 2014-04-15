package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QGrouprole is a Querydsl query type for Grouprole
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QGrouprole extends EntityPathBase<GroupRole> {

    private static final long serialVersionUID = -1623361490;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QGrouprole grouprole = new QGrouprole("grouprole");

    public final QGroup group;

    public final StringPath grouproleid = createString("grouproleid");

    public final StringPath isvalidate = createString("isvalidate");

    public final QRole role;

    public QGrouprole(String variable) {
        this(GroupRole.class, forVariable(variable), INITS);
    }

    public QGrouprole(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGrouprole(PathMetadata<?> metadata, PathInits inits) {
        this(GroupRole.class, metadata, inits);
    }

    public QGrouprole(Class<? extends GroupRole> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role")) : null;
    }

}

