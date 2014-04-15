package com.sinosoft.one.rms.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QUsergroup is a Querydsl query type for Usergroup
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUsergroup extends EntityPathBase<UserGroup> {

    private static final long serialVersionUID = 205589069;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QUsergroup usergroup = new QUsergroup("usergroup");

    public final QGroup group;

    public final StringPath isvalidate = createString("isvalidate");

    public final StringPath usercode = createString("usercode");

    public final StringPath usergropuid = createString("usergropuid");

    public final QUserpower userpower;

    public QUsergroup(String variable) {
        this(UserGroup.class, forVariable(variable), INITS);
    }

    public QUsergroup(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUsergroup(PathMetadata<?> metadata, PathInits inits) {
        this(UserGroup.class, metadata, inits);
    }

    public QUsergroup(Class<? extends UserGroup> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group")) : null;
        this.userpower = inits.isInitialized("userpower") ? new QUserpower(forProperty("userpower")) : null;
    }

}

